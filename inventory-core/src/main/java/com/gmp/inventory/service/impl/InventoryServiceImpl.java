package com.gmp.inventory.service.impl;

import com.gmp.inventory.api.request.InventoryRequestDTO;
import com.gmp.inventory.api.response.InventoryResponseDTO;
import com.gmp.inventory.mapper.InventoryMapper;
import com.gmp.inventory.persistence.model.Inventory;
import com.gmp.inventory.repository.interfaces.InventoryRepository;
import com.gmp.inventory.service.interfaces.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of InventoryService.
 * DTOs in/out; uses mapper to convert to/from entities for repository calls.
 *
 * @author Mrigank Tandon
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public List<InventoryResponseDTO> getAllInventories(Integer companyId, String tenant) {
        log.debug("Fetching all active inventories for company: {}, tenant: {}", companyId, tenant);
        List<Inventory> entities = inventoryRepository.findActiveByCompanyId(companyId, tenant);
        return entities.stream()
                .map(inventoryMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryResponseDTO getInventoryById(Long id, String tenant) {
        log.debug("Fetching inventory by id: {}, tenant: {}", id, tenant);
        Inventory entity = inventoryRepository.findByIdAndTenantAndDeleted(id, tenant, 0)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id + " for tenant: " + tenant));
        return inventoryMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional
    public InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequest, String tenant) {
        log.info("Creating new inventory: {}, tenant: {}", inventoryRequest.getType(), tenant);
        Inventory entity = inventoryMapper.toEntity(inventoryRequest);
        entity.setTenant(tenant);
        entity.setDeleted(0);
        Inventory savedEntity = inventoryRepository.save(entity);
        return inventoryMapper.toResponseDTO(savedEntity);
    }

    @Override
    @Transactional
    public InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO inventoryRequest, String tenant) {
        log.info("Updating inventory with id: {}, tenant: {}", id, tenant);
        Inventory existingEntity = inventoryRepository.findByIdAndTenantAndDeleted(id, tenant, 0)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id + " for tenant: " + tenant));
        inventoryMapper.updateEntityFromRequest(existingEntity, inventoryRequest);
        Inventory updatedEntity = inventoryRepository.save(existingEntity);
        return inventoryMapper.toResponseDTO(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteInventory(Long id, String tenant) {
        log.info("Soft deleting inventory with id: {}, tenant: {}", id, tenant);
        Inventory entity = inventoryRepository.findByIdAndTenantAndDeleted(id, tenant, 0)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id + " for tenant: " + tenant));
        entity.setDeleted(1);
        inventoryRepository.save(entity);
    }

    @Override
    public List<InventoryResponseDTO> findInventoriesWithFilters(Integer companyId,
                                                               Integer branchId,
                                                               String category,
                                                               String status,
                                                               String tenant) {
        log.debug("Finding inventories with filters - companyId: {}, branchId: {}, category: {}, status: {}, tenant: {}",
                companyId, branchId, category, status, tenant);
        List<Inventory> entities = inventoryRepository.findInventoriesWithFilters(companyId, branchId, category, status, tenant);
        return entities.stream()
                .map(inventoryMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryResponseDTO> getInventoriesByBranchId(Integer branchId, String tenant) {
        log.debug("Fetching inventories by branchId: {}, tenant: {}", branchId, tenant);
        List<Inventory> entities = inventoryRepository.findActiveByBranchId(branchId, tenant);
        return entities.stream()
                .map(inventoryMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryResponseDTO> getInventoriesByLocationId(Long inventoryLocationId, String tenant) {
        log.debug("Fetching inventories by locationId: {}, tenant: {}", inventoryLocationId, tenant);
        List<Inventory> entities = inventoryRepository.findActiveByInventoryLocationId(inventoryLocationId, tenant);
        return entities.stream()
                .map(inventoryMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryResponseDTO getInventoryBySerialNumber(String serialPrefix, String serialNo, String tenant) {
        log.debug("Fetching inventory by serial - prefix: {}, serialNo: {}, tenant: {}", serialPrefix, serialNo, tenant);
        Inventory entity = inventoryRepository.findBySerialPrefixAndSerialNo(serialPrefix, serialNo, tenant)
                .orElseThrow(() -> new RuntimeException("Inventory not found with serial prefix: " + serialPrefix + " and serial no: " + serialNo));
        return inventoryMapper.toResponseDTO(entity);
    }
}
