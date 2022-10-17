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

import {DataFlowService} from './../../services/data-flow.service';
import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MatTableDataSource} from '@angular/material';

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

const ELEMENT_DATA: PeriodicElement[] = [
  {position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
  {position: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
  {position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
  {position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
  {position: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
  {position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
  {position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
  {position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
  {position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
  {position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
];

@Component({
  selector: 'pcm-wf-activity',
  templateUrl: './wf-activity.component.html',
  styleUrls: ['./wf-activity.component.scss']
})
export class WfActivityComponent implements OnInit {

  constructor(
    private service: DataFlowService
  ) { }
  @Input () partnerPkId: string;
  @Input () applicationPkId: string;
  @Output() cancelPage: EventEmitter<any> = new EventEmitter();
  searchResults = [];
  showResults = false;
  totalElements = 0;
  totalPages = 0;
  currentPage = 0;
  sortBy = 'activityDt';
  sortDir = 'desc';
  page: Number;
  size: Number;

  qryParams: Object = {
    page: 0,
    sortBy: 'activityDt',
    sortDir: 'desc',
    size: 10,
    processRefId: ''
  };

  displayedColumns: string[] = ['activityDt', 'userName', 'activity'];
  dataSource;

  ngOnInit() {
    this.loadSearchResults(this.partnerPkId, this.applicationPkId);
  }

  sort(event) {
    // @ts-ignore
    this.currentPage = this.page;
    this.sortBy = event.active;
    this.sortDir = event.direction;
    this.qryParams = {
      ...this.qryParams,
      ...{
        sortBy: this.sortBy,
        sortDir: this.sortDir,
        page: this.page,
        size: this.size
      }
    };
    this.loadSearchResults(this.partnerPkId, this.applicationPkId, this.qryParams);
  }

  pagination(event) {
    this.page = event.pageIndex + 1;
    this.currentPage = event.pageIndex;
    this.qryParams = {
      ...this.qryParams,
      ...{
        page: Number(this.page) - 1,
        size: event.pageSize
      }
    };
    this.loadSearchResults( this.partnerPkId, this.applicationPkId, this.qryParams);
  }

  loadSearchResults( partnerPkid, applicationPkid, qryParams?) {
    this.searchResults = [];
    this.showResults = false;

    this.service.getActivity( partnerPkid, applicationPkid, qryParams).subscribe(res => {
      this.showResults = true;
      this.searchResults = res['content'];
      this.dataSource = new MatTableDataSource(this.searchResults);
      this.size = res['size'];
      this.page = res['number'];
      this.totalElements = res['totalElements'];
      this.totalPages = res['totalPages'];
    });
  }
}
