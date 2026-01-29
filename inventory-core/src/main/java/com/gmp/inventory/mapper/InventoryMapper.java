package com.gmp.inventory.mapper;

import com.gmp.inventory.api.request.InventoryRequestDTO;
import com.gmp.inventory.api.response.InventoryResponseDTO;
import com.gmp.inventory.persistence.model.Inventory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Inventory entity and DTOs.
 * Controller and service layers use only DTOs; this layer converts Entity ↔ DTO.
 *
 * @author Mrigank Tandon
 */
@Component
@RequiredArgsConstructor
public class InventoryMapper {

    private final ModelMapper modelMapper;

    /**
     * Convert Inventory entity to response DTO.
     */
    public InventoryResponseDTO toResponseDTO(Inventory entity) {
        if (entity == null) {
            return null;
        }
        InventoryResponseDTO dto = modelMapper.map(entity, InventoryResponseDTO.class);
        dto.setId(entity.getId() != null ? entity.getId().intValue() : null);
        dto.setInventoryLocationId(entity.getInventoryLocationId() != null ? entity.getInventoryLocationId().intValue() : null);
        dto.setEntityId(entity.getEntityId() != null ? entity.getEntityId().intValue() : null);
        return dto;
    }

    /**
     * Convert request DTO to Inventory entity (for create).
     */
    public Inventory toEntity(InventoryRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Inventory entity = modelMapper.map(dto, Inventory.class);
        if (dto.getInventoryLocationId() != null) {
            entity.setInventoryLocationId(dto.getInventoryLocationId().longValue());
        }
        if (dto.getEntityId() != null) {
            entity.setEntityId(dto.getEntityId().longValue());
        }
        return entity;
    }

    /**
     * Update existing entity from request DTO (for update). Does not change id, tenant, or audit fields.
     */
    public void updateEntityFromRequest(Inventory entity, InventoryRequestDTO dto) {
        if (entity == null || dto == null) {
            return;
        }
        entity.setType(dto.getType());
        entity.setBranchId(dto.getBranchId());
        entity.setCompanyId(dto.getCompanyId());
        entity.setCategory(dto.getCategory());
        entity.setSerialPrefix(dto.getSerialPrefix());
        entity.setSerialNo(dto.getSerialNo());
        entity.setInventoryLocationId(dto.getInventoryLocationId() != null ? dto.getInventoryLocationId().longValue() : null);
        entity.setInventoryStatus(dto.getInventoryStatus());
        entity.setEntityType(dto.getEntityType());
        entity.setEntityId(dto.getEntityId() != null ? dto.getEntityId().longValue() : null);
    }
}
