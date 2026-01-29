package com.gmp.inventory.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

/**
 * Base entity for all inventory management tables
 * Contains common fields: tenant, created_at, updated_at, deleted
 * 
 * @author Mrigank Tandon
 */
@MappedSuperclass
@Getter
@Setter
public class BaseEntity implements Serializable {

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    protected Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    protected Date updatedAt;

    @Column(name = "deleted", nullable = false, columnDefinition = "SMALLINT DEFAULT 0")
    protected Integer deleted = 0;

    @Column(name = "tenant", nullable = false, length = 255)
    private String tenant;
}
