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

import {Component, OnInit, ViewChild, ChangeDetectorRef} from '@angular/core';
import {SwalComponent} from '@sweetalert2/ngx-sweetalert2';
import {FormBuilder, FormGroup} from '@angular/forms';
import {EnvelopeService} from '../envelope.service';
import {MANAGE_X2_ENVELOPE_FORM_FIELDS} from '../envelope.constants';
import {frameFormObj} from '../../utility';
import {TableConfig} from 'src/utility/table-config';

import {environment} from '../../../environments/environment';
import Swal from "sweetalert2";
import { TranslateService } from '@ngx-translate/core';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'pcm-manage-x12-envelope',
  templateUrl: './manage-x12-envelope.component.html',
  styleUrls: ['./manage-x12-envelope.component.scss']
})
export class ManageX12EnvelopeComponent implements OnInit {

  @ViewChild('invalidSwal') private invalidSwal: SwalComponent;
  form: FormGroup;
  formFields: any = [];

  constructor(
    private fb: FormBuilder,
    private envelopeService: EnvelopeService,
    private tblConf: TableConfig,
    private cd: ChangeDetectorRef,
    private translate: TranslateService
  ) {
  }

  private pageIndex = 3;
  showResults = false;
  searchResults = [];

  sortBy = 'tpName';
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
    'Partner Profile',
    'Direction',
    'ISASenderId',
    'ISAReceiverId',
    'GSSenderId',
    'GSReceiverId',
    'STSenderId',
    'STReceiverId',
    'Version',
    'Transaction'
  ];
  displayedColumnsRef: string[] = [
    'partnername',
    'direction',
    'isasenderid',
    'isareceiverid',
    'gssenderid',
    'gsreceiverid',
    'stsenderid',
    'streceiverid',
    'interversion',
    'acceptlookalias'
  ];

  actionButtons = [
    {
      page: 'manage envelop',
      label: 'Edit',
      iconCls: '',
      class: 'btn btn-pcm-edit',
      link: '',
      id: 'envelopEdit'
    },
    {
      page: 'manage envelop',
      label: 'Delete',
      iconCls: '',
      class: 'btn btn-pcm-delete',
      link: environment.ENVELOPE.CREATE_X12,
      id: 'envelopDelete'
    },
    {
      page: 'manage envelop',
      label: 'View Activity',
      iconCls: '',
      class: 'btn btn-pcm-view',
      link: '',
      id: 'viewEnvelop'
    }
  ];

  reqObj: Object;
  qryParams: Object = {
    page: 0,
    sortBy: 'partnername',
    sortDir: 'ASC',
    size: 10
  };

  ngOnInit() {
    this.createFormGroup();
    this.fetchDropdownOptions();
    this.displayedColumns = this.displayedColumnsRef;
    this.serachTblConfig = this.tblConf.formColumn(
      this.displayedColumns,
      this.headerColumns
    );
    this.serachTblConfigRef = this.serachTblConfig;

  }

  createFormGroup() {
    this.formFields = [...MANAGE_X2_ENVELOPE_FORM_FIELDS];
    this.form = this.fb.group({...frameFormObj(this.formFields)});
  }

  resetForm() {
    this.form.reset();
    this.showResults = false;
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
    this.loadSearchResults(this.reqObj, this.qryParams, true);
  }

  searchManageEnvelopes(form: FormGroup) {
    this.searchResults = [];
    this.reqObj = {
      'ediProperties': {
        'direction': form.value.direction,
        'partnerName': form.value.partnerName,
      },
      'gsSegment': {
        'gsReceiverId': form.value.gsReceiverId,
        'gsSenderId': form.value.gsSenderId,
      },
      'isaSegment': {
        'isaReceiverId': form.value.isaReceiverId,
        'isaSenderId': form.value.isaSenderId,
        'interVersion': form.value.version
      },
      'stSegment': {
        'acceptLookAlias': form.value.acceptLookAlias,
        'stReceiverId': form.value.stReceiverId,
        'stSenderId': form.value.stSenderId,
      }
    };
    this.currentPage = 0;
    this.qryParams = {
      page: 0,
      sortBy: 'partnername',
      sortDir: 'ASC',
      size: 10
    };
    this.showResults = false;
    this.loadSearchResults(this.reqObj);
    // sessionStorage.setItem('ENVELOPKEY', JSON.stringify(form.value));
  }

  loadSearchResults(req, qryParams?, pagination?) {
    this.envelopeService.searchManageEnvelopes(req, qryParams).subscribe(res => {
      this.showResults = true;
      console.log(res);
      this.searchResults = res['content'];
      this.size = res['size'];
      this.page = res['number'];
      this.totalElements = res['totalElements'];
      this.totalPages = res['totalPages'];
    }, (err) => {
      Swal.fire(
        this.translate.instant('SWEET_ALERT.ENVELOP.TITLE'),
        err['error']['errorDescription'],
        'error'
      );
    });
  }

  resFromTable(eve) {
    if (eve.statusCode === 200) {
      this.searchManageEnvelopes(this.form);
    }
  }

  fetchDropdownOptions() {
    this.fetchPartnerProfileDropdownList();
  }

  fetchPartnerProfileDropdownList() {
    this.envelopeService.getPartnerMap().subscribe((data: any) => {
      (this.formFields || [])
        .forEach(item => {
          if (item.formControlName === 'partnerName') {
            item.options = data.map(listItem => {
              return {
                value: listItem.value,
                label: listItem.value,
              };
            });
          }
        });
      if (data) {
        const envelope = JSON.parse(sessionStorage.getItem('ENVELOPKEY') || '{}');
        sessionStorage.removeItem('ENVELOPKEY');
        this.form.patchValue(envelope);
      }
    });
  }


}
