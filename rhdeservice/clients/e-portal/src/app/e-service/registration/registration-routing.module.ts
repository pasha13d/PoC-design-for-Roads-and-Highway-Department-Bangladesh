import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegistrationComponent } from './registration.component';
import { RegistrationListComponent } from './registration-list/registration-list.component';
import { RegistrationDetailsComponent } from './registration-details/registration-details.component';

const routes: Routes = [
  {
    path: '',
    component: RegistrationComponent,
    data: { title : 'registration' },
    children:  [
      {
      path: '',
      redirectTo: 'registration-list',
      pathMatch: 'full'
      },
      {
        path: 'registration-list',
        component: RegistrationListComponent,
        data: { title: 'registration-list' }
      },
      {
        path: 'registration-details/:oid',
        component: RegistrationDetailsComponent,
        data: { title: 'registration-details' }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RegistrationRoutingModule { }
