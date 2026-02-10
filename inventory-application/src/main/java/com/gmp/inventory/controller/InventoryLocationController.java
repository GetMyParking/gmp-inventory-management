package com.gmp.inventory.controller;

import com.gmp.inventory.api.request.InventoryLocationParkingMapRequestDTO;
import com.gmp.inventory.api.request.InventoryLocationRequestDTO;
import com.gmp.inventory.api.request.InventoryParkingMetadataRequestDTO;
import com.gmp.inventory.api.response.InventoryLocationParkingMapResponseDTO;
import com.gmp.inventory.api.response.InventoryLocationResponseDTO;
import com.gmp.inventory.api.response.InventoryParkingMetadataResponseDTO;
import com.gmp.inventory.service.interfaces.InventoryLocationParkingMapService;
import com.gmp.inventory.service.interfaces.InventoryLocationService;
import com.gmp.inventory.service.interfaces.InventoryParkingMetadataService;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.gmp.entities.request.RequestHeaders.HEADER_GMP_TENANT;

/**
 * REST controller for Inventory Location, Location-Parking-Map and Parking Metadata.
 */
@RestController
@Slf4j
@RequestMapping("/inventory-management/v1/inventory/location")
@RequiredArgsConstructor
public class InventoryLocationController {

    private final InventoryLocationService inventoryLocationService;
    private final InventoryParkingMetadataService inventoryParkingMetadataService;
    private final InventoryLocationParkingMapService inventoryLocationParkingMapService;

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
