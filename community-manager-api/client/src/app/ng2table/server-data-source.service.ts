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
import { HttpClient } from '@angular/common/http';
import { LocalDataSource } from 'ng2-smart-table';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ServerDataSourceService extends LocalDataSource{

  lastRequestCount: number = 0;

  constructor(protected http: HttpClient) {
    super();
  }

  count(): number {
    return this.lastRequestCount;
  }

  getElements(): Promise<any> {
    let url = 'https://jsonplaceholder.typicode.com/photos?';

    if (this.sortConf) {
      this.sortConf.forEach((fieldConf) => {
        url += `_sort=${fieldConf.field}&_order=${fieldConf.direction.toUpperCase()}&`;
      });
    }

    if (this.pagingConf && this.pagingConf['page'] && this.pagingConf['perPage']) {
      url += `_page=${this.pagingConf['page']}&_limit=${this.pagingConf['perPage']}&`;
    }

    if (this.filterConf.filters) {
      this.filterConf.filters.forEach((fieldConf) => {
        if (fieldConf['search']) {
          url += `${fieldConf['field']}_like=${fieldConf['search']}&`;
        }
      });
    }

    return this.http.get(url, { observe: 'response' })
      .pipe(
        map(res => {
          this.lastRequestCount = +res.headers.get('x-total-count');
          return res.body;
        })
      ).toPromise();
  }
}
