package com.gmp.inventory.persistence.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity for inventory table
 * Core inventory item entity tracking physical inventory items
 * 
 * @author Mrigank Tandon
 */
@Entity
@Table(name = "inventory", schema = "inventory_management")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Inventory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "type", length = 100)
    private String type;

    @Column(name = "branch_id")
    private Integer branchId;

    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "serial_prefix", length = 50)
    private String serialPrefix;

    @Column(name = "serial_no", length = 100)
    private String serialNo;

    @Column(name = "inventory_location_id")
    private Long inventoryLocationId;

    @Column(name = "inventory_status", nullable = false, length = 50)
    private String inventoryStatus;

    @Column(name = "entity_type", length = 50)
    private String entityType;

    @Column(name = "entity_id")
    private Long entityId;
}
