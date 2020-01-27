import { AuthService } from './../../core/auth/auth.service';
import { Observable } from 'rxjs/internal/Observable';
import { HttpEvent,    HttpInterceptor,    HttpHandler,    HttpRequest,    HttpResponse,    HttpErrorResponse } from '@angular/common/http';
import { retry, catchError, switchMap, filter, take } from 'rxjs/operators';
import { throwError } from 'rxjs/internal/observable/throwError';
import { Injectable, Injector } from '@angular/core';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import 'rxjs/add/operator/catch';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {
    private isRefreshing = false;
    private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(null);
    constructor(private authService: AuthService) {}
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).catch(error => {
            if (error instanceof HttpErrorResponse && error.status === 401) {
                // return this.handle401Error(request, next);
                return <any>this.authService.logoutWithoutToken();
            } else if (error.status === 400) {
                return throwError(error);
                // return <any>this.authService.logoutWithoutToken();
            } else {
                return throwError(error);
            }
        });
    }

    private addToken(request: HttpRequest<any>, token: string) {
        return request.clone({
          setHeaders: {
            'Authorization': `Bearer ${token}`
          }
        });
    }


    // private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    //   debugger
    //     if (!this.isRefreshing) {
    //       this.isRefreshing = true;
    //       this.refreshTokenSubject.next(null);
    //       return this.authService.refreshToken().pipe(
    //         switchMap((token: any) => {
    //           this.isRefreshing = false;
    //           this.refreshTokenSubject.next(token.access_token);
    //           return next.handle(this.addToken(request, token.access_token));
    //         }));
    //     } else {
    //       return this.refreshTokenSubject.pipe(
    //         filter(token => token != null),
    //         take(1),
    //         switchMap(accessToken => {
    //           return next.handle(this.addToken(request, accessToken));
    //         }));
    //     }
    // }

}
