package com.gmp.inventory.persistence.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity for inventory_location_parking_map table
 * Maps inventory locations to parking facilities (many-to-many)
 * 
 * @author Mrigank Tandon
 */
@Entity
@Table(name = "inventory_location_parking_map", schema = "inventory_management")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class InventoryLocationParkingMap extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "inventory_location_id", nullable = false)
    private Long inventoryLocationId;

    @Column(name = "parking_id", nullable = false)
    private Integer parkingId;
}
