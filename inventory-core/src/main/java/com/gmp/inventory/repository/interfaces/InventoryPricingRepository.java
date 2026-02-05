package com.gmp.inventory.repository.interfaces;

import com.gmp.inventory.persistence.model.InventoryPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for InventoryPricing entity.
 *
 * @author Mrigank Tandon
 */
@Repository
public interface InventoryPricingRepository extends JpaRepository<InventoryPricing, Long> {

    Optional<InventoryPricing> findByIdAndTenantAndDeleted(Long id, String tenant, Integer deleted);

    @Query("SELECT p FROM InventoryPricing p WHERE p.branchId = :branchId AND p.tenant = :tenant AND p.deleted = 0")
    List<InventoryPricing> findActiveByBranchId(@Param("branchId") Integer branchId, @Param("tenant") String tenant);

    @Query("SELECT p FROM InventoryPricing p WHERE p.parkingId = :parkingId AND p.tenant = :tenant AND p.deleted = 0")
    List<InventoryPricing> findActiveByParkingId(@Param("parkingId") Integer parkingId, @Param("tenant") String tenant);

    @Query("SELECT p FROM InventoryPricing p WHERE p.permitMasterId = :permitMasterId AND p.tenant = :tenant AND p.deleted = 0")
    List<InventoryPricing> findActiveByPermitMasterId(@Param("permitMasterId") Long permitMasterId, @Param("tenant") String tenant);

    @Query("SELECT p FROM InventoryPricing p WHERE p.permitMasterId IN :permitMasterIds AND p.tenant = :tenant AND p.deleted = 0")
    List<InventoryPricing> findActiveByPermitMasterIdIn(@Param("permitMasterIds") List<Long> permitMasterIds, @Param("tenant") String tenant);
}
