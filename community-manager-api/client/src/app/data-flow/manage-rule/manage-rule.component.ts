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
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {parseJson} from 'src/app/utility';
import {RuleService} from '../../services/rule.service';
import {TableConfig} from 'src/utility/table-config';
import {SEARCH_KEY} from '../create-rule/create-rule.component';
import Swal from 'sweetalert2';
import {ModuleName} from "../../../store/layout/action/layout.action";
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import {ruleActionButtons} from "../../services/menu.permissions";
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-manage-rule',
  templateUrl: './manage-rule.component.html',
  styleUrls: ['./manage-rule.component.scss']
})
export class ManageRuleComponent implements OnInit {
  searchFilterCtrl: any = {};
  showResults = false;
  searchResults = [];

  sortBy = 'ruleName';
  sortDir = 'asc';
  page: number;
  size: Number;
  totalElements: Number;
  totalPages: Number;

  displayedColumns: String[];
  serachTblConfig: any;
  serachTblConfigRef: any;

  headerColumns: string[] = [
    this.translate.instant('DATA_FLOW.RULE.MANGE_RULE.HEADER.RULE'),
    this.translate.instant('DATA_FLOW.RULE.MANGE_RULE.HEADER.BP_ID')
  ];
  displayedColumnsRef: string[] = [
    'ruleName',
    'businessProcessId'
  ];

  actionButtons = [];
  private pageIndex = 3;
  currentPage = 0;

  reqObj: Object;
  qryParams: Object = {
    page: 0,
    sortBy: 'ruleName',
    sortDir: 'asc',
    size: 10
  };

  constructor(
    private fb: FormBuilder,
    private cd: ChangeDetectorRef,
    private ruleService: RuleService,
    private tblConf: TableConfig,
    private appComponent: AppComponent,
    private store: Store,
    private translate: TranslateService
  ) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.DATA_FLW'));
    this.appComponent.selected = 'dataFlow';
    this.searchRule();
  }

  searchRulesForm: FormGroup;
  businessProcessOptions: any = [];

  searchRule() {
    this.searchRulesForm = this.fb.group({
      businessProcessId: ['', []],
      propertyName1: [''],
      propertyName2: [''],
      propertyName3: [''],
      propertyName4: [''],
      propertyName5: [''],
      propertyName6: [''],
      propertyName7: [''],
      propertyName8: [''],
      propertyName9: [''],
      propertyName10: [''],
      propertyName11: [''],
      propertyName12: [''],
      propertyName13: [''],
      propertyName14: [''],
      propertyName15: [''],
      propertyName16: [''],
      propertyName17: [''],
      propertyName18: [''],
      propertyName19: [''],
      propertyName20: [''],
      propertyName21: [''],
      propertyName22: [''],
      propertyName23: [''],
      propertyName24: [''],
      propertyName25: [''],
      ruleId: [''],
      ruleName: ['']
    });
  }

  ngOnInit() {
    this.actionButtons = ruleActionButtons();
    this.ruleService.getBusinessProcessList().subscribe(res => {
      this.searchFilterCtrl['businessProcessId'] = new FormControl();
      this.businessProcessOptions = res;
      if (!!this.businessProcessOptions) {
        this.searchRulesForm.patchValue(parseJson(sessionStorage.getItem(SEARCH_KEY || '{}')) || {});
        sessionStorage.removeItem(SEARCH_KEY);
      }
    });

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

  loadSearchResults(req, qryParams?) {
    this.ruleService.searchRule(req, qryParams).subscribe(res => {
      this.showResults = true;
      this.searchResults = res['content'];
      this.size = res['size'];
      this.page = res['number'];
      this.currentPage = this.page;
      this.totalElements = res['totalElements'];
      this.totalPages = res['totalPages'];
    }, (err) => {
      if(err.status !== 401) {
        Swal.fire(
          this.translate.instant('SWEET_ALERT.DT_FLW.RULE.TITLE'),
          err['error']['errorDescription'],
          'error'
        );
      }
    });
  }

  onSubmit(form: FormGroup) {
    this.searchResults = [];
    this.reqObj = form.value;
    this.currentPage = 0;
    this.showResults = false;
    this.loadSearchResults(this.reqObj);
  }

  resFromTable(eve) {
    if (eve.statusCode === 200) {
      this.onSubmit(this.searchRulesForm);
    }
  }

  cancel() {
    this.searchRule();
    this.showResults = false;
    this.currentPage = 0;
  }

  getSearchFilterOptions(options = [], field) {
    const value = (this.searchFilterCtrl[field].value || '').toLowerCase();
    if (!value) {
      return options;
    }
    return options.filter(option => option.name.toLowerCase().includes(value));
  }
}
