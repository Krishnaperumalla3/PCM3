
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

import {ChangeDetectorRef, Component, HostBinding, OnInit} from '@angular/core';
import {REPORT_FORM} from '../../model/reports.constant';
import {frameFormObj} from '../../utility';
import {Form, FormArray, FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {ReportsService} from '../reports.service';
import {TableConfig} from 'src/utility/table-config';
import Swal from 'sweetalert2';
import {AppComponent} from '../../app.component';
import {ModuleName} from "../../../store/layout/action/layout.action";
import {Store} from "@ngxs/store";
import {TranslateService} from "@ngx-translate/core";
import Utility from "../../../utility/Utility";
import {ruleProperties} from "../../model/correlation.constant";

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'pcm-data-flow',
  templateUrl: './data-flow.component.html',
  styleUrls: ['./data-flow.component.scss']
})
export class DataFlowComponent implements OnInit {
  @HostBinding('attr.class') className = '';
  searchPanel = true;
  advSearchPanel = false;
  form: FormGroup;
  advanceForm: FormGroup;
  formFields: any = [];
  searchFieldCtrl: any = {};
  constructor(
    private fb: FormBuilder,
    private cd: ChangeDetectorRef,
    private service: ReportsService,
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

  sortBy = 'partnerProfile';
  sortDir = 'desc';
  page: number;
  size: Number;
  totalElements: Number;
  totalPages: Number;

  displayedColumns: String[];
  searchTblConfig: any;
  searchTblConfigRef: any;

  headerColumns: string[] = [
    "#",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.PARTNER",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.APPLICATION",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.FLOW",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.SEQ_TYPE",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.FILE_NAME",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.DOC_TYPE",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.TRANSACTION",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.SENDER_ID",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RECEIVER_ID",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RULE_NAME",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.BP_ID",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP1",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP2",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP3",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP4",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP5",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP6",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP7",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP8",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP9",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP10",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP11",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP12",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP13",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP14",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP15",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP16",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP17",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP18",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP19",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP20",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP21",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP22",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP23",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP24",
    "REPORTS.TABLE.DATAFLOW_COLUMNS.RP25"
  ];
  displayedColumnsRef: string[] = [
    'rowNum',
    'partnerProfile',
    'applicationProfile',
    'flow',
    'seqType',
    'fileName',
    'docType',
    'transaction',
    'partnerId',
    'receiverId',
    'ruleName',
    'businessProcessId',
    'ruleProperty1',
    'ruleProperty2',
    'ruleProperty3',
    'ruleProperty4',
    'ruleProperty5',
    'ruleProperty6',
    'ruleProperty7',
    'ruleProperty8',
    'ruleProperty9',
    'ruleProperty10',
    'ruleProperty11',
    'ruleProperty12',
    'ruleProperty13',
    'ruleProperty14',
    'ruleProperty15',
    'ruleProperty16',
    'ruleProperty17',
    'ruleProperty18',
    'ruleProperty19',
    'ruleProperty20',
    'ruleProperty21',
    'ruleProperty22',
    'ruleProperty23',
    'ruleProperty24',
    'ruleProperty25'
  ];
  ruleList: any;
  reqObj: Object;
  qryParams: Object = {
    page: 0,
    sortBy: 'partnerProfile',
    sortDir: 'asc',
    size: 10
  };
  currentPage = 0;

  ngOnInit() {
    this.createFormGroup();
    this.advanceFormGroup();
    this.getRuleNames();
    this.displayedColumns = this.displayedColumnsRef;
    this.searchFieldCtrl['ruleName'] = new FormControl();
    this.searchFieldCtrl['ruleValue'] = new FormControl();
    this.searchTblConfig = this.tblConf.formColumn(
      this.displayedColumns,
      this.headerColumns
    );
    this.searchTblConfigRef = this.searchTblConfig;
  }

  createFormGroup() {
    this.form = this.fb.group({
      partnerName: [''],
      applicationName: [''],
      seqType: [''],
      flow: [''],
      fileType: [''],
      docType: [''],
      transaction: [''],
      senderId: [''],
      receiverId: [''],
      ruleName: [''],
      ruleValue: ['']
      // ruleValue: this.fb.array([this.fb.group({
      //   rulePropertyName: [''],
      //   rulePropertyValue: ['']
      // })])
    });
  }

  advanceFormGroup() {
    this.advanceForm = this.fb.group({
      ruleName: [''],
      propertyList: this.fb.array([this.newRuleList()])
    })
  }

  ruleProperties(): FormArray {
    return this.advanceForm.get("propertyList") as FormArray
  }

  newRuleList(): FormGroup {
    this.formFields = REPORT_FORM.PROPERTY;
    const formObj = frameFormObj(this.formFields);
    return this.fb.group(formObj);
  }

  addRuleList() {
    this.ruleProperties().push(this.newRuleList());
  }

  removeRuleList(ruleIndex:number) {
    this.ruleProperties().removeAt(ruleIndex);
  }

  getRuleNames() {
    this.service.getRuleList().subscribe(res => {
      this.ruleList = res;
    });
  }

  getSearchFilterOptions(options = [], field) {
    const value = (this.searchFieldCtrl[field].value || '').toLowerCase();
    if (!value) {
      return options;
    } else if(field === 'ruleName') {
      return options.filter(option => option.ruleName.toLowerCase().includes(value));
    } else if(field === 'ruleValue') {
      return options.filter(option => option.label.toLowerCase().includes(value));
    }
  }

  onRuleSelect(event) {
    let rulePropertyList = [];
    rulePropertyList = DataFlowComponent.frameAdvanceSearchPanel(event);
    this.formFields = REPORT_FORM.PROPERTY;
    this.formFields = this.formFields.map(val => {
      if (val.formControlName === 'ruleValue') {
        val.options = rulePropertyList;
      }
      return val;
    });
    this.newRuleList();
  }

  private static frameAdvanceSearchPanel(fieldMap) {
    const advSrchflds = [];
    for (const key in fieldMap) {
      if (Utility.isNotEmpty(fieldMap[key]) && key !== 'pkId' && key !== 'businessProcessId' && key !== 'ruleId' && key !== 'ruleName') {
        const obj = {
          label: fieldMap[key],
          value: ruleProperties[key]
        };
        advSrchflds.push(obj);
      }
    }
    return advSrchflds;

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
    this.searchTblConfig = this.searchTblConfigRef.slice().filter(col => selectedColumns.indexOf(col.key) > -1);
    this.displayedColumns = this.searchTblConfig.map(col => col.key);
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

  exportAllWorkFlow() {
    this.service.exportDatFlow();
  }

  submit(form: FormGroup) {
    let ruleModel = {} ;
    this.advanceForm.get("propertyList")['controls'].forEach( item => {
      if(!!item.value['ruleValue'])
      ruleModel[item.value['ruleValue']] = item.value['rulePropertyValue'];
    });
    ruleModel['ruleName'] = this.advanceForm.value['ruleName'];
    this.appComponent.drawer.toggle(false);
    this.searchResults = [];
    this.reqObj = form;
    this.reqObj['ruleModel'] = ruleModel;
    this.qryParams = {
      page: 0,
      sortBy: 'partnerProfile',
      sortDir: 'asc',
      size: 10
    };
    this.showResults = false;
    this.loadSearchResults(this.reqObj);
  }

  loadSearchResults(req, qryParams?) {
    this.service.searchDataFlow(req, qryParams).subscribe(res => {
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
          this.translate.instant('SWEET_ALERT.DT_FLW.SERCH.TITLE'),
          err['error']['errorDescription'],
          'error'
        );
      }
    });
  }

  resFromTable(eve) {
    if (eve.statusCode === 200) {
      this.submit(this.form);
    }
  }

  reset() {
    this.appComponent.drawer.toggle(true);
    this.form.reset();
    this.advanceForm.reset();
    this.showResults = false;
    this.totalPages = 0;
    this.totalElements = 0;
    this.currentPage = 0;
    this.qryParams = {
      page: 0,
      sortBy: 'partnerProfile',
      sortDir: 'asc',
      size: 10
    };
  }
}
