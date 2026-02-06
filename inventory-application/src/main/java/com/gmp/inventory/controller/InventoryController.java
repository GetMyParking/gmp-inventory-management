package com.gmp.inventory.controller;

import com.gmp.inventory.api.request.GetDevicesByPermitIdsRequest;
import com.gmp.inventory.api.request.InventoryPricingRequestDTO;
import com.gmp.inventory.api.request.InventoryRequestDTO;
import com.gmp.inventory.api.request.PricingByPermitIdsRequest;
import com.gmp.inventory.api.response.InventoryPricingResponseDTO;
import com.gmp.inventory.api.response.InventoryResponseDTO;
import com.gmp.inventory.api.response.PricingByPermitIdsResponse;
import com.gmp.inventory.service.interfaces.InventoryPricingService;
import com.gmp.inventory.service.interfaces.InventoryService;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gmp.entities.request.RequestHeaders.HEADER_GMP_TENANT;

/**
 * REST controller for Inventory (items), Inventory Pricing and Inventory Fulfilment.
 */
@RestController
@Slf4j
@RequestMapping("/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final InventoryPricingService inventoryPricingService;

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
        return ResponseEntity.ok(inventoryService.getInventoryBySerialNumber(serialPrefix, serialNo, tenant));
    }

    // --- Inventory Pricing ---

    @GetMapping(value = "/pricing/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryPricingResponseDTO> getPricingById(
            @PathVariable Long id,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.ok(inventoryPricingService.getById(id, tenant));
    }

    @PostMapping(value = "/pricing", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryPricingResponseDTO> createPricing(
            @RequestBody InventoryPricingRequestDTO request,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryPricingService.create(request, tenant));
    }

    @PutMapping(value = "/pricing/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryPricingResponseDTO> updatePricing(
            @PathVariable Long id,
            @RequestBody InventoryPricingRequestDTO request,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.ok(inventoryPricingService.update(id, request, tenant));
    }

    @DeleteMapping(value = "/pricing/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> deletePricing(
            @PathVariable Long id,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        inventoryPricingService.delete(id, tenant);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/pricing/branch/{branchId}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryPricingResponseDTO>> getPricingByBranchId(
            @PathVariable Integer branchId,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.ok(inventoryPricingService.getByBranchId(branchId, tenant));
    }

    @GetMapping(value = "/pricing/parking/{parkingId}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryPricingResponseDTO>> getPricingByParkingId(
            @PathVariable Integer parkingId,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.ok(inventoryPricingService.getByParkingId(parkingId, tenant));
    }

    @GetMapping(value = "/pricing/permit-master/{permitMasterId}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryPricingResponseDTO>> getPricingByPermitMasterId(
            @PathVariable Long permitMasterId,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.ok(inventoryPricingService.getByPermitMasterId(permitMasterId, tenant));
    }

    @PostMapping(value = "/pricing/by-permit-ids", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<PricingByPermitIdsResponse> getPricingByPermitIds(
            @RequestBody @Valid PricingByPermitIdsRequest request,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.ok(inventoryPricingService.getPricingByPermitIds(request, tenant));
    }

    @PostMapping(value = "/pricing/devices-by-permit-ids", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryPricingResponseDTO>> getDevicesByPermitIds(
            @RequestBody @Valid GetDevicesByPermitIdsRequest request,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.ok(inventoryPricingService.getDevicesByPermitIds(request, tenant));
    }
}
