create table inventory_management.inventory_parking_metadata
(
    id                      bigserial
        constraint inventory_parking_metadata_pk primary key,
    parking_id              int8                           not null,
    lead_time               INTEGER,
    return_grace_time       INTEGER,
    is_pick_up_available    BOOLEAN          default false,
    is_mail_available       BOOLEAN          default false,
    mail_lead_time          INTEGER,
    mail_charges            INTEGER,
    is_courier_available    BOOLEAN          default false,
    courier_lead_time       INTEGER,
    courier_charges         INTEGER,
    tenant                  VARCHAR(255)                      not null,
    created_at              TIMESTAMP WITH TIME ZONE default now() not null,
    updated_at              TIMESTAMP WITH TIME ZONE default now() not null,
    deleted                 SMALLINT         default 0
);

comment on table inventory_management.inventory_parking_metadata is 'stores parking-specific metadata for inventory management';
comment on column inventory_management.inventory_parking_metadata.parking_id is 'reference to parking table in parking schema (no FK constraint)';
comment on column inventory_management.inventory_parking_metadata.lead_time is 'preparation time in minutes';
comment on column inventory_management.inventory_parking_metadata.return_grace_time is 'return grace period in minutes';
comment on column inventory_management.inventory_parking_metadata.is_pick_up_available is 'pickup availability flag';
comment on column inventory_management.inventory_parking_metadata.is_mail_available is 'mail delivery availability flag';
comment on column inventory_management.inventory_parking_metadata.mail_lead_time is 'mail delivery lead time in minutes';
comment on column inventory_management.inventory_parking_metadata.mail_charges is 'mail charges in cents';
comment on column inventory_management.inventory_parking_metadata.is_courier_available is 'courier delivery availability flag';
comment on column inventory_management.inventory_parking_metadata.courier_lead_time is 'courier delivery lead time in minutes';
comment on column inventory_management.inventory_parking_metadata.courier_charges is 'courier charges in cents';

create index idx_inv_parking_metadata_parking_id on inventory_management.inventory_parking_metadata(parking_id);
create index idx_inv_parking_metadata_tenant on inventory_management.inventory_parking_metadata(tenant);
