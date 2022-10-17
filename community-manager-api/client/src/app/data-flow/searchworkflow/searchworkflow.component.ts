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

import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {SEARCH_WORKFLOW} from '../../model/data-flow.constants';
import {frameFormObj, markFormFieldTouched, removeSpaces} from '../../utility';
import {TableConfig} from '../../../utility/table-config';
import Swal from 'sweetalert2';
import {SelectionModel} from '@angular/cdk/collections';
import {Router} from '@angular/router';
import {SwalComponent} from '@sweetalert2/ngx-sweetalert2';
import {MatSort} from '@angular/material/sort';
import {LocalDataSource} from 'ng2-smart-table';
import {DataFlowService} from '../../services/data-flow.service';
import {RuleService} from '../../services/rule.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'pcm-searchworkflow',
  templateUrl: './searchworkflow.component.html',
  styleUrls: ['./searchworkflow.component.scss']
})

export class SearchworkflowComponent implements OnInit {
  @ViewChild('sort1') private sort1: MatSort;
  @ViewChild('invalidSwal') private invalidSwal: SwalComponent;

  setAppProfile = {
    inboundFlow: {
      mfts: {
        processDocModels: []
      },
      docHandlings: {
        processDocModels: []
      }
    },
    outboundFlow: {
      mfts: {
        processDocModels: []
      },
      docHandlings: {
        processDocModels: []
      }
    }
  };
  appPrflFound: boolean;
  showResults = false;
  searchResults: any;
  dataSource: any;

  constructor(private fb: FormBuilder,
              private tblConf: TableConfig,
              private service: RuleService,
              private cd: ChangeDetectorRef,
              private router: Router,
              private dataFlowService: DataFlowService,
              private translate: TranslateService
  ) {
    this.searchFormGroup();
    if (this.router.url === '/pcm/data-flow/addflow') {
      this.setAppProfile = JSON.parse(localStorage.getItem('appProfile'));
      this.appPrflFound = true;
    }
  }

  updateObj = {};
  private selectedFlows: any;
  createPanel = true;
  searchPanel = true;
  pkId: any;
  searchForm: FormGroup;
  copyAppProfile: FormGroup;
  boundForm: FormGroup;
  searchField = SEARCH_WORKFLOW.searchField;
  copyAppFlds = [
    {
      placeholder: 'DATA_FLOW.FIELDS.PRTNR_PRDCR.LABEL',
      formControlName: 'partnerProfile',
      inputType: 'pcm-select',
      options: [],
      required: true,
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'DATA_FLOW.FIELDS.APP_CUST.LABEL',
      formControlName: 'applicationProfile',
      inputType: 'pcm-select',
      options: [],
      required: true,
      validation: ['', [Validators.required]]
    }
  ];
  boundFlds = [
    {

      placeholder: 'DATA_FLOW.SRCH_MUL_WRK_FLW.FIELDS.INBUND_SNDR_ID.LABEL',
      formControlName: 'inSenderId',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'DATA_FLOW.SRCH_MUL_WRK_FLW.FIELDS.INBUN_RCVR_ID.LABEL',
      formControlName: 'inReceiverId',
      inputType: 'pcm-text',
      validation: ['']
    },
    {

      placeholder: 'DATA_FLOW.SRCH_MUL_WRK_FLW.FIELDS.OUTBUND_SNDR_ID.LABEL',
      formControlName: 'outSenderId',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'DATA_FLOW.SRCH_MUL_WRK_FLW.FIELDS.OUTBUN_RCVR_ID.LABEL',
      formControlName: 'outReceiverId',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    }
  ];

  data: LocalDataSource;

  searchFilterCtrl: any = {};

  totalRecords;
  selection = new SelectionModel<string[]>(true, []);
  sortBy = 'partnerProfile';
  sortDir = 'desc';
  page: number;
  size: Number;
  totalElements: Number;
  totalPages: Number;
  filter: boolean;

