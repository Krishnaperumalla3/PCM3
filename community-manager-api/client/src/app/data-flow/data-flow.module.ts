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
import {BuildDataFlowComponent} from './build-data-flow/build-data-flow.component';
import {BuildRulesComponent} from './build-rules/build-rules.component';
import {MaterialModule} from '../material/material.module';
import {ReactiveFormsModule} from '@angular/forms';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import {SharedModule} from '../shared/shared.module';
import {TableRenderComponent} from './table-render.component';
import {CreateRuleComponent} from './create-rule/create-rule.component';
import {ManageRuleComponent} from './manage-rule/manage-rule.component';
import {SweetAlert2Module} from '@sweetalert2/ngx-sweetalert2';
import {WfActivityComponent} from './wf-activity/wf-activity.component';
import {FileHandlingModalComponent} from './file-handling-modal/file-handling-modal.component';
import {SearchworkflowComponent} from './searchworkflow/searchworkflow.component';
import {ViewDataFlowComponent} from './view-data-flow/view-data-flow.component';
import {Role} from "../model/roles";
import {AuthGuard} from "../guards/auth.guard";
import {ViewRuleComponent} from "./view-rule/view-rule.component";

const dataFlowRoutes: Routes = [
  {
    path: 'build',
    component: BuildDataFlowComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.superAdmin, Role.onBoarder, Role.businessAdmin]}
  },
  {
    path: 'edit-build/:partner/:application/:flow/:type',
    component: BuildDataFlowComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.superAdmin, Role.onBoarder, Role.businessAdmin]}
  },
  {
    path: 'copy-multipleflows',
    component: SearchworkflowComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.superAdmin, Role.onBoarder, Role.businessAdmin]}
  },
  {
    path: 'addflow',
    component: SearchworkflowComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.superAdmin, Role.onBoarder, Role.businessAdmin]}
  },
  {
    path: 'view',
    component: ViewDataFlowComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.businessUser]}
  },
  {
    path: 'view-build/:partner/:application/:flow/:type',
    component: ViewDataFlowComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.superAdmin, Role.onBoarder, Role.businessAdmin]}
  },
  {
    path: 'create/rule',
    component: CreateRuleComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.superAdmin, Role.businessAdmin, Role.onBoarder]}
  },
  {
    path: 'edit/rule/:pkId',
    component: CreateRuleComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.superAdmin, Role.businessAdmin, Role.onBoarder]}
  },
  {
    path: 'manage',
    component: ManageRuleComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.superAdmin, Role.businessAdmin, Role.onBoarder, Role.businessUser]}
  },
  {
    path: 'view/rule/:pkId',
    component: ViewRuleComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.businessUser]}
  },
];

@NgModule({
  declarations: [
    BuildDataFlowComponent,
    BuildRulesComponent,
    TableRenderComponent,
    CreateRuleComponent,
    ManageRuleComponent,
    WfActivityComponent,
    FileHandlingModalComponent,
    SearchworkflowComponent,
    ViewDataFlowComponent,
    ViewRuleComponent

  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    Ng2SmartTableModule,
    SharedModule,
    RouterModule.forChild(dataFlowRoutes),
    SweetAlert2Module
  ],
  entryComponents: [
    BuildRulesComponent,
    TableRenderComponent,
    FileHandlingModalComponent
  ]
})
export class DataFlowModule {
}
