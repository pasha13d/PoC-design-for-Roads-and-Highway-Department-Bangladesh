/*
Roles used for users
oid                                      : Surrogate primary key
roleId                                   : Role Id
roleDescription                          : Description of Role
status                                   : Role status
accessJson                               : Access JSON array comes from ANP sheet
createdBy                                : Who created the role, default is System
createdOn                                : When it was created
updatedBy                                : Who last update the role
updatedOn                                : When it was updated
*/
create table                             Role
(
oid                                      varchar(128)                                                not null,
roleId                                   varchar(128)                                                not null,
roleDescription                          text                                                        not null,
status                                   varchar(32)                                                 not null,
accessJson                               text,
createdBy                                varchar(128)                                                not null       default 'System',
createdOn                                timestamp                                                   not null       default current_timestamp,
updatedBy                                varchar(128),
updatedOn                                timestamp,
constraint                               pk_Role                                                     primary key    (oid),
constraint                               u_roleId_Role                                               unique         (roleId),
constraint                               ck_status_Role                                              check          (status = 'Active' or status = 'Inactive')
);

/*
This table to be used to store user Login information.
oid                                      : Surrogate primary key
username                                 : Login Id
password                                 : Password
nameEn                                   : User name of User
nameBn                                   : User name of User
email                                    : Email id
mobileNo                                 : Mobile no
menuJson                                 : Bank Id
userPhotoPath                            : user Photo path
status                                   : Who deactivated
resetRequired                            : Is password required
roleOid                                  : Role oid
account_expired                          : 
credentials_expired                      : 
account_locked                           : 
traceId                                  : Trace Id of this record
currentVersion                           : Count of how many times a record is being approved. Each count is treated as a new version. Ex: 1,2,......
editedBy                                 : Who (which login) last edited the record
editedOn                                 : When the record was last edited
approvedBy                               : Who (which login) approved the record
approvedOn                               : When the record was approved
remarkedBy                               : Who (which login) remarked the record
remarkedOn                               : When the record was remarked
isApproverRemarks                        : Status if approver add any remarks. It will be Yes or No
approverRemarks                          : Approver remarks for further action
isNewRecord                              : Record will show up new for a specific time. It will be Yes or No
createdBy                                : Who (which login) created the record
createdOn                                : When the record was created
activatedBy                              : Who (which login) activated the record
activatedOn                              : When the record was activated
closedBy                                 : Who (which login) closed the record
closedOn                                 : When the record was closed
closingRemark                            : Closing Remarks
deletedBy                                : Who (which login) deleted the record
deletedOn                                : When the record was deleted
deletionRemark                           : Delition remarks
*/
create table                             Login
(
oid                                      varchar(128)                                                not null,
username                                 varchar(128)                                                not null,
password                                 varchar(256)                                                not null,
nameEn                                   varchar(128)                                                not null,
nameBn                                   varchar(128)                                                not null,
email                                    varchar(256),
mobileNo                                 varchar(64),
menuJson                                 text,
userPhotoPath                            varchar(512),
status                                   varchar(128),
resetRequired                            varchar(32),
roleOid                                  varchar(128)                                                not null,
account_expired                          varchar(32)                                                 not null       default 'No',
credentials_expired                      varchar(32)                                                 not null       default 'No',
account_locked                           varchar(32)                                                 not null       default 'No',
traceId                                  varchar(128),
currentVersion                           varchar(32)                                                 not null       default '1',
editedBy                                 varchar(128),
editedOn                                 timestamp,
approvedBy                               varchar(128),
approvedOn                               timestamp,
remarkedBy                               varchar(128),
remarkedOn                               timestamp,
isApproverRemarks                        varchar(32),
approverRemarks                          text,
isNewRecord                              varchar(32),
createdBy                                varchar(128)                                                not null       default 'System',
createdOn                                timestamp                                                   not null       default current_timestamp,
activatedBy                              varchar(128),
activatedOn                              timestamp,
closedBy                                 varchar(128),
closedOn                                 timestamp,
closingRemark                            text,
deletedBy                                varchar(128),
deletedOn                                timestamp,
deletionRemark                           text,
constraint                               pk_Login                                                    primary key    (oid),
constraint                               u_username_Login                                            unique         (username),
constraint                               ck_status_Login                                             check          (status = 'Active' or status = 'Inactive'),
constraint                               fk_roleOid_Login                                            foreign key    (roleOid)
                                                                                                     references     Role(oid),
constraint                               ck_account_expired_Login                                    check          (account_expired = 'Yes' or account_expired = 'No'),
constraint                               ck_credentials_expired_Login                                check          (credentials_expired = 'Yes' or credentials_expired = 'No'),
constraint                               ck_account_locked_Login                                     check          (account_locked = 'Yes' or account_locked = 'No'),
constraint                               ck_isApproverRemarks_Login                                  check          (isApproverRemarks = 'Yes' or isApproverRemarks = 'No'),
constraint                               ck_isNewRecord_Login                                        check          (isNewRecord = 'Yes' or isNewRecord = 'No')
);

/*
This table to be used to store user Registration information.
oid                                      : Surrogate primary key
designation                              : Designation of user
userName                                 : Login Id
password                                 : Password
nameBn                                   : User name of User
nameEn                                   : User name of User
fatherName                               : Father's name
motherName                               : Mother's name
gender                                   : Gender
dateOfBirth                              : Date of birth of user
email                                    : Email id
mobileNo                                 : Mobile no
nidNo                                    : National Id card no
presentAddress                           : Present Address of user
permanentAddress                         : Permanent Address of user
status                                   : User registration status
comment                                  : Give comment on approval or received purpose
receivedBy                               : Received by
createdBy                                : Who submited the registration data, default is System
createdOn                                : When it was created
updatedBy                                : Who last update the registration data
updatedOn                                : When it was updated
*/
create table                             Registration
(
oid                                      varchar(128)                                                not null,
designation                              varchar(128)                                                not null,
userName                                 varchar(128)                                                not null,
password                                 varchar(256)                                                not null,
nameBn                                   varchar(128)                                                not null,
nameEn                                   varchar(128)                                                not null,
fatherName                               varchar(128),
motherName                               varchar(128),
gender                                   varchar(128),
dateOfBirth                              date                                                        not null,
email                                    varchar(256),
mobileNo                                 varchar(64),
nidNo                                    varchar(64)                                                 not null,
presentAddress                           varchar(256),
permanentAddress                         varchar(256),
status                                   varchar(128),
comment                                  varchar(128),
receivedBy                               varchar(128),
createdBy                                varchar(128)                                                not null       default 'System',
createdOn                                timestamp                                                   not null       default current_timestamp,
updatedBy                                varchar(128),
updatedOn                                timestamp,
constraint                               pk_Registration                                             primary key    (oid),
constraint                               u_userName_Registration                                     unique         (userName),
constraint                               ck_status_Registration                                      check          (status = 'Submitted' or status = 'Approved')
);


