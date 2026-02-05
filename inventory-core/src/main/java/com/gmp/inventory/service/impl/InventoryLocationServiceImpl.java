package com.gmp.inventory.service.impl;

import com.gmp.inventory.api.request.InventoryLocationRequestDTO;
import com.gmp.inventory.api.response.InventoryLocationResponseDTO;
import com.gmp.inventory.mapper.InventoryLocationMapper;
import com.gmp.inventory.persistence.model.InventoryLocation;
import com.gmp.inventory.repository.interfaces.InventoryLocationRepository;
import com.gmp.inventory.service.interfaces.InventoryLocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of InventoryLocationService.
 *
 * @author Mrigank Tandon
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryLocationServiceImpl implements InventoryLocationService {

    private final InventoryLocationRepository inventoryLocationRepository;
    private final InventoryLocationMapper inventoryLocationMapper;

    @Override
    public InventoryLocationResponseDTO getById(Long id, String tenant) {
        log.debug("Fetching inventory location by id: {}, tenant: {}", id, tenant);
        InventoryLocation entity = inventoryLocationRepository.findByIdAndTenantAndDeleted(id, tenant, 0)
                .orElseThrow(() -> new RuntimeException("Inventory location not found with id: " + id + " for tenant: " + tenant));
        return inventoryLocationMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional
    public InventoryLocationResponseDTO create(InventoryLocationRequestDTO request, String tenant) {
        log.info("Creating inventory location, tenant: {}", tenant);
        InventoryLocation entity = inventoryLocationMapper.toEntity(request);
        entity.setTenant(tenant);
        entity.setDeleted(0);
        InventoryLocation saved = inventoryLocationRepository.save(entity);
        return inventoryLocationMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public InventoryLocationResponseDTO update(Long id, InventoryLocationRequestDTO request, String tenant) {
        log.info("Updating inventory location id: {}, tenant: {}", id, tenant);
        InventoryLocation existing = inventoryLocationRepository.findByIdAndTenantAndDeleted(id, tenant, 0)
                .orElseThrow(() -> new RuntimeException("Inventory location not found with id: " + id + " for tenant: " + tenant));
        inventoryLocationMapper.updateEntityFromRequest(existing, request);
        InventoryLocation updated = inventoryLocationRepository.save(existing);
        return inventoryLocationMapper.toResponseDTO(updated);
    }

    @Override
    public List<InventoryLocationResponseDTO> getByBranchId(Long branchId, String tenant) {
        log.debug("Fetching inventory locations by branchId: {}, tenant: {}", branchId, tenant);
        return inventoryLocationRepository.findActiveByBranchId(branchId, tenant).stream()
                .map(inventoryLocationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
