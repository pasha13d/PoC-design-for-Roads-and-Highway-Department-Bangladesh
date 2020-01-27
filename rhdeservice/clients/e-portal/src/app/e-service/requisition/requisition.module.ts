import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RequisitionRoutingModule } from './requisition-routing.module';
import { RequisitionComponent } from './requisition.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [RequisitionComponent],
  imports: [
    CommonModule,
    FormsModule,
    RequisitionRoutingModule,
  ]
})
export class RequisitionModule { }
