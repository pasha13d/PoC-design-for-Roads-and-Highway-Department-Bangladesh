import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SyncfusionModule } from '../../../syncfusion.module';
import { MaterialModule } from '../../../material.module';
import { TranslateModule } from '@ngx-translate/core';
import { SharedPipesModule } from '../../../shared/pipes/shared-pipe.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { RoadRoutingModule } from './road-routing.module';
import { RoadListComponent } from './road-list/road-list.component';
import { RoadAddComponent } from './road-add/road-add.component';
import { RoadDetailsComponent } from './road-details/road-details.component';
import { RoadRequisitionCommentComponent } from './road-details/road-requisiton-comment/road-requisition-comment.component';

@NgModule({
  declarations: [
    RoadListComponent, 
    RoadAddComponent, 
    RoadDetailsComponent,
    RoadRequisitionCommentComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    RoadRoutingModule,
    ReactiveFormsModule,
    TranslateModule,
    SharedPipesModule,
    MaterialModule,
    SyncfusionModule
  ]
})
export class RoadModule { }
