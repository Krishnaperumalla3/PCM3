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
import {frameFormObj, parseJson} from 'src/app/utility';
import {CREATE_USER} from 'src/app/model/user.constants';
import {TableConfig} from 'src/utility/table-config';
import {UserService} from '../../services/user.service';
import {environment} from '../../../environments/environment';
import {SEARCH_KEY} from '../create-user/create-user.component';
import {Router} from '@angular/router';
import Swal from 'sweetalert2';
import {ModuleName} from "../../../store/layout/action/layout.action";
import {AppComponent} from "../../app.component";
import {Store} from '@ngxs/store';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-manage-user',
  templateUrl: './manage-user.component.html',
  styleUrls: ['./manage-user.component.scss']
})
export class ManageUserComponent implements OnInit {
  showResults = false;
  roles: any;
  searchResults = [];
  private pageIndex = 3;
  sortBy = 'userid';
  sortDir = 'asc';
  page: Number;
  size: Number;
  totalElements: Number;
  totalPages: Number;

  displayedColumns: String[];
  serachTblConfig: any;
  serachTblConfigRef: any;

  headerColumns: string[] = [
    "USERS.TABLE.COLUMNS.USER_NAME",
    "USERS.TABLE.COLUMNS.FIRST_NAME",
    "USERS.TABLE.COLUMNS.LAST_NAME",
    "USERS.TABLE.COLUMNS.ROLE",
    "USERS.TABLE.COLUMNS.STATUS"
  ];
  displayedColumnsRef: string[] = [
    'userid',
    'firstName',
    'lastName',
    'role',
    'status'
  ];

  actionButtons = [
    {
      page: 'manage user',
      label: "USERS.BUTTONS.ACTIVATE",
      iconCls: '',
      class: 'btn btn-success',
      link: environment.CHANGE_USER_STATUS,
      id: 'userActivate'
    },
    {
      page: 'manage user',
      label: "USERS.BUTTONS.DEACTIVATE",
      iconCls: '',
      class: 'btn btn-pcm-danger',
      link: environment.CHANGE_USER_STATUS,
      id: 'userDeactivate'
    },
    {
      page: 'manage user',
      label: "USERS.BUTTONS.EDIT",
      iconCls: '',
      class: 'btn btn-pcm-edit',
      link: environment.CREATE_USER,
      id: 'userEdit'
    },
    {
      page: 'manage user',
      label: "USERS.BUTTONS.DELETE",
      iconCls: '',
      class: 'btn btn-pcm-delete',
      link: environment.DELETE_USER,
      id: 'userDelete'
    }
  ];

  reqObj: Object;
  qryParams: Object = {
    page: 0,
    sortBy: 'userid',
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
    this.manageUserFormGroup();
  }

  manageUserForm: FormGroup;
  manageUserFields = CREATE_USER.manageUserFields;

  manageUserFormGroup() {
    this.manageUserForm = this.fb.group(frameFormObj(this.manageUserFields));
  }

  trimValue(formControl) {
    formControl.setValue(formControl.value.trimLeft());
  }

  ngOnInit() {
    this.getRolesList();
    this.displayedColumns = this.displayedColumnsRef;
    this.serachTblConfig = this.tblConf.formColumn(
      this.displayedColumns,
      this.headerColumns
    );
    this.serachTblConfigRef = this.serachTblConfig;
    this.manageUserForm.patchValue(parseJson(sessionStorage.getItem(SEARCH_KEY) || '{}'));
    sessionStorage.removeItem(SEARCH_KEY);
  }

  getRolesList() {
    this.userService.getRoles().subscribe(res => {
      this.roles = res;
      this.manageUserFields = this.manageUserFields.map(val => {
        if (val.formControlName === 'userRole') {
          val.options = this.roles.map(opt => {
            return {
              label: opt.value,
              value: opt.key
            };
          });
        }
        return val;
      });
    });
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
    this.userService.searchUser(req, qryParams).subscribe(res => {
      this.showResults = true;
      this.searchResults = res['content'].filter(val => {
        if (val['status'] === 'N') {
          return val['status'] = 'Inactive';
        } else if (val['status'] === 'Y') {
          return val['status'] = 'Active';
        }
      });
      this.size = res['size'];
      this.page = res['number'];
      this.totalElements = res['totalElements'];
      this.totalPages = res['totalPages'];
    }, (err) => {
      if (err.status !== 401) {
        Swal.fire(
          'User',
          err['error']['errorDescription'],
          'error'
        );
      }
    });
  }

  onSubmit(form: FormGroup) {
    this.searchResults = [];
    this.reqObj = form.value;
    this.reqObj['b2bUser'] = true;
    this.currentPage = 0;
    this.qryParams = {
      page: 0,
      sortBy: 'userid',
      sortDir: 'asc',
      size: 10
    };
    this.showResults = false;
    this.loadSearchResults(this.reqObj);
  }

  resFromTable(eve) {
    if (eve.statusCode === 200) {
      this.loadSearchResults(this.manageUserForm.value, this.qryParams);
    }
  }

  cancel() {
    this.manageUserFormGroup();
    this.showResults = false;
    this.currentPage = 0;
  }

}