  displayedColumns: String[];
  serachTblConfig: any;
  serachTblConfigRef: any;

  reqObj: Object;
  qryParams: Object = {
    page: 0,
    sortBy: 'partnerProfile',
    sortDir: 'asc',
    size: 10
  };
  currentPage = 0;

  headerColumns: string[] = [
    'DATA_FLOW.SRCH_MUL_WRK_FLW.TABLE.COLUMNS.SLCT',
    'DATA_FLOW.SRCH_MUL_WRK_FLW.TABLE.COLUMNS.PRTNR_NME',
    'DATA_FLOW.SRCH_MUL_WRK_FLW.TABLE.COLUMNS.APP_NME',
    'DATA_FLOW.SRCH_MUL_WRK_FLW.TABLE.COLUMNS.SEQ_TYPE',
    'DATA_FLOW.SRCH_MUL_WRK_FLW.TABLE.COLUMNS.FLW',
    'DATA_FLOW.SRCH_MUL_WRK_FLW.TABLE.COLUMNS.FL_NME',
    'DATA_FLOW.SRCH_MUL_WRK_FLW.TABLE.COLUMNS.DOC_TYPE',
    'DATA_FLOW.SRCH_MUL_WRK_FLW.TABLE.COLUMNS.TRNSACTN',
    'DATA_FLOW.SRCH_MUL_WRK_FLW.TABLE.COLUMNS.SNDR_ID',
    'DATA_FLOW.SRCH_MUL_WRK_FLW.TABLE.COLUMNS.RCVR_ID'
  ];

  displayedColumnsRef: string[] = [
    'select',
    'partnerProfile',
    'applicationProfile',
    'seqType',
    'flow',
    'fileName',
    'docType',
    'receiverId',
    'partnerId',
    'transaction'
  ];

  searchFormGroup() {
    this.searchForm = this.fb.group(frameFormObj(this.searchField));
  }

  ngOnInit() {
    this.searchFormGroup();
    const appForm = this.copyAppFlds.map(fld => {
      this.searchFilterCtrl[fld.formControlName] = new FormControl();
      if (fld.formControlName === 'partnerProfile') {
        fld.options = (JSON.parse(localStorage.getItem('partnerProfileList')) || []).map(val => {
          return {label: val.value, value: val.key};
        });
      } else if (fld.formControlName === 'applicationProfile') {
        fld.options = (JSON.parse(localStorage.getItem('applicationProfileList')) || []).map(val => {
          return {label: val.value, value: val.key};
        });
      }
      return fld;
    });

    this.copyAppProfile = this.fb.group(frameFormObj(appForm));
    this.boundForm = this.fb.group(frameFormObj(this.boundFlds));
    this.displayedColumns = this.displayedColumnsRef;
    this.serachTblConfig = this.tblConf.formColumn(
      this.displayedColumns,
      this.headerColumns
    );

    this.copyAppProfile.controls['partnerProfile'].setValue(
      localStorage.getItem('partnerProfile')
    );
    this.copyAppProfile.controls['applicationProfile'].setValue(
      localStorage.getItem('applicationProfile')
    );
    this.serachTblConfigRef = this.serachTblConfig;

    this.updateObj = {
      appProfileForm: this.copyAppProfile,
      boundForm: this.boundForm
    };

    this.selection.isSelected = this.isChecked.bind(this);
  }

  getSearchFilterOptions(options = [], field) {
    const value = (this.searchFilterCtrl[field].value || '').toLowerCase();
    if (!value) {
      return options;
    }
    return options.filter(option => option.label.toLowerCase().includes(value));
  }

  getFieldName(field) {
    return field.placeholder;
  }

  columnsChanged(result) {
    this.showResults = false;
    const selectedColumns = result.map(val => val.value);
    this.serachTblConfig = this.serachTblConfigRef.slice().filter(col => selectedColumns.indexOf(col.key) > -1);
    this.displayedColumns = this.serachTblConfig.map(col => col.key);
    this.cd.detectChanges();
    this.showResults = true;
  }

