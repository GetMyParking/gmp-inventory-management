package com.gmp.inventory.repository.interfaces;

import com.gmp.inventory.persistence.model.InventoryLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for InventoryLocation entity.
 *
 * @author Mrigank Tandon
 */
@Repository
public interface InventoryLocationRepository extends JpaRepository<InventoryLocation, Long> {

    Optional<InventoryLocation> findByIdAndTenantAndDeleted(Long id, String tenant, Integer deleted);

    @Query("SELECT l FROM InventoryLocation l WHERE l.branchId = :branchId AND l.tenant = :tenant AND l.deleted = 0")
    List<InventoryLocation> findActiveByBranchId(@Param("branchId") Long branchId, @Param("tenant") String tenant);

    @Query("SELECT l FROM InventoryLocation l WHERE l.id IN :ids AND l.tenant = :tenant AND l.deleted = 0")
    List<InventoryLocation> findByIdInAndTenantAndDeleted(@Param("ids") List<Long> ids, @Param("tenant") String tenant);
}
