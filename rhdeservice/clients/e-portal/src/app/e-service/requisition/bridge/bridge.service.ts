import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { RestClient } from '../../../shared/services/rest-client';
import { E_SERVICE_URL } from '../../../shared/constant/api';

@Injectable({
  providedIn: 'root'
})
export class BridgeService {

  constructor(private restClient: RestClient) { }

  getList(): Observable<any> {
    return this.restClient.get('https://jsonplaceholder.typicode.com/users');
  }
}
