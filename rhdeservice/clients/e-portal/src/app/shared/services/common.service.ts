import { Injectable } from '@angular/core';
import { RestClient } from './rest-client';
import { RequestUser } from '../model/request/request-user';
import { GET_USER_INFO, E_SERVICE_URL, SEND_MAIL } from '../constant/api';
import { SendMailRequest } from '../model/request/send-mail';

@Injectable({
    providedIn: 'root'
})
export class CommonService {

    constructor(private restClient: RestClient) {}

    getUserBasicInfo() {
        const requestParams = new RequestUser();
        requestParams.header.requestType = GET_USER_INFO;
        requestParams.header.requestSourceService = 'get-user-info-by-username';
        requestParams.body.operation = 'GetUserInfo';
        return this.restClient.post(E_SERVICE_URL + GET_USER_INFO, requestParams);
    }
    
    sendMail(body){
        const requestParams = new SendMailRequest();
        requestParams.header.requestType = SEND_MAIL;
        requestParams.header.requestSourceService = 'send-email';
        requestParams.body.sendMail = body ;
        return this.restClient.post(E_SERVICE_URL + SEND_MAIL, requestParams);
      }
}
