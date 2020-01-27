/*
This table to be used to store road data
oid                                      : Surrogate primary key
divisionOid                              : Divison oid
districtOid                              : District oid
upazillaOid                              : Upazilla.oid
postalCode                               : postal code
location                                 : location - village or union
startPoint                               : start point
endPoint                                 : end point
isriverOrWaterbodynear                   : water body name
purpose                                  : puropose of the road
status                                   : puropose of the road
nextStep                                 : In which step the process is
comment                                  : Comment for the requisition
createdBy                                : Who created the district, default is System
createdOn                                : When it was created
updatedBy                                : Who last update the district
updatedOn                                : When it was updated
*/
create table                             Road_Requisition
(
oid                                      varchar(128)                                                not null,
divisionOid                              varchar(128)                                                not null,
districtOid                              varchar(128)                                                not null,
upazillaOid                              varchar(128)                                                not null,
postalCode                               varchar(128)                                                not null,
location                                 varchar(128)                                                not null,
startPoint                               varchar(128)                                                not null,
endPoint                                 varchar(128)                                                not null,
isriverOrWaterbodynear                   varchar(128),
purpose                                  text                                                        not null,
status                                   varchar                                                     not null,
nextStep                                 varchar                                                     not null,
comment                                  text,
createdBy                                varchar(128)                                                not null       default 'System',
createdOn                                timestamp                                                   not null       default current_timestamp,
updatedBy                                varchar(128),
updatedOn                                timestamp,
constraint                               pk_Road_Requisition                                         primary key    (oid),
constraint                               fk_divisionOid_Road_Requisition                             foreign key    (divisionOid)
                                                                                                     references     Division(oid),
constraint                               fk_districtOid_Road_Requisition                             foreign key    (districtOid)
                                                                                                     references     District(oid),
constraint                               fk_upazillaOid_Road_Requisition                             foreign key    (upazillaOid)
                                                                                                     references     Upazilla(oid)
);

/*
Requisition Comment Information
oid                                      : Surrogate primary key
roadRequisitionOid                       : Requisition oid to tag Requisition with Requisition comment
comment                                  : comment of requisition
stepOid                                  : Step oid to tag Step with Requisition comment
createdBy                                : who created the record, default is System
createdOn                                : when the record was created
updatedBy                                : who last updated the record
updatedOn                                : when the record was last updated
*/
create table                             Road_Requisition_Comment
(
oid                                      varchar(128)                                                not null,
roadRequisitionOid                       varchar(128)                                                not null,
comment                                  text                                                        not null,
stepOid                                  varchar(128)                                                not null,
createdBy                                varchar(128)                                                not null       default 'System',
createdOn                                timestamp                                                   not null       default current_timestamp,
updatedBy                                varchar(128),
updatedOn                                timestamp,
constraint                               pk_Road_Requisition_Comment                                 primary key    (oid),
constraint                               fk_roadRequisitionOid_Road_Requisition_Comment              foreign key    (roadRequisitionOid)
                                                                                                     references     Road_Requisition(oid),
constraint                               fk_stepOid_Road_Requisition_Comment                         foreign key    (stepOid)
                                                                                                     references     Step(oid)
);

/*
Requisition State Information
oid                                      : Surrogate primary key
roadRequisitionOid                       : Requisition oid to tag Requisition with Requisition comment
stepOid                                  : Step oid to tag Step with Road Requisition 
createdBy                                : who created the record, default is System
createdOn                                : when the record was created
updatedBy                                : who last updated the record
updatedOn                                : when the record was last updated
*/
create table                             Road_Requisition_State
(
oid                                      varchar(128)                                                not null,
roadRequisitionOid                       varchar(128)                                                not null,
stepOid                                  varchar(128)                                                not null,
createdBy                                varchar(128)                                                not null       default 'System',
createdOn                                timestamp                                                   not null       default current_timestamp,
updatedBy                                varchar(128),
updatedOn                                timestamp,
constraint                               pk_Road_Requisition_State                                   primary key    (oid),
constraint                               fk_roadRequisitionOid_Road_Requisition_State                foreign key    (roadRequisitionOid)
                                                                                                     references     Road_Requisition(oid),
constraint                               fk_stepOid_Road_Requisition_State                           foreign key    (stepOid)
                                                                                                     references     Step(oid)
);


