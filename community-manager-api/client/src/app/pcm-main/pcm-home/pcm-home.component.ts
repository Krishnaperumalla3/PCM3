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

import {FILE_SEARCH_COLS} from '../../ng2table/file-search.columns';
import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import Utility from 'src/utility/Utility';
import {FileSearchService} from 'src/app/services/file-search/file-search.service';
import {TableConfig} from 'src/utility/table-config';
import {DatePipe} from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from 'src/app/services/user.service';
import {correlationMap} from '../../model/correlation.constant';
import {OtherService} from '../../services/other/other.service';
import Swal from 'sweetalert2';
import {AppComponent} from '../../app.component';
import {ModuleName} from '../../../store/layout/action/layout.action';
import {Store} from '@ngxs/store';
import {fileTransferSearchButtons} from "../../services/menu.permissions";
import { TranslateService } from '@ngx-translate/core';
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";

@Component({
  selector: 'app-pcm-home',
  templateUrl: './pcm-home.component.html',
  styleUrls: ['./pcm-home.component.scss']
})
export class PcmHomeComponent implements OnInit {
  isB2bActive: any;
  searchFilterCtrl: any = {};
  myForm: FormGroup;
  correlationForm;
  columnsFS: any;
  range: number;
  exportHeaders: any = [];
  correlationFields: any = [];
  userInfo: any;
  req;

