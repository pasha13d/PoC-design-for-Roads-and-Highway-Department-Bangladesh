create table                    "public".oauth_client_details
(
client_id                       varchar(256)                                                 not null,
resource_ids                    varchar(256),
client_secret                   varchar(256),
scope                           varchar(256),
authorized_grant_types          varchar(256),
web_server_redirect_uri         varchar(256),
authorities                     varchar(256),
access_token_validity           numeric(5,0),
refresh_token_validity          numeric(5,0),
additional_information          text,
autoapprove                     varchar(256),
constraint                      pk_oauth_client_details                                      primary key    (client_id)
);

create table "public".oauth_client_token
(
token_id                        varchar(255),
token                           bytea,
authentication_id               varchar(255),
user_name                       varchar(255),
client_id                       varchar(255)
);

create table "public".oauth_access_token
(
token_id                        varchar(255),
token                           bytea,
authentication_id               varchar(255),
user_name                       varchar(255),
client_id                       varchar(255),
authentication                  bytea,
refresh_token                   varchar(255)
);

create table "public".oauth_refresh_token
(
token_id                        varchar(255),
token                           bytea,
authentication                  bytea
);

create table "public".oauth_code
(
code                            varchar(255),
authentication                  bytea
);


-- oauth_client_details
insert into "public".oauth_client_details (client_id, client_secret, scope, authorized_grant_types, authorities, access_token_validity, refresh_token_validity) values ('grp-web-portal', '$2a$10$Nj9AB/ffFQX/FEj3KgdmLe9vu6NlVMfBrG6/bsq9zD0eUAlMMJ6o.', 'read,write', 'password,refresh_token,client_credentials,authorization_code', 'ROLE_CLIENT,ROLE_TRUSTED_CLIENT', 86400, 86400);
