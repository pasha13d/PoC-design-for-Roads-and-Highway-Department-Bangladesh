/*
This table to be used to store user Designation list.
oid                                      : Surrogate primary key
designationBn                            : Designation name in bangla
designationEn                            : Designation name in endlish
createdBy                                : Who inserted designation into the System
createdOn                                : When it was created
updatedBy                                : Who last update the designation
updatedOn                                : When it was updated
*/
create table                             Designation
(
oid                                      varchar(128)                                                not null,
designationBn                            varchar(128)                                                not null,
designationEn                            varchar(128)                                                not null,
createdBy                                varchar(128)                                                not null       default 'System',
createdOn                                timestamp                                                   not null       default current_timestamp,
updatedBy                                varchar(128),
updatedOn                                timestamp,
constraint                               pk_Designation                                              primary key    (oid),
constraint                               u_designationBn_Designation                                 unique         (designationBn),
constraint                               u_designationEn_Designation                                 unique         (designationEn)
);

/*
This table to be used to store user Division list.
oid                                      : Surrogate primary key
divisionNameBn                           : Divison Name in Bangla
divisionNameEn                           : Divison Name in English
createdBy                                : Who created the division, default is System
createdOn                                : When it was created
updatedBy                                : Who last update the division
updatedOn                                : When it was updated
*/
create table                             Division
(
oid                                      varchar(128)                                                not null,
divisionNameBn                           varchar(128)                                                not null,
divisionNameEn                           varchar(128)                                                not null,
createdBy                                varchar(128)                                                not null       default 'System',
createdOn                                timestamp                                                   not null       default current_timestamp,
updatedBy                                varchar(128),
updatedOn                                timestamp,
constraint                               pk_Division                                                 primary key    (oid),
constraint                               u_divisionNameBn_Division                                   unique         (divisionNameBn),
constraint                               u_divisionNameEn_Division                                   unique         (divisionNameEn)
);

/*
This table to be used to store user District list.
oid                                      : Surrogate primary key
districtNameBn                           : District Name in Bangla
divisionOid                              : Divison OId
districtNameEn                           : District Name in English
createdBy                                : Who created the district, default is System
createdOn                                : When it was created
updatedBy                                : Who last update the district
updatedOn                                : When it was updated
*/
create table                             District
(
oid                                      varchar(128)                                                not null,
districtNameBn                           varchar(128)                                                not null,
divisionOid                              varchar(128)                                                not null,
districtNameEn                           varchar(128)                                                not null,
createdBy                                varchar(128)                                                not null       default 'System',
createdOn                                timestamp                                                   not null       default current_timestamp,
updatedBy                                varchar(128),
updatedOn                                timestamp,
constraint                               pk_District                                                 primary key    (oid),
constraint                               u_districtNameBn_District                                   unique         (districtNameBn),
constraint                               fk_divisionOid_District                                     foreign key    (divisionOid)
                                                                                                     references     Division(oid),
constraint                               u_districtNameEn_District                                   unique         (districtNameEn)
);

/*
This table to be used to store user District list.
oid                                      : Surrogate primary key
upazillaNameBn                           : Upazilla Name in Bangla
districtOid                              : District OId
upazillaNameEn                           : Upazilla Name in English
createdBy                                : Who created the district, default is System
createdOn                                : When it was created
updatedBy                                : Who last update the district
updatedOn                                : When it was updated
*/
create table                             Upazilla
(
oid                                      varchar(128)                                                not null,
upazillaNameBn                           varchar(128)                                                not null,
districtOid                              varchar(128)                                                not null,
upazillaNameEn                           varchar(128)                                                not null,
createdBy                                varchar(128)                                                not null       default 'System',
createdOn                                timestamp                                                   not null       default current_timestamp,
updatedBy                                varchar(128),
updatedOn                                timestamp,
constraint                               pk_Upazilla                                                 primary key    (oid),
constraint                               u_upazillaNameBn_Upazilla                                   unique         (upazillaNameBn),
constraint                               fk_districtOid_Upazilla                                     foreign key    (districtOid)
                                                                                                     references     District(oid),
constraint                               u_upazillaNameEn_Upazilla                                   unique         (upazillaNameEn)
);


