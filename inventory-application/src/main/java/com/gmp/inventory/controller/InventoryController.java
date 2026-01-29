package com.gmp.inventory.controller;

import com.gmp.inventory.api.request.InventoryRequestDTO;
import com.gmp.inventory.api.response.InventoryResponseDTO;
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
 * REST controller for Inventory Management.
 *
 * @author Mrigank Tandon
 */
@RestController
@Slf4j
@RequestMapping("/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping(value = "/items", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryResponseDTO>> getAllInventories(
            @RequestParam Integer companyId,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryService.getAllInventories(companyId, tenantValue));
    }

    @GetMapping(value = "/items/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryResponseDTO> getInventoryById(
            @PathVariable Long id,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryService.getInventoryById(id, tenantValue));
    }

    @PostMapping(value = "/items", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryResponseDTO> createInventory(
            @RequestBody InventoryRequestDTO inventoryRequest,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryService.createInventory(inventoryRequest, tenantValue));
    }

    @PutMapping(value = "/items/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryResponseDTO> updateInventory(
            @PathVariable Long id,
            @RequestBody InventoryRequestDTO inventoryRequest,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryService.updateInventory(id, inventoryRequest, tenantValue));
    }

    @DeleteMapping(value = "/items/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> deleteInventory(
            @PathVariable Long id,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        inventoryService.deleteInventory(id, tenantValue);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/items/search", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryResponseDTO>> searchInventories(
            @RequestParam(required = false) Integer companyId,
            @RequestParam(required = false) Integer branchId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryService.findInventoriesWithFilters(companyId, branchId, category, status, tenantValue));
    }

    @GetMapping(value = "/items/branch/{branchId}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryResponseDTO>> getInventoriesByBranchId(
            @PathVariable Integer branchId,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryService.getInventoriesByBranchId(branchId, tenantValue));
    }

    @GetMapping(value = "/items/location/{locationId}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryResponseDTO>> getInventoriesByLocationId(
            @PathVariable Long locationId,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryService.getInventoriesByLocationId(locationId, tenantValue));
    }

    @GetMapping(value = "/items/serial", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryResponseDTO> getInventoryBySerialNumber(
            @RequestParam String serialPrefix,
            @RequestParam String serialNo,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryService.getInventoryBySerialNumber(serialPrefix, serialNo, tenantValue));
    }

    private String getTenant(String tenant) {
        if (tenant != null && !tenant.isEmpty()) {
            return tenant;
        }
        return MDC.get(TENANT);
    }
}
