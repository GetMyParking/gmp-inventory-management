package com.gmp.inventory.mapper;

import com.gmp.inventory.api.enums.InventoryCategory;
import com.gmp.inventory.api.enums.InventoryPricingLevel;
import com.gmp.inventory.api.request.InventoryPricingRequestDTO;
import com.gmp.inventory.api.response.InventoryPricingResponseDTO;
import com.gmp.inventory.persistence.model.InventoryPricing;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@RequiredArgsConstructor
public class InventoryPricingMapper {

    private final ModelMapper modelMapper;

    public InventoryPricingResponseDTO toResponseDTO(InventoryPricing entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        InventoryPricingResponseDTO dto = modelMapper.map(entity, InventoryPricingResponseDTO.class);
        dto.setActivationFeeInCents(Objects.nonNull(entity.getActivationFeeInCents()) ? entity.getActivationFeeInCents().longValue() : null);
        dto.setDepositFeeInCents(Objects.nonNull(entity.getDepositFeeInCents()) ? entity.getDepositFeeInCents().longValue() : null);
        dto.setBranchId(Objects.nonNull(entity.getBranchId()) ? entity.getBranchId().longValue() : null);
        dto.setParkingId(Objects.nonNull(entity.getParkingId()) ? entity.getParkingId().longValue() : null);
        dto.setCategory(entity.getCategory());
        dto.setLevel(entity.getLevel());
        return dto;
    }

    private static InventoryCategory parseCategoryEnum(String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            return null;
        }
        try {
            return InventoryCategory.valueOf(value.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static InventoryPricingLevel parseInventoryPricingLevel(String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            return null;
        }
        try {
            return InventoryPricingLevel.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public InventoryPricing toEntity(InventoryPricingRequestDTO inventoryPricingRequestDTO) {
        if (Objects.isNull(inventoryPricingRequestDTO)) {
            return null;
        }
        InventoryPricing entity = modelMapper.map(inventoryPricingRequestDTO, InventoryPricing.class);
        entity.setActivationFeeInCents(inventoryPricingRequestDTO.getActivationFeeInCents());
        entity.setDepositFeeInCents(inventoryPricingRequestDTO.getDepositFeeInCents());
        if (Objects.nonNull(inventoryPricingRequestDTO.getPermitMasterId())) {
            entity.setPermitMasterId(inventoryPricingRequestDTO.getPermitMasterId());
        }
        return entity;
    }

    public void updateEntityFromRequest(InventoryPricing inventoryPricing, InventoryPricingRequestDTO inventoryPricingRequestDTO) {
        if (Objects.isNull(inventoryPricing) || Objects.isNull(inventoryPricingRequestDTO)) {
            return;
        }
        inventoryPricing.setType(inventoryPricingRequestDTO.getType());
        inventoryPricing.setBranchId(inventoryPricingRequestDTO.getBranchId());
        inventoryPricing.setParkingId(inventoryPricingRequestDTO.getParkingId());
        if (Objects.nonNull(inventoryPricingRequestDTO.getPermitMasterId())) {
            inventoryPricing.setPermitMasterId(inventoryPricingRequestDTO.getPermitMasterId());
        }
        inventoryPricing.setActivationFeeInCents(inventoryPricingRequestDTO.getActivationFeeInCents());
        inventoryPricing.setDepositFeeInCents(inventoryPricingRequestDTO.getDepositFeeInCents());
        inventoryPricing.setCategory(inventoryPricingRequestDTO.getCategory());
        inventoryPricing.setLevel(inventoryPricingRequestDTO.getLevel());
    }
}
