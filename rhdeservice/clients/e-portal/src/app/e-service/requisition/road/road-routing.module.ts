import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RoadListComponent } from './road-list/road-list.component';
import { RoadAddComponent } from './road-add/road-add.component';
import { RoadDetailsComponent } from './road-details/road-details.component';

const routes: Routes = [
  {
    path: '',
    component: RoadListComponent,
    data: { title: 'road-list' }
  },
  {
    path: 'add-road',
    component: RoadAddComponent,
    data: { title: 'road-add' }
  },
  {
    path: 'road-details/:oid',
    component: RoadDetailsComponent,
    data: { title: 'road-details' }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RoadRoutingModule { }
