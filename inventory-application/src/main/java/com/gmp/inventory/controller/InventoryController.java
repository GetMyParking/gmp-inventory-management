package com.gmp.inventory.controller;

import com.gmp.inventory.api.Inventory;
import com.gmp.inventory.service.interfaces.InventoryService;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gmp.entities.request.RequestHeaders.HEADER_GMP_TENANT;
import static com.gmp.spring.constants.RequestHeaderConstants.TENANT;

/**
 * REST controller for Inventory Management
 * 
 * @author Mrigank Tandon
 */
@RestController
@Slf4j
@RequestMapping(value = "/v1/inventory", produces = MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class InventoryController {
    
    private final InventoryService inventoryService;
    
    @GetMapping("/items")
    public ResponseEntity<List<Inventory>> getAllInventories(
            @RequestParam Integer companyId,
            @RequestHeader(value = HEADER_GMP_TENANT, required = false) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryService.getAllInventories(companyId, tenantValue));
    }
    
    @GetMapping("/items/{id}")
    public ResponseEntity<Inventory> getInventoryById(
            @PathVariable Long id,
            @RequestHeader(value = HEADER_GMP_TENANT, required = false) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryService.getInventoryById(id, tenantValue));
    }
    
    @PostMapping("/items")
    public ResponseEntity<Inventory> createInventory(
            @RequestBody Inventory inventory,
            @RequestHeader(value = HEADER_GMP_TENANT, required = false) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryService.createInventory(inventory, tenantValue));
    }
    
    @PutMapping("/items/{id}")
    public ResponseEntity<Inventory> updateInventory(
            @PathVariable Long id,
            @RequestBody Inventory inventory,
            @RequestHeader(value = HEADER_GMP_TENANT, required = false) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryService.updateInventory(id, inventory, tenantValue));
    }
    
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteInventory(
            @PathVariable Long id,
            @RequestHeader(value = HEADER_GMP_TENANT, required = false) String tenant) {
        String tenantValue = getTenant(tenant);
        inventoryService.deleteInventory(id, tenantValue);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/items/search")
    public ResponseEntity<List<Inventory>> searchInventories(
            @RequestParam(required = false) Integer companyId,
            @RequestParam(required = false) Integer branchId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestHeader(value = HEADER_GMP_TENANT, required = false) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryService.findInventoriesWithFilters(companyId, branchId, category, status, tenantValue));
    }
    
    @GetMapping("/items/branch/{branchId}")
    public ResponseEntity<List<Inventory>> getInventoriesByBranchId(
            @PathVariable Integer branchId,
            @RequestHeader(value = HEADER_GMP_TENANT, required = false) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryService.getInventoriesByBranchId(branchId, tenantValue));
    }
    
    @GetMapping("/items/location/{locationId}")
    public ResponseEntity<List<Inventory>> getInventoriesByLocationId(
            @PathVariable Long locationId,
            @RequestHeader(value = HEADER_GMP_TENANT, required = false) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryService.getInventoriesByLocationId(locationId, tenantValue));
    }
    
    @GetMapping("/items/serial")
    public ResponseEntity<Inventory> getInventoryBySerialNumber(
            @RequestParam String serialPrefix,
            @RequestParam String serialNo,
            @RequestHeader(value = HEADER_GMP_TENANT, required = false) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryService.getInventoryBySerialNumber(serialPrefix, serialNo, tenantValue));
    }
    
    /**
     * Get tenant from header or MDC
     */
    private String getTenant(String tenant) {
        if (tenant != null && !tenant.isEmpty()) {
            return tenant;
        }
        return MDC.get(TENANT);
    }
}
