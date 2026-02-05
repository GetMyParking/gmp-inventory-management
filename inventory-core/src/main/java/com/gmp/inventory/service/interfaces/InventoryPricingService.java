package com.gmp.inventory.service.interfaces;

import com.gmp.inventory.api.request.GetDevicesByPermitIdsRequest;
import com.gmp.inventory.api.request.InventoryPricingRequestDTO;
import com.gmp.inventory.api.request.PricingByPermitIdsRequest;
import com.gmp.inventory.api.response.PricingByPermitIdsResponse;
import com.gmp.inventory.api.response.InventoryPricingResponseDTO;

import java.util.List;

/**
 * Service interface for InventoryPricing CRUD.
 *
 * @author Mrigank Tandon
 */
public interface InventoryPricingService {

    InventoryPricingResponseDTO getById(Long id, String tenant);

    InventoryPricingResponseDTO create(InventoryPricingRequestDTO request, String tenant);

    InventoryPricingResponseDTO update(Long id, InventoryPricingRequestDTO request, String tenant);

    void delete(Long id, String tenant);

    List<InventoryPricingResponseDTO> getByBranchId(Integer branchId, String tenant);

    List<InventoryPricingResponseDTO> getByParkingId(Integer parkingId, String tenant);

    List<InventoryPricingResponseDTO> getByPermitMasterId(Long permitMasterId, String tenant);

    PricingByPermitIdsResponse getPricingByPermitIds(PricingByPermitIdsRequest request, String tenant);

    List<InventoryPricingResponseDTO> getDevicesByPermitIds(GetDevicesByPermitIdsRequest request, String tenant);
}
