import { AuthGuard } from '../core/auth/auth.guard';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { DashboardComponent } from './dashboard.component';
import { Routes, RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { MaterialModule } from '../material.module';


const routes: Routes = [
      {
        path: '',
        component: DashboardComponent,
        canActivate: [AuthGuard],
        data: { title: 'Dashboard' }
      }
];

@NgModule({
    imports: [
      CommonModule, RouterModule.forChild(routes), TranslateModule,
      MaterialModule
    ],
    exports: [RouterModule],
    providers: [],
    declarations: [
        DashboardComponent
    ]
  })
export class DashboardModule { }
