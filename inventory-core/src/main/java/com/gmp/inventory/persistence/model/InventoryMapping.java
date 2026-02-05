package com.gmp.inventory.persistence.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity for inventory_mapping table
 * Maps serial prefixes and ranges to parking facilities
 *
 * @author Mrigank Tandon
 */
@Entity
@Table(name = "inventory_mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class InventoryMapping extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "serial_prefix", nullable = false, length = 50)
    private String serialPrefix;

    @Column(name = "parking_id")
    private Long parkingId;

    @Column(name = "serial_start")
    private Long serialStart;

    @Column(name = "serial_end")
    private Long serialEnd;
}
