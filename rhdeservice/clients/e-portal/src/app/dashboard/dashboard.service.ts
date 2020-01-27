import { Injectable } from '@angular/core';
import { RestClient } from '../shared/services/rest-client';

@Injectable({
    providedIn: 'root'
})
export class DashboardService {

    constructor(private restClient: RestClient) {}

    
}
