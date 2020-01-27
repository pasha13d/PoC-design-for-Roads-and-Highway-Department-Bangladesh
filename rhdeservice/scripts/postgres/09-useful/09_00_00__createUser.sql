------------------------- Production DB Server -------------------------
-- open terminal login as postgres in operation system
sudo su
su - postgres

-- login database of postgres using postgres user
sudo -u postgres psql -U postgres

-- Create User
create user rhd password 'rhd';
alter user rhd with superuser;


-- Create Database
create database rhd WITH owner=rhd encoding='UTF8';

-- Create Schema absnew as default, absold
create schema if not exists rhd authorization rhd;

-- Rename Schema
alter schema public rename to my_schema;
alter schema original_public rename to public;
