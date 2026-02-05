package com.gmp.inventory.controller;

import com.gmp.inventory.api.request.InventoryLocationParkingMapRequestDTO;
import com.gmp.inventory.api.request.InventoryLocationRequestDTO;
import com.gmp.inventory.api.request.InventoryParkingMetadataRequestDTO;
import com.gmp.inventory.api.request.InventoryRequestDTO;
import com.gmp.inventory.api.response.InventoryLocationParkingMapResponseDTO;
import com.gmp.inventory.api.response.InventoryLocationResponseDTO;
import com.gmp.inventory.api.response.InventoryParkingMetadataResponseDTO;
import com.gmp.inventory.api.response.InventoryResponseDTO;
import com.gmp.inventory.service.interfaces.InventoryLocationParkingMapService;
import com.gmp.inventory.service.interfaces.InventoryLocationService;
import com.gmp.inventory.service.interfaces.InventoryParkingMetadataService;
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
    private final InventoryLocationService inventoryLocationService;
    private final InventoryParkingMetadataService inventoryParkingMetadataService;
    private final InventoryLocationParkingMapService inventoryLocationParkingMapService;

    @GetMapping(value = "/items", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryResponseDTO>> allInventories(
            @RequestParam Integer companyId,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {

        return ResponseEntity.ok(inventoryService.getAllInventories(companyId, tenant));
    }

    @GetMapping(value = "/items/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryResponseDTO> InventoryById(
            @PathVariable Long id,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {

        return ResponseEntity.ok(inventoryService.getInventoryById(id, tenant));
    }

    @PostMapping(value = "/items", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryResponseDTO> createInventory(
            @RequestBody InventoryRequestDTO inventoryRequest,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryService.createInventory(inventoryRequest, tenant));
    }

    @PutMapping(value = "/items/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryResponseDTO> updateInventory(
            @PathVariable Long id,
            @RequestBody InventoryRequestDTO inventoryRequest,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {

        return ResponseEntity.ok(inventoryService.updateInventory(id, inventoryRequest, tenant));
    }

    @DeleteMapping(value = "/items/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> deleteInventory(
            @PathVariable Long id,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {

        inventoryService.deleteInventory(id, tenant);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/items/search", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryResponseDTO>> searchInventories(
            @RequestParam(required = false) Integer companyId,
            @RequestParam(required = false) Integer branchId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {

        return ResponseEntity.ok(inventoryService.findInventoriesWithFilters(companyId, branchId, category, status, tenant));
    }

    @GetMapping(value = "/items/branch/{branchId}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryResponseDTO>> inventoriesByBranchId(
            @PathVariable Integer branchId,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {

        return ResponseEntity.ok(inventoryService.getInventoriesByBranchId(branchId, tenant));
    }

    @GetMapping(value = "/items/location/{locationId}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryResponseDTO>> inventoriesByLocationId(
            @PathVariable Long locationId,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {

        return ResponseEntity.ok(inventoryService.getInventoriesByLocationId(locationId, tenant));
    }

    @GetMapping(value = "/items/serial", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryResponseDTO> inventoryBySerialNumber(
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
