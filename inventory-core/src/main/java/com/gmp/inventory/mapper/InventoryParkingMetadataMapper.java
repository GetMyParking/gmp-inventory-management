package com.gmp.inventory.mapper;

import com.gmp.inventory.api.request.InventoryParkingMetadataRequestDTO;
import com.gmp.inventory.api.response.InventoryParkingMetadataResponseDTO;
import com.gmp.inventory.persistence.model.InventoryParkingMetadata;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Mapper for InventoryParkingMetadata entity and DTOs.
 *
 * @author Mrigank Tandon
 */
@Component
@RequiredArgsConstructor
public class InventoryParkingMetadataMapper {

    private final ModelMapper modelMapper;

    public InventoryParkingMetadataResponseDTO toResponseDTO(InventoryParkingMetadata entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return modelMapper.map(entity, InventoryParkingMetadataResponseDTO.class);
    }

    public InventoryParkingMetadata toEntity(InventoryParkingMetadataRequestDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        InventoryParkingMetadata entity = modelMapper.map(dto, InventoryParkingMetadata.class);
        if (Objects.nonNull(dto.getLeadTime())) {
            entity.setLeadTime(dto.getLeadTime().intValue());
        }
        return entity;
    }

    public void updateEntityFromRequest(InventoryParkingMetadata entity, InventoryParkingMetadataRequestDTO dto) {
        if (Objects.isNull(entity) || Objects.isNull(dto)) {
            return;
        }
        entity.setParkingId(dto.getParkingId());
        entity.setLeadTime(dto.getLeadTime() != null ? dto.getLeadTime().intValue() : null);
        entity.setReturnGraceTime(dto.getReturnGraceTime());
        entity.setIsPickUpAvailable(dto.getIsPickUpAvailable());
        entity.setIsMailAvailable(dto.getIsMailAvailable());
        entity.setMailLeadTime(dto.getMailLeadTime());
        entity.setMailChargesInCents(dto.getMailChargesInCents());
        entity.setIsCourierAvailable(dto.getIsCourierAvailable());
        entity.setCourierLeadTime(dto.getCourierLeadTime());
        entity.setCourierChargesInCents(dto.getCourierChargesInCents());
    }
}
