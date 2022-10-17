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

import {Component, OnInit, ChangeDetectorRef} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import {EnvelopeService} from '../envelope.service';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'pcm-view-activity',
  templateUrl: './view-activity.component.html',
  styleUrls: ['./view-activity.component.scss']
})
export class ViewActivityComponent implements OnInit {
  pkId: any;
  activityDetails: any;
  partnerRow = {
    partnername : '',
    direction : '',
    validateinput : '',
    validateoutput : '',
    useindicator: ''
  };
  partnerName = '';
  direction = '';
  validateInput = '';
  validateOutput = '';
  useIndicator = '';
  constructor(
    private router: Router,
    private Route: ActivatedRoute,
    private cd: ChangeDetectorRef,
    private envelopeService: EnvelopeService) {
    this.Route.params.subscribe(params => {
      this.pkId = params['pkId'];
    });
  }

  displayedColumns: string[] = [ 'activityDt', 'userName', 'activity'];
  sortBy = 'activityDt';
  sortDir = 'desc';
  page: Number;
  size: Number;
  totalElements: Number;
  totalPages: Number;
  currentPage = 0;


  qryParams: Object = {
    page: 0,
    sortBy: 'activityDt',
    sortDir: 'desc',
    size: 10
  };
  ngOnInit() {
    this.partnerRow = JSON.parse(sessionStorage.getItem('VIEW_ENVELOP_KEY') || '{}');
    this.loadSearchResults(this.pkId);
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
    this.loadSearchResults(this.pkId, this.qryParams);
  }

  pagination(event) {
    this.page = event.pageIndex + 1;
    this.currentPage = event.pageIndex;
    this.qryParams = {
      ...this.qryParams,
      ...{
        page: Number(this.page) - 1,
        size: event.pageSize,
      }
    };
    this.loadSearchResults(this.pkId, this.qryParams, true);
  }

  loadSearchResults(pkId, qryParams?, pagination?) {
    this.activityDetails = [];
    this.envelopeService.getActivity(pkId, qryParams).subscribe(res => {
      console.log(res);
      this.activityDetails = res['content'];
      this.size = res['size'];
      this.page = res['number'];
      this.totalElements = res['totalElements'];
      this.totalPages = res['totalPages'];
    });
  }

  cancel() {
    this.router.navigate(['/pcm/envelope/manage-x12']);
  }
}
