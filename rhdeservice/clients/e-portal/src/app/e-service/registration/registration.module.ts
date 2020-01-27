import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { SyncfusionModule } from '../../syncfusion.module';
import { MaterialModule } from '../../material.module';
import { TranslateModule } from '@ngx-translate/core';
import { SharedPipesModule } from '../../shared/pipes/shared-pipe.module';

import { RegistrationRoutingModule } from './registration-routing.module';

import { RegistrationComponent } from './registration.component';
import { RegistrationListComponent } from './registration-list/registration-list.component';
import { RegistrationDetailsComponent } from './registration-details/registration-details.component';

@NgModule({
  declarations: [RegistrationComponent, RegistrationListComponent, RegistrationDetailsComponent],
  imports: [
    CommonModule,
    RegistrationRoutingModule,
    ReactiveFormsModule,
    TranslateModule,
    SharedPipesModule,
    MaterialModule,
    SyncfusionModule
  ]
})
export class RegistrationModule { }

