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

import {Columns} from './../app/ng2table/columns';
import {Injectable} from '@angular/core';

@Injectable({
    providedIn: 'root'
  })
export class TableConfig {

    constructor() {}

  formColumn(cols, header) {
     const columns = cols;
     const columnConfig: any = [];
     columns.forEach((col: any, i) => {
          const obj = {
              key: col,
              header: header[i],
              cell: (element: any) => element[col]
          };
          columnConfig.push(obj);
      });
      return columnConfig;
  }

  wrapperColumn(columns: Array<Columns>) {
    const wrapperObj = {};
    columns.forEach(col => {
      wrapperObj[col.id] =  Object.assign({title: col.title}, col);
    });
    return wrapperObj;
 }
}
