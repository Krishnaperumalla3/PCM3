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
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})

export class ScheduleService {

  constructor(private http: HttpClient) { }

  cloudList() {
    return this.http.get(environment.GET_CLOUD_LST);
  }

  regionList() {
    return this.http.get(environment.GET_REGIONS);
  }

  schedule() {
    return this.http.get(environment.GET_SCHEDULE);
  }

  create(req, id) {
    if(id) {
      return this.http.put(environment.GET_SCHEDULE, req);
    } else {
      return this.http.post(environment.GET_SCHEDULE, req);
    }
  }

  delete() {
    return this.http.delete(environment.GET_SCHEDULE);
  }
}
