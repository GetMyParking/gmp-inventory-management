package com.gmp.inventory.repository.interfaces;

import com.gmp.inventory.persistence.model.InventoryLocationParkingMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for InventoryLocationParkingMap entity.
 *
 * @author Mrigank Tandon
 */
@Repository
public interface InventoryLocationParkingMapRepository extends JpaRepository<InventoryLocationParkingMap, Long> {

    Optional<InventoryLocationParkingMap> findByIdAndTenantAndDeleted(Long id, String tenant, Integer deleted);

    @Query("SELECT m FROM InventoryLocationParkingMap m WHERE m.inventoryLocationId = :inventoryLocationId AND m.tenant = :tenant AND m.deleted = 0")
    List<InventoryLocationParkingMap> findActiveByInventoryLocationId(@Param("inventoryLocationId") Long inventoryLocationId, @Param("tenant") String tenant);

    @Query("SELECT m FROM InventoryLocationParkingMap m WHERE m.parkingId = :parkingId AND m.tenant = :tenant AND m.deleted = 0")
    List<InventoryLocationParkingMap> findActiveByParkingId(@Param("parkingId") Long parkingId, @Param("tenant") String tenant);

    @Query("SELECT m FROM InventoryLocationParkingMap m WHERE m.parkingId IN :parkingIds AND m.tenant = :tenant AND m.deleted = 0")
    List<InventoryLocationParkingMap> findActiveByParkingIdIn(@Param("parkingIds") List<Long> parkingIds, @Param("tenant") String tenant);
}
