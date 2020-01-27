import { Injectable } from '@angular/core';
import { RestClient } from '../../../shared/services/rest-client';
import { RequestList } from '../../../shared/model/request/request-list';
import { RequestByOid } from '../../../shared/model/request/request-by-oid';
import { SaveRequest, StatusCheckRequest, SaveCommentRequest, StepInstanceRequest, FileDownloadRequest} from './road-request';
import {
  E_SERVICE_URL, DISTRICT_LIST, UPAZILLA_LIST, SAVE_ROAD, SAVE_ROAD_COMMENT, GET_ROAD_LIST, 
  GET_FORM_COMPONENT_ID, ROAD_GET_BY_OID,  ROAD_STATUS_CHECK, GET_ROAD_COMMENT_LIST, ROAD_ASSING_PERSON,

  ROAD_STATE_DETAILS, FILE_UPLOAD, GET_INSTANCE_STEPLIST,  STEP_LIST, SAVE_STEP_INSTANCE, LOGIN_STEP_MAPPING, FILE_DOWNLOAD} from '../../../shared/constant/api';
import { HttpRequest, HttpHeaders, HttpClient } from '@angular/common/http';
import { AuthService } from '../../../core/auth/auth.service';
import { headersToString } from 'selenium-webdriver/http';



@Injectable({
  providedIn: 'root'
})
export class RoadService {


  constructor(private restClient: RestClient, private _http: HttpClient, private authService: AuthService) { }

  getRoadList() {
    const requestParams = new RequestList();
    requestParams.header.requestType = GET_ROAD_LIST;
    requestParams.header.requestSourceService = 'get-list';
    return this.restClient.post(E_SERVICE_URL + GET_ROAD_LIST, requestParams);
  }

  getRoadDetails(roadOid) {
    const requestParams = new RequestByOid();
    requestParams.header.requestType = ROAD_GET_BY_OID;
    requestParams.header.requestSourceService = 'get-by-oid';
    requestParams.body.oid = roadOid;
    return this.restClient.post(E_SERVICE_URL + ROAD_GET_BY_OID, requestParams);
  }

  upload(file: File, formData: any) {
    const currentUser = this.authService.currentUserValue;
    if (!file) { return; }
    const bearer = 'Bearer {0}'.replace('{0}', currentUser.access_token);
    const req = new HttpRequest('POST', E_SERVICE_URL + FILE_UPLOAD, formData, {
      headers: new HttpHeaders().set('Authorization', bearer),
      reportProgress: true
    });
    return this._http.request(req);
  }

  // fileDownload(fileName){
  //   const currentUser = this.authService.currentUserValue;
  //   const requestParams = new FileDownloadRequest();
  //   if (!fileName) { return; }
  //   const bearer = 'Bearer {0}'.replace('{0}', currentUser.access_token);
  //   const req = new HttpRequest('POST', E_SERVICE_URL + FILE_DOWNLOAD, fileName, {
  //     headers: new HttpHeaders().set('Authorization', bearer)
  //     responseType: 'arraybuffer',
  //     // -e: 'application/json',
  //     reportProgress: true
  //   });
  //   return this._http.request(req);
  // }

  fileDownload(fileName){
    const requestParams = new FileDownloadRequest();
    requestParams.header.requestType = FILE_DOWNLOAD;
    requestParams.header.requestSourceService = 'download';
    requestParams.body.fileName = fileName;
    return this.restClient.filePost(E_SERVICE_URL + FILE_DOWNLOAD, requestParams);
  }

  getFormComponetID(roadOid) {
    const requestParams = new RequestByOid();
    requestParams.header.requestType = GET_FORM_COMPONENT_ID;
    requestParams.header.requestSourceService = 'get-formComponentID-by-oid';
    requestParams.body.oid = roadOid;
    return this.restClient.post(E_SERVICE_URL + GET_FORM_COMPONENT_ID, requestParams);
  }
  getDistrictList(divisionOid) {
    const requestParams = new RequestByOid();
    requestParams.header.requestType = DISTRICT_LIST;
    requestParams.header.requestSourceService = 'get-list';
    requestParams.body.oid = divisionOid;
    return this.restClient.post(E_SERVICE_URL + DISTRICT_LIST, requestParams);
  }
  getUpazillaList(districtOid) {
    const requestParams = new RequestByOid();
    requestParams.header.requestType = UPAZILLA_LIST;
    requestParams.header.requestSourceService = 'get-list';
    requestParams.body.oid = districtOid;
    return this.restClient.post(E_SERVICE_URL + UPAZILLA_LIST, requestParams);
  }

