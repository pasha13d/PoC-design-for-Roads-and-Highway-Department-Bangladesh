import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { RestClient } from '../../shared/services/rest-client';
import { RequestByOid } from '../../shared/model/request/request-by-oid';
import { SaveRequest} from '../../e-service/registration/registration-list/registration-request';
import { ApproveRequest} from '../../e-service/registration/registration-list/registration-request';
import { ReceiveRequest} from '../../e-service/registration/registration-list/registration-request';
import { E_SERVICE_URL, SAVE_REGISTERATION_INFO, DESIGNATION_LIST, GET_REGISTERATION_LIST, GET_REGISTRATION_DETAILS, REGISTRATION_APPROVAL, REGISTRATION_RECEIVED, SEND_MAIL} from '../../shared/constant/api';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private restClient: RestClient) { }

  getList(): Observable<any> {
    return this.restClient.get('https://jsonplaceholder.typicode.com/users');
  }

  //get all registration information and show them
  getRegistrationList() {
    const requestParams = new RequestByOid();
    requestParams.header.requestType = GET_REGISTERATION_LIST;
    requestParams.header.requestSourceService = 'get-list';
    requestParams.body.oid = 'Submitted';
    return this.restClient.post(E_SERVICE_URL + GET_REGISTERATION_LIST, requestParams);
  }

  //get all registration information to show into grid after clicking on details
  getRegistrationDetails(registrationOid) {
    const requestParams = new RequestByOid();
    requestParams.header.requestType = GET_REGISTRATION_DETAILS;
    requestParams.header.requestSourceService = 'get-by-oid';
    requestParams.body.oid = registrationOid;
    return this.restClient.post(E_SERVICE_URL + GET_REGISTRATION_DETAILS, requestParams);
  }

  //get designation list from DB
  getDesignationList(designationOid) {
    const requestParams = new RequestByOid();
    requestParams.header.requestType = DESIGNATION_LIST;
    requestParams.header.requestSourceService = 'get-list';
    requestParams.body.oid = designationOid;
    return this.restClient.post(E_SERVICE_URL + DESIGNATION_LIST, requestParams);
  }

  //save registration
  registerInfo(body) {
    const requestParams = new SaveRequest();
    requestParams.header.requestType = SAVE_REGISTERATION_INFO;
    requestParams.header.requestSourceService = 'save';
    requestParams.body.registration = body ;
    console.log(requestParams.body.registration);
    console.log(requestParams);
    return this.restClient.post(E_SERVICE_URL + SAVE_REGISTERATION_INFO, requestParams);
  }
  
  //registration approval
  regApproved(body){
    const requestParams = new ApproveRequest();
    requestParams.header.requestType = REGISTRATION_APPROVAL;
    requestParams.header.requestSourceService = 'registration-approved';
    requestParams.body.registration = body ;
    console.log(requestParams.body.registration)
    console.log(requestParams);
    return this.restClient.post(E_SERVICE_URL + REGISTRATION_APPROVAL, requestParams);
  }

  //registration received
  regReceived(body){
    const requestParams = new ReceiveRequest();
    requestParams.header.requestType = REGISTRATION_RECEIVED;
    requestParams.header.requestSourceService = 'registration-received';
    requestParams.body.registration = body;
    console.log(requestParams.body.registration)
    console.log(requestParams);
    return this.restClient.post(E_SERVICE_URL + REGISTRATION_RECEIVED, requestParams);
  }

  
}
