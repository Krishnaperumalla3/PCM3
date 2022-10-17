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
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RuleService {

  constructor(private http: HttpClient) {
  }

  getBusinessProcessList() {
    return this.http.get(environment.GET_BP_LIST);
  }

  createRule(req, isEdit = false) {
    return !!isEdit ? this.upDateRule(req) : this.http.post(environment.CREATE_RULE, req);
  }

  searchRule(req, qryParams?) {
    let URL = environment.SEARCH_RULE;
    if (qryParams) {
      URL = `${environment.SEARCH_RULE}?size=${qryParams.size}&page=${
        qryParams.page
        }&sort=${qryParams.sortBy},${(qryParams.sortDir || '').toUpperCase()}`;
    }
    return this.http.post(URL, req);
  }

  searchAppliedRules(req, qryParams?) {
    let URL = environment.SEARCH_FLOWS;
    if (qryParams) {
      URL = `${environment.SEARCH_FLOWS}?size=${qryParams.size}&page=${
        qryParams.page
        }&sort=${qryParams.sortBy},${(qryParams.sortDir || '').toUpperCase()}`;
    }
    return this.http.post(URL, req);
  }

  getRule(url, id) {
    return this.http.get(url + '/' + id);
  }

  upDateRule(req) {
    return this.http.put(environment.CREATE_RULE, req);
  }

  deleteRule(url, id) {
    return this.http.delete(url + '/' + id);
  }

  SearchAppliedRules(req) {
    return this.http.post(environment.SEARCH_APPLIED_RULES, req);
  }
}
