import { Injectable } from '@angular/core';
import { SlackService } from './slack.service';

@Injectable({
    providedIn: 'root'
})
export class LoggingService {

    constructor(private slackService: SlackService) { }

    logError(message: Error, stack?: string) {
        console.error(message, stack);
        // this.slackService.postErrorOnSlack(message);
    }
}
