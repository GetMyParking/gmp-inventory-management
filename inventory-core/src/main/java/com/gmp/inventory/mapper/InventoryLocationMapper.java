package com.gmp.inventory.mapper;

import com.gmp.inventory.api.request.InventoryLocationRequestDTO;
import com.gmp.inventory.api.response.InventoryLocationResponseDTO;
import com.gmp.inventory.persistence.model.InventoryLocation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class InventoryLocationMapper {

    private final ModelMapper modelMapper;

    public InventoryLocationResponseDTO toResponseDTO(InventoryLocation entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        return modelMapper.map(entity, InventoryLocationResponseDTO.class);
    }

    public InventoryLocation toEntity(InventoryLocationRequestDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        return modelMapper.map(dto, InventoryLocation.class);
    }

    public void updateEntityFromRequest(InventoryLocation entity, InventoryLocationRequestDTO dto) {
        if (Objects.isNull(entity) || Objects.isNull(dto)) {
            return;
        }
        entity.setBranchId(dto.getBranchId());
        entity.setName(dto.getName());
        entity.setGeolocation(dto.getGeolocation());
        entity.setMetadata(dto.getMetadata());
        entity.setParkingId(dto.getParkingId());
    }
}
