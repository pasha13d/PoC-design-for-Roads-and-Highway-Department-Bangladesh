import * as uuid from 'uuid';
import { APPLICATION_NAME, CLIENT, VERSION } from '../../../shared/constant/constant';
import { Registration } from '../../../shared/model/model/registration';

export class Header {
    requestId = uuid.v4();
    requestClient = CLIENT;
    requestType: string;
    requestSource = APPLICATION_NAME;
    requestSourceService: string;
    requestVersion = VERSION;
    requestTime: Date = new Date();
    requestTimeoutInSeconds = 20;
    requestRetryCount = 0;
}
class Body {
   registration = new Registration();
}

export class SaveRequest {
    header: Header = new Header();
    meta: Object = new Object();
    body: Body = new Body();
}

export class ApproveRequest {
    header: Header = new Header();
    meta: Object = new Object();
    body: Body = new Body();
}

export class ReceiveRequest {
    header: Header = new Header();
    meta: Object = new Object();
    body: Body = new Body();
}
