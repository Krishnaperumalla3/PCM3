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

import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormBuilder, FormGroup , Validators } from '@angular/forms';
import {UserService} from "../../services/user.service";
import {TableConfig} from "../../../utility/table-config";
import {environment} from "../../../environments/environment";
import {ModuleName} from "../../../store/layout/action/layout.action";
import {AppComponent} from "../../app.component";
import {Store} from '@ngxs/store';

@Component({
  selector: 'pcm-manage-b2bi-users',
  templateUrl: './manage-b2bi-users.component.html',
  styleUrls: ['./manage-b2bi-users.component.scss']
})
export class ManageB2biUsersComponent implements OnInit {

  manageB2biUserForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private cd: ChangeDetectorRef,
    private userService: UserService,
    private tblConf: TableConfig,
    private appcomponent: AppComponent,
    private store: Store
  ) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.ACCESS_MNGNT'));
    this.appcomponent.selected = 'accessMgt';
    this.manageB2biUserFormGroup();
  }
  manageGroupForm: FormGroup;

  showResults = false;
  searchResults = [];

  sortBy = 'status';
  sortDir = 'desc';
  page: Number;
  size: Number;
  totalElements: Number;
  totalPages: Number;

  displayedColumns: String[];
  serachTblConfig: any;
  serachTblConfigRef: any;

  headerColumns: string[] = [
    'Group Name'
  ];
  displayedColumnsRef: string[] = [
    'groupName'
  ];

  actionButtons = [
    {
      page: 'manage b2bi',
      label: 'Edit',
      iconCls: '',
      class: 'btn btn-pcm-2',
      link: environment.FIND_GROUP,
      id: 'b2bEdit'
    }
  ];

  reqObj: Object;
  qryParams: Object = {
    page: 0,
    sortBy: 'status',
    sortDir: 'desc',
    size: 10
  };

  ngOnInit() {
    this.displayedColumns = this.displayedColumnsRef;
    this.serachTblConfig = this.tblConf.formColumn(
      this.displayedColumns,
      this.headerColumns
    );
    this.serachTblConfigRef = this.serachTblConfig;
  }

  sort(event) {
    this.sortBy = event.active;
    this.sortDir = event.direction;
    this.qryParams = {
      ...this.qryParams,
      ...{
        sortBy: this.sortBy,
        sortDir: this.sortDir,
        page: 0
      }
    };
    this.loadSearchResults(this.reqObj, this.qryParams);
  }

  manageB2biUserFormGroup(){
    this.manageB2biUserForm = this.fb.group({
      userId: ['', []],
      partnerId: ['', []]
    });
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
    this.page = event.pageIndex;
    this.qryParams = {
      ...this.qryParams,
      ...{
        page: this.page,
        size: event.pageSize
      }
    };

    this.loadSearchResults(this.reqObj, this.qryParams, true);
  }

  loadSearchResults(req, qryParams?, pagination?) {
    this.searchResults = [];
    this.showResults = false;
    this.userService.searchGroup(req, qryParams).subscribe(res => {
      this.showResults = true;
      console.log(res);
      this.searchResults = res['content'];
      this.totalElements = res['totalElements'];
      this.totalPages = res['totalPages'];
    });
  }

  onSubmit( form: FormGroup ) {
    this.reqObj = form.value;
    this.loadSearchResults(this.reqObj);
  }

  resFromTable(eve) {
    if(eve.statusCode === 200) {
      this.onSubmit(this.manageB2biUserForm);
    }
  }
}

