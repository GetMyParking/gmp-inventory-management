package com.gmp.inventory.service.impl;

import com.gmp.inventory.api.request.InventoryParkingMetadataRequestDTO;
import com.gmp.inventory.api.response.InventoryParkingMetadataResponseDTO;
import com.gmp.inventory.mapper.InventoryParkingMetadataMapper;
import com.gmp.inventory.persistence.model.InventoryParkingMetadata;
import com.gmp.inventory.repository.interfaces.InventoryParkingMetadataRepository;
import com.gmp.inventory.service.interfaces.InventoryParkingMetadataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of InventoryParkingMetadataService.
 *
 * @author Mrigank Tandon
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryParkingMetadataServiceImpl implements InventoryParkingMetadataService {

    private final InventoryParkingMetadataRepository repository;
    private final InventoryParkingMetadataMapper mapper;

    @Override
    public InventoryParkingMetadataResponseDTO getById(Long id, String tenant) {
        log.debug("Fetching inventory parking metadata by id: {}, tenant: {}", id, tenant);
        InventoryParkingMetadata entity = repository.findByIdAndTenantAndDeleted(id, tenant, 0)
                .orElseThrow(() -> new RuntimeException("Inventory parking metadata not found with id: " + id + " for tenant: " + tenant));
        return mapper.toResponseDTO(entity);
    }

    @Override
    @Transactional
    public InventoryParkingMetadataResponseDTO create(InventoryParkingMetadataRequestDTO request, String tenant) {
        log.info("Creating inventory parking metadata, tenant: {}", tenant);
        InventoryParkingMetadata entity = mapper.toEntity(request);
        entity.setTenant(tenant);
        entity.setDeleted(0);
        InventoryParkingMetadata saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    @Override
    public List<InventoryParkingMetadataResponseDTO> getByParkingId(Long parkingId, String tenant) {
        log.debug("Fetching inventory parking metadata by parkingId: {}, tenant: {}", parkingId, tenant);
        return repository.findActiveByParkingId(parkingId, tenant).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
