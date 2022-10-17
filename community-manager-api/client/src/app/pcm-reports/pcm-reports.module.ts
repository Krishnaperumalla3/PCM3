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

import {CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DataFlowComponent} from './data-flow/data-flow.component';
import {OverdueComponent} from './overdue/overdue.component';
import {VolumeReportsComponent} from './volume-reports/volume-reports.component';
import {DoctypeReportsComponent} from './doctype-reports/doctype-reports.component';
import {VolumePartnersComponent} from './volume-partners/volume-partners.component';
import {MaterialModule} from '../material/material.module';
import {ReactiveFormsModule} from '@angular/forms';
import {OwlDateTimeModule, OwlNativeDateTimeModule} from 'ng-pick-datetime';
import {SweetAlert2Module} from '@sweetalert2/ngx-sweetalert2';
import {ChartsModule} from 'ng2-charts';
import {GoogleChartsModule} from 'angular-google-charts';
import { SharedModule } from '../shared/shared.module';
import {Routes, RouterModule} from '@angular/router';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { PCMInterceptor } from '../services/pcm.interceptor';
import {AuthGuard} from "../guards/auth.guard";
import {Role} from "../model/roles";
import { PcdComponent } from './pcd/pcd.component';
import { SfgPcdComponent } from './sfg-pcd/sfg-pcd.component';
import { SfgComponent } from './sfg/sfg.component';
import { FileProcessedComponent } from './file-processed/file-processed.component';
import {ECharts} from "echarts";
// @ts-ignore
import {NgxEchartsModule} from "ngx-echarts";
import { PartnersComponent } from './partners/partners.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { FileSizeProcessedComponent } from './file-size-processed/file-size-processed.component';
import { FileDashboardComponent } from './file-dashboard/file-dashboard/file-dashboard.component';

const routes: Routes = [
  {
    path: 'data-flow-reports',
    component: DataFlowComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.superAdmin, Role.businessAdmin, Role.businessUser, Role.dataProcessor, Role.fileOperator, Role.dataProcessorRestricted] }
  },
  { path: 'overdue',
    component: OverdueComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.superAdmin, Role.businessAdmin, Role.businessUser, Role.dataProcessor, Role.fileOperator, Role.dataProcessorRestricted] }
  },
  {
    path: 'volume-reports',
    component: VolumeReportsComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.superAdmin, Role.businessAdmin, Role.businessUser, Role.dataProcessor, Role.fileOperator, Role.dataProcessorRestricted] }
  },
  {
    path: 'doctype-reports',
    component: DoctypeReportsComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.superAdmin, Role.businessAdmin, Role.businessUser, Role.dataProcessor, Role.fileOperator, Role.dataProcessorRestricted] }
  },
  {
    path: 'volume-partners',
    component: VolumePartnersComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.superAdmin, Role.businessAdmin, Role.businessUser, Role.dataProcessor, Role.fileOperator, Role.dataProcessorRestricted] }
  },
  {
    path: 'SFG',
    component: SfgComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.superAdmin, Role.businessAdmin, Role.businessUser, Role.dataProcessor, Role.fileOperator, Role.dataProcessorRestricted] }
  },
  {
    path: 'SFG-PCD',
    component: SfgPcdComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.superAdmin, Role.businessAdmin, Role.businessUser, Role.dataProcessor, Role.fileOperator, Role.dataProcessorRestricted] }
  },
  {
    path: 'PCD',
    component: PcdComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.superAdmin, Role.businessAdmin, Role.businessUser, Role.dataProcessor, Role.fileOperator, Role.dataProcessorRestricted] }
  },
  {
    path: 'file-processed',
    component: FileProcessedComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.superAdmin, Role.businessAdmin, Role.businessUser, Role.dataProcessor, Role.fileOperator, Role.dataProcessorRestricted] }
  },
  {
    path: 'partner',
    component: PartnersComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.superAdmin, Role.businessAdmin, Role.businessUser, Role.dataProcessor, Role.fileOperator, Role.dataProcessorRestricted] }
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.superAdmin, Role.businessAdmin, Role.businessUser, Role.dataProcessor, Role.fileOperator, Role.dataProcessorRestricted] }
  },
  {
    path: 'file-size',
    component: FileSizeProcessedComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.superAdmin, Role.businessAdmin, Role.businessUser, Role.dataProcessor, Role.fileOperator, Role.dataProcessorRestricted] }
  },
  {
    path: 'dashboard-report',
    component: FileDashboardComponent,
    canActivate: [AuthGuard],
    data: { roles: [Role.fileOperator] }
  },
];

@NgModule({
  declarations: [DataFlowComponent, OverdueComponent, VolumeReportsComponent, DoctypeReportsComponent, VolumePartnersComponent, PcdComponent, SfgPcdComponent, SfgComponent, FileProcessedComponent, PartnersComponent, DashboardComponent, FileSizeProcessedComponent, FileDashboardComponent],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes),
    OwlDateTimeModule,
    OwlNativeDateTimeModule,
    SharedModule,
    SweetAlert2Module.forRoot(),
    ChartsModule,
    NgxEchartsModule,
    GoogleChartsModule.forRoot()

  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: PCMInterceptor,
      multi: true
    }
  ],
})
export class PcmReportsModule {
}
