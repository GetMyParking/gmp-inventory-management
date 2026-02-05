package com.gmp.inventory.service.interfaces;

import com.gmp.inventory.api.request.InventoryParkingMetadataRequestDTO;
import com.gmp.inventory.api.response.InventoryParkingMetadataResponseDTO;

import java.util.List;

/**
 * Service interface for InventoryParkingMetadata CRUD.
 *
 * @author Mrigank Tandon
 */
public interface InventoryParkingMetadataService {

    InventoryParkingMetadataResponseDTO getById(Long id, String tenant);

    InventoryParkingMetadataResponseDTO create(InventoryParkingMetadataRequestDTO request, String tenant);

    List<InventoryParkingMetadataResponseDTO> getByParkingId(Long parkingId, String tenant);
}
