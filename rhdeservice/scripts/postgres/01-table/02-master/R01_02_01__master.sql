/*
This table to be used to store process for features
oid                                      : Surrogate primary key
processName                              : name of the process
createdBy                                : who created the process
createdOn                                : When it was created
updatedBy                                : Who last update the process
updatedOn                                : When it was updated
*/
create table                             Process
(
oid                                      varchar(128)                                                not null,
processName                              varchar(256)                                                not null,
createdBy                                varchar(128)                                                not null       default 'System',
createdOn                                timestamp                                                   not null       default current_timestamp,
updatedBy                                varchar(128),
updatedOn                                timestamp,
constraint                               pk_Process                                                  primary key    (oid)
);

/*
This table to be used to store step for process
oid                                      : Surrogate primary key
processOid                               : Process oid
stepName                                 : name of the step
sortOrder                                : order of the step
formComponentID                          : ID of the from compoent
forward                                  : can this step forward
backward                                 : 
approve                                  : 
nextStepOid                              : 
status                                   : status of the step
createdBy                                : who created the process
createdOn                                : When it was created
updatedBy                                : Who last update the process
updatedOn                                : When it was updated
*/
create table                             Step
(
oid                                      varchar(128)                                                not null,
processOid                               varchar(128)                                                not null,
stepName                                 varchar(256)                                                not null,
sortOrder                                numeric(128),
formComponentID                          varchar(128)                                                not null,
forward                                  varchar(128),
backward                                 varchar(128),
approve                                  varchar(128),
nextStepOid                              varchar(128),
status                                   varchar(128)                                                not null,
createdBy                                varchar(128)                                                not null       default 'System',
createdOn                                timestamp                                                   not null       default current_timestamp,
updatedBy                                varchar(128),
updatedOn                                timestamp,
constraint                               pk_Step                                                     primary key    (oid),
constraint                               fk_processOid_Step                                          foreign key    (processOid)
                                                                                                     references     Process(oid)
);

/*
This table to be used to mpping step with user
oid                                      : This table to be used to mpping step with user
loginId                                  : This table to be used to mpping step with user
stepOid                                  : This table to be used to mpping step with user
createdBy                                : Who created the data, defaut is System
createdOn                                : When it was created
updatedBy                                : Who last update the data
updatedOn                                : When it was updated
*/
create table                             Login_Step_Mapping
(
oid                                      varchar(128)                                                not null,
loginId                                  varchar(128)                                                not null,
stepOid                                  varchar(128)                                                not null,
createdBy                                varchar(128)                                                not null       default 'System',
createdOn                                timestamp                                                   not null       default current_timestamp,
updatedBy                                varchar(128),
updatedOn                                timestamp,
constraint                               pk_Login_Step_Mapping                                       primary key    (oid),
constraint                               fk_stepOid_Login_Step_Mapping                               foreign key    (stepOid)
                                                                                                     references     Step(oid)
);


