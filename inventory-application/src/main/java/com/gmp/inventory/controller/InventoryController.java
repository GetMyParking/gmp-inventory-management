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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gmp.entities.request.RequestHeaders.HEADER_GMP_TENANT;

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
        return ResponseEntity.ok(inventoryService.getInventoryBySerialNumber(serialPrefix, serialNo, tenant));
    }


    @GetMapping(value = "/locations/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryLocationResponseDTO> locationById(
            @PathVariable Long id,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.ok(inventoryLocationService.getById(id, tenant));
    }

    @PostMapping(value = "/locations", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryLocationResponseDTO> createLocation(
            @RequestBody InventoryLocationRequestDTO request,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryLocationService.create(request, tenant));
    }

    @PutMapping(value = "/locations/{id}", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryLocationResponseDTO> updateLocation(
            @PathVariable Long id,
            @RequestBody InventoryLocationRequestDTO request,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.ok(inventoryLocationService.update(id, request, tenant));
    }

    @GetMapping(value = "/locations/branch/{branchId}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryLocationResponseDTO>> locationsByBranchId(
            @PathVariable Long branchId,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.ok(inventoryLocationService.getByBranchId(branchId, tenant));
    }

    @GetMapping(value = "/parking-metadata/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryParkingMetadataResponseDTO> parkingMetadataById(
            @PathVariable Long id,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.ok(inventoryParkingMetadataService.getById(id, tenant));
    }

    @PostMapping(value = "/parking-metadata", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryParkingMetadataResponseDTO> createParkingMetadata(
            @RequestBody InventoryParkingMetadataRequestDTO request,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryParkingMetadataService.create(request, tenant));
    }

    @GetMapping(value = "/parking-metadata/parking/{parkingId}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryParkingMetadataResponseDTO>> parkingMetadataByParkingId(
            @PathVariable Long parkingId,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.ok(inventoryParkingMetadataService.getByParkingId(parkingId, tenant));
    }

    @GetMapping(value = "/location-parking-maps/{id}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryLocationParkingMapResponseDTO> locationParkingMapById(
            @PathVariable Long id,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.ok(inventoryLocationParkingMapService.getById(id, tenant));
    }

    @PostMapping(value = "/location-parking-maps", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<InventoryLocationParkingMapResponseDTO> createLocationParkingMap(
            @RequestBody InventoryLocationParkingMapRequestDTO request,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inventoryLocationParkingMapService.create(request, tenant));
    }

    @GetMapping(value = "/location-parking-maps/location/{inventoryLocationId}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryLocationParkingMapResponseDTO>> locationParkingMapsByLocationId(
            @PathVariable Long inventoryLocationId,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.ok(inventoryLocationParkingMapService.getByInventoryLocationId(inventoryLocationId, tenant));
    }

    @GetMapping(value = "/location-parking-maps/parking/{parkingId}", produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<List<InventoryLocationParkingMapResponseDTO>> locationParkingMapsByParkingId(
            @PathVariable Long parkingId,
            @RequestHeader(value = HEADER_GMP_TENANT) String tenant) {
        return ResponseEntity.ok(inventoryLocationParkingMapService.getByParkingId(parkingId, tenant));
    }
}
