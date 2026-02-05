package com.gmp.inventory.service.interfaces;

import com.gmp.inventory.api.request.InventoryLocationParkingMapRequestDTO;
import com.gmp.inventory.api.response.InventoryLocationParkingMapResponseDTO;

import java.util.List;

/**
 * Service interface for InventoryLocationParkingMap CRUD.
 *
 * @author Mrigank Tandon
 */
public interface InventoryLocationParkingMapService {

    InventoryLocationParkingMapResponseDTO getById(Long id, String tenant);

    InventoryLocationParkingMapResponseDTO create(InventoryLocationParkingMapRequestDTO request, String tenant);

    List<InventoryLocationParkingMapResponseDTO> getByInventoryLocationId(Long inventoryLocationId, String tenant);

    List<InventoryLocationParkingMapResponseDTO> getByParkingId(Long parkingId, String tenant);
}
