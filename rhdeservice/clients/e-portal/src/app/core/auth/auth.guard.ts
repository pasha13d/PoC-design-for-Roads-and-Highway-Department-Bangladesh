import { StorageService } from './../../shared/services/storage.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/internal/Observable';
import { AuthService } from './auth.service';

@Injectable({ providedIn: 'root'})
export class AuthGuard implements CanActivate {

    constructor(private _router: Router, private authService: AuthService, private storageService: StorageService) {
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {
        const currentRole = this.storageService.read('currentRoleOid');
        if (this.authService.currentUserValue) {
            if (route.data.roles && route.data.roles.indexOf(currentRole) === -1) {
                // role not authorised so redirect to home page
                this._router.navigate(['/dashboard']);
                return false;
            }
            return true;
          }
          this._router.navigate(['/login'], {queryParams: {returnUrl: state.url}});
          return false;
    }
}
