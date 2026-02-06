create table inventory_management.inventory_mapping
(
    id              bigserial
        constraint inventory_mapping_pk primary key,
    serial_prefix   VARCHAR(50)                         not null,
    parking_id      int8,
    serial_start    int8,
    serial_end      int8,
    tenant          VARCHAR(255)                       not null,
    created_at      TIMESTAMP WITH TIME ZONE default now() not null,
    updated_at      TIMESTAMP WITH TIME ZONE default now() not null,
    deleted         SMALLINT             default 0     not null
);

comment on table inventory_management.inventory_mapping is 'maps serial prefixes and serial number ranges to parking facilities';
comment on column inventory_management.inventory_mapping.serial_prefix is 'prefix for serial numbers in this mapping';
comment on column inventory_management.inventory_mapping.parking_id is 'reference to parking table in parking schema (no FK constraint)';
comment on column inventory_management.inventory_mapping.serial_start is 'start of serial number range (inclusive)';
comment on column inventory_management.inventory_mapping.serial_end is 'end of serial number range (inclusive)';

create index idx_inventory_mapping_serial_prefix on inventory_management.inventory_mapping(serial_prefix);
create index idx_inventory_mapping_parking_id on inventory_management.inventory_mapping(parking_id);
create index idx_inventory_mapping_tenant on inventory_management.inventory_mapping(tenant);
