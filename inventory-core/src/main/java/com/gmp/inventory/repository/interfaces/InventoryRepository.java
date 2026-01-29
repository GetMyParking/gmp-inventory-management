package com.gmp.inventory.repository.interfaces;

import com.gmp.inventory.persistence.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Inventory entity
 * 
 * @author Mrigank Tandon
 */

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>, InventoryCustomRepository {
    
    /**
     * Find inventory by ID, tenant, and not deleted
     */
    Optional<Inventory> findByIdAndTenantAndDeleted(Long id, String tenant, Integer deleted);
    
    /**
     * Find all active inventories by company ID
     */
    @Query("SELECT i FROM Inventory i WHERE i.companyId = :companyId AND i.tenant = :tenant AND i.deleted = 0")
    List<Inventory> findActiveByCompanyId(@Param("companyId") Integer companyId, @Param("tenant") String tenant);
    
    /**
     * Find all active inventories by branch ID
     */
    @Query("SELECT i FROM Inventory i WHERE i.branchId = :branchId AND i.tenant = :tenant AND i.deleted = 0")
    List<Inventory> findActiveByBranchId(@Param("branchId") Integer branchId, @Param("tenant") String tenant);
    
    /**
     * Find all active inventories by inventory location ID
     */
    @Query("SELECT i FROM Inventory i WHERE i.inventoryLocationId = :inventoryLocationId AND i.tenant = :tenant AND i.deleted = 0")
    List<Inventory> findActiveByInventoryLocationId(@Param("inventoryLocationId") Long inventoryLocationId, @Param("tenant") String tenant);
    
    /**
     * Find inventory by serial prefix and serial number
     */
    @Query("SELECT i FROM Inventory i WHERE i.serialPrefix = :serialPrefix AND i.serialNo = :serialNo AND i.tenant = :tenant AND i.deleted = 0")
    Optional<Inventory> findBySerialPrefixAndSerialNo(@Param("serialPrefix") String serialPrefix, 
                                                       @Param("serialNo") String serialNo, 
                                                       @Param("tenant") String tenant);
    
    /**
     * Find all inventories by status
     */
    @Query("SELECT i FROM Inventory i WHERE i.inventoryStatus = :status AND i.tenant = :tenant AND i.deleted = 0")
    List<Inventory> findByStatus(@Param("status") String status, @Param("tenant") String tenant);
    
    /**
     * Find all inventories by category
     */
    @Query("SELECT i FROM Inventory i WHERE i.category = :category AND i.tenant = :tenant AND i.deleted = 0")
    List<Inventory> findByCategory(@Param("category") String category, @Param("tenant") String tenant);
    
    /**
     * Find inventories by entity type and entity ID (polymorphic association)
     */
    @Query("SELECT i FROM Inventory i WHERE i.entityType = :entityType AND i.entityId = :entityId AND i.tenant = :tenant AND i.deleted = 0")
    List<Inventory> findByEntityTypeAndEntityId(@Param("entityType") String entityType, 
                                                  @Param("entityId") Long entityId, 
                                                  @Param("tenant") String tenant);
}