  constructor(
    private fb: FormBuilder,
    private service: FileSearchService,
    public cdRef: ChangeDetectorRef,
    private tblConf: TableConfig,
    private datePipe: DatePipe,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private otherService: OtherService,
    private appComponent: AppComponent,
    private store: Store,
    private translate: TranslateService
  ) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.FL_TRNSFR'));
    this.appComponent.selected = 'fileTransfer';
    this.otherService.getTimeRange().subscribe(res => {
      this.range = parseInt(res);
      this.fileSearchForm();
    });
    this.fileSearchForm();
    this.columnsFS = tblConf.wrapperColumn(FILE_SEARCH_COLS);
  }

  searchPanel = true;
  advSearchPanel = false;

  searchResults = [];
  showResults = false;

  sortBy = 'filearrived';
  sortDir = 'desc';
  page: Number;
  size: Number;
  totalElements: Number;
  totalPages: Number;

  reqObj: Object;
  qryParams: Object = {
    page: 0,
    sortBy: 'filearrived',
    sortDir: 'desc',
    size: 10
  };
  currentPage = 0;
  directionOpts = [
    {label: 'Inbound', value: 'Inbound'},
    {label: 'Outbound', value: 'Outbound'},
    // {label: 'Upload', value: 'Upload'},
    // {label: 'Download', value: 'Download'}
  ];
  transferTypeOpts = [{label: 'MFT', value: 'MFT'}, {label: 'DocHandling', value: 'DocHandling'}];
  docTypeOpts = [
    {label: 'EDIFACT', value: 'EDIFACT'},
    {label: 'FLATFILE', value: 'FLATFILE'},
    {label: 'XML', value: 'XML'},
    {label: 'X12', value: 'X12'}
  ];
  statusOpts = [
    {label: 'Success', value: 'Success'},
    {label: 'Failed', value: 'Failed'},
    {label: 'Reprocessed', value: 'Reprocessed'},
    {label: 'ManualReprocess', value: 'ManualReprocess'},
    {label: 'Repicked', value: 'Repicked'},
    {label: 'Redropped', value: 'Redropped'},
    {label: 'MarkedSuccess', value: 'MarkedSuccess'},
    {label: 'No Action Required', value: 'No Action Required'},
    {label: 'Under Review', value: 'Under Review'}
  ];
  srcProtocolOpts: any;
  partnerOPts: any;
  searchFormTextFields = [
    {
      role: 'super_admin',
      placeholder: 'FILE_TRANSFER.FIELDS.PRTNR',
      formControlName: 'partner'
    },
    {
      role: 'super_admin | business_user | business_admin | file_operator | data_processor | data_processor_restricted',
      placeholder: 'FILE_TRANSFER.FIELDS.APP',
      formControlName: 'application'
    },
    {
      role: 'super_admin | business_user | business_admin | file_operator | data_processor | data_processor_restricted',
      placeholder: 'FILE_TRANSFER.FIELDS.SNDR_ID',
      formControlName: 'senderId'
    },
    {
      role: 'super_admin | business_user | business_admin | file_operator | data_processor | data_processor_restricted',
      placeholder: 'FILE_TRANSFER.FIELDS.RCVR_ID',
      formControlName: 'receiverId'
    },
    {
      role: 'super_admin | business_user | business_admin | file_operator | data_processor | data_processor_restricted',
      placeholder: 'FILE_TRANSFER.FIELDS.TRNS',
      formControlName: 'docTrans'
    },
    {
      role: 'super_admin | business_user | business_admin | file_operator | data_processor | data_processor_restricted',
      placeholder: 'FILE_TRANSFER.FIELDS.ERR_STATUS',
      formControlName: 'errorStatus'
    },
    {
      role: 'super_admin | business_user | business_admin | file_operator | data_processor | data_processor_restricted',
      placeholder: 'FILE_TRANSFER.FIELDS.SRC_FL_NME',
      formControlName: 'srcFileName'
    },
    {
      role: 'super_admin | business_user | business_admin | file_operator | data_processor | data_processor_restricted',
      placeholder: 'FILE_TRANSFER.FIELDS.DEST_FL_NME',
      formControlName: 'destFileName'
    },
    {
      role: 'super_admin | business_user | business_admin | file_operator | data_processor | data_processor_restricted',
      placeholder: 'FILE_TRANSFER.FIELDS.FL_NME_REGX',
      formControlName: 'fileNameRegExpression'
    }
  ];
  dropdownMenu = [
    {
      role: 'super_admin | business_user | business_admin | file_operator | data_processor | data_processor_restricted',
      placeholder: 'FILE_TRANSFER.FIELDS.DIRCTN',
      formControlName: 'flowInOut',
      options: this.directionOpts
    },
    {
      role: 'super_admin | business_user | business_admin | file_operator | data_processor | data_processor_restricted',
      placeholder: 'FILE_TRANSFER.FIELDS.TRNSFR_TYPE',
      formControlName: 'typeOfTransfer',
      options: this.transferTypeOpts
    },
    {
      role: 'super_admin | business_user | business_admin | file_operator | data_processor | data_processor_restricted',
      placeholder: 'FILE_TRANSFER.FIELDS.DOC_TYPE',
      formControlName: 'doctype',
      options: this.docTypeOpts
    },
    {
      role: 'super_admin | business_user | business_admin | file_operator | data_processor | data_processor_restricted',
      placeholder: 'FILE_TRANSFER.FIELDS.STATUS',
      formControlName: 'status',
      options: this.statusOpts
    },
    {
      role: 'super_admin | business_user | business_admin | file_operator | data_processor | data_processor_restricted',
      placeholder: 'FILE_TRANSFER.FIELDS.SRC_PROTOCOL',
      formControlName: 'srcProtocol',
      options: [{label: 'Select', value: ''}]
    },
    {
      role: 'super_admin | business_user | business_admin | file_operator | data_processor | data_processor_restricted',
      placeholder: 'FILE_TRANSFER.FIELDS.DEST_PROTOCOL',
      formControlName: 'destProtocol',
      options: [{label: 'Select', value: ''}]
    },
    {
      role: 'business_user | business_admin | file_operator | data_processor | data_processor_restricted',
      placeholder: 'FILE_TRANSFER.FIELDS.PRTNR',
      formControlName: 'partner',
      options: []
    }
  ];

  // Advance Search
  advanceSearch: any = [];
  errorOptions: Observable<any>;

  headerColumns: string[] = [
    'FILE_TRANSFER.TABLE.COLUMNS.FL_ARVD',
    'FILE_TRANSFER.TABLE.COLUMNS.PRTNR',
    'FILE_TRANSFER.TABLE.COLUMNS.APP',
    'FILE_TRANSFER.TABLE.COLUMNS.SRC_FL_NME',
    'FILE_TRANSFER.TABLE.COLUMNS.DEST_FL_NME',
    'FILE_TRANSFER.TABLE.COLUMNS.SRC_FL_SIZE',
    'FILE_TRANSFER.TABLE.COLUMNS.TRNSFR_FL',
    'FILE_TRANSFER.TABLE.COLUMNS.STATUS',
    'FILE_TRANSFER.TABLE.COLUMNS.DIR',
    'FILE_TRANSFER.TABLE.COLUMNS.TRNSACTN',
    'FILE_TRANSFER.TABLE.COLUMNS.SNDR_ID',
    'FILE_TRANSFER.TABLE.COLUMNS.RCVR_ID'
  ];
  displayedColumnsRef: string[] = [
    'filearrived',
    'partner',
    'application',
    'srcfilename',
    'destfilename',
    'srcFileSize',
    'transfile',
    'status',
    'flowinout',
    'doctrans',
    'senderid',
    'reciverid'
  ];
  actionButtons = [];

  displayedColumns: String[];
  searchTblConfig: any;
  searchTblConfigRef: any;
  isSearched = false;

  trimValue(formControl) {
    formControl.setValue(formControl.value.trimLeft());
  }

  fileSearchForm() {
    const date: Date = new Date();
    this.myForm = this.fb.group({
      dateRangeEnd: [new Date(date), [Validators.required]],
      dateRangeStart: [new Date(date.setHours(date.getHours() - this.range)), [Validators.required]],
      flowInOut: [''],
      typeOfTransfer: [''],
      doctype: [''],
      status: [''],
      srcProtocol: [''],
      destProtocol: [''],
      partner: [''],
      application: [''],
      senderId: [''],
      receiverId: [''],
      docTrans: [''],
      errorStatus: [''],
      srcFileName: [''],
      destFileName: [''],
      fileNameRegExpression: ['']
    });
    this.errorOptions = this.myForm.get('errorStatus').valueChanges.pipe(
      startWith(''),
      map(val => this.filter(val))
    );
  }

  ngOnInit() {
    this.userInfo = sessionStorage.getItem('PCM_USER');
    this.actionButtons = fileTransferSearchButtons();
    if (this.userRole() === 'business_user' || this.userRole() === 'business_admin' || this.userRole() === 'file_operator' || this.userRole() === 'data_processor' || this.userRole() === 'data_processor_restricted') {
      this.service.getPartnerList().subscribe(res => {
        this.partnerOPts = res;
        this.dropdownMenu.forEach(field => {
          if (field.formControlName === 'partner') {
            this.searchFilterCtrl[field.formControlName] = new FormControl();
            field.options = this.partnerOPts.map(val => {
              return {label: val.name, value: val.name};
            });
          }
        });
      });
    }
    this.otherService.getB2bActive().subscribe(res => {
      this.isB2bActive = res;
      sessionStorage.setItem('isB2Bactive', this.isB2bActive);
    });
    this.service.getProtocolList().subscribe(res => {
      this.srcProtocolOpts = res;
      this.dropdownMenu.forEach(field => {
        if (field.formControlName === 'srcProtocol' || field.formControlName === 'destProtocol') {
          return field.options = this.srcProtocolOpts.map(val => {
            return {label: val['value'], value: val['key']};
          });
        }
      });
    });
    this.getCorrelationData();
    this.displayedColumns = this.displayedColumnsRef;
    this.searchTblConfig = this.tblConf.formColumn(
      this.displayedColumns,
      this.headerColumns
    );
    this.searchTblConfigRef = this.searchTblConfig;
    this.activatedRoute.queryParams.subscribe(params => {
      this.isSearched = params.hasOwnProperty('search');
      if (!this.isSearched) {
        this.searchPanel = true;
        this.fileSearchForm();
      }
    });
  }

  filter(val) {
    return [
      'API PickUp Error',
      'CD PickUp Error',
      'Core Process Error',
      'FTP Put Error',
      'FTPS Connectivity Error',
      'FTPS Get Error',
      'ITXA Translate Error',
      'Mailbox PickUp Error',
      'No Partner Setup Found',
      'Rule Processing Error',
      'SFG FTP Get Error',
      'SFG SFTP Get Error',
      'SFTP Connectivity Error',
      'SFTP Put Error'
    ].filter(option =>
      option.toLowerCase().indexOf(val.toLowerCase()) === 0);
  }

  getCorrelationData() {
    this.correlationFields = [];
    this.service.getCorrelationData().subscribe(res => {
      this.correlationForm = new FormGroup({});
      this.advanceSearch = PcmHomeComponent.frameAdvanceSearchPanel(res);
      [...this.advanceSearch].forEach((item,i) => {
        if(i <= 7) {
          this.correlationForm.addControl('correlationKey' + i, new FormControl(''));
          this.correlationForm.addControl('correlationValue' + i, new FormControl(''));
          this.correlationFields.push({key: 'correlationKey' + i, value: 'correlationValue' + i});
        }
      });
    });
  }

  private static frameAdvanceSearchPanel(fieldMap) {
    const advSrchflds = [];

    for (const key in fieldMap) {
      if (Utility.isNotEmpty(fieldMap[key]) && key !== 'pkId') {
        const obj = {
          label: fieldMap[key],
          value: correlationMap[key]
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
    this.exportHeaders = result.map(val => val._element['nativeElement']['innerText']);
    this.searchTblConfig = this.searchTblConfigRef.slice().filter(col => selectedColumns.indexOf(col.key) > -1);
    this.displayedColumns = this.searchTblConfig.map(col => col.key);
    this.cdRef.detectChanges();
    this.showResults = true;
    console.log(this.exportHeaders);
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

  onSubmit(form: FormGroup, correlationForm: FormGroup) {
    this.appComponent.drawer.toggle(false);
    let correlationKey = {};
    correlationKey[correlationForm.value['correlationKey0']] = correlationForm.value['correlationValue0'];
    correlationKey[correlationForm.value['correlationKey1']] = correlationForm.value['correlationValue1'];
    correlationKey[correlationForm.value['correlationKey2']] = correlationForm.value['correlationValue2'];
    correlationKey[correlationForm.value['correlationKey3']] = correlationForm.value['correlationValue3'];
    correlationKey[correlationForm.value['correlationKey4']] = correlationForm.value['correlationValue4'];
    correlationKey[correlationForm.value['correlationKey5']] = correlationForm.value['correlationValue5'];
    correlationKey[correlationForm.value['correlationKey6']] = correlationForm.value['correlationValue6'];
    correlationKey[correlationForm.value['correlationKey7']] = correlationForm.value['correlationValue7'];
    this.searchResults = [];
    let formObj = form.value;
    Object.keys(formObj).forEach(key => {
      if (key === 'dateRangeEnd' || key === 'dateRangeStart') {
        formObj[key] = this.datePipe.transform(new Date(formObj[key]), 'E MMM dd yyyy HH:mm:ss zzzz');
      }
    });
    this.req = {...formObj, ...correlationKey};
    this.reqObj = this.req;
    this.currentPage = 0;
    this.qryParams = {
      page: 0,
      sortBy: 'filearrived',
      sortDir: 'desc',
      size: 10
    };
    this.showResults = false;
    this.loadSearchResults(this.req);
  }

  loadSearchResults(req, qryParams?) {
    const urlTree = this.router.createUrlTree([], {
      queryParams: {search: ''},
      queryParamsHandling: 'merge',
      preserveFragment: true
    });

    this.router.navigateByUrl(urlTree);

    this.service.searchFile(req, qryParams).subscribe(res => {
      this.searchPanel = false;
      this.advSearchPanel = false;
      this.showResults = true;
      this.searchResults = res['content'];
      this.exportHeaders = this.headerColumns;
      this.size = res['size'];
      this.page = res['number'];
      this.currentPage = res['number'];
      this.totalElements = res['totalElements'];
      this.totalPages = res['totalPages'];
    }, (err) => {
      if(err.status !== 401) {
        Swal.fire(
          this.translate.instant('SWEET_ALERT.FL_TRANSFER.SERCH.TITLE'),
          err['error']['errorDescription'],
          'error'
        );
      }
    });
  }

  userRole() {
    return this.userService.role;
  }

  resetForm() {
    this.appComponent.drawer.toggle(true);
    this.currentPage = 0;
    this.showResults = false;
    this.searchPanel = true;
    this.fileSearchForm();
    this.getCorrelationData();
  }

  getSearchFilterOptions(options = [], field) {
    const value = (this.searchFilterCtrl[field].value || '').toLowerCase();
    if (!value) {
      return options;
    }
    return options.filter(option => option.value.toLowerCase().includes(value));
  }

  resFromTable(eve) {
    if (eve.statusCode === 200) {
      this.loadSearchResults(this.myForm.value, this.qryParams);
    }
  }

  navigate() {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.SETTINGS'));
    this.appComponent.selected = 'settings';
    this.router.navigate(['pcm/settings/correlation']);
  }

  statusChange(event) {
    const req = {coreBpId: event.rec.corebpid, newStatus: event.event, oldStatus: event.rec.status, seqId: event.rec.seqid}
    Swal.fire({
      type: 'question',
      customClass: {
        icon: 'swal2-question-mark'
      },
      title: this.translate.instant('SWEET_ALERT.FL_TRANSFER.SERCH.TITLE'),
      text: this.translate.instant('SWEET_ALERT.FL_TRANSFER.FL_STS.TXT'),
      input: 'textarea',
      inputPlaceholder: 'Custom Message',
      showCancelButton: true,
      confirmButtonText: 'Yes'
    }).then((result) => {
      if (!result.dismiss) {
        const req = {coreBpId: event.rec.corebpid, customMessage:result.value, newStatus: event.event, oldStatus: event.rec.status, seqId: event.rec.seqid}
        this.service.fileStatusChange(req).subscribe(res => {
          Swal.fire({
            title: 'File Transfer Change',
            text: res['statusMessage'],
            type: 'success',
            showConfirmButton: false,
            timer: 2000
          });
          this.loadSearchResults(this.req, this.qryParams);
        }, (err) => {
          Swal.fire(
            this.translate.instant('SWEET_ALERT.FL_TRANSFER.SERCH.TITLE'),
            err['error']['errorDescription'],
            'error'
          );
          this.showResults = false;
          setInterval( ()=> {
            this.showResults = true;
          }, 1);
        });
      } else {
        this.showResults = false;
        setInterval( ()=> {
          this.showResults = true;
        }, 1);
      }
    });
  }
}
