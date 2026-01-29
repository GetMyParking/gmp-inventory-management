create table inventory_management.inventory_pricing
(
    id                  bigserial
        constraint inventory_pricing_pk primary key,
    type                VARCHAR(100),
    branch_id           integer,
    parking_id          integer,
    permit_master_id    bigint,
    activation_fee      INTEGER,
    deposit_fee         INTEGER,
    category            VARCHAR(50),
    supported           BOOLEAN          default false,
    tenant              VARCHAR(255)                      not null,
    created_at          TIMESTAMP WITH TIME ZONE default now() not null,
    updated_at          TIMESTAMP WITH TIME ZONE default now() not null,
    deleted             SMALLINT         default 0
);

comment on table inventory_management.inventory_pricing is 'manages pricing information for inventory items';
comment on column inventory_management.inventory_pricing.type is 'pricing type';
comment on column inventory_management.inventory_pricing.branch_id is 'reference to branch table in parking schema (no FK constraint)';
comment on column inventory_management.inventory_pricing.parking_id is 'reference to parking table in parking schema (no FK constraint)';
comment on column inventory_management.inventory_pricing.permit_master_id is 'reference to permit_master table in permit schema (no FK constraint)';
comment on column inventory_management.inventory_pricing.activation_fee is 'activation fee in cents';
comment on column inventory_management.inventory_pricing.deposit_fee is 'deposit fee in cents';
comment on column inventory_management.inventory_pricing.category is 'category: access, permit, space, parking_equipment';
comment on column inventory_management.inventory_pricing.supported is 'is supported flag';

create index idx_inv_pricing_branch_id on inventory_management.inventory_pricing(branch_id);
create index idx_inv_pricing_parking_id on inventory_management.inventory_pricing(parking_id);
create index idx_inv_pricing_permit_master_id on inventory_management.inventory_pricing(permit_master_id);
create index idx_inv_pricing_category on inventory_management.inventory_pricing(category);
create index idx_inv_pricing_tenant on inventory_management.inventory_pricing(tenant);
