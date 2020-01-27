import * as uuid from 'uuid';
import { APPLICATION_NAME, CLIENT, VERSION } from '../../constant/constant';
import { SendMail } from '../model/send-mail';

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
    sendMail= new SendMail();
}

export class SendMailRequest {
    header: Header = new Header();
    meta: Object = new Object();
    body: Body = new Body();
}
