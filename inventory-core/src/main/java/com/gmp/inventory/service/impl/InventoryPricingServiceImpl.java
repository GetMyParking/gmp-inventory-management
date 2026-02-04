package com.gmp.inventory.service.impl;

import com.gmp.inventory.api.request.PricingByPermitIdsRequest;
import com.gmp.inventory.api.request.InventoryPricingRequestDTO;
import com.gmp.inventory.api.request.PricingByPermitIdsRequest;
import com.gmp.inventory.api.response.PricingByPermitIdsResponse;
import com.gmp.inventory.api.response.InventoryPricingResponseDTO;
import com.gmp.inventory.api.response.ParkingPricingGroupDTO;
import com.gmp.inventory.api.response.PermitPricingGroupDTO;
import com.gmp.inventory.api.response.PricingByPermitIdsResponse;
import com.gmp.inventory.mapper.InventoryPricingMapper;
import com.gmp.inventory.persistence.model.InventoryPricing;
import com.gmp.inventory.repository.interfaces.InventoryPricingRepository;
import com.gmp.inventory.service.interfaces.InventoryPricingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementation of InventoryPricingService.
 *
 * @author Mrigank Tandon
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryPricingServiceImpl implements InventoryPricingService {

    private final InventoryPricingRepository inventoryPricingRepository;
    private final InventoryPricingMapper inventoryPricingMapper;

    @Override
    public List<InventoryPricingResponseDTO> getAll(String tenant) {
        log.debug("Fetching all active inventory pricing for tenant: {}", tenant);
        return inventoryPricingRepository.findAllActiveByTenant(tenant).stream()
                .map(inventoryPricingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryPricingResponseDTO getById(Long id, String tenant) {
        log.debug("Fetching inventory pricing by id: {}, tenant: {}", id, tenant);
        InventoryPricing entity = inventoryPricingRepository.findByIdAndTenantAndDeleted(id, tenant, 0)
                .orElseThrow(() -> new RuntimeException("Inventory pricing not found with id: " + id + " for tenant: " + tenant));
        return inventoryPricingMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional
    public InventoryPricingResponseDTO create(InventoryPricingRequestDTO request, String tenant) {
        log.info("Creating inventory pricing, tenant: {}", tenant);
        InventoryPricing entity = inventoryPricingMapper.toEntity(request);
        entity.setTenant(tenant);
        entity.setDeleted(0);
        InventoryPricing saved = inventoryPricingRepository.save(entity);
        return inventoryPricingMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public InventoryPricingResponseDTO update(Long id, InventoryPricingRequestDTO request, String tenant) {
        log.info("Updating inventory pricing id: {}, tenant: {}", id, tenant);
        InventoryPricing existing = inventoryPricingRepository.findByIdAndTenantAndDeleted(id, tenant, 0)
                .orElseThrow(() -> new RuntimeException("Inventory pricing not found with id: " + id + " for tenant: " + tenant));
        inventoryPricingMapper.updateEntityFromRequest(existing, request);
        InventoryPricing updated = inventoryPricingRepository.save(existing);
        return inventoryPricingMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(Long id, String tenant) {
        log.info("Soft deleting inventory pricing id: {}, tenant: {}", id, tenant);
        InventoryPricing entity = inventoryPricingRepository.findByIdAndTenantAndDeleted(id, tenant, 0)
                .orElseThrow(() -> new RuntimeException("Inventory pricing not found with id: " + id + " for tenant: " + tenant));
        entity.setDeleted(1);
        inventoryPricingRepository.save(entity);
    }

    @Override
    public List<InventoryPricingResponseDTO> getByBranchId(Integer branchId, String tenant) {
        log.debug("Fetching inventory pricing by branchId: {}, tenant: {}", branchId, tenant);
        return inventoryPricingRepository.findActiveByBranchId(branchId, tenant).stream()
                .map(inventoryPricingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryPricingResponseDTO> getByParkingId(Integer parkingId, String tenant) {
        log.debug("Fetching inventory pricing by parkingId: {}, tenant: {}", parkingId, tenant);
        return inventoryPricingRepository.findActiveByParkingId(parkingId, tenant).stream()
                .map(inventoryPricingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryPricingResponseDTO> getByPermitMasterId(Long permitMasterId, String tenant) {
        log.debug("Fetching inventory pricing by permitMasterId: {}, tenant: {}", permitMasterId, tenant);
        return inventoryPricingRepository.findActiveByPermitMasterId(permitMasterId, tenant).stream()
                .map(inventoryPricingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PricingByPermitIdsResponse getPricingByPermitIds(PricingByPermitIdsRequest request, String tenant) {
        List<Long> permitMasterIds = request.getPermitMasterIds();
        if (permitMasterIds == null || permitMasterIds.isEmpty()) {
            return PricingByPermitIdsResponse.builder()
                    .pricingByPermitId(Collections.emptyList())
                    .build();
        }
        log.debug("Fetching pricing by permit master ids: {}, tenant: {}", permitMasterIds, tenant);
        List<InventoryPricing> entities = inventoryPricingRepository.findActiveByPermitMasterIdIn(permitMasterIds, tenant);

        // Group by permitMasterId first, then by parkingId within each permit
        Map<Long, Map<Long, List<InventoryPricingResponseDTO>>> byPermitThenParking = new LinkedHashMap<>();
        for (InventoryPricing entity : entities) {
            Long permitId = entity.getPermitMasterId();
            Long parkingIdBoxed = entity.getParkingId();
            if (permitId == null || parkingIdBoxed == null) {
                continue;
            }
            Long parkingId = parkingIdBoxed.longValue();
            byPermitThenParking
                    .computeIfAbsent(permitId, k -> new LinkedHashMap<>())
                    .computeIfAbsent(parkingId, k -> new ArrayList<>())
                    .add(inventoryPricingMapper.toResponseDTO(entity));
        }

        // Preserve order of permit IDs as in the request
        List<PermitPricingGroupDTO> pricingByPermitId = permitMasterIds.stream()
                .map(permitId -> {
                    Map<Long, List<InventoryPricingResponseDTO>> parkingMap = byPermitThenParking.getOrDefault(permitId, Collections.emptyMap());
                    List<ParkingPricingGroupDTO> pricingByParking = parkingMap.entrySet().stream()
                            .map(e -> ParkingPricingGroupDTO.builder()
                                    .parkingId(e.getKey())
                                    .devices(e.getValue())
                                    .build())
                            .collect(Collectors.toList());
                    return PermitPricingGroupDTO.builder()
                            .permitMasterId(permitId)
                            .pricingByParking(pricingByParking)
                            .build();
                })
                .collect(Collectors.toList());

        return PricingByPermitIdsResponse.builder()
                .pricingByPermitId(pricingByPermitId)
                .build();
    }
}
