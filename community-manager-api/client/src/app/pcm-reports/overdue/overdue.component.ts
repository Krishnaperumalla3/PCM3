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

import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {TableConfig} from 'src/utility/table-config';
import {ReportsService} from '../reports.service';
import {DatePipe} from '@angular/common';
import Swal from "sweetalert2";
import {ModuleName} from "../../../store/layout/action/layout.action";
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'pcm-overdue',
  templateUrl: './overdue.component.html',
  styleUrls: ['./overdue.component.scss']
})
export class OverdueComponent implements OnInit {

  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private cd: ChangeDetectorRef,
    private service: ReportsService,
    private datePipe: DatePipe,
    private tblConf: TableConfig,
    private appComponent: AppComponent,
    private store: Store,
    public translate: TranslateService
  ) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.REPORTS'));
    this.appComponent.selected = 'reports';
  }

  showResults = false;
  searchResults = [];

  sortBy = 'fileArrived';
  sortDir = 'desc';
  page: Number;
  size: Number;
  totalElements: Number;
  totalPages: Number;

  displayedColumns: String[];
  serachTblConfig: any;
  serachTblConfigRef: any;

  headerColumns: string[] = [
    "#",
    "REPORTS.TABLE.OVERDUE_COLUMNS.FILE_ARRIVED",
    "REPORTS.TABLE.OVERDUE_COLUMNS.DEST_FILE_NAME",
    "REPORTS.TABLE.OVERDUE_COLUMNS.PARTNER",
    "REPORTS.TABLE.OVERDUE_COLUMNS.TRANSACTION",
    "REPORTS.TABLE.OVERDUE_COLUMNS.GS_COUNT",
    "REPORTS.TABLE.OVERDUE_COLUMNS.SENDER_ID",
    "REPORTS.TABLE.OVERDUE_COLUMNS.RECEIVER_ID",
    "REPORTS.TABLE.OVERDUE_COLUMNS.STATUS",
    "REPORTS.TABLE.OVERDUE_COLUMNS.OVERDUE",
  ];
  displayedColumnsRef: string[] = [
    'rowNum',
    'fileArrived',
    'destFileName',
    'partner',
    'docTrans',
    'groupCount',
    'senderId',
    'receiverId',
    'status',
    'overdue'
  ];

  reqObj: Object;
  qryParams: Object = {
    page: 0,
    sortBy: 'fileArrived',
    sortDir: 'desc',
    size: 10
  };
  currentPage = 0;

  ngOnInit() {
    this.createFormGroup();
    this.displayedColumns = this.displayedColumnsRef;
    this.serachTblConfig = this.tblConf.formColumn(
      this.displayedColumns,
      this.headerColumns
    );
    this.serachTblConfigRef = this.serachTblConfig;
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
    this.loadSearchResults(this.reqObj, this.qryParams);
  }

  columnsChanged(result) {
    this.showResults = false;
    const selectedColumns = result.map(val => val.value);
    this.serachTblConfig = this.serachTblConfigRef.slice().filter(col => selectedColumns.indexOf(col.key) > -1);
    this.displayedColumns = this.serachTblConfig.map(col => col.key);
    this.cd.detectChanges();
    this.showResults = true;
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
    this.loadSearchResults(this.reqObj, this.qryParams);
  }

  createFormGroup() {
    const date = new Date();
    this.form = this.fb.group({
      dateRangeStart: [new Date(date.setDate(date.getDate() - 1)), [Validators.required]],
      dateRangeEnd: [new Date(date.setDate(date.getDate() + 1)), [Validators.required]],
      partner: [''],
      docTrans: [''],
      destFileName: [''],
      status: [''],
      senderId: [''],
      receiverId: [''],
      groupCount: ['']
    });
  }

  loadSearchResults(req, qryParams?) {
    this.service.searchOverDue(req, qryParams).subscribe(res => {
      this.showResults = true;
      this.searchResults = res['content'];
      this.size = res['size'];
      this.page = res['number'];
      this.totalElements = res['totalElements'];
      this.totalPages = res['totalPages'];
    }, (err) => {
      if(err.status !== 401) {
        Swal.fire(
          this.translate.instant('SWEET_ALERT.OVR_DUE.SERCH.TITLE'),
          err['error']['errorDescription'],
          'error'
        );
      }
    });
  }

  submit(form: FormGroup) {
    this.searchResults = [];
    this.reqObj = form;

    Object.keys(this.reqObj).forEach(key => {
      if (key === 'dateRangeEnd' || key === 'dateRangeStart') {
        this.reqObj[key] = this.datePipe.transform(this.reqObj[key], 'yyyy-MM-dd hh:mm:ss');
        // this.reqObj[key] = this.datePipe.transform(new Date(this.reqObj[key]), 'E MMM dd yyyy HH:mm:ss zzzz');
      }
    });
    this.qryParams = {
      page: 0,
      sortBy: 'fileArrived',
      sortDir: 'desc',
      size: 10
    };
    this.showResults = false;
    this.loadSearchResults(this.reqObj);
  }

  reset() {
    this.createFormGroup();
    this.showResults = false;
    this.totalPages = 0;
    this.totalElements = 0;
    this.currentPage = 0;
    this.qryParams = {
      page: 0,
      sortBy: 'fileArrived',
      sortDir: 'desc',
      size: 10
    };
  }
}
