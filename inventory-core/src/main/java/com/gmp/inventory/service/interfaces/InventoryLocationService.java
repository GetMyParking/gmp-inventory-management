package com.gmp.inventory.service.interfaces;

import com.gmp.inventory.api.request.InventoryLocationRequestDTO;
import com.gmp.inventory.api.response.InventoryLocationResponseDTO;

import java.util.List;

/**
 * Service interface for InventoryLocation CRUD.
 *
 * @author Mrigank Tandon
 */
public interface InventoryLocationService {

    InventoryLocationResponseDTO getById(Long id, String tenant);

    InventoryLocationResponseDTO create(InventoryLocationRequestDTO request, String tenant);

    InventoryLocationResponseDTO update(Long id, InventoryLocationRequestDTO request, String tenant);

    List<InventoryLocationResponseDTO> getByBranchId(Long branchId, String tenant);
}
