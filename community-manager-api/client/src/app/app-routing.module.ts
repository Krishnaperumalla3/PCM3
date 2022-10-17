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
// @ts-ignore
import {Routes, RouterModule} from '@angular/router';
import {PcmHomeComponent} from './pcm-main/pcm-home/pcm-home.component';
import {LoginComponent} from './auth/login/login.component';
import {AuthGuard} from './guards/auth.guard';
import {Role} from "./model/roles";
import {FileSearchComponent} from "./pcm-main/file-search/file-search.component";

const routes: Routes = [
  {path: '', redirectTo: 'pcm', pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {
    path: 'pcm', children: [
      {path: '', redirectTo: 'home', pathMatch: 'full'},
      {
        path: 'home',
        canActivate: [AuthGuard],
        component: PcmHomeComponent,
        data: {roles: [Role.superAdmin, Role.businessAdmin, Role.businessUser, Role.dataProcessor, Role.dataProcessorRestricted]}
      },
      {
        path: 'file-search',
        canActivate: [AuthGuard],
        component: FileSearchComponent,
        data: {roles: [Role.fileOperator]}
      },
      {
        path: 'partner',
        // canActivate: [AuthGuard],
        loadChildren: './pcm-partner/pcm-partner.module#PcmPartnerModule'
      },
      {
        path: 'reports',
        // canActivate: [AuthGuard],
        loadChildren: './pcm-reports/pcm-reports.module#PcmReportsModule'
      },
      {
        path: 'api',
        // canActivate: [AuthGuard],
        loadChildren: './pcm-api/pcm-api.module#PcmApiModule'
      },
      {
        path: 'envelope',
        // canActivate: [AuthGuard],
        loadChildren: './pcm-envelope/pcm-envelope.module#PcmEnvelopeModule'
      },
      {
        path: 'application',
        // canActivate: [AuthGuard],
        loadChildren: './pcm-application/pcm-application.module#PcmApplicationModule'
      },
      {
        path: 'data-flow',
        // canActivate: [AuthGuard],
        loadChildren: './data-flow/data-flow.module#DataFlowModule'
      },
      {
        path: 'accessManagement',
        // canActivate: [AuthGuard],
        loadChildren: './pcm-access-management/pcm-access-management.module#PcmAccessManagementModule'
      },
      {
        path: 'settings',
        // canActivate: [AuthGuard],
        loadChildren: './pcm-settings/pcm-settings.module#PcmSettingsModule'
      },
      {
        path: 'mft',
        loadChildren: './pcm-mft/pcm-mft.module#PcmMftModule'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
