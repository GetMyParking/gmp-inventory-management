package com.gmp.inventory.service.interfaces;

import com.gmp.inventory.api.request.InventoryRequestDTO;
import com.gmp.inventory.api.response.InventoryResponseDTO;

import java.util.List;

/**
 * Service interface for Inventory operations.
 * DTOs only in API.
 *
 * @author Mrigank Tandon
 */
public interface InventoryService {

    List<InventoryResponseDTO> getAllInventories(Integer companyId, String tenant);

    InventoryResponseDTO getInventoryById(Long id, String tenant);

    InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequest, String tenant);

    InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO inventoryRequest, String tenant);

    void deleteInventory(Long id, String tenant);

    List<InventoryResponseDTO> findInventoriesWithFilters(Integer companyId,
                                                          Integer branchId,
                                                          String category,
                                                          String status,
                                                          String tenant);

    List<InventoryResponseDTO> getInventoriesByBranchId(Integer branchId, String tenant);

    List<InventoryResponseDTO> getInventoriesByLocationId(Long inventoryLocationId, String tenant);

    InventoryResponseDTO getInventoryBySerialNumber(String serialPrefix, String serialNo, String tenant);
}
