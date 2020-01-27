import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BridgeRoutingModule } from './bridge-routing.module';
import { BridgeListComponent } from './bridge-list/bridge-list.component';
import { BridgeAddComponent } from './bridge-add/bridge-add.component';
import { SyncfusionModule } from '../../../syncfusion.module';
import { MaterialModule } from '../../../material.module';
import { TranslateModule } from '@ngx-translate/core';
import { SharedPipesModule } from '../../../shared/pipes/shared-pipe.module';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [BridgeListComponent, BridgeAddComponent],
  imports: [
    CommonModule,
    BridgeRoutingModule,
    ReactiveFormsModule,
    TranslateModule,
    SharedPipesModule,
    MaterialModule,
    SyncfusionModule
  ]
})
export class BridgeModule { }
