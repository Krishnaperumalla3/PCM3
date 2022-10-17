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
import {HttpClient, HttpParams} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {

  constructor(private http: HttpClient) {
  }

  getPoolingInterval() {
    return this.http.get(environment.GET_POOLING_INTERVAL);
  }

  getAdapterNames() {
    return this.http.get(environment.GET_ADAPTER_NAMES);
  }

  getCaCertMap() {
    return this.http.get(environment.GET_CA_CERT_MAP);
  }

  getCertMap(url) {
    return this.http.get(url);
  }

  getCipherSuitesMap() {
    return this.http.get(environment.GET_CIPHER_SUITES);
  }

  getSSHkhostList() {
    return this.http.get(environment.SSH_KHOST_LIST);
  }

  getSystemCertMap() {
    return this.http.get(environment.SYSTEM_CERT_MAP);
  }


  getPartnersByProtocol(req) {
    const params = new HttpParams().set('protocol', req);
    return this.http.post(environment.GET_PARTNERS_BY_PROTOCOL, params);
  }

  createApplication(protocol, req) {
    const url = protocol === 'remote-ftp' || protocol == 'remote-as2'  ? '/pcm/si/application/' : environment.CREATE_APPLICATION;
    return this.http.post(
      url + protocol.toLowerCase(),
      req
    );
  }

  updateApplication(protocol, req) {
    const url = protocol === 'remote-ftp' || protocol == 'remote-as2'  ? '/pcm/si/application/' : environment.CREATE_APPLICATION;
    return this.http.put(
      url + protocol.toLowerCase(),
      req
    );
  }

  searchFile(req, qryParams?) {
    let URL = environment.CREATE_APPLICATION_PROTOCOL;
    if (qryParams) {
      URL = `${environment.CREATE_APPLICATION_PROTOCOL}?size=${qryParams.size}&page=${
        qryParams.page
        }&sort=${qryParams.sortBy},${qryParams.sortDir.toUpperCase()}`;
    }
    return this.http.post(URL, req);
  }

  uploadCertificates(req) {
    return this.http.post(req.url, req.fb);
  }

  getPkIdDetails(url) {
    return this.http.get(url);
  }

  getActivityHistory(pkId, qryParams?) {
    let URL = environment.GET_APPLICATION_ACTIVITY;
    if (qryParams) {
      URL = `${environment.GET_APPLICATION_ACTIVITY}${pkId}?size=${qryParams.size}&page=${
        qryParams.page
        }&sort=${qryParams.sortBy},${qryParams.sortDir.toUpperCase()}`;
      return this.http.post(URL, {});
    } else {
      return this.http.post(URL + pkId, {});
    }
  }

  getSshKeyPair() {
    return this.http.get(environment.GET_SSH_KEY_PAIR);
  }

  getSshUserMap() {
    return this.http.get(environment.GET_SSH_USER_LIST);
  }
}
