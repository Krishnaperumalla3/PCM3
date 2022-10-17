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

import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'pcm-active-cell',
  template: `
      <span [ngClass]="{'pcm-success' : rowData.appIsActive === 'Active', 'pcm-danger' : rowData.appIsActive === 'Inactive'}">
        {{rowData.appIsActive}}
      </span>
  `,
  styles: [`
  .pcm-success {
    color: white;
    padding: 3px 8px;
    border-radius: 2px;
    background-color: #4CAF50;
  }
  .pcm-danger {
    color: white;
    padding: 3px;
    border-radius: 2px;
    background-color: #F44336;
  }
  .act-danger {
    background-color: #F44336 !important;
  }
  `]
})
export class ActiveCellComponent implements OnInit {

  constructor() { }
  @Input() title = '';
  @Input() key = '';
  @Input() value: string | number;
  @Input() rowData: any;
  ngOnInit() {
  }

}
