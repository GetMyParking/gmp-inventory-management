package com.gmp.inventory.persistence.model;

import com.gmp.inventory.api.enums.InventoryCategory;
import com.gmp.inventory.api.enums.InventoryPricingLevel;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity for inventory_pricing table
 * Manages pricing information for inventory items
 * 
 * @author Mrigank Tandon
 */
@Entity
@Table(name = "inventory_pricing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class InventoryPricing extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "type", length = 100)
    private String type;

    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "parking_id")
    private Long parkingId;

    @Column(name = "permit_master_id")
    private Long permitMasterId;

    @Column(name = "activation_fee")
    private Long activationFeeInCents;

    @Column(name = "deposit_fee")
    private Long depositFeeInCents;

    @Column(name = "category", length = 50)
    @Enumerated(EnumType.STRING)
    private InventoryCategory category;

    @Column(name = "level", length = 50)
    @Enumerated(EnumType.STRING)
    private InventoryPricingLevel level;

}
