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
import {RouterModule, Routes} from '@angular/router';
import {MaterialModule} from '../material/material.module';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {  RxReactiveFormsModule } from "@rxweb/reactive-form-validators"
import {SharedModule} from '../shared/shared.module';
import {CorrelationNamesComponent} from './correlation-names/correlation-names.component';
import {PoolingIntervalComponent} from './pooling-interval/pooling-interval.component';
import {SweetAlert2Module} from '@sweetalert2/ngx-sweetalert2';
import {AuthGuard} from '../guards/auth.guard';
import {Role} from "../model/roles";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {PCMInterceptor} from "../services/pcm.interceptor";
import {SchedulerComponent} from './scheduler/scheduler.component';
import {TransactionNamesComponent} from "./transaction-names/transaction-names.component";

const settingRouts: Routes = [
  {
    path: 'correlation',
    component: CorrelationNamesComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.admin, Role.superAdmin]}
  },
  {
    path: 'pooling-interval',
    component: PoolingIntervalComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.admin, Role.superAdmin]}
  },
  {
    path: 'scheduler',
    component: SchedulerComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.admin, Role.superAdmin]}
  },
  {
    path: 'transaction',
    component: TransactionNamesComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.admin, Role.superAdmin]}
  }
];

@NgModule({
  declarations: [CorrelationNamesComponent, PoolingIntervalComponent, SchedulerComponent,
    TransactionNamesComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    RxReactiveFormsModule,
    SharedModule,
    FormsModule,
    SweetAlert2Module,
    RouterModule.forChild(settingRouts)
  ],
  exports: [RouterModule],
  schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: PCMInterceptor,
      multi: true
    }
  ],
})

export class PcmSettingsModule {}
