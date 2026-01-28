package com.gmp.inventory.persistence.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity for inventory_parking_metadata table
 * Stores parking-specific metadata for inventory management
 * 
 * @author Mrigank Tandon
 */
@Entity
@Table(name = "inventory_parking_metadata", schema = "inventory_management")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class InventoryParkingMetadata extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "parking_id", nullable = false)
    private Integer parkingId;

    @Column(name = "lead_time")
    private Integer leadTime;

    @Column(name = "return_grace_time")
    private Integer returnGraceTime;

    @Column(name = "is_pick_up_available", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isPickUpAvailable;

    @Column(name = "is_mail_available", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isMailAvailable;

    @Column(name = "mail_lead_time")
    private Integer mailLeadTime;

    @Column(name = "mail_charges")
    private Integer mailCharges;

    @Column(name = "is_courier_available", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isCourierAvailable;

    @Column(name = "courier_lead_time")
    private Integer courierLeadTime;

    @Column(name = "courier_charges")
    private Integer courierCharges;
}
