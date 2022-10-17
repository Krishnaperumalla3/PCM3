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

import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from 'src/environments/environment';
import {protocolAPIS} from '../model/protocols.constants';
import {Store} from '@ngxs/store';

@Injectable({
  providedIn: 'root'
})
export class PartnerService {
  constructor(private http: HttpClient, private store: Store) {
  }

  getAs2OrgProfilePartnersList() {
    return this.http.get(environment.GET_AS2_ORG_PROFILES_LIST);
  }

  getAs2PartnersProfilesList() {
    return this.http.get(environment.GET_AS2_PARTNER_PROFILES_LIST);
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

  getCipherSuitesMap() {
    return this.http.get(environment.GET_CIPHER_SUITES);
  }

  getCertMap(url) {
    return this.http.get(url);
  }

  getTypeByProtocol(req, flow) {
    const params = new HttpParams().set('protocol', req);
    return this.http.post(flow === 'inbound' ? environment.GET_APPLICATION_BY_PROTOCOL : environment.GET_PARTNERS_BY_PROTOCOL, params);
  }

  getPartnersByProtocol(req) {
    const params = new HttpParams().set('protocol', req);
    return this.http.post(environment.GET_PARTNERS_BY_PROTOCOL, params);
  }

  createPartner(protocol, req) {
    const url = protocol === 'remote-ftp' || protocol == 'remote-as2' ? '/pcm/si/partner/' : environment.CREATE_PARTNER_PROTOCOL;
    return this.http.post(
      url + protocol.toLowerCase(), req
    );
  }

  updatePartner(protocol, req) {
    const url = protocol === 'remote-ftp' || protocol == 'remote-as2' ? '/pcm/si/partner/' : environment.CREATE_PARTNER_PROTOCOL
    return this.http.put(
      url + protocol.toLowerCase(),
      req
    );
  }

  searchPartner(req, qryParams?) {
    let URL = environment.SEARCH_PARTNER;
    if (qryParams) {
      URL = `${environment.SEARCH_PARTNER}?size=${qryParams.size}&page=${
        qryParams.page
      }&sort=${qryParams.sortBy},${(qryParams.sortDir || '').toUpperCase()}`;
    }
    return this.http.post(URL, req);
  }

  statusPartner(url, id, status) {
    let params;
    if (status === 'Active') {
      params = new HttpParams()
        .set('pkId', id)
        .set('status', 'false');
    } else {
      params = new HttpParams()
        .set('pkId', id)
        .set('status', 'true');
    }
    return this.http.post(url, params);
  }

  statusUser(url, id, status) {
    let params = {};
    if (status === 'Active') {
      params['pkId'] = id;
      params['status'] = false;
    } else {
      params['pkId'] = id;
      params['status'] = true;
    }
    return this.http.post(url, params);
  }

  deletePartner(url, protocol, id, deleteUser?, deleteMailbox?) {
    protocol = protocolAPIS[protocol];
    if (protocol === 'remote-ftp') {
      const params = new HttpParams()
        .set('pkId', id)
        .set('deleteUser', deleteUser)
        .set('deleteMailboxes', deleteMailbox);

      return this.http.delete(url + protocol.toLowerCase(), {params});
    } else if (protocol === 'remote-connect-direct') {
      const params = new HttpParams()
        .set('pkId', id)
        .set('isDeleteInSI', deleteUser);

      return this.http.delete(url + protocol.toLowerCase(), {params});
    } else {
      if (protocol == 'remote-as2') {
        url = '/pcm/si/partner/'
      }
      return this.http.delete(url + protocol.toLowerCase() + '/' + id);
    }
  }

  deleteApp(url, protocol, id, deleteUser?, deleteMailbox?) {
    protocol = protocolAPIS[protocol];
    if (protocol === 'remote-ftp') {
      const params = new HttpParams()
        .set('pkId', id)
        .set('deleteUser', deleteUser)
        .set('deleteMailboxes', deleteMailbox);

      return this.http.delete(url + protocol.toLowerCase(), {params});
    } else if (protocol === 'remote-connect-direct') {
      const params = new HttpParams()
        .set('pkId', id)
        .set('isDeleteInSI', deleteUser);

      return this.http.delete(url + protocol.toLowerCase(), {params});
    } else {
      if (protocol == 'remote-as2') {
        url = '/pcm/si/application/'
      }
      return this.http.delete(url + protocol.toLowerCase() + '/' + id);
    }
  }

  uploadCertificates(req) {
    return this.http.post(req.url, req.fb);
  }

  getPkIdDetails(url) {
    return this.http.get(url);
  }

  getActivityHistory(pkId, qryParams?) {
    let URL = environment.GET_PARTNER_ACTIVITY;
    if (qryParams) {
      URL = `${environment.GET_PARTNER_ACTIVITY}${pkId}?size=${qryParams.size}&page=${
        qryParams.page
      }&sort=${qryParams.sortBy},${qryParams.sortDir.toUpperCase()}`;
      return this.http.post(URL, {});
    } else {
      return this.http.post(URL + pkId, {});
    }

  }

  getSSHkhostList() {
    return this.http.get(environment.SSH_KHOST_LIST);
  }

  getSshKeyPair() {
    return this.http.get(environment.GET_SSH_KEY_PAIR);
  }

  isValid(req) {
    return this.http.post(environment.IS_VALID, req);
  }

  createAs2Relation(req: any) {
    return this.http.post(
      environment.AS2_PROFILES_MAPPING, req
    );
  }

  getSshUserMap() {
    return this.http.get(environment.GET_SSH_USER_LIST);
  }
}
