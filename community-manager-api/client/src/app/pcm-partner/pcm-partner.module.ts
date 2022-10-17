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

import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {CreatePartnerComponent} from './create-partner/create-partner.component';
import {ManagePartnerComponent} from './manage-partner/manage-partner.component';
import {CreateAs2RelationshipComponent} from './create-as2-relationship/create-as2-relationship.component';
import {MaterialModule} from '../material/material.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {SharedModule} from '../shared/shared.module';
import {ViewPartnerComponent} from './view-partner/view-partner.component';
import {SweetAlert2Module} from '@sweetalert2/ngx-sweetalert2';
import {NgxsModule} from '@ngxs/store';
import {PartnerState} from './store/partner-state';
import {AuthGuard} from '../guards/auth.guard';
import {Role} from "../model/roles";

const partnerRoutes: Routes = [
  {
    path: 'create',
    component: CreatePartnerComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.superAdmin, Role.onBoarder, Role.businessAdmin]}
  },
  {
    path: 'edit/:protocol/:pkId',
    component: CreatePartnerComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.superAdmin, Role.onBoarder, Role.businessAdmin]}
  },
  {
    path: 'manage',
    component: ManagePartnerComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.superAdmin, Role.onBoarder, Role.businessAdmin, Role.businessUser]}
  },
  {
    path: 'create/as2',
    component: CreateAs2RelationshipComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.superAdmin, Role.onBoarder, Role.businessAdmin]}
  },
  {
    path: 'view/:protocol/:pkId',
    component: ViewPartnerComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.businessUser]}
  }
];

@NgModule({
  declarations: [
    CreatePartnerComponent,
    ManagePartnerComponent,
    CreateAs2RelationshipComponent,
    ViewPartnerComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule,
    RouterModule.forChild(partnerRoutes),
    SweetAlert2Module.forRoot(),
    NgxsModule.forFeature([PartnerState])
  ],
  exports: [RouterModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class PcmPartnerModule {
}
