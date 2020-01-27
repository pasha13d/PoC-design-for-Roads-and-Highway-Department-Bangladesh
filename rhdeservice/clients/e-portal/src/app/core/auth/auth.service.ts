import { LOGOUT_URL } from './../../shared/constant/api';
import { StorageService } from './../../shared/services/storage.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { E_SERVICE_URL, AUTH_TOKEN_URL } from '../../shared/constant/api';
import { ICurrentUser } from '../../shared/model/model/current-user';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { Observable } from 'rxjs/internal/Observable';
import { map } from 'rxjs/internal/operators/map';
import { RequestList } from '../../shared/model/request/request-list';
import { Router } from '@angular/router';
import { throwError } from 'rxjs/internal/observable/throwError';
import { RequestUser } from '../../shared/model/request/request-user';

@Injectable({ providedIn: 'root'})
export class AuthService {

    private currentUserSubject: BehaviorSubject<ICurrentUser>;
    public currentUser: Observable<ICurrentUser>;
    private refresh_token: string;
    constructor(private http: HttpClient, private storageService: StorageService, private _router: Router) {
        this.currentUserSubject = new BehaviorSubject<ICurrentUser>(JSON.parse(this.storageService.read('currentUser')));
        this.currentUser = this.currentUserSubject.asObservable();
    }

    public get currentUserValue(): ICurrentUser {
        return this.currentUserSubject.value;
    }

    login(username: string, password: string) {
        const creds = 'username=' + username + '&password=' + password + '&grant_type=password' + '&credentials=true';
        let headers = new HttpHeaders();
        headers = headers.append('Authorization', 'Basic ' + btoa('grp-web-portal:123456'));
        headers = headers.append('Content-Type', 'application/x-www-form-urlencoded;charset=UTF-8');
        return this.http.post<any>(E_SERVICE_URL + 'oauth/token', creds , { headers: headers}).pipe(map(user => {
            this.storageService.save('currentUser', JSON.stringify(user));
            this.currentUserSubject.next(user);
            return user;
        }));
    }

    logout() {
        const requestParams = new RequestUser();
        requestParams.header.requestType = LOGOUT_URL;
        requestParams.header.requestSourceService = 'logout';
        requestParams.body.operation = 'logout';
        let headers = new HttpHeaders();
        headers = headers.append('Authorization', 'Bearer ' + this.currentUserValue.access_token);
        headers = headers.append('Content-Type', 'application/json');
        this.http.post(E_SERVICE_URL + LOGOUT_URL, requestParams , { headers: headers})
        .subscribe(user => {
            this.storageService.clear();
            this.currentUserSubject.next(null);
            this._router.navigateByUrl('/login');
        });
    }

    logoutWithoutToken() {
        this.storageService.clear();
        this.currentUserSubject.next(null);
        this._router.navigateByUrl('/login');
        return throwError('Your session has expired!, try again');
    }

    refreshToken(): Observable<ICurrentUser> {
        const currentUser = this.currentUserValue;
        if (currentUser && currentUser.refresh_token) {
            this.refresh_token = currentUser.refresh_token;
        }
        const creds = 'token=' + this.refresh_token + '&grant_type=refresh_token';
        return this.http.post<ICurrentUser>(E_SERVICE_URL + AUTH_TOKEN_URL, creds)
          .pipe(map(user => {
              if (user && user.access_token) {
                this.storageService.save('currentUser', JSON.stringify(user));
              }
              return <ICurrentUser>user;
        }));
    }
}
