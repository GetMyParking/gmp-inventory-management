package com.gmp.inventory.persistence.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity for inventory_fulfilment table
 * Tracks inventory fulfilment and shipping information
 * 
 * @author Mrigank Tandon
 */
@Entity
@Table(name = "inventory_fulfilment", schema = "inventory_management")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class InventoryFulfilment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "tracking_id", length = 255)
    private String trackingId;

    @Column(name = "method", length = 50)
    private String method;

    @Column(name = "inventory_location_id")
    private Long inventoryLocationId;

    @Column(name = "shipment_address", length = 500)
    private String shipmentAddress;
}
