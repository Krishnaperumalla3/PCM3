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
import {FormBuilder, FormGroup} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {TableConfig} from '../../../utility/table-config';
import {environment} from '../../../environments/environment';
import {parseJson} from '../../utility';
import {SEARCH_KEY} from '../create-user/create-user.component';
import {Router} from '@angular/router';
import Swal from 'sweetalert2';
import {AppComponent} from '../../app.component';
import {Store} from '@ngxs/store';
import {ModuleName} from "../../../store/layout/action/layout.action";
import {TranslateService} from "@ngx-translate/core";

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'pcm-manage-group',
  templateUrl: './manage-group.component.html',
  styleUrls: ['./manage-group.component.scss']
})
export class ManageGroupComponent implements OnInit {

  manageGroupForm: FormGroup;
  showResults = false;
  searchResults = [];

  sortBy = 'groupname';
  sortDir = 'asc';
  page: Number;
  size: Number;
  totalElements: Number;
  totalPages: Number;

  displayedColumns: String[];
  serachTblConfig: any;
  serachTblConfigRef: any;

  reqObj: Object;
  qryParams: Object = {
    page: 0,
    sortBy: 'groupname',
    sortDir: 'asc',
    size: 10
  };
  currentPage = 0;
  constructor(
    private fb: FormBuilder,
    private cd: ChangeDetectorRef,
    private userService: UserService,
    private tblConf: TableConfig,
    private router: Router,
    private appComponent: AppComponent,
    private store: Store,
    public translate: TranslateService
  ) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.ACCESS_MNGNT'));
    this.appComponent.selected = 'accessMgt';
    this.manageFormGroup();
  }

  headerColumns: string[] = [
    "GROUPS.TABLE.COLUMNS.GROUP_NAME"
  ];
  displayedColumnsRef: string[] = [
    'groupname'
  ];

  actionButtons = [
    {
      page: 'manage group',
      label: "GROUPS.BUTTONS.EDIT",
      iconCls: '',
      class: 'btn btn-pcm-edit',
      link: environment.FIND_GROUP,
      id: 'groupEdit'
    },
    {
      page: 'manage group',
      label: "GROUPS.BUTTONS.DELETE",
      iconCls: '',
      class: 'btn btn-pcm-delete',
      link: environment.FIND_GROUP,
      id: 'groupDelete'
    },
  ];

  manageFormGroup() {
    this.manageGroupForm = this.fb.group({
      groupName: ['', []]
    });
  }

  trimValue(formControl) { formControl.setValue(formControl.value.trimLeft()); }

  ngOnInit() {
    this.displayedColumns = this.displayedColumnsRef;
    this.serachTblConfig = this.tblConf.formColumn(
      this.displayedColumns,
      this.headerColumns
    );
    this.serachTblConfigRef = this.serachTblConfig;
    this.manageGroupForm.patchValue(parseJson(sessionStorage.getItem(SEARCH_KEY) || '{}'));
    sessionStorage.removeItem(SEARCH_KEY);
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
    this.currentPage = event.pageIndex;
    this.page = event.pageIndex + 1;
    this.qryParams = {
      ...this.qryParams,
      ...{
        page: Number(this.page) - 1,
        size: event.pageSize
      }
    };
    this.loadSearchResults(this.reqObj, this.qryParams);
  }

  loadSearchResults(req, qryParams?) {
    this.userService.searchGroup(req, qryParams).subscribe(res => {
      this.showResults = true;
      this.searchResults = res['content'];
      this.size = res['size'];
      this.page = res['number'];
      this.currentPage = res['number'];
      this.totalElements = res['totalElements'];
      this.totalPages = res['totalPages'];
    }, (err) => {
      if(err.status !== 401) {
        Swal.fire(
          this.translate.instant('SWEET_ALERT.GROUP.TITLE'),
          err['error']['errorDescription'],
          'error'
        );
      }
    });
  }

  onSubmit(form: FormGroup) {
    this.searchResults = [];
    this.reqObj = form.value;
    this.qryParams = {
      page: 0,
      sortBy: 'groupname',
      sortDir: 'asc',
      size: 10
    };
    this.showResults = false;
    this.loadSearchResults(this.reqObj);
  }

  resFromTable(eve) {
    if (eve.statusCode === 200) {
      this.loadSearchResults(this.manageGroupForm.value, this.qryParams);
    }
  }

  resetForm() {
    this.manageGroupForm.reset();
    this.showResults = false;
  }
}
