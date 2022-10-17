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

import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import Swal from 'sweetalert2';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class DataFlowService {
  private copyWorkflowForm = {};
  private fileNamePattern: any;
  public partnerLst: any;
  public appLst: any;
  constructor(
    private http: HttpClient,
    private translate: TranslateService
    ) {}

  getPartnerList() {
    return this.http.get(environment.GET_PARTNER_MAP);
  }

  getAppList() {
    return this.http.get(environment.GET_APP_LIST);
  }

  getRules() {
    return this.http.get(environment.GET_RULES);
  }

  getAppProfile(URL): Observable<any> {
    return this.http.get(URL);
  }

  exportWorkFlow(URL) {
    const options = { headers: new HttpHeaders().set('Content-Type', 'application/xml'), };
    return this.http.get(URL, { responseType: 'arraybuffer', observe: 'response' })
    .subscribe(res => this.downLoadFile(res, 'application/xml'),
    err => {
      Swal.fire(
        this.translate.instant('SWEET_ALERT.WRK_FLW.TITLE'),
        this.translate.instant('SWEET_ALERT.WRK_FLW.ERROR.BODY'),
        'error'
      );
    });
  }

  downLoadFile(data: any, type: string) {
    // tslint:disable-next-line:max-line-length
    const content = data.headers ? data.headers.get('content-disposition') || 'attachment; filename=WorkFlowExport.xml' : 'attachment; filename=WorkFlowExport.xml';
    const filename = ( (content || '').split('filename=')[1] || 'WorkFlowExport.xml').split("'").join('').split('"').join('');
    const blob = new Blob([data.body], { type: type});
    Swal.fire(
      this.translate.instant('SWEET_ALERT.WRK_FLW.TITLE'),
      this.translate.instant('SWEET_ALERT.WRK_FLW.SUCCESS.BODY'),
      'success'
    );
    // const url = window.URL.createObjectURL(blob);
    if (window.navigator && window.navigator.msSaveBlob) {
      window.navigator.msSaveBlob(blob, filename);
    }
    const a = document.createElement("a");
    document.body.appendChild(a);
    a.href = URL.createObjectURL(blob);
    a.download = filename;
    a.target = '_self';
    a.click();
    // window.URL.revokeObjectURL(url);
    // const pwa = window.open(url);
}

  getActivity( partnerPkid, applicationPkid, qryParams?) {
    let URL = environment.GET_ACTIVITY + partnerPkid + '_' + applicationPkid;
    if (qryParams) {
      URL = `${URL}?size=${qryParams.size}&page=${qryParams.page}&sort=${qryParams.sortBy},${qryParams.sortDir.toUpperCase()}`;
    }
    return this.http.get(URL);
  }

  importWorkFlow(URL, req) {
    const options = { headers: new HttpHeaders().set('Content-Type', 'multipart/form-data') };

    return this.http.post(
      URL,
      req,
      options
    );
  }

  createWorkFlow(URL, req) {
    return this.http.post(
      URL,
      req
    );
  }

  deleteWorkLow(URL, req) {
    return this.http.delete(
      URL,
      req
    );
  }

  createPartner(protocol, req) {
    return this.http.post(
      environment.CREATE_PARTNER_PROTOCOL + protocol.toLowerCase(),
      req
    );
  }

  getIsMFTDuplicate() {
    return this.http.get(environment.GET_IS_MFT_DUPLICATE);
  }

  setCopyForm(val) {
    this.copyWorkflowForm = val;
  }

  get getCopyForm() {
    return this.copyWorkflowForm;
  }

  setMultpleCopyWorkFlow(val: any) {
    this.fileNamePattern = val;
  }

  get getMultpleCopyWorkFlow() {
    return this.fileNamePattern;
  }
}
