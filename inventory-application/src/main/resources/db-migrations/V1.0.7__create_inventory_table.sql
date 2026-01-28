create table inventory_management.inventory
(
    id                      bigserial
        constraint inventory_pk primary key,
    type                    VARCHAR(100),
    branch_id               integer,
    company_id              integer,
    category                VARCHAR(50),
    serial_prefix           VARCHAR(50),
    serial_no               VARCHAR(100),
    inventory_location_id    bigint,
    inventory_status        VARCHAR(50)                      not null,
    entity_type             VARCHAR(50),
    entity_id               bigint,
    tenant                  VARCHAR(255)                      not null,
    created_at              TIMESTAMP WITH TIME ZONE default now() not null,
    updated_at              TIMESTAMP WITH TIME ZONE default now() not null,
    deleted                 SMALLINT         default 0
);

comment on table inventory_management.inventory is 'core inventory item entity tracking physical inventory items';
comment on column inventory_management.inventory.type is 'inventory type';
comment on column inventory_management.inventory.branch_id is 'reference to branch table in parking schema (no FK constraint)';
comment on column inventory_management.inventory.company_id is 'reference to company table in parking schema (no FK constraint)';
comment on column inventory_management.inventory.category is 'category: access, permit, space, parking_equipment';
comment on column inventory_management.inventory.serial_prefix is 'serial number prefix';
comment on column inventory_management.inventory.serial_no is 'serial number';
comment on column inventory_management.inventory.inventory_location_id is 'reference to inventory_location table (no FK constraint)';
comment on column inventory_management.inventory.inventory_status is 'status: unavailable, available, ready, assigned, destroyed';
comment on column inventory_management.inventory.entity_type is 'entity type for polymorphic association';
comment on column inventory_management.inventory.entity_id is 'entity id for polymorphic association';

create index idx_inventory_branch_id on inventory_management.inventory(branch_id);
create index idx_inventory_company_id on inventory_management.inventory(company_id);
create index idx_inventory_location_id on inventory_management.inventory(inventory_location_id);
create index idx_inventory_status on inventory_management.inventory(inventory_status);
create index idx_inventory_category on inventory_management.inventory(category);
create index idx_inventory_entity on inventory_management.inventory(entity_type, entity_id);
create index idx_inventory_serial on inventory_management.inventory(serial_prefix, serial_no);
create index idx_inventory_tenant on inventory_management.inventory(tenant);
