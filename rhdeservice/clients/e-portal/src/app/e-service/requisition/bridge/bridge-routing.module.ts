import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BridgeListComponent } from './bridge-list/bridge-list.component';
import { BridgeAddComponent } from './bridge-add/bridge-add.component';

const routes: Routes = [
  {
    path: '',
    component: BridgeListComponent,
    data: { title: 'bridge-list' }
  },
  {
    path: 'add-bridge',
    component: BridgeAddComponent,
    data: { title: 'bridge-add' }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BridgeRoutingModule { }
