import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { throwError } from 'rxjs/internal/observable/throwError';
import { Observable } from 'rxjs/internal/Observable';
import { AuthService } from '../../core/auth/auth.service';
@Injectable({ providedIn: 'root' })
export class RestClient {
   private token = '';
   private httpOptions = {};
   constructor(private _http: HttpClient, private authService: AuthService) {
   }
   private get _headers(): any {
       const currentUser = this.authService.currentUserValue;
       if (currentUser && currentUser.access_token) {
            this.token = currentUser.access_token;
       }
       if (currentUser && currentUser.access_token) {
            this.httpOptions = {
                headers: new HttpHeaders({
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    'Authorization': 'Bearer ' + this.token
                })
            };
        }else{
            this.httpOptions = {
                headers: new HttpHeaders({
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                })
            };

        }
       return this.httpOptions;
   }
   getFromFile(url: string): any {
       return this._http.get(url, this._headers);
   }
   get(url: string): Observable<any> {
       try {
           return this._http.get<Observable<any>>(url, this._headers);
       } catch (err) {
           return throwError(err.message);
       }
   }
   post(url: string, data: any): Observable<any> {
       try {
           return this._http.post<any>(url, data, this._headers);
       } catch (err) {
           return throwError(err.message);
       }
   }
   filePost(url: string, data: any): Observable<any> {
        const currentUser = this.authService.currentUserValue;
        if (currentUser && currentUser.access_token) {
            this.token = currentUser.access_token;
        }
        this.httpOptions = {
            headers: new HttpHeaders({
                'Accept': 'application/json',
                'Authorization': 'Bearer ' + this.token                
            }), 'responseType' : 'blob'
        };
       try {
           return this._http.post<any>(url, data, this.httpOptions);
       } catch (err) {
           return throwError(err.message);
       }
   }
   
}
