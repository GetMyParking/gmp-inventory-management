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
        InventoryResponseDTO inventoryResponseDTO = modelMapper.map(entity, InventoryResponseDTO.class);
        inventoryResponseDTO.setId(entity.getId() != null ? entity.getId() : null);
        inventoryResponseDTO.setInventoryLocationId(entity.getInventoryLocationId() != null ? entity.getInventoryLocationId() : null);
        inventoryResponseDTO.setEntityId(entity.getEntityId() != null ? entity.getEntityId() : null);
        return inventoryResponseDTO;
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
    public void updateEntityFromRequest(Inventory inventory, InventoryRequestDTO inventoryRequestDTO) {
        if (inventory == null || inventoryRequestDTO == null) {
            return;
        }
        inventory.setType(inventoryRequestDTO.getType());
        inventory.setBranchId(inventoryRequestDTO.getBranchId());
        inventory.setCompanyId(inventoryRequestDTO.getCompanyId());
        inventory.setCategory(inventoryRequestDTO.getCategory());
        inventory.setSerialPrefix(inventoryRequestDTO.getSerialPrefix());
        inventory.setSerialNo(inventoryRequestDTO.getSerialNo());
        inventory.setInventoryLocationId(inventoryRequestDTO.getInventoryLocationId() != null ? inventoryRequestDTO.getInventoryLocationId().longValue() : null);
        inventory.setInventoryStatus(inventoryRequestDTO.getInventoryStatus());
        inventory.setEntityType(inventoryRequestDTO.getEntityType());
        inventory.setEntityId(inventoryRequestDTO.getEntityId() != null ? inventoryRequestDTO.getEntityId().longValue() : null);
    }
}
