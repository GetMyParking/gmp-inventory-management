package com.gmp.inventory.service.interfaces;

import com.gmp.inventory.api.Inventory;

import java.util.List;

/**
 * Service interface for Inventory operations
 * 
 * @author Mrigank Tandon
 */

public interface InventoryService {
    
    /**
     * Get all active inventories for a company
     * 
     * @param companyId Company ID
     * @param tenant Tenant identifier
     * @return List of inventory DTOs
     */
    List<Inventory> getAllInventories(Integer companyId, String tenant);
    
    /**
     * Get inventory by ID
     * 
     * @param id Inventory ID
     * @param tenant Tenant identifier
     * @return Inventory DTO
     */
    Inventory getInventoryById(Long id, String tenant);
    
    /**
     * Create a new inventory
     * 
     * @param inventory Inventory DTO
     * @param tenant Tenant identifier
     * @return Created inventory DTO
     */
    Inventory createInventory(Inventory inventory, String tenant);
    
    /**
     * Update an existing inventory
     * 
     * @param id Inventory ID
     * @param inventory Updated inventory DTO
     * @param tenant Tenant identifier
     * @return Updated inventory DTO
     */
    Inventory updateInventory(Long id, Inventory inventory, String tenant);
    
    /**
     * Soft delete an inventory
     * 
     * @param id Inventory ID
     * @param tenant Tenant identifier
     */
    void deleteInventory(Long id, String tenant);
    
    /**
     * Find inventories with filters
     * 
     * @param companyId Company ID filter (optional)
     * @param branchId Branch ID filter (optional)
     * @param category Category filter (optional)
     * @param status Status filter (optional)
     * @param tenant Tenant identifier
     * @return List of matching inventory DTOs
     */
    List<Inventory> findInventoriesWithFilters(Integer companyId, 
                                                Integer branchId, 
                                                String category, 
                                                String status, 
                                                String tenant);
    
    /**
     * Get inventories by branch ID
     * 
     * @param branchId Branch ID
     * @param tenant Tenant identifier
     * @return List of inventory DTOs
     */
    List<Inventory> getInventoriesByBranchId(Integer branchId, String tenant);
    
    /**
     * Get inventories by inventory location ID
     * 
     * @param inventoryLocationId Inventory location ID
     * @param tenant Tenant identifier
     * @return List of inventory DTOs
     */
    List<Inventory> getInventoriesByLocationId(Long inventoryLocationId, String tenant);
    
    /**
     * Get inventory by serial number
     * 
     * @param serialPrefix Serial prefix
     * @param serialNo Serial number
     * @param tenant Tenant identifier
     * @return Inventory DTO
     */
    Inventory getInventoryBySerialNumber(String serialPrefix, String serialNo, String tenant);
}
