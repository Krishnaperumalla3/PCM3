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

import {CreateX12EnvelopeComponent} from './create-x12-envelope/create-x12-envelope.component';
import {ManageX12EnvelopeComponent} from './manage-x12-envelope/manage-x12-envelope.component';
import {MaterialModule} from '../material/material.module';
import {ReactiveFormsModule} from '@angular/forms';
import {OwlDateTimeModule, OwlNativeDateTimeModule} from 'ng-pick-datetime';
import {SharedModule} from '../shared/shared.module';
import {SweetAlert2Module} from '@sweetalert2/ngx-sweetalert2';
import {ChartsModule} from 'ng2-charts';
import {GoogleChartsModule} from 'angular-google-charts';
import {EnvelopeService} from './envelope.service';
import {RuleService} from '../services/rule.service';
import {UserService} from '../services/user.service';
import {FileSearchService} from '../services/file-search/file-search.service';
import {ViewActivityComponent} from './view-activity/view-activity.component';
import {Routes, RouterModule} from '@angular/router';

const routes: Routes = [
  { path: 'create-x12', component: CreateX12EnvelopeComponent },
  { path: 'manage-x12', component: ManageX12EnvelopeComponent },
  { path: 'view/:pkId', component: ViewActivityComponent },
  { path: 'edit/:pkId', component: CreateX12EnvelopeComponent }
];

@NgModule({
  declarations: [
    CreateX12EnvelopeComponent,
    ManageX12EnvelopeComponent,
    ViewActivityComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    OwlDateTimeModule,
    OwlNativeDateTimeModule,
    SharedModule,
    SweetAlert2Module.forRoot(),
    ChartsModule,
    GoogleChartsModule.forRoot(),
    RouterModule.forChild(routes)],
  providers: []
})
export class PcmEnvelopeModule {
}
