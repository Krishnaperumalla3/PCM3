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

import {CommonState} from '../store/layout/state/common.state';
import {BrowserModule, Title} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SharedModule} from './shared/shared.module';
import {PcmMainModule} from './pcm-main/pcm-main.module';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MaterialModule} from './material/material.module';
import {NgxsModule} from '@ngxs/store';
import {LayoutState} from 'src/store/layout/state/layout.state';
import {PCMInterceptor} from './services/pcm.interceptor';
import {NgxLoadingModule} from 'ngx-loading';
import {ToastrModule} from 'ngx-toastr';
import {LoginComponent} from './auth/login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {SweetAlert2Module} from '@sweetalert2/ngx-sweetalert2';
import { PcmRenderCellComponent } from './ng2table/pcm-render-cell/pcm-render-cell.component';
import { PcmActionCellComponent } from './ng2table/pcm-action-cell/pcm-action-cell.component';
import { ActiveCellComponent } from './ng2table/active-cell/active-cell.component';
import { DateCellComponent } from './ng2table/date-cell/date-cell.component';
import { NgIdleKeepaliveModule } from '@ng-idle/keepalive';
import { MomentModule } from 'angular2-moment';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { HttpClient } from '@angular/common/http';
import { ModuleTranslateLoader, IModuleTranslationOptions } from '@larscom/ngx-translate-module-loader';
// @ts-ignore
import {NgxEchartsModule} from "ngx-echarts";

export function ModuleHttpLoaderFactory(http: HttpClient) {
  const baseTranslateUrl = "./assets/i18n";

  const options: IModuleTranslationOptions = {
    translateError: (error, path) => {
      console.log("Oeps! an error occurred: ", { error, path });
    },
    modules: [
      { baseTranslateUrl, moduleName: "login" },
      { baseTranslateUrl, moduleName: "partners" },
      { baseTranslateUrl, moduleName: "protocols" },
      { baseTranslateUrl, moduleName: "certificate" },
      { baseTranslateUrl, moduleName: "application" },
      { baseTranslateUrl, moduleName: "data_flow" },
      { baseTranslateUrl, moduleName: "file_transfer" },
      { baseTranslateUrl, moduleName: "users" },
      { baseTranslateUrl, moduleName: "groups" },
      { baseTranslateUrl, moduleName: "settings" },
      { baseTranslateUrl, moduleName: "reports" },
      { baseTranslateUrl, moduleName: "common" },
      { baseTranslateUrl, moduleName: "sweet_alert" },
      { baseTranslateUrl, moduleName: "displayfields" }
    ]
  };
  return new ModuleTranslateLoader(http, options);
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    PcmRenderCellComponent,
    PcmActionCellComponent,
    ActiveCellComponent,
    DateCellComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MaterialModule,
    PcmMainModule,
    SharedModule,
    BrowserAnimationsModule,
    NgxLoadingModule,
    NgxsModule.forRoot([
      LayoutState,
      CommonState
    ]),
    ToastrModule.forRoot({
      positionClass: 'toast-top-center',
      preventDuplicates: false,
      disableTimeOut: false,
      closeButton: true
    }),
    FormsModule,
    ReactiveFormsModule,
    SweetAlert2Module.forRoot(),
    NgIdleKeepaliveModule.forRoot(),
    MomentModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: ModuleHttpLoaderFactory,
        deps: [HttpClient],
      }
    })
  ],
  providers: [
    Title,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: PCMInterceptor,
      multi: true
    }
  ],
  entryComponents: [PcmRenderCellComponent, PcmActionCellComponent],
  exports: [PcmRenderCellComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
}
