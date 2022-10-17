import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {MaterialModule} from '../material/material.module';
import {ReactiveFormsModule} from '@angular/forms';
import {Role} from "../model/roles";
import {AuthGuard} from "../guards/auth.guard";
import { CreateApiComponent } from './create-api/create-api.component';
import { ManageApiComponent } from './manage-api/manage-api.component';
import {SharedModule} from "../shared/shared.module";
import { BuildFlowComponent } from './build-flow/build-flow.component';
import {Ng2SmartTableModule} from 'ng2-smart-table';

const routes: Routes = [
  {
    path: 'create',
    component: CreateApiComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.superAdmin] }
  },
  {
    path: 'edit/:pkId',
    component: CreateApiComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.superAdmin] }
  },
  {
    path: 'manage',
    component: ManageApiComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.superAdmin, Role.onBoarder, Role.businessAdmin] }
  },
  {
    path: 'build-flow',
    component: BuildFlowComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.superAdmin, Role.onBoarder, Role.businessAdmin] }
  }
];

@NgModule({
  declarations: [CreateApiComponent, ManageApiComponent, BuildFlowComponent],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    SharedModule,
    Ng2SmartTableModule
  ],
})
export class PcmApiModule { }
