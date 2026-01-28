package com.gmp.inventory.constants;

/**
 * Centralized endpoint path constants for the Inventory Management Service.
 * Used by REST controllers and external service clients for consistent, readable paths.
 *
 * @author Mrigank Tandon
 */
public final class ApiPaths {

    private ApiPaths() {
        // utility class
    }

    // -------------------------------------------------------------------------
    // Inventory API (this service)
    // -------------------------------------------------------------------------

    /** Base path for inventory REST API */
    public static final String INVENTORY_BASE = "/v1/inventory";

    /** List all inventory items */
    public static final String ITEMS = "/items";

    /** Single inventory item by ID */
    public static final String ITEMS_ID = "/items/{id}";

    /** Search inventory with filters */
    public static final String ITEMS_SEARCH = "/items/search";

    /** Inventory items by branch ID */
    public static final String ITEMS_BRANCH_ID = "/items/branch/{branchId}";

    /** Inventory items by location ID */
    public static final String ITEMS_LOCATION_ID = "/items/location/{locationId}";

    /** Inventory item by serial number */
    public static final String ITEMS_SERIAL = "/items/serial";

    // -------------------------------------------------------------------------
    // Health check (this service)
    // -------------------------------------------------------------------------

    /** Health check endpoint */
    public static final String HEALTH_CHECK = "v1/healthCheck";

    // -------------------------------------------------------------------------
    // External – Permit Service
    // -------------------------------------------------------------------------

    /** Permit allocation by ID (Permit Service) */
    public static final String PERMIT_ALLOCATION_BY_ID = "/pms-admin/v1/permit-allocation/{allocationId}";

    // -------------------------------------------------------------------------
    // External – Parking Service
    // -------------------------------------------------------------------------

    /** Parking by ID (Parking Service) */
    public static final String PARKING_BY_ID = "/v1/parking/{parkingId}";

    /** Parking list (Parking Service) */
    public static final String PARKING_LIST = "/v1/parking/parkingList";

    /** Parking IDs by company ID (Parking Service) */
    public static final String PARKING_IDS_BY_COMPANY = "/v1/parking/parkingIdByCompany/{companyId}";
}
