package com.gmp.inventory.service.impl;

import com.gmp.inventory.api.enums.FulfilmentMethod;
import com.gmp.inventory.api.request.PricingByPermitIdsRequest;
import com.gmp.inventory.api.request.InventoryPricingRequestDTO;
import com.gmp.inventory.api.response.FulfilmentMethodDTO;
import com.gmp.inventory.api.response.GeoLocationDTO;
import com.gmp.inventory.api.response.InventoryPricingResponseDTO;
import com.gmp.inventory.api.response.ParkingPricingGroupDTO;
import com.gmp.inventory.api.response.PermitPricingGroupDTO;
import com.gmp.inventory.api.response.PickupLocationDTO;
import com.gmp.inventory.api.response.PricingByPermitIdsResponse;
import com.gmp.inventory.mapper.InventoryPricingMapper;
import com.gmp.inventory.persistence.model.InventoryLocation;
import com.gmp.inventory.persistence.model.InventoryLocationParkingMap;
import com.gmp.inventory.persistence.model.InventoryParkingMetadata;
import com.gmp.inventory.persistence.model.InventoryPricing;
import com.gmp.inventory.repository.interfaces.InventoryLocationParkingMapRepository;
import com.gmp.inventory.repository.interfaces.InventoryLocationRepository;
import com.gmp.inventory.repository.interfaces.InventoryParkingMetadataRepository;
import com.gmp.inventory.repository.interfaces.InventoryPricingRepository;
import com.gmp.inventory.service.interfaces.InventoryPricingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of InventoryPricingService.
 *
 * @author Mrigank Tandon
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryPricingServiceImpl implements InventoryPricingService {

    private final InventoryPricingRepository inventoryPricingRepository;
    private final InventoryPricingMapper inventoryPricingMapper;
    private final InventoryParkingMetadataRepository inventoryParkingMetadataRepository;
    private final InventoryLocationParkingMapRepository inventoryLocationParkingMapRepository;
    private final InventoryLocationRepository inventoryLocationRepository;

    @Override
    public InventoryPricingResponseDTO getById(Long id, String tenant) {
        log.debug("Fetching inventory pricing by id: {}, tenant: {}", id, tenant);
        InventoryPricing entity = inventoryPricingRepository.findByIdAndTenantAndDeleted(id, tenant, 0)
                .orElseThrow(() -> new RuntimeException("Inventory pricing not found with id: " + id + " for tenant: " + tenant));
        return inventoryPricingMapper.toResponseDTO(entity);
    }

    @Override
    @Transactional
    public InventoryPricingResponseDTO create(InventoryPricingRequestDTO request, String tenant) {
        log.info("Creating inventory pricing, tenant: {}", tenant);
        InventoryPricing entity = inventoryPricingMapper.toEntity(request);
        entity.setTenant(tenant);
        entity.setDeleted(0);
        InventoryPricing saved = inventoryPricingRepository.save(entity);
        return inventoryPricingMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional
    public InventoryPricingResponseDTO update(Long id, InventoryPricingRequestDTO request, String tenant) {
        log.info("Updating inventory pricing id: {}, tenant: {}", id, tenant);
        InventoryPricing existing = inventoryPricingRepository.findByIdAndTenantAndDeleted(id, tenant, 0)
                .orElseThrow(() -> new RuntimeException("Inventory pricing not found with id: " + id + " for tenant: " + tenant));
        inventoryPricingMapper.updateEntityFromRequest(existing, request);
        InventoryPricing updated = inventoryPricingRepository.save(existing);
        return inventoryPricingMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(Long id, String tenant) {
        log.info("Soft deleting inventory pricing id: {}, tenant: {}", id, tenant);
        InventoryPricing entity = inventoryPricingRepository.findByIdAndTenantAndDeleted(id, tenant, 0)
                .orElseThrow(() -> new RuntimeException("Inventory pricing not found with id: " + id + " for tenant: " + tenant));
        entity.setDeleted(1);
        inventoryPricingRepository.save(entity);
    }

    @Override
    public List<InventoryPricingResponseDTO> getByBranchId(Integer branchId, String tenant) {
        log.debug("Fetching inventory pricing by branchId: {}, tenant: {}", branchId, tenant);
        return inventoryPricingRepository.findActiveByBranchId(branchId, tenant).stream()
                .map(inventoryPricingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryPricingResponseDTO> getByParkingId(Integer parkingId, String tenant) {
        log.debug("Fetching inventory pricing by parkingId: {}, tenant: {}", parkingId, tenant);
        return inventoryPricingRepository.findActiveByParkingId(parkingId, tenant).stream()
                .map(inventoryPricingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InventoryPricingResponseDTO> getByPermitMasterId(Long permitMasterId, String tenant) {
        log.debug("Fetching inventory pricing by permitMasterId: {}, tenant: {}", permitMasterId, tenant);
        return inventoryPricingRepository.findActiveByPermitMasterId(permitMasterId, tenant).stream()
                .map(inventoryPricingMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PricingByPermitIdsResponse getPricingByPermitIds(PricingByPermitIdsRequest request, String tenant) {
        Long permitMasterId = request.getPermitMasterId();
        if (permitMasterId == null) {
            return PricingByPermitIdsResponse.builder()
                    .pricingByPermitId(Collections.emptyList())
                    .build();
        }

        Long requestParkingId = request.getParkingId();
        boolean includeFulfilmentDetails = requestParkingId != null && Boolean.TRUE.equals(request.getIncludeFulfilmentDetails());
        log.debug("Fetching pricing by permit: {}, parkingId: {}, includeFulfilmentDetails: {}, tenant: {}",
                permitMasterId, requestParkingId, includeFulfilmentDetails, tenant);

        List<InventoryPricing> pricingEntities = inventoryPricingRepository.findActiveByPermitMasterId(permitMasterId, tenant);

        Map<Long, InventoryParkingMetadata> metadataByParkingId = new LinkedHashMap<>();
        Map<Long, List<Long>> locationIdsByParkingId = new LinkedHashMap<>();
        Map<Long, InventoryLocation> locationById = new LinkedHashMap<>();

        if (includeFulfilmentDetails && Objects.nonNull(requestParkingId)) {
            List<InventoryParkingMetadata> metadataList = inventoryParkingMetadataRepository.findActiveByParkingId(requestParkingId, tenant);
            List<InventoryLocationParkingMap> mapList = inventoryLocationParkingMapRepository.findActiveByParkingId(requestParkingId, tenant);

            if (!metadataList.isEmpty()) {
                metadataByParkingId.put(requestParkingId, metadataList.get(0));
            }
            locationIdsByParkingId.put(requestParkingId, mapList.stream()
                    .map(InventoryLocationParkingMap::getInventoryLocationId)
                    .collect(Collectors.toList()));

            List<Long> locIds = locationIdsByParkingId.get(requestParkingId);
            if (locIds != null && !locIds.isEmpty()) {
                locationById = inventoryLocationRepository.findByIdInAndTenantAndDeleted(locIds, tenant).stream()
                        .collect(Collectors.toMap(InventoryLocation::getId, l -> l, (a, b) -> a));
            }
        }

        Map<Long, List<InventoryPricingResponseDTO>> devicesByParkingId = new LinkedHashMap<>();
        for (InventoryPricing entity : pricingEntities) {
            Long parkingId = entity.getParkingId() != null ? entity.getParkingId().longValue() : null;
            if (parkingId == null) continue;
            if (requestParkingId != null && !requestParkingId.equals(parkingId)) continue;
            devicesByParkingId
                    .computeIfAbsent(parkingId, k -> new ArrayList<>())
                    .add(inventoryPricingMapper.toResponseDTO(entity));
        }

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        List<ParkingPricingGroupDTO> parkingGroups = new ArrayList<>();
        for (Map.Entry<Long, List<InventoryPricingResponseDTO>> e : devicesByParkingId.entrySet()) {
            Long parkingId = e.getKey();
            List<FulfilmentMethodDTO> fulfilmentMethods = null;
            if (includeFulfilmentDetails && requestParkingId != null && requestParkingId.equals(parkingId)) {
                InventoryParkingMetadata meta = metadataByParkingId.get(parkingId);
                fulfilmentMethods = buildFulfilmentMethods(meta, parkingId, now, locationIdsByParkingId, locationById);
            }
            parkingGroups.add(ParkingPricingGroupDTO.builder()
                    .parkingId(parkingId)
                    .devices(e.getValue())
                    .fulfilmentMethods(fulfilmentMethods)
                    .build());
        }

        List<PermitPricingGroupDTO> pricingByPermitId = Collections.singletonList(
                PermitPricingGroupDTO.builder()
                        .permitMasterId(permitMasterId)
                        .pricingByParking(parkingGroups)
                        .build());

        return PricingByPermitIdsResponse.builder()
                .pricingByPermitId(pricingByPermitId)
                .build();
    }

    private List<FulfilmentMethodDTO> buildFulfilmentMethods(
            InventoryParkingMetadata meta,
            Long parkingId,
            OffsetDateTime now,
            Map<Long, List<Long>> locationIdsByParkingId,
            Map<Long, InventoryLocation> locationById) {

        List<FulfilmentMethodDTO> result = new ArrayList<>();
        int leadTimeMinutes = meta != null && meta.getLeadTime() != null ? meta.getLeadTime() : 0;

        boolean mailAvailable = meta != null && Boolean.TRUE.equals(meta.getIsMailAvailable());
        int mailLeadTime = meta != null && meta.getMailLeadTime() != null ? meta.getMailLeadTime() : 0;
        result.add(FulfilmentMethodDTO.builder()
                .type(FulfilmentMethod.MAIL)
                .available(mailAvailable)
                .chargesInCents(mailAvailable && meta != null ? meta.getMailChargesInCents() : null)
                .startDate(now.plusMinutes(leadTimeMinutes + mailLeadTime))
                .pickupLocations(null)
                .build());

        boolean pickupAvailable = meta != null && Boolean.TRUE.equals(meta.getIsPickUpAvailable());
        List<PickupLocationDTO> pickupLocations = pickupAvailable ? buildPickupLocations(parkingId, locationIdsByParkingId, locationById) : null;
        result.add(FulfilmentMethodDTO.builder()
                .type(FulfilmentMethod.PICK_UP)
                .available(pickupAvailable)
                .chargesInCents(null)
                .startDate(now.plusMinutes(leadTimeMinutes))
                .pickupLocations(pickupLocations != null ? pickupLocations : Collections.emptyList())
                .build());

        boolean courierAvailable = meta != null && Boolean.TRUE.equals(meta.getIsCourierAvailable());
        int courierLeadTime = meta != null && meta.getCourierLeadTime() != null ? meta.getCourierLeadTime() : 0;
        result.add(FulfilmentMethodDTO.builder()
                .type(FulfilmentMethod.COURIER)
                .available(courierAvailable)
                .chargesInCents(courierAvailable && meta != null ? meta.getCourierChargesInCents() : null)
                .startDate(now.plusMinutes(leadTimeMinutes + courierLeadTime))
                .pickupLocations(null)
                .build());

        return result;
    }

    private List<PickupLocationDTO> buildPickupLocations(
            Long parkingId,
            Map<Long, List<Long>> locationIdsByParkingId,
            Map<Long, InventoryLocation> locationById) {

        List<Long> locationIds = locationIdsByParkingId.getOrDefault(parkingId, Collections.emptyList());
        if (locationIds.isEmpty()) return Collections.emptyList();

        List<PickupLocationDTO> list = new ArrayList<>();
        for (Long locId : locationIds) {
            InventoryLocation loc = locationById.get(locId);
            if (loc == null) continue;
            GeoLocationDTO geo = null;
            if (loc.getGeolocation() != null) {
                Point p = loc.getGeolocation();
                geo = GeoLocationDTO.builder()
                        .latitude(p.getY())
                        .longitude(p.getX())
                        .build();
            }
            list.add(PickupLocationDTO.builder()
                    .name(loc.getName())
                    .geolocation(geo)
                    .metadata(loc.getMetadata())
                    .build());
        }
        return list;
    }
}
