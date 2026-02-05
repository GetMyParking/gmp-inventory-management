package com.gmp.inventory.repository.interfaces;

import com.gmp.inventory.persistence.model.InventoryParkingMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for InventoryParkingMetadata entity.
 *
 * @author Mrigank Tandon
 */
@Repository
public interface InventoryParkingMetadataRepository extends JpaRepository<InventoryParkingMetadata, Long> {

    Optional<InventoryParkingMetadata> findByIdAndTenantAndDeleted(Long id, String tenant, Integer deleted);

    @Query("SELECT m FROM InventoryParkingMetadata m WHERE m.parkingId = :parkingId AND m.tenant = :tenant AND m.deleted = 0")
    List<InventoryParkingMetadata> findActiveByParkingId(@Param("parkingId") Long parkingId, @Param("tenant") String tenant);

    @Query("SELECT m FROM InventoryParkingMetadata m WHERE m.parkingId IN :parkingIds AND m.tenant = :tenant AND m.deleted = 0")
    List<InventoryParkingMetadata> findActiveByParkingIdIn(@Param("parkingIds") List<Long> parkingIds, @Param("tenant") String tenant);
}