  saveRoad(body) {
    const requestParams = new SaveRequest();
    requestParams.header.requestType = SAVE_ROAD;
    requestParams.header.requestSourceService = 'save';
    requestParams.body.road = body ;
    return this.restClient.post(E_SERVICE_URL + SAVE_ROAD, requestParams);
  }
  
  updateNextStep(nextStep ){
    const requestParams = new StatusCheckRequest();
    requestParams.header.requestType = ROAD_STATUS_CHECK;
    requestParams.header.requestSourceService = 'status-check';
    requestParams.body.statusCheck = nextStep ;
    return this.restClient.post(E_SERVICE_URL + ROAD_STATUS_CHECK, requestParams);
    
  }
  verifyRoad(verifyBody ){
    const requestParams = new StatusCheckRequest();
    requestParams.header.requestType = ROAD_STATUS_CHECK;
    requestParams.header.requestSourceService = 'status-verify';
    requestParams.body.statusCheck = verifyBody ;
    return this.restClient.post(E_SERVICE_URL + ROAD_STATUS_CHECK, requestParams);
  }

  approveRoad(approveBody){
    const requestParams = new StatusCheckRequest();
    requestParams.header.requestType = ROAD_STATUS_CHECK;
    requestParams.header.requestSourceService = 'status-approved';
    requestParams.body.statusCheck = approveBody ;
    return this.restClient.post(E_SERVICE_URL + ROAD_STATUS_CHECK, requestParams);
  }

  assignPerson(assignPerson){
    const requestParams = new StatusCheckRequest();
    requestParams.header.requestType = ROAD_ASSING_PERSON;
    requestParams.header.requestSourceService = 'status-approved';
    requestParams.body.statusCheck = assignPerson ;
    return this.restClient.post(E_SERVICE_URL + ROAD_ASSING_PERSON, requestParams);
  }
  
  saveComment(body) {
    const requestParams = new SaveCommentRequest();
    requestParams.header.requestType = SAVE_ROAD_COMMENT;
    requestParams.header.requestSourceService = 'comment-save';
    requestParams.body.comment = body ;
    return this.restClient.post(E_SERVICE_URL + SAVE_ROAD_COMMENT, requestParams);
  }

  getAllComment(roadRequisitionOid) {
    const requestParams = new RequestByOid();
    requestParams.header.requestType = GET_ROAD_COMMENT_LIST;
    requestParams.header.requestSourceService = 'get-comment-list';
    requestParams.body.oid = roadRequisitionOid;
    return this.restClient.post(E_SERVICE_URL + GET_ROAD_COMMENT_LIST, requestParams);
  }

  getStepList(processOid) {
    const requestParams = new RequestByOid();
    requestParams.header.requestType = STEP_LIST;
    requestParams.header.requestSourceService = 'get-step-list';
    requestParams.body.oid = processOid;
    return this.restClient.post(E_SERVICE_URL + STEP_LIST, requestParams);
  }
  
  saveStepInstance(instance) {
    const requestParams = new StepInstanceRequest();
    requestParams.header.requestType = SAVE_STEP_INSTANCE;
    requestParams.header.requestSourceService = 'save-step-instance';
    requestParams.body.requisitionInstance = instance;
    console.log(requestParams)
    return this.restClient.post(E_SERVICE_URL + SAVE_STEP_INSTANCE, requestParams);
  }

  getStateDetails(oid){
    const requestParams = new RequestByOid();
    requestParams.header.requestType = ROAD_STATE_DETAILS;
    requestParams.header.requestSourceService = 'get-state-list';
    requestParams.body.oid = oid;
    return this.restClient.post(E_SERVICE_URL + ROAD_STATE_DETAILS, requestParams);

  }
  
  getInstanceStepList(oid){
    const requestParams = new RequestByOid();
    requestParams.header.requestType = GET_INSTANCE_STEPLIST;
    requestParams.header.requestSourceService = 'get-instance-step-list';
    requestParams.body.oid = oid;
    return this.restClient.post(E_SERVICE_URL + GET_INSTANCE_STEPLIST, requestParams);
  }
  getLoginingStepMapping(oid) {
    const requestParams = new RequestByOid();
    requestParams.header.requestType = LOGIN_STEP_MAPPING;
    requestParams.header.requestSourceService = 'get-login-step-mapping';
    requestParams.body.oid = oid;
    return this.restClient.post(E_SERVICE_URL + LOGIN_STEP_MAPPING, requestParams);
  }
}
