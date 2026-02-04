package com.gmp.inventory.controller;

import com.gmp.inventory.api.request.PricingByPermitIdsRequest;
import com.gmp.inventory.api.request.InventoryPricingRequestDTO;
import com.gmp.inventory.api.response.PricingByPermitIdsResponse;
import com.gmp.inventory.api.response.InventoryPricingResponseDTO;
import com.gmp.inventory.service.interfaces.InventoryPricingService;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import static com.gmp.entities.request.RequestHeaders.HEADER_GMP_TENANT;
import static com.gmp.spring.constants.RequestHeaderConstants.TENANT;

/**
 * REST controller for InventoryPricing CRUD.
 *
 * @author Mrigank Tandon
 */
@RestController
@Slf4j
@RequestMapping("/v1/inventory/pricing")
@RequiredArgsConstructor
public class InventoryPricingController {

    private final InventoryPricingService inventoryPricingService;

    @GetMapping(produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryPricingResponseDTO>> getAll(
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryPricingService.getAll(tenantValue));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryPricingResponseDTO> getById(
            @PathVariable Long id,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryPricingService.getById(id, tenantValue));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryPricingResponseDTO> create(
            @RequestBody InventoryPricingRequestDTO request,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryPricingService.create(request, tenantValue));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryPricingResponseDTO> update(
            @PathVariable Long id,
            @RequestBody InventoryPricingRequestDTO request,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryPricingService.update(id, request, tenantValue));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        inventoryPricingService.delete(id, tenantValue);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/branch/{branchId}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryPricingResponseDTO>> getByBranchId(
            @PathVariable Integer branchId,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryPricingService.getByBranchId(branchId, tenantValue));
    }

    @GetMapping(value = "/parking/{parkingId}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryPricingResponseDTO>> getByParkingId(
            @PathVariable Integer parkingId,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryPricingService.getByParkingId(parkingId, tenantValue));
    }

    @GetMapping(value = "/permit-master/{permitMasterId}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryPricingResponseDTO>> getByPermitMasterId(
            @PathVariable Long permitMasterId,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryPricingService.getByPermitMasterId(permitMasterId, tenantValue));
    }

    @PostMapping(value = "/by-permit-ids", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<PricingByPermitIdsResponse> getPricingByPermitIds(
            @RequestBody @Valid PricingByPermitIdsRequest request,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        String tenantValue = getTenant(tenant);
        return ResponseEntity.ok(inventoryPricingService.getPricingByPermitIds(request, tenantValue));
    }

    private String getTenant(String tenant) {
        if (tenant != null && !tenant.isEmpty()) {
            return tenant;
        }
        return MDC.get(TENANT);
    }
}
