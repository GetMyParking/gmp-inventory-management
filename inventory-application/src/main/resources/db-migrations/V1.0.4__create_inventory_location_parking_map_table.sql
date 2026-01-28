create table inventory_management.inventory_location_parking_map
(
    id                      bigserial
        constraint inventory_location_parking_map_pk primary key,
    inventory_location_id   bigint                           not null,
    parking_id              integer                           not null,
    tenant                  VARCHAR(255)                      not null,
    created_at              TIMESTAMP WITH TIME ZONE default now() not null,
    updated_at              TIMESTAMP WITH TIME ZONE default now() not null,
    deleted                 SMALLINT         default 0
);

comment on table inventory_management.inventory_location_parking_map is 'maps inventory locations to parking facilities (many-to-many)';
comment on column inventory_management.inventory_location_parking_map.inventory_location_id is 'reference to inventory_location table (no FK constraint)';
comment on column inventory_management.inventory_location_parking_map.parking_id is 'reference to parking table in parking schema (no FK constraint)';

create index idx_inv_loc_parking_map_location_id on inventory_management.inventory_location_parking_map(inventory_location_id);
create index idx_inv_loc_parking_map_parking_id on inventory_management.inventory_location_parking_map(parking_id);
create index idx_inv_loc_parking_map_tenant on inventory_management.inventory_location_parking_map(tenant);
