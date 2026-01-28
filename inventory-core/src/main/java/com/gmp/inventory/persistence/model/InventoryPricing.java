package com.gmp.inventory.persistence.model;

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
    private Integer branchId;

    @Column(name = "parking_id")
    private Integer parkingId;

    @Column(name = "permit_master_id")
    private Long permitMasterId;

    @Column(name = "activation_fee")
    private Integer activationFee;

    @Column(name = "deposit_fee")
    private Integer depositFee;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "supported", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean supported;
}
