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

import {environment} from '../../../environments/environment';
import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FileSearchService {
  constructor(private http: HttpClient) {
  }

  getProtocolList() {
    // return this.http.get(environment.GET_PROTOCOL_LIST);
    return this.http.get(environment.GET_PROTOCOL_MAP);
  }

  getPartnerList() {
    return this.http.get(environment.GET_PARTNER_LIST);
  }

  getMapNameListPromise() {
    return this.http.get(environment.GET_MAP_NAME).toPromise();
  }

  getMapNameList() {
    return this.http.get(environment.GET_MAP_NAME);
  }

  getCorrelationData() {
    return this.http.get(environment.GET_CORRELATION);
  }

  getPoolingInterval() {
    return this.http.get(environment.PCM_POOLING_INTERVAL);
  }

  savePoolingInterval(req) {
    return this.http.put(environment.PCM_POOLING_INTERVAL, req);
  }

  // wrapperSearch(req, qryParams?) {
  //   let URL = environment.SEARCH_FILES;
  //   if (qryParams) {
  //     URL = `${environment.SEARCH_FILES}?size=${qryParams.size}&page=${
  //       qryParams.page
  //       }&sort=${qryParams.sortBy},${qryParams.sortDir.toUpperCase()}`;
  //
  //     return new ServerDataSource(this.http, {
  //       endPoint: URL,
  //       dataKey: 'data',
  //       totalKey: 'totalElements',
  //       perPage: 'page',
  //       pagerPageKey: 'size'
  //     });
  //   }
  // }

  searchFile(req, qryParams?) {
    let URL = environment.SEARCH_FILES;
    if (qryParams) {
      URL = `${environment.SEARCH_FILES}?size=${qryParams.size}&page=${
        qryParams.page
      }&sort=${qryParams.sortBy},${qryParams.sortDir.toUpperCase()}`;
    }
    return this.http.post(URL, req);
  }

  searchFileActivity(req, qryParams?) {
    let URL = environment.FILE_OPERATOR_SEARCH;
    if (qryParams) {
      URL = `${environment.FILE_OPERATOR_SEARCH}?size=${qryParams.size}&page=${
        qryParams.page
      }&sort=${qryParams.sortBy},${qryParams.sortDir.toUpperCase()}`;
    }
    return this.http.post(URL, req);
  }

  updateCorrelationData(req) {
    return this.http.put(environment.GET_CORRELATION, req);
  }

  fileSearchAction(URL) {
    return this.http.get(URL);
  }

  // postData() {
  //   this.http.post(environment.SEARCH_FILES, {}).subscribe(
  //     res => {
  //       console.log(res);
  //     },
  //     (err: HttpErrorResponse) => {
  //       console.log(err.error);
  //       console.log(err.name);
  //       console.log(err.message);
  //       console.log(err.status);
  //     }
  //   );
  // }

  fileActivity(url, bpid, seqid) {
    return this.http.get(`${url}?bpId=${bpid}&seqId=${seqid}`);
  }

  // filePickUp(url, pickBpId, seqId) {
  //   const params = new HttpParams()
  //     .set('pickupBpId', pickBpId)
  //     .set('seqId', seqId);
  //   return this.http.post(url, params);
  // }

  fileReprocess(url, coreBpId, seqId) {
    const params = new HttpParams()
      .set('coreBpId', coreBpId)
      .set('seqId', seqId);
    return this.http.put(url, params);
  }

  fileProcess(url, processId, seqId, type) {
    let params;
    if (type === 'fileReprocess') {
      params = new HttpParams().set('coreBpId', processId)
        .set('seqId', seqId);
    } else if (type === 'filePickup') {
      params = new HttpParams().set('pickupBpId', processId)
        .set('seqId', seqId);
    } else if (type === 'fileDropAgain') {
      params = new HttpParams().set('deliveryBpId', processId)
        .set('seqId', seqId);
    }
    return this.http.put(url, params);
  }

  viewFile(seqId, type) {
    //filePath = encodeURIComponent(filePath);
    return this.http.get(`${environment.FILE_VIEW}?seqId=${seqId}&type=${type}`, {responseType: 'text'});
  }

  downloadFile(seqId, type) {
    // filePath = encodeURIComponent(filePath);
    return this.http.get(`${environment.FILE_DOWNLOAD}?seqId=${seqId}&type=${type}`, {responseType: 'blob'});
  }

  multipleProcess(req) {
    return this.http.post(environment.MULTI_PROCESS, req);
  }

  fileStatusChange(req) {
    return this.http.post(environment.FILE_STATUS_CHANGE, req);
  }

  uploadFile(req, headers) {
    return this.http.post(environment.UPLOAD_FILE, req, {headers: headers});
  }

  activityFile(req) {
    return this.http.get(`${environment.ACTIVITY_DF}?filePath=${encodeURIComponent(req)}`, {responseType: 'text'})
  }

}
