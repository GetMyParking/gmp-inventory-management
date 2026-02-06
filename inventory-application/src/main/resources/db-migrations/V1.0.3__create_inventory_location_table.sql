create table inventory_management.inventory_location
(
    id                  bigserial
        constraint inventory_location_pk primary key,
    branch_id           int8,
    name                VARCHAR(255)                      not null,
    address             VARCHAR(500),
    geolocation         POINT,
    metadata            jsonb,
    parking_id          int8,
    tenant              VARCHAR(255)                      not null,
    created_at          TIMESTAMP WITH TIME ZONE default now() not null,
    updated_at          TIMESTAMP WITH TIME ZONE default now() not null,
    deleted             SMALLINT         default 0
);

comment on table inventory_management.inventory_location is 'stores inventory location information';
comment on column inventory_management.inventory_location.branch_id is 'reference to branch table in parking schema (no FK constraint)';
comment on column inventory_management.inventory_location.name is 'location name';
comment on column inventory_management.inventory_location.address is 'location address';
comment on column inventory_management.inventory_location.geolocation is 'geographic coordinates as POINT';
comment on column inventory_management.inventory_location.metadata is 'JSON metadata for additional information';
comment on column inventory_management.inventory_location.parking_id is 'reference to parking table in parking schema (no FK constraint)';

create index idx_inventory_location_branch_id on inventory_management.inventory_location(branch_id);
create index idx_inventory_location_parking_id on inventory_management.inventory_location(parking_id);
create index idx_inventory_location_tenant on inventory_management.inventory_location(tenant);
