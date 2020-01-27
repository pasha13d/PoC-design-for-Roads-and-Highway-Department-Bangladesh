
// export const E_SERVICE_URL = 'http://localhost:9000/e-service/';
export const E_SERVICE_URL = 'http://192.168.0.170:9000/e-service/';

export const AUTH_TOKEN_URL = 'oauth/token';
export const LOGOUT_URL = 'authentication/user/v1/logout';
export const GET_USER_INFO = 'authentication/user/v1/get-user-info-by-username';

export const SAVE_BRIDGE = 'authentication/user/v1/get-user-info-by-username';

export const SAVE_REGISTERATION_INFO = 'public/authentication/registration/v1/save';
export const GET_REGISTERATION_LIST = 'authentication/registration/v1/get-list';
export const GET_REGISTRATION_DETAILS = 'authentication/registration/v1/get-by-oid';
export const REGISTRATION_APPROVAL = 'authentication/registration/v1/registration-approved';
export const REGISTRATION_RECEIVED = 'authentication/registration/v1/registration-received';

export const DESIGNATION_LIST = 'public/master/designation/v1/get-list';
export const DIVISION_LIST = 'master/division/v1/get-list';
export const DISTRICT_LIST = 'master/district/v1/get-list';
export const UPAZILLA_LIST = 'master/upazilla/v1/get-list';
export const STEP_LIST = 'master/step/v1/get-step-list-by-processOid';
export const SAVE_STEP_INSTANCE = 'master/step/v1/save-requisition-instance';
export const LOGIN_STEP_MAPPING = 'master/step/v1/get-login-step-mapping';


export const SAVE_ROAD = 'requisition/road/v1/save';
export const GET_ROAD_LIST = 'requisition/road/v1/get-list';
export const GET_FORM_COMPONENT_ID = 'requisition/road/v1/get-formComponentID-by-oid';
export const ROAD_GET_BY_OID = 'requisition/road/v1/get-by-oid';
export const ROAD_STATUS_CHECK = 'requisition/road/v1/status-check';
export const ROAD_ASSING_PERSON = 'requisition/road/v1/assign-person';
export const ROAD_STATE_DETAILS = 'requisition/road/v1/get-state-details';

export const SAVE_ROAD_COMMENT = 'requisition/road/v1/comment-save';
export const GET_ROAD_COMMENT_LIST = 'requisition/road/v1/get-comment-list';

export const SEND_MAIL = 'master/email/v1/send-email';
export const FILE_UPLOAD = 'master/document/v1/upload';
export const FILE_DOWNLOAD='master/document/v1/download';
export const GET_STEPLIST_BY_PROCESSOID = 'master/step/v1/get-step-list-by-processOid';
export const GET_INSTANCE_STEPLIST = 'master/step/v1/get-instance-step-list';