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
import {CreateUserComponent} from './create-user/create-user.component';
import {MaterialModule} from '../material/material.module';
import {ReactiveFormsModule} from '@angular/forms';
import {ManageUserComponent} from './manage-user/manage-user.component';
import {CreateGroupComponent} from './create-group/create-group.component';
import {ManageGroupComponent} from './manage-group/manage-group.component';
import {ManageB2biUsersComponent} from './manage-b2bi-users/manage-b2bi-users.component';
import {SharedModule} from '../shared/shared.module';
import {AuthGuard} from '../guards/auth.guard';
import {Role} from "../model/roles";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {PCMInterceptor} from "../services/pcm.interceptor";
import { ManageChargeBackComponent } from './manage-charge-back/manage-charge-back.component';
import { UserPermissionsComponent } from './user-permissions/user-permissions.component';

const accessManagementRoutes: Routes = [
  { path: 'create/user',component: CreateUserComponent,canActivate: [AuthGuard],data: { roles: [Role.admin, Role.superAdmin] }},
  { path: 'edit/user/:userId', component: CreateUserComponent,canActivate: [AuthGuard],data: { roles: [Role.admin, Role.superAdmin] }},
  { path: 'manage/user', component: ManageUserComponent,canActivate: [AuthGuard],data: { roles: [Role.admin, Role.superAdmin] }},
  { path: 'create/group', component: CreateGroupComponent,canActivate: [AuthGuard],data: { roles: [Role.admin, Role.superAdmin] }},
  { path: 'edit/group/:pkId', component: CreateGroupComponent,canActivate: [AuthGuard],data: { roles: [Role.admin, Role.superAdmin] }},
  { path: 'manage/group', component: ManageGroupComponent,canActivate: [AuthGuard],data: { roles: [Role.admin, Role.superAdmin] }},
  { path: 'manage/charge-back', component: ManageChargeBackComponent,canActivate: [AuthGuard],data: { roles: [Role.admin, Role.superAdmin] }},
  { path: 'manage/b2bi', component: ManageB2biUsersComponent,canActivate: [AuthGuard],data: { roles: [Role.admin, Role.superAdmin] }},
  { path: 'user-permissions/:id',component: UserPermissionsComponent,canActivate: [AuthGuard],data: { roles: [Role.admin, Role.superAdmin] }},
];

@NgModule({
  declarations: [
    CreateUserComponent,
    ManageUserComponent,
    CreateGroupComponent,
    ManageGroupComponent,
    ManageB2biUsersComponent,
    ManageChargeBackComponent,
    UserPermissionsComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    SharedModule,
    RouterModule.forChild(accessManagementRoutes)
  ],
  exports: [RouterModule]
})

export class PcmAccessManagementModule {}
