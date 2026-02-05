package com.gmp.inventory.service.impl;

import com.gmp.inventory.api.request.InventoryLocationParkingMapRequestDTO;
import com.gmp.inventory.api.response.InventoryLocationParkingMapResponseDTO;
import com.gmp.inventory.mapper.InventoryLocationParkingMapMapper;
import com.gmp.inventory.persistence.model.InventoryLocationParkingMap;
import com.gmp.inventory.repository.interfaces.InventoryLocationParkingMapRepository;
import com.gmp.inventory.service.interfaces.InventoryLocationParkingMapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of InventoryLocationParkingMapService.
 *
 * @author Mrigank Tandon
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryLocationParkingMapServiceImpl implements InventoryLocationParkingMapService {

    private final InventoryLocationParkingMapRepository repository;
    private final InventoryLocationParkingMapMapper mapper;

    @Override
    public InventoryLocationParkingMapResponseDTO getById(Long id, String tenant) {
        log.debug("Fetching inventory location parking map by id: {}, tenant: {}", id, tenant);
        InventoryLocationParkingMap entity = repository.findByIdAndTenantAndDeleted(id, tenant, 0)
                .orElseThrow(() -> new RuntimeException("Inventory location parking map not found with id: " + id + " for tenant: " + tenant));
        return mapper.toResponseDTO(entity);
    }

    @Override
    @Transactional
    public InventoryLocationParkingMapResponseDTO create(InventoryLocationParkingMapRequestDTO request, String tenant) {
        log.info("Creating inventory location parking map, tenant: {}", tenant);
        InventoryLocationParkingMap entity = mapper.toEntity(request);
        entity.setTenant(tenant);
        entity.setDeleted(0);
        InventoryLocationParkingMap saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    @Override
    public List<InventoryLocationParkingMapResponseDTO> getByInventoryLocationId(Long inventoryLocationId, String tenant) {
        log.debug("Fetching maps by inventoryLocationId: {}, tenant: {}", inventoryLocationId, tenant);
        return repository.findActiveByInventoryLocationId(inventoryLocationId, tenant).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryLocationParkingMapResponseDTO> getByParkingId(Long parkingId, String tenant) {
        log.debug("Fetching maps by parkingId: {}, tenant: {}", parkingId, tenant);
        return repository.findActiveByParkingId(parkingId, tenant).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
