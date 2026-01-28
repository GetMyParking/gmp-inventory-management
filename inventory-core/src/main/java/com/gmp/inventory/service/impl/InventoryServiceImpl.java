package com.gmp.inventory.service.impl;

import com.gmp.inventory.api.Inventory;
import com.gmp.inventory.repository.interfaces.InventoryRepository;
import com.gmp.inventory.service.interfaces.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of InventoryService
 * 
 * @author Mrigank Tandon
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    
    @Override
    public List<Inventory> getAllInventories(Integer companyId, String tenant) {
        log.debug("Fetching all active inventories for company: {}, tenant: {}", companyId, tenant);
        List<com.gmp.inventory.persistence.model.Inventory> entities = inventoryRepository.findActiveByCompanyId(companyId, tenant);
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public Inventory getInventoryById(Long id, String tenant) {
        log.debug("Fetching inventory by id: {}, tenant: {}", id, tenant);
        com.gmp.inventory.persistence.model.Inventory entity = inventoryRepository.findByIdAndTenantAndDeleted(id, tenant, 0)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id + " for tenant: " + tenant));
        return convertToDTO(entity);
    }
    
    @Override
    @Transactional
    public Inventory createInventory(Inventory inventory, String tenant) {
        log.info("Creating new inventory: {}, tenant: {}", inventory.getType(), tenant);
        com.gmp.inventory.persistence.model.Inventory entity = convertToEntity(inventory);
        entity.setTenant(tenant);
        entity.setDeleted(0);
        com.gmp.inventory.persistence.model.Inventory savedEntity = inventoryRepository.save(entity);
        return convertToDTO(savedEntity);
    }
    
    @Override
    @Transactional
    public Inventory updateInventory(Long id, Inventory inventory, String tenant) {
        log.info("Updating inventory with id: {}, tenant: {}", id, tenant);
        com.gmp.inventory.persistence.model.Inventory existingEntity = inventoryRepository.findByIdAndTenantAndDeleted(id, tenant, 0)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id + " for tenant: " + tenant));
        
        // Update fields
        existingEntity.setType(inventory.getType());
        existingEntity.setBranchId(inventory.getBranchId());
        existingEntity.setCompanyId(inventory.getCompanyId());
        existingEntity.setCategory(inventory.getCategory());
        existingEntity.setSerialPrefix(inventory.getSerialPrefix());
        existingEntity.setSerialNo(inventory.getSerialNo());
        existingEntity.setInventoryLocationId(inventory.getInventoryLocationId() != null ? inventory.getInventoryLocationId().longValue() : null);
        existingEntity.setInventoryStatus(inventory.getInventoryStatus());
        existingEntity.setEntityType(inventory.getEntityType());
        existingEntity.setEntityId(inventory.getEntityId() != null ? inventory.getEntityId().longValue() : null);
        
        com.gmp.inventory.persistence.model.Inventory updatedEntity = inventoryRepository.save(existingEntity);
        return convertToDTO(updatedEntity);
    }
    
    @Override
    @Transactional
    public void deleteInventory(Long id, String tenant) {
        log.info("Soft deleting inventory with id: {}, tenant: {}", id, tenant);
        com.gmp.inventory.persistence.model.Inventory entity = inventoryRepository.findByIdAndTenantAndDeleted(id, tenant, 0)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id + " for tenant: " + tenant));
        entity.setDeleted(1);
        inventoryRepository.save(entity);
    }
    
    @Override
    public List<Inventory> findInventoriesWithFilters(Integer companyId, 
                                                       Integer branchId, 
                                                       String category, 
                                                       String status, 
                                                       String tenant) {
        log.debug("Finding inventories with filters - companyId: {}, branchId: {}, category: {}, status: {}, tenant: {}", 
                  companyId, branchId, category, status, tenant);
        List<com.gmp.inventory.persistence.model.Inventory> entities = inventoryRepository.findInventoriesWithFilters(companyId, branchId, category, status, tenant);
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Inventory> getInventoriesByBranchId(Integer branchId, String tenant) {
        log.debug("Fetching inventories by branchId: {}, tenant: {}", branchId, tenant);
        List<com.gmp.inventory.persistence.model.Inventory> entities = inventoryRepository.findActiveByBranchId(branchId, tenant);
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Inventory> getInventoriesByLocationId(Long inventoryLocationId, String tenant) {
        log.debug("Fetching inventories by locationId: {}, tenant: {}", inventoryLocationId, tenant);
        List<com.gmp.inventory.persistence.model.Inventory> entities = inventoryRepository.findActiveByInventoryLocationId(inventoryLocationId, tenant);
        return entities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public Inventory getInventoryBySerialNumber(String serialPrefix, String serialNo, String tenant) {
        log.debug("Fetching inventory by serial - prefix: {}, serialNo: {}, tenant: {}", serialPrefix, serialNo, tenant);
        com.gmp.inventory.persistence.model.Inventory entity = inventoryRepository.findBySerialPrefixAndSerialNo(serialPrefix, serialNo, tenant)
                .orElseThrow(() -> new RuntimeException("Inventory not found with serial prefix: " + serialPrefix + " and serial no: " + serialNo));
        return convertToDTO(entity);
    }
    
    /**
     * Convert entity to DTO
     */
    private Inventory convertToDTO(com.gmp.inventory.persistence.model.Inventory entity) {
        Inventory dto = modelMapper.map(entity, Inventory.class);
        dto.setId(entity.getId().intValue());
        dto.setInventoryLocationId(entity.getInventoryLocationId() != null ? entity.getInventoryLocationId().intValue() : null);
        dto.setEntityId(entity.getEntityId() != null ? entity.getEntityId().intValue() : null);
        return dto;
    }
    
    /**
     * Convert DTO to entity
     */
    private com.gmp.inventory.persistence.model.Inventory convertToEntity(Inventory dto) {
        com.gmp.inventory.persistence.model.Inventory entity = modelMapper.map(dto, com.gmp.inventory.persistence.model.Inventory.class);
        if (dto.getId() != null) {
            entity.setId(dto.getId().longValue());
        }
        if (dto.getInventoryLocationId() != null) {
            entity.setInventoryLocationId(dto.getInventoryLocationId().longValue());
        }
        if (dto.getEntityId() != null) {
            entity.setEntityId(dto.getEntityId().longValue());
        }
        return entity;
    }
}
