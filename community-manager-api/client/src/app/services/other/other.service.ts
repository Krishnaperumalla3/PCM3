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
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class OtherService {

  constructor(private http: HttpClient) {
  }

  getB2bActive(): any {
    return this.http.get(environment.IS_B2B_ACTIVE);
  }

  getTimeRange(): any {
    return this.http.get(environment.GET_TIME_RANGE);
  }

  postFileDrop(req, headers) {
    return this.http.post(environment.POST_FILE_DROP, req, {headers: headers});
  }

  getPartnerList() {
    return this.http.get(environment.GET_PARTNER_MAP);
  }

  getPartnerMailbox() {
    return this.http.get(environment.PARTNER_MAILBOX);
  }

  getMailboxById(mailbox) {
    return this.http.get(`${environment.GET_MAILBOX}?mailbox=${mailbox}`);
  }

  downloadMailbox(req) {
    return this.http.get(`${environment.DOWNLOAD_MAILBOX}?fileName=${req.fileName}&mailBox=${req.mailbox}`, {responseType: 'blob'})
  }

}
