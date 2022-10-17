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

import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {applicationProtocols} from 'src/app/model/protocols.constants';
import {frameFormObj} from 'src/app/utility';
import {FileSearchService} from 'src/app/services/file-search/file-search.service';
import {ApplicationService} from 'src/app/services/application/application.service';
import {TableConfig} from 'src/utility/table-config';
import {APPLICATION_KEY} from '../create-application/create-application.component';
import Swal from 'sweetalert2';
import {appActionButtons} from "../../services/menu.permissions";
import {ModuleName} from "../../../store/layout/action/layout.action";
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-manage-application',
  templateUrl: './manage-application.component.html',
  styleUrls: ['./manage-application.component.scss']
})
export class ManageApplicationComponent implements OnInit, OnDestroy {

  searchFilterCtrl: any = {};
  constructor(
    private fb: FormBuilder,
    private cd: ChangeDetectorRef,
    private fService: FileSearchService,
    private applicationService: ApplicationService,
    private tblConf: TableConfig,
    private appComponent: AppComponent,
    private store: Store,
    public translate: TranslateService
  ) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.APPLICATION'));
    this.appComponent.selected = 'application';
    this.manageApplicationFormGroup();
  }

  manageApplicationForm: FormGroup;
  manageApplicationFields = applicationProtocols.ManageApplicationFields;
  proLst: any;

  showResults = false;
  searchResults = [];

  sortBy = 'applicationName';
  sortDir = 'asc';
  page: Number;
  size: Number;
  totalElements: Number;
  totalPages: Number;
  currentPage = 0;
  displayedColumns: String[];
  serachTblConfig: any;
  serachTblConfigRef: any;

  headerColumns: string[] = [
    "APPLICATION.TABLE.COLUMNS.APP_NME",
    "APPLICATION.TABLE.COLUMNS.APP_ID",
    "APPLICATION.TABLE.COLUMNS.PRTOCOL",
    "APPLICATION.TABLE.COLUMNS.EMAIL",
    "APPLICATION.TABLE.COLUMNS.STATUS"
  ];
  displayedColumnsRef: string[] = [
    'applicationName',
    'applicationId',
    'appIntegrationProtocol',
    'emailId',
    'appIsActive'
  ];

  actionButtons = [];

  reqObj: Object;
  qryParams: Object = {
    page: 0,
    sortBy: 'applicationName',
    sortDir: 'asc',
    size: 10
  };

  manageApplicationFormGroup() {
    this.manageApplicationForm = this.fb.group(frameFormObj(this.manageApplicationFields));
  }

  ngOnInit() {
    this.manageApplicationFields.map(fld => {
      this.searchFilterCtrl[fld.formControlName] = new FormControl();
    });
    this.actionButtons = appActionButtons();
    this.fService.getProtocolList().subscribe(res => {
      this.proLst = res;
      this.manageApplicationFields = this.manageApplicationFields.map(val => {
        if (val.formControlName === 'protocol') {
          val.options = this.proLst.map(opt => {
            return {
              label: opt.value,
              value: opt.key
            };
          }).filter(type => (type.value !== 'ExistingConnection' && type.value !== 'CUSTOM_PROTOCOL'));
        }
        return val;
      });
    });

    const partner = JSON.parse(sessionStorage.getItem('APPLICATION_KEY') || '{}');
    sessionStorage.removeItem('APPLICATION_KEY');
    this.manageApplicationForm.patchValue(partner);

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

  onSubmit(form: FormGroup) {
    this.searchResults = [];
    sessionStorage.removeItem('APPLICATION_KEY');
    sessionStorage.setItem('APPLICATION_KEY', JSON.stringify(form.value));
    this.reqObj = form.value;
    this.currentPage = 0;
    this.qryParams = {
      page: 0,
      sortBy: 'applicationName',
      sortDir: 'asc',
      size: 10
    };
    this.showResults = false;
    this.loadSearchResults(this.reqObj);
  }

  loadSearchResults(req, qryParams?) {
    this.applicationService.searchFile(req, qryParams).subscribe(res => {
      this.showResults = true;
      this.searchResults = res['content'].filter(val => {
        if (val['appIsActive'] === 'N') {
          return val['appIsActive'] = 'Inactive';
        } else {
          return val['appIsActive'] = 'Active';
        }
      });
      this.size = res['size'];
      this.page = res['number'];
      this.currentPage = res['number'];
      this.totalElements = res['totalElements'];
      this.totalPages = res['totalPages'];
    }, (err) => {
      if(err.status !== 401) {
        Swal.fire(
          this.translate.instant('SWEET_ALERT.APP.TITLE'),
          err['error']['errorDescription'],
          'error'
        );
      }
    });
  }

  resFromTable(eve) {
    if (eve.statusCode === 200) {
      this.loadSearchResults(this.manageApplicationForm.value, this.qryParams);
    }
  }

  resetForm() {
    this.manageApplicationFormGroup();
    this.showResults = false;
    this.currentPage = 0;
  }

  getSearchFilterOptions(options = [], field) {
    const value = (this.searchFilterCtrl[field].value || '').toLowerCase();
    if (!value) {
      return options;
    }
    return options.filter(option => option.label.toLowerCase().includes(value));
  }

  ngOnDestroy() {
    sessionStorage.removeItem('APPLICATION_KEY');
  }
}
