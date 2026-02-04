package com.gmp.inventory.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

/**
 * Entity for inventory_location table
 * 
 * @author Mrigank Tandon
 */
@Entity
@Table(name = "inventory_location")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class InventoryLocation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "geolocation", columnDefinition = "POINT")
    private Point geolocation;

    @Column(name = "metadata", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> metadata;

    @Column(name = "parking_id")
    private Long parkingId;
}