  loadSearchResults(req, qryParams?) {
    this.service.searchAppliedRules(req, qryParams).subscribe(res => {
      this.showResults = true;
      this.dataSource = res['content'];
      this.searchResults = this.dataSource;
      this.size = res['size'];
      this.page = res['number'];
      this.currentPage = this.page;
      this.totalElements = res['totalElements'];
      this.totalRecords = this.totalElements;
        this.totalPages = res['totalPages'];
    }, (err) => {
      if(err.status !== 401) {
        Swal.fire(
          this.translate.instant('SWEET_ALERT.DT_FLW.TITLE'),
          err['error']['errorDescription'],
          'error'
        );
      }
    });
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

  sort(event) {
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

  search(form: FormGroup) {
    this.createPanel = false;
    this.reqObj = form;
    this.reqObj['applicationPkId'] = this.copyAppProfile.value['applicationProfile'];
    this.reqObj['partnerPkId'] = this.copyAppProfile.value['partnerProfile'];
    this.qryParams = {
      page: 0,
      sortBy: 'partnerProfile',
      sortDir: 'asc',
      size: 10
    };
    this.showResults = false;
    // this.selection.clear();
    this.loadSearchResults(this.reqObj);
  }

  isChecked(row: any): boolean {
    const found = this.selection.selected.find(el => el['processDocPkId'] === row['processDocPkId']);
    return !!found;

  }

  isAllSelected() {
    this.selectedFlows = this.selection.selected;
    const numSelected = this.selection.selected.length;
    const numRows = this.searchResults.length;
    return numSelected === numRows;
  }

  selectionToggle(event, row) {
    if (event['checked'] === false) {
      this.selection.selected.forEach((el, index) => {
        if (el['processDocPkId'] === row['processDocPkId']) {
          this.selection.selected.splice(index, 1);
        }
      })
    } else {
      this.selection.selected.push(row);
    }
  }

  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.searchResults.forEach(row => this.selection.select(row));
  }

  checkboxLabel(row) {
    if (!row) {
      return `${this.isAllSelected() ? 'select' : 'deselect'} all`;
    }
    return `${this.selection.isSelected(row) ? 'deselect' : 'select'} row ${row.position - 1}`;
  }

  reset() {
    this.showResults = false;
    this.searchFormGroup();
  }

  applyFilter(filterValue) {
    const fltrString = filterValue['target']['value'].trim().toLowerCase();
    if (fltrString !== '') {
      this.searchResults = this.dataSource.slice().filter(row => {
        for (const key of this.displayedColumnsRef) {
          // @ts-ignore
          if (row[key] && row[key] !== null && row[key] !== undefined) {
            // @ts-ignore
            const val = row[key].toString().toLowerCase();
            if (val.indexOf(fltrString) > -1) {
              return row;
            }
          }
        }
      });
      this.totalRecords = this.searchResults.length;
    } else {
      this.searchResults = this.dataSource.slice();
      this.totalRecords = this.totalElements;
    }
  }

  cancel() {
    this.router.navigate(['pcm/data-flow/build']);
  }

  next() {
    const isInvalid = this.copyAppProfile.invalid || this.boundForm.invalid;
    markFormFieldTouched(this.copyAppProfile);
    markFormFieldTouched(this.boundForm);
    if (isInvalid) {
      Swal.fire(
        this.translate.instant('SWEET_ALERT.DT_FLW.MAND_ERR.TITLE'),
        this.translate.instant('SWEET_ALERT.DT_FLW.MAND_ERR.BODY'),
        'error'
      );
      this.createPanel = true;
      this.searchPanel = false;
      return false;
    }

    if (this.selectedFlows.length === 0) {
      Swal.fire(
        this.translate.instant('SWEET_ALERT.DT_FLW.SLCT_ATLEAST.TITLE'),
        this.translate.instant('SWEET_ALERT.DT_FLW.SLCT_ATLEAST.BODY'),
        'error'
      );
      return false;
    }

    let content = [];
    let selectedRuleFlows = [];
    this.selectedFlows.forEach(workFlow => {
      content.push(workFlow.processDocPkId);
    });

    this.service.SearchAppliedRules({content}).subscribe(res => {
      const appliedRulesArray = res['content'];
      this.selectedFlows.forEach(selectedFlow => {
        appliedRulesArray.forEach((rulesListObject) => {
          if (selectedFlow.processDocPkId === rulesListObject.key) {
            selectedFlow['processRulesList'] = rulesListObject['list'];
            selectedRuleFlows.push(selectedFlow);
            return;
          }
        })
      });

      this.doMapResult(selectedRuleFlows);
    });
  }

  doMapResult(selectedFlows) {
    selectedFlows.forEach(selectedFlow => {

      let seqAppObj = {
        'fileNamePattern': '',
        'docType': '',
        'partnerId': '',
        'receiverId': '',
        'docTrans': '',
        'versionNo': '',
        'processRulesList': []
      };
      if (selectedFlow.flow === 'Inbound') {
        if (selectedFlow.seqType === 'MFT') {
          seqAppObj.fileNamePattern = selectedFlow.fileName;
          seqAppObj.docType = selectedFlow.docType;
          seqAppObj.partnerId = selectedFlow.partnerId;
          seqAppObj.processRulesList = selectedFlow.processRulesList;
          this.setAppProfile.inboundFlow.mfts.processDocModels.push(seqAppObj);
        } else if (selectedFlow.seqType === 'DocHandling') {
          seqAppObj.fileNamePattern = selectedFlow.fileName;
          seqAppObj.docType = selectedFlow.docType;
          seqAppObj.partnerId = this.boundForm.value['inSenderId'] ? this.boundForm.value['inSenderId'] : selectedFlow.partnerId;
          seqAppObj.receiverId = this.boundForm.value['inReceiverId'] ? this.boundForm.value['inReceiverId'] : selectedFlow.receiverId;
          seqAppObj.processRulesList = selectedFlow.processRulesList;
          seqAppObj.versionNo = selectedFlow.versionNo;
          seqAppObj.docTrans = selectedFlow.transaction;
          this.setAppProfile.inboundFlow.docHandlings.processDocModels.push(seqAppObj);
        }

      } else if (selectedFlow.flow === 'Outbound') {

        if (selectedFlow.seqType === 'MFT') {
          seqAppObj.fileNamePattern = selectedFlow.fileName;
          seqAppObj.docType = selectedFlow.docType;
          seqAppObj.partnerId = selectedFlow.partnerId;
          seqAppObj.processRulesList = selectedFlow.processRulesList;
          this.setAppProfile.outboundFlow.mfts.processDocModels.push(seqAppObj);
        } else if (selectedFlow.seqType === 'DocHandling') {
          seqAppObj.fileNamePattern = selectedFlow.fileName;
          seqAppObj.docType = selectedFlow.docType;
          seqAppObj.partnerId = this.boundForm.value['outSenderId'] ? this.boundForm.value['outSenderId'] : selectedFlow.partnerId;
          seqAppObj.receiverId = this.boundForm.value['outReceiverId'] ? this.boundForm.value['outReceiverId'] : selectedFlow.receiverId;
          seqAppObj.docTrans = selectedFlow.transaction;
          seqAppObj.versionNo = selectedFlow.versionNo;
          seqAppObj.processRulesList = selectedFlow.processRulesList;
          this.setAppProfile.outboundFlow.docHandlings.processDocModels.push(seqAppObj);
        }
      }
    });
    this.dataFlowService.setCopyForm(this.updateObj);
    this.dataFlowService.setMultpleCopyWorkFlow(this.setAppProfile);
    localStorage.setItem('previousURl', this.router.url);
    this.router.navigate(['pcm/data-flow/build']);
  }
}
