create table inventory_management.inventory_fulfilment
(
    id                      bigserial
        constraint inventory_fulfilment_pk primary key,
    tracking_id             VARCHAR(255),
    method                  VARCHAR(50),
    inventory_location_id   bigint,
    shipment_address        VARCHAR(500),
    tenant                  VARCHAR(255)                      not null,
    created_at              TIMESTAMP WITH TIME ZONE default now() not null,
    updated_at              TIMESTAMP WITH TIME ZONE default now() not null,
    deleted                 SMALLINT         default 0
);

comment on table inventory_management.inventory_fulfilment is 'tracks inventory fulfilment and shipping information';
comment on column inventory_management.inventory_fulfilment.tracking_id is 'tracking identifier';
comment on column inventory_management.inventory_fulfilment.method is 'fulfilment method: PICKUP, MAIL, COURIER';
comment on column inventory_management.inventory_fulfilment.inventory_location_id is 'reference to inventory_location table (no FK constraint)';
comment on column inventory_management.inventory_fulfilment.shipment_address is 'shipment address';

create index idx_inv_fulfilment_location_id on inventory_management.inventory_fulfilment(inventory_location_id);
create index idx_inv_fulfilment_tracking_id on inventory_management.inventory_fulfilment(tracking_id);
create index idx_inv_fulfilment_method on inventory_management.inventory_fulfilment(method);
create index idx_inv_fulfilment_tenant on inventory_management.inventory_fulfilment(tenant);
