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
import {RuleService} from '../services/rule.service';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {UserService} from '../services/user.service';
import {FileSearchService} from '../services/file-search/file-search.service';

@Injectable({
  providedIn: 'root'
})
export class EnvelopeService {

  constructor(
    private ruleService: RuleService,
    private userService: UserService,
    private fileSearchService: FileSearchService,
    private http: HttpClient
  ) {
  }

  getBusinessProcessList() {
    return this.ruleService.getBusinessProcessList();
  }

  getPartnerMap() {
    return this.userService.getPartnerMap();
  }

  getMapNameList() {
    return this.fileSearchService.getMapNameList();
  }

  getTerminatorsMapList() {
    return this.http.get(environment.GET_TERMINATORS_MAP_LIST);
  }

  createX12(payload: any, isUpdate = false) {
    return isUpdate ? this.http.put(environment.ENVELOPE.CREATE_X12, payload) : this.http.post(environment.ENVELOPE.CREATE_X12, payload);
  }

  searchManageEnvelopes(req, qryParams?) {
    let URL = environment.ENVELOPE.MANAGE_X12;
    if (qryParams) {
      URL = `${environment.ENVELOPE.MANAGE_X12}?size=${qryParams.size}&page=${
        qryParams.page
        }&sort=${qryParams.sortBy}&direction=${(qryParams.sortDir || '').toUpperCase()}`;
    }
    return this.http.post(URL, req);
  }

  delete(url, id) {
    return this.http.delete(url + '/' + id);
  }

  getEnvelopByPkid(id) {
    return this.http.get(environment.ENVELOPE.CREATE_X12 + '/' + id);
  }

  getActivity(pkId, qryParams?) {
    let URL = environment.ENVELOPE.ACTIVITY;
    if (qryParams) {
      // tslint:disable-next-line:max-line-length
      URL = `${environment.ENVELOPE.ACTIVITY}${pkId}?size=${qryParams.size}&page=${qryParams.page}&sort=${qryParams.sortBy},${qryParams.sortDir.toUpperCase()}`;
      return this.http.post(URL, {});
    } else {
      return this.http.post(URL + pkId, {});
    }
  }
}
