/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {CreateApplicationComponent} from './create-application/create-application.component';
import {MaterialModule} from '../material/material.module';
import {ReactiveFormsModule} from '@angular/forms';
import {SharedModule} from '../shared/shared.module';
import {ManageApplicationComponent} from './manage-application/manage-application.component';
import {ViewApplicationComponent} from './view-application/view-application.component';
import {SweetAlert2Module} from '@sweetalert2/ngx-sweetalert2';
import {BuildDataFlowComponent} from "../data-flow/build-data-flow/build-data-flow.component";
import {AuthGuard} from "../guards/auth.guard";
import {Role} from "../model/roles";

const applicationRoutes: Routes = [
  {
    path: 'create',
    component: CreateApplicationComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.superAdmin, Role.onBoarder, Role.businessAdmin]}
  },
  {
    path: 'edit/:protocol/:pkId',
    component: CreateApplicationComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.superAdmin, Role.onBoarder, Role.businessAdmin]}
  },
  {
    path: 'manage',
    component: ManageApplicationComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.superAdmin, Role.onBoarder, Role.businessAdmin, Role.businessUser]}
  },
  {
    path: 'view/:protocol/:pkId',
    component: ViewApplicationComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.businessUser]}
  }
];

@NgModule({
  declarations: [
    CreateApplicationComponent,
    ManageApplicationComponent,
    ViewApplicationComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    SharedModule,
    RouterModule.forChild(applicationRoutes),
    SweetAlert2Module.forRoot()
  ],
  exports: [RouterModule]
})
export class PcmApplicationModule {
}
