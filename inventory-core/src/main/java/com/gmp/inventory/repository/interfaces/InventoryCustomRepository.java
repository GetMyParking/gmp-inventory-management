package com.gmp.inventory.repository.interfaces;

import com.gmp.inventory.persistence.model.Inventory;

import java.util.List;

/**
 * Custom repository interface for Inventory with complex queries
 * 
 * @author Mrigank Tandon
 */

public interface InventoryCustomRepository {
    
    /**
     * Find inventories with filters
     * 
     * @param companyId Company ID filter
     * @param branchId Branch ID filter
     * @param category Category filter
     * @param status Status filter
     * @param tenant Tenant identifier
     * @return List of matching inventories
     */
    List<Inventory> findInventoriesWithFilters(Integer companyId, 
                                               Integer branchId, 
                                               String category, 
                                               String status, 
                                               String tenant);
}
