package com.gmp.inventory.mapper;

import com.gmp.inventory.api.request.InventoryLocationParkingMapRequestDTO;
import com.gmp.inventory.api.response.InventoryLocationParkingMapResponseDTO;
import com.gmp.inventory.persistence.model.InventoryLocationParkingMap;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Mapper for InventoryLocationParkingMap entity and DTOs.
 *
 * @author Mrigank Tandon
 */
@Component
@RequiredArgsConstructor
public class InventoryLocationParkingMapMapper {

    private final ModelMapper modelMapper;

    public InventoryLocationParkingMapResponseDTO toResponseDTO(InventoryLocationParkingMap entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return modelMapper.map(entity, InventoryLocationParkingMapResponseDTO.class);
    }

    public InventoryLocationParkingMap toEntity(InventoryLocationParkingMapRequestDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        return modelMapper.map(dto, InventoryLocationParkingMap.class);
    }

    public void updateEntityFromRequest(InventoryLocationParkingMap entity, InventoryLocationParkingMapRequestDTO dto) {
        if (Objects.isNull(entity) || Objects.isNull(dto)) {
            return;
        }
        entity.setInventoryLocationId(dto.getInventoryLocationId());
        entity.setParkingId(dto.getParkingId());
    }
}
