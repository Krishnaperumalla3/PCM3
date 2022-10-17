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
import {partnerProtocols} from '../../model/protocols.constants';
import {frameFormObj} from 'src/app/utility';
import {FileSearchService} from 'src/app/services/file-search/file-search.service';
import {PartnerService} from 'src/app/services/partner.service';
import {TableConfig} from 'src/utility/table-config';
import {PARTNER_KEY} from '../create-partner/create-partner.component';
import {Store} from '@ngxs/store';
import {partnerActionButtons} from "../../services/menu.permissions";
import {
  ClearSearchPartner,
  PartnerListSuccess,
  PartnerTotalElements,
  PartnerTotalPages,
  SearchPartner,
  UpdatePartnerPagination
} from '../store/partner-actions';
import {Observable} from 'rxjs';
import {IPagination, IPartnerSearch} from '../store/partner-models';
import Swal from 'sweetalert2';
import {ModuleName} from "../../../store/layout/action/layout.action";
import {AppComponent} from "../../app.component";
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-manage-partner',
  templateUrl: './manage-partner.component.html',
  styleUrls: ['./manage-partner.component.scss']
})
export class ManagePartnerComponent implements OnInit {
  partnerList = [];
  searchFilterCtrl: any = {};

  private searchPartnerFields$: Observable<IPartnerSearch>;
  private pagination$: Observable<IPagination>;
  private partnerList$: Observable<any>;
  private totalPages$: Observable<any>;
  private totalElements$: Observable<any>;

  constructor(
    private fb: FormBuilder,
    private cd: ChangeDetectorRef,
    private fService: FileSearchService,
    private pService: PartnerService,
    private tblConf: TableConfig,
    private appComponent: AppComponent,
    private store: Store,
    public translate: TranslateService
  ) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.PARTNER'));
    this.appComponent.selected = 'partner';
    this.searchPartnerFields$ = this.store.select(state => state.partner.searchFields);
    this.pagination$ = this.store.select(state => state.partner.pagination);
    this.partnerList$ = this.store.select(state => state.partner.partnersList);
    this.totalPages$ = this.store.select(state => state.partner.totalPages);
    this.totalElements$ = this.store.select(state => state.partner.totalElements);
    this.managePartnerFormGroup();
  }

  managePartnerForm: FormGroup;
  managePartnerFields = partnerProtocols.ManagePartnerFields;
  proLst: any;
  showResults = false;
  searchResults = [];
  sortBy = 'tpName';
  sortDir = 'asc';
  page: number;
  size: Number;
  totalElements: Number;
  totalPages: Number;
  currentPage = 0;
  displayedColumns: String[];
  serachTblConfig: any;
  serachTblConfigRef: any;
  headerColumns: string[] = [
    "PARTNERS.TABLE.COLUMNS.PART_NME",
    "PARTNERS.TABLE.COLUMNS.PART_ID",
    "PARTNERS.TABLE.COLUMNS.PRTOCOL",
    "PARTNERS.TABLE.COLUMNS.EMAIL",
    "PARTNERS.TABLE.COLUMNS.STATUS"
  ];
  displayedColumnsRef: string[] = [
    'tpName',
    'tpId',
    'tpProtocol',
    'email',
    'status'
  ];
  actionButtons = [];
  reqObj: Object;
  qryParams: Object = {
    page: 0,
    sortBy: 'tpName',
    sortDir: 'asc',
    size: 10
  };

  managePartnerFormGroup() {
    this.managePartnerForm = this.fb.group(frameFormObj([...this.managePartnerFields]));
    // setTimeout(() => {
    //   this.searchPartnerFields$.subscribe(fields => {
    //     Object.keys(fields).forEach(key => {
    //       const formField = this.managePartnerForm.get(key);
    //       if (!!formField) {
    //         formField.setValue(fields[key]);
    //       }
    //     })
    //   });
    //
    //   this.pagination$.subscribe(params => {
    //     this.qryParams = {...params}
    //     this.currentPage = params.page;
    //     this.sortBy = params.sortBy;
    //     this.sortDir = params.sortDir;
    //     console.log('::Params::', params);
    //   });
    //
    //   this.partnerList$.subscribe(data => {
    //     this.searchResults = data;
    //     this.showResults = !!data && !!data.length;
    //     console.log('::Data::',  data);
    //   })
    //   this.totalElements$.subscribe(data => {
    //     this.totalElements = data;
    //   })
    //   this.totalPages$.subscribe(data => {
    //     this.totalPages = data;
    //   })
    // }, 1000)
  }

  trimValue(formControl) {
    formControl.setValue(formControl.value.trimLeft());
  }

  ngOnInit() {
    this.managePartnerFields.map(fld => {
      this.searchFilterCtrl[fld.formControlName] = new FormControl();
    });
    this.actionButtons = partnerActionButtons();
    this.fService.getProtocolList().subscribe(res => {
      this.proLst = res;
      this.managePartnerFields = this.managePartnerFields.map(val => {
        if (val.formControlName === 'protocol') {
          val.options = this.proLst.map(opt => {
            return {
              label: opt.value,
              value: opt.key
            };
          });
        }
        return val;
      });
    });

    this.displayedColumns = this.displayedColumnsRef;
    this.serachTblConfig = this.tblConf.formColumn(
      this.displayedColumns,
      this.headerColumns
    );
    this.serachTblConfigRef = this.serachTblConfig;

    const partner = JSON.parse(sessionStorage.getItem(PARTNER_KEY) || '{}');
    sessionStorage.removeItem(PARTNER_KEY);
    this.managePartnerForm.patchValue(partner);
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
    this.loadSearchResults(this.managePartnerForm.value, this.qryParams);
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
    this.loadSearchResults(this.managePartnerForm.value, this.qryParams);
  }

  onSubmit(form: FormGroup) {
    this.searchResults = [];
    this.reqObj = form.value;
    this.currentPage = 0;
    this.showResults = false;
    this.loadSearchResults(this.reqObj);
  }

  loadSearchResults(req, qryParams?) {
    this.store.dispatch(new UpdatePartnerPagination(qryParams));
    this.store.dispatch(new SearchPartner(req));
    this.pService.searchPartner(req, qryParams).subscribe(res => {
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
      this.currentPage = this.page;
      this.totalElements = res['totalElements'];
      this.totalPages = res['totalPages'];

      this.store.dispatch(new PartnerListSuccess([...this.searchResults]));
      this.store.dispatch(new PartnerTotalElements(this.totalElements));
      this.store.dispatch(new PartnerTotalPages(this.totalPages));
    }, (err) => {
      if(err.status !== 401) {
        Swal.fire(
          this.translate.instant('SWEET_ALERT.PARTNER.TITLE'),
          err['error']['errorDescription'],
          'error'
        );
      }
    });
  }

  resFromTable(eve) {
    if (eve.statusCode === 200) {
      this.loadSearchResults(this.managePartnerForm.value, this.qryParams);
    }
  }

  resetForm() {
    this.managePartnerFormGroup();
    this.showResults = false;
    this.currentPage = 0;
    this.store.dispatch(new ClearSearchPartner());
  }

  getSearchFilterOptions(options = [], field) {
    const value = (this.searchFilterCtrl[field].value || '').toLowerCase();
    if (!value) {
      return options;
    }
    return options.filter(option => option.label.toLowerCase().includes(value));
  }
}
