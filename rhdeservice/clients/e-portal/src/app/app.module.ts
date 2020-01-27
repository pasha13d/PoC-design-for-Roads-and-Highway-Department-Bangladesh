import { DetailRowService } from '@syncfusion/ej2-angular-grids';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
import { APP_BASE_HREF, DatePipe } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { AppComponent } from './app.component';

// Import containers
import { DefaultLayoutComponent } from './containers';
import { P404Component } from './core/error/404.component';
import { LoginComponent } from './core/login/login.component';
import { RegisterComponent } from './core/register/register.component';
import { TranslateHttpLoader} from '@ngx-translate/http-loader';

import { MaterialModule } from './material.module';
import { ReactiveFormsModule } from '@angular/forms';

const APP_CONTAINERS = [
  DefaultLayoutComponent
];

import {AppAsideModule,  AppHeaderModule,  AppFooterModule } from '@coreui/angular';

// Import routing module
import { AppRoutingModule } from './app.routing';

// Import 3rd party components
import { HttpClient, HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HrefPreventDefaultDirective } from './core/directives/href-prevent-default.directive';
import { environment } from '../environments/environment';
import { ServiceWorkerModule } from '@angular/service-worker';
import { MAT_DATE_LOCALE, MatSnackBarModule } from '@angular/material';
import { AppSidebarModule } from './core/sidebar';
import { AppBreadcrumbModule } from './core/breadcrumb';
import { FieldErrorDisplayComponent } from './shared/templates/field-error-display.component';
import { HttpErrorInterceptor } from './shared/services/http-error.interceptor';
import { GlobalErrorHandler } from './shared/services/global-error-handler';
import { SlimLoadingBarModule } from 'ng2-slim-loading-bar';
import { TokenInterceptor } from './shared/services/token.interceptor';

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ServiceWorkerModule.register('./ngsw-worker.js', { enabled: environment.production }),
    FormsModule,
    AppRoutingModule,
    AppAsideModule,
    AppBreadcrumbModule.forRoot(),
    AppFooterModule,
    AppHeaderModule,
    AppSidebarModule,
    PerfectScrollbarModule,
    HttpClientModule,
    SlimLoadingBarModule,
    MaterialModule,
    ReactiveFormsModule,
    TranslateModule.forRoot({
      loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
      }
    }),
    MatSnackBarModule
  ],
  declarations: [
    AppComponent,
    ...APP_CONTAINERS,
    HrefPreventDefaultDirective,
    P404Component,
    LoginComponent,
    RegisterComponent,
    FieldErrorDisplayComponent
  ],
  exports: [HrefPreventDefaultDirective],
  providers: [DetailRowService, DatePipe,
    { provide: APP_BASE_HREF, useValue: '/e-portal'},
    { provide: MAT_DATE_LOCALE, useValue: 'bn-BD'},
    { provide: ErrorHandler, useClass: GlobalErrorHandler },
    { provide: HTTP_INTERCEPTORS,  useClass: HttpErrorInterceptor,   multi: true  }
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
