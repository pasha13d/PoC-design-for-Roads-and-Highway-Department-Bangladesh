import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RequisitionComponent } from './requisition.component';
import { BridgeAddComponent } from './bridge/bridge-add/bridge-add.component';
import { RoadAddComponent } from './road/road-add/road-add.component';

const routes: Routes = [
  {
    path: '',
    component: RequisitionComponent,
    data: { title : 'requisition' },
    children: [
      {
        path: '',
        redirectTo: 'bridge',
        pathMatch: 'full'
      },
      {
        path: 'bridge',
        loadChildren: () => import('./bridge/bridge.module').then(m => m.BridgeModule),
      },
      {
        path: 'road',
        loadChildren: () => import('./road/road.module').then(m => m.RoadModule),
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RequisitionRoutingModule { }
