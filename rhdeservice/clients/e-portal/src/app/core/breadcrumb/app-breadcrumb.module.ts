import { CommonModule} from '@angular/common';
import { NgModule, ModuleWithProviders} from '@angular/core';
import { RouterModule } from '@angular/router';

// App Breadcrumb Component
import { AppBreadcrumbService } from './app-breadcrumb.service';
import { AppBreadcrumbComponent } from './app-breadcrumb.component';
import { CuiBreadcrumbComponent } from './cui-breadcrumb.component';
import { TranslateModule } from '@ngx-translate/core';

// @dynamic
@NgModule({
  imports: [ CommonModule, RouterModule, TranslateModule ],
  exports: [ AppBreadcrumbComponent, CuiBreadcrumbComponent ],
  declarations: [ AppBreadcrumbComponent, CuiBreadcrumbComponent ]
})
export class AppBreadcrumbModule {
  static forRoot(config?: any): ModuleWithProviders {
    return {
      ngModule: AppBreadcrumbModule,
      providers: [
        AppBreadcrumbService
      ]
    };
  }
}
