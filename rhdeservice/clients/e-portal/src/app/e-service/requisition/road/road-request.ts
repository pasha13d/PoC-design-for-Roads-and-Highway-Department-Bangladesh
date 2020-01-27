import * as uuid from 'uuid';
import { APPLICATION_NAME, CLIENT, VERSION } from '../../../shared/constant/constant';
import { Road, StatusCheck, Comment, StepInstance, FileDownload } from '../../../shared/model/model/road';

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
   road = new Road();
}

export class SaveRequest {
    header: Header = new Header();
    meta: Object = new Object();
    body: Body = new Body();
}

class CommentBody {
    comment = new Comment();
}

export class SaveCommentRequest {
    header: Header = new Header();
    meta: Object = new Object();
    body: CommentBody = new CommentBody();
}

class StatusCheckBody {
    statusCheck = new StatusCheck();
 }
export class StatusCheckRequest {
    header: Header = new Header();
    meta: Object = new Object();
    body: StatusCheckBody = new StatusCheckBody();
}

class StepInstanceBody {
    requisitionInstance = new StepInstance();
 }
export class StepInstanceRequest {
    header: Header = new Header();
    meta: Object = new Object();
    body: StepInstanceBody = new StepInstanceBody();
}

class FileDownloadBody {
    fileName = new FileDownload();
 }
export class FileDownloadRequest {
    header: Header = new Header();
    meta: Object = new Object();
    body: FileDownloadBody = new FileDownloadBody();
}
