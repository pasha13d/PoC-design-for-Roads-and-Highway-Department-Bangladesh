import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { AuthService } from '../auth/auth.service';
import { first } from 'rxjs/internal/operators/first';
import { NotificationService } from '../../shared/services/notification.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: 'login.component.html'
})
export class LoginComponent implements OnInit {

  model: any = {};
  returnUrl: string;
  constructor(private _router: Router, private translate: TranslateService, private notificationService: NotificationService,
    private _authService: AuthService, private activatedRoute: ActivatedRoute) {
      if (this._authService.currentUserValue) {
        this._router.navigate(['/login']);
      }
  }
  signIn() {
      this._authService.login(this.model.username, this.model.password).pipe(first()).subscribe(data => {
          this._router.navigate([this.returnUrl]);
        }, error => {
          this.notificationService.showError('ব্যবহারকারীর নাম বা পাসওয়ার্ড ভুল!');
          console.error(error);
      });
  }

  ngOnInit() {
    this.translate.setDefaultLang('bn');
    this.model.username = '';
    this.model.password = '';
    this.returnUrl = this.activatedRoute.snapshot.queryParams['returnUrl'] || '/dashboard';
  }

}
