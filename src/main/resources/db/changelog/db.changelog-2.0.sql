--liquibase formatted sql

--changeset dmatveneka:1
alter table users
    add column created_at TIMESTAMP;

alter table users
    add column modified_at TIMESTAMP;

alter table users
    add column created_by varchar(32);

alter table users
    add column modified_by varchar(32);