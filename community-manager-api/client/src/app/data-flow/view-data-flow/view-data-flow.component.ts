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
import {Component, OnInit} from '@angular/core';
import {Observable, Observer} from 'rxjs';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {ActivatedRoute} from "@angular/router";
import {DATA_FLOW_OBJ} from 'src/app/model/data-flow.constants';
import {frameFormObj} from 'src/app/utility';
import {environment} from 'src/environments/environment';
import {DataFlowService} from 'src/app/services/data-flow.service';
import {Select, Store} from '@ngxs/store';
import {LocalDataSource} from 'ng2-smart-table';
import {MatDialog} from '@angular/material';
import {FileHandlingComponent} from 'src/app/shared/file-handling/file-handling.component';
import {CommonState} from 'src/store/layout/state/common.state';
import swal from 'sweetalert';
import {CopyWorkflowComponent} from 'src/app/shared/copy-workflow/copy-workflow.component';
import Swal from 'sweetalert2';
import {FileSearchService} from 'src/app/services/file-search/file-search.service';
import {Router} from '@angular/router';
import {ModuleName} from "../../../store/layout/action/layout.action";
import {AppComponent} from "../../app.component";
import { TranslateService } from '@ngx-translate/core';
import {UploadModalComponent} from '../../shared/upload-modal/upload-modal.component';
import {ListBoxComponent} from '../../shared/list-box/list-box.component';

export interface ExampleTab {
  label: string;
  content: string;
}
@Component({
  selector: 'pcm-view-data-flow',
  templateUrl: './view-data-flow.component.html',
  styleUrls: ['./view-data-flow.component.scss']
})
export class ViewDataFlowComponent implements OnInit {
  searchFilterCtrl: any = {};
  addFlowDisable: boolean;

  constructor(
    private fb: FormBuilder,
    public dialog: MatDialog,
    private service: DataFlowService,
    private fService: FileSearchService,
    private router: Router,
    private appComponent: AppComponent,
    private store: Store,
    private translate: TranslateService,
    private routeParams: ActivatedRoute
  ) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.DATA_FLW'));
    this.appComponent.selected = 'dataFlow';
    this.appProfileForm = this.fb.group(frameFormObj(this.appProfileField));
    this.asyncTabs = new Observable((observer: Observer<ExampleTab[]>) => {
      observer.next([
        {label: 'DATA_FLOW.TABS.INBOUND.LABEL', content: ''},
        {label: 'DATA_FLOW.TABS.OUTBOUND.LABEL', content: ''},
        {label: 'DATA_FLOW.TABS.ACTIVITY.LABEL', content: 'DATA_FLOW.TABS.ACTIVITY.CONTENT'}
      ]);
    });
  }

  tabs = ['inboundFlow', 'outboundFlow'];
  handlingMap = {
    MFT: 'mfts',
    DocHandling: 'docHandlings'
  };
  availableRules = [];
  mapNameList: any;
  asyncTabs: Observable<ExampleTab[]>;
  appProfileForm: FormGroup;
  appProfileField = DATA_FLOW_OBJ.build;
  fileHandling = 'MFT';
  fileHandlings: string[] = ['MFT', 'DocHandling'];
  @Select(CommonState.getRules) rules$: Observable<any>;
  key = 'ruleId';
  display = 'ruleName';
  selected = [];
  noAppPrfl = false;
  appPrflFound = false;
  disableSetWF = false;
  showFlowDtls = false;
  data: LocalDataSource;
  protocolList: any = [];
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
  appProfile = {
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
  settingsMFT = {
    filter: false,
    hideSubHeader: false,
    actions: {
      delete: {
        confirmDelete: true
      },
      add: {
        confirmCreate: true,
        inputClass: 'add_flow',
        addButtonContent: this.translate.instant('DATA_FLOW.MFT.TABLE.ACTIONS.ADD_FLW')
      },
      edit: {
        confirmSave: true
      },
      custom: [{name: 'rules', title: 'Rules'}],
      position: 'right'
    },
    delete: {
      confirmDelete: true
    },
    add: {
      confirmCreate: true,
      addButtonContent: this.translate.instant('DATA_FLOW.MFT.TABLE.ACTIONS.ADD_FLW')
    },
    edit: {
      confirmSave: true
    },
    attr: {
      class: 'mat-table'
    },
    columns: {
      index: {
        title: this.translate.instant('DATA_FLOW.MFT.TABLE.COLUMNS.S_NO'),
        width: '65px',
        editable: false,
        filter: false,
        valuePrepareFunction(value, row, cell) {
          return value;
        }
      },
      fileNamePattern: {
        title: this.translate.instant('DATA_FLOW.MFT.TABLE.COLUMNS.FL_NME'),
        filter: true
      },
      docType: {
        title: this.translate.instant('DATA_FLOW.MFT.TABLE.COLUMNS.FLTR'),
        filter: true
      }
    },
    pager: {
      display: true,
      perPage: 10
    },
    noDataMessage: this.translate.instant('DATA_FLOW.LABELS.NO_DT_FND')
  };
  settingsDocHandling = {
    filter: false,
    hideSubHeader: false,
    actions: {
      delete: {
        confirmDelete: true
      },
      add: {
        confirmCreate: true,
        addButtonContent: this.translate.instant('DATA_FLOW.DocHandling.TABLE.ACTIONS.ADD_FLW')
      },
      edit: {
        confirmSave: true
      },
      custom: [{name: 'rules', title: 'Rules'}],
      position: 'right'
    },
    delete: {
      confirmDelete: true
    },
    add: {
      confirmCreate: true,
      addButtonContent: this.translate.instant('DATA_FLOW.DocHandling.TABLE.ACTIONS.ADD_FLW')
    },
    edit: {
      confirmSave: true
    },
    attr: {
      class: 'mat-table'
    },
    columns: {
      index: {
        title: this.translate.instant('DATA_FLOW.DocHandling.TABLE.COLUMNS.S_NO'),
        width: '65px',
        editable: false,
        filter: false,
        valuePrepareFunction(value, row, cell) {
          return value;
        }
      },
      fileNamePattern: {
        title: this.translate.instant('DATA_FLOW.DocHandling.TABLE.COLUMNS.FL_NME'),
        filter: true
      },
      docType: {
        title: this.translate.instant('DATA_FLOW.DocHandling.TABLE.COLUMNS.DOC_TYPE'),
        filter: true
      },
      versionNo: {
        title: this.translate.instant('DATA_FLOW.DocHandling.TABLE.COLUMNS.VERSION'),
        filter: true
      },
      docTrans: {
        title: this.translate.instant('DATA_FLOW.DocHandling.TABLE.COLUMNS.TRNSCTION'),
        filter: true
      },
      partnerId: {
        title: this.translate.instant('DATA_FLOW.DocHandling.TABLE.COLUMNS.SNDR_ID'),
        filter: true
      },
      receiverId: {
        title: this.translate.instant('DATA_FLOW.DocHandling.TABLE.COLUMNS.RCVR_ID'),
        filter: true
      }
    },

    pager: {
      display: true,
      perPage: 10
    },
    noDataMessage: this.translate.instant('DATA_FLOW.LABELS.NO_DT_FND')
  };
  partnerLst: any;
  appLst: any;
  currentTab = 0;
  slNo = 1;
  openWorkFlow = false;

  config = {
    displayKey: 'label',
    search: true,
    height: '250px',
    placeholder: 'Select',
    noResultsFound: this.translate.instant('DATA_FLOW.LABELS.NO_RSLT_FND'),
    searchPlaceholder: 'Search',
    searchOnKey: 'label'
  };

  updateSelect(e, fld, form) {
    form.controls[fld].setValue(e.value.value);
  }

  getHeader(val) {
    return val.split('.').reduce((o, i) => o[i], this.translate.translations.en);
  }

  ngOnInit() {
    this.service.getPartnerList().subscribe(res => {
      this.partnerLst = res;
      this.service.getAppList().subscribe(resp => {
        this.appLst = resp;
        const appForm = this.appProfileField.map(fld => {
          this.searchFilterCtrl[fld.formControlName] = new FormControl();
          if (fld.formControlName === 'partnerProfile') {
            fld.options = this.partnerLst.map(val => {
              return {label: val.value, value: val.key};
            });
          } else if (fld.formControlName === 'applicationProfile') {
            fld.options = this.appLst.map(val => {
              return {label: val.value, value: val.key};
            });
          }
          return fld;
        });
        // this.getRules();
        this.appProfileForm = this.fb.group(frameFormObj(appForm));
        const previousURl = localStorage.getItem('previousURl');
        if (previousURl === '/pcm/data-flow/addflow' && this.service.getMultpleCopyWorkFlow) {
          const data = this.service.getCopyForm['appProfileForm'].value;
          this.appProfileForm.controls['partnerProfile'].setValue(
            data['partnerProfile']
          );
          this.appProfileForm.controls['applicationProfile'].setValue(
            data['applicationProfile']
          );
          // this.boundForm.controls['applicationProfile'].setValue(boundValues['applicationProfile']);

          this.showFlowDtls = true;
          this.appPrflFound = true;
          this.openWorkFlow = true;
          this.noAppPrfl = false;
          this.appProfile = this.service.getMultpleCopyWorkFlow;
          this.addFlowDisable = true;
          this.fileHandling = 'MFT';
          this.currentTab = 0;
          this.data = this.appProfile[this.tabs[this.currentTab]][
            this.handlingMap[this.fileHandling]
            ]['processDocModels'];
          this.updTblSrchTxt();
          Swal.fire(
            `Multiple Flows ADD`,
            'Please verify the transaction before saving the WorkFlow.',
            'info'
          );
        } else if (previousURl === '/pcm/data-flow/copy-multipleflows' && this.service.getMultpleCopyWorkFlow) {
          const data = this.service.getCopyForm['appProfileForm'].value;
          this.appProfileForm.controls['partnerProfile'].setValue(
            data['partnerProfile']
          );
          this.appProfileForm.controls['applicationProfile'].setValue(
            data['applicationProfile']
          );

          this.showFlowDtls = true;
          this.appPrflFound = true;
          this.openWorkFlow = true;
          this.noAppPrfl = false;
          this.appProfile = this.service.getMultpleCopyWorkFlow;
          this.addFlowDisable = false;
          this.fileHandling = 'MFT';
          this.currentTab = 0;
          this.data = this.appProfile[this.tabs[this.currentTab]][
            this.handlingMap[this.fileHandling]
            ]['processDocModels'];
          this.updTblSrchTxt();
        }

        this.routeParams.params.subscribe(params => {
          if((this.partnerLst && this.appLst) && (params.partner && params.application)) {
            this.partnerLst.filter(val => {
              if(val.value === params.partner){
                this.appProfileForm.controls['partnerProfile'].setValue(
                  val.key
                );
              }
            });
            this.appLst.filter(val => {
              if(val.value === params.application){
                this.appProfileForm.controls['applicationProfile'].setValue(
                  val.key
                );
              }
            });
            this.disableSetWF = false;
            const URL = `${environment.SEARCH_WORK_FLOW}partnerPkId=${
              this.appProfileForm.value.partnerProfile
            }&applicationPkId=${this.appProfileForm.value.applicationProfile}`;
            this.service.getAppProfile(URL).subscribe(
              res => {
                this.showFlowDtls = true;
                this.appPrflFound = true;
                this.openWorkFlow = true;
                this.noAppPrfl = false;
                this.appProfile = res;
                this.fileHandling = params.type;
                const tab = params.flow === 'Outbound' ? 1 : 0;
                this.currentTab = tab;
                this.data = this.appProfile[this.tabs[this.currentTab]][
                  this.handlingMap[this.fileHandling]
                  ]['processDocModels'];
                this.updTblSrchTxt();
              },
              err => {
                this.showFlowDtls = false;
                if (err.error) {
                  if (err.error.errorCode === 404) {
                    // Swal.fire({
                    //   title: 'Workflow',
                    //   text: 'No Matching Workflow found',
                    //   type: 'error',
                    //   showConfirmButton: false,
                    //   timer: 2000
                    // });
                    this.noAppPrfl = true;
                  } else if(err.status !== 401) {
                    Swal.fire(
                      this.translate.instant('SWEET_ALERT.DT_FLW.SERCH_WF.TITLE'),
                      this.translate.instant('SWEET_ALERT.DT_FLW.SERCH_WF.BODY'),
                      'error'
                    );
                  }
                }
              }
            );
          }
        });
      });
    });
  }

  rules(event) {
    this.getRules(event);
  }

  selectedRules(list) {
    this.selected = list;
  }

  searchThru(e, options) {
    let optAry = [];
    const qry = e.target.value.toLowerCase();
    optAry = options;
    return optAry.filter(opts => opts.label.toLowerCase().indexOf(qry));
  }

  openRulesModal(rowData) {
    const dialogRef = this.dialog.open(ListBoxComponent, {
      data: {
        rowData,
        mapNameList: this.mapNameList,
        availableRules: this.availableRules,
        protocolList: this.protocolList,
        flow: this.currentTab === 0 ? 'inbound' : 'outbound',
        viewMode: false
      },
      disableClose: true
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        rowData.processRulesList = result;
      }
    });
  }

  getRules(event) {
    const rowData = event.data;
    if (!rowData.processRulesList) {
      rowData.processRulesList = [];
    }
    if (this.availableRules.length === 0) {
      this.service.getRules().subscribe(res => {
        for (const i in res) {
          if (res[i]) {
            this.availableRules.push(res[i]);
          }
        }
        this.fService.getProtocolList().subscribe(resp => {
          this.protocolList = resp;
          this.openRulesModal(rowData);
        });
      });
    } else {
      this.openRulesModal(rowData);
    }
  }

  onDeleteConfirm(event) {
    Swal.fire({
      type: 'question',
      customClass: {
        icon: 'swal2-question-mark'
      },
      title: this.translate.instant('SWEET_ALERT.DT_FLW.DELETE_FLW.TITLE'),
      text: this.translate.instant('SWEET_ALERT.DT_FLW.DELETE_FLW.BODY'),
      inputPlaceholder: 'Cancel',
      showCancelButton: true,
      confirmButtonText: this.translate.instant('SWEET_ALERT.DT_FLW.DELETE_FLW.CNFRM_TXT')
    })
      .then((result) => {
        if (result.value) {
          const index = event.source.data.indexOf(event.data);
          this.appProfile[this.tabs[this.currentTab]][
            this.handlingMap[this.fileHandling]
            ]['processDocModels'].splice(index, 1);
          this.data = new LocalDataSource(
            this.appProfile[this.tabs[this.currentTab]][
              this.handlingMap[this.fileHandling]
              ]['processDocModels']
          );
          event.confirm.resolve();
        } else {
          event.confirm.reject();
        }
      });
  }

  onSaveConfirm(event) {
    this.checkForvalidFields(event);
  }

  onCreateConfirm(event) {
    this.checkForvalidFields(event);
  }

  checkForvalidFields(event) {
    this.service.getIsMFTDuplicate().subscribe(val => {
      if (this.fileHandling === 'MFT') {
        if (this.checkMftMandatory(event)) {
          if (this.checkMftDuplicate(event) === 0) {
            event.confirm.resolve(event.newData);
            this.slNo = 1;
          } else if (this.checkMftDuplicate(event) > 0 && !val) {
            this.duplicateMessage();
            event.confirm.reject();
          } else {
            event.confirm.resolve(event.newData);
            this.slNo = 1;
          }
        } else {
          this.mandatoryMessage();
          event.confirm.reject();
        }
      } else {
        if (this.checkDocMandatory(event)) {
          if (this.checkDocDuplicate(event) === 0) {
            this.slNo = 1;
            event.confirm.resolve(event.newData);
          } else {
            this.duplicateMessage();
            event.confirm.reject();
          }
        } else {
          this.mandatoryMessage();
          event.confirm.reject();
        }
      }
    });

  }

  duplicateMessage() {
    Swal.fire(
      `Oops..., you are trying to create a duplicate ${this.fileHandling} Flow.`,
      '',
      'warning'
    );
  }

  mandatoryMessage() {
    Swal.fire(
      '',
      'Please fill in the Mandatory fields marked with (*).',
      'warning'
    );
  }

  checkMftDuplicate(e) {
    const newData = e.newData;
    const selectedIndex = e.source.data.indexOf(e.data);
    return this.appProfile[this.tabs[this.currentTab]][
      this.handlingMap[this.fileHandling]
      ]['processDocModels'].filter(
      (val, index) =>
        val.fileNamePattern === newData.fileNamePattern &&
        selectedIndex !== index
    ).length;
  }

  checkDocDuplicate(e) {
    const newData = e.newData;
    const selectedIndex = e.source.data.indexOf(e.data);
    return this.appProfile[this.tabs[this.currentTab]][
      this.handlingMap[this.fileHandling]
      ]['processDocModels'].filter(
      (val, index) =>
        val.docTrans === newData.docTrans &&
        val.docType === newData.docType &&
        val.partnerId === newData.partnerId &&
        val.receiverId === newData.receiverId &&
        selectedIndex !== index
    ).length;
  }

  checkMftMandatory(e) {
    const newData = e.newData;
    return newData.fileNamePattern.trim() !== '';
  }

  checkDocMandatory(e) {
    const newData = e.newData;
    return (
      newData.docTrans.trim() !== '' &&
      newData.docType.trim() !== '' &&
      newData.partnerId.trim() !== '' &&
      newData.receiverId.trim() !== ''
    );
  }

  updTblSrchTxt() {
    setTimeout(() =>
      document.querySelectorAll('input-filter').forEach(val => val.getElementsByTagName('input')[0].placeholder = 'Search...'), 10);
  }

  getFieldName(field) {
    return field.placeholder;
  }

  changeFileType(e) {
    this.updTblSrchTxt();
    this.fileHandling = e.value;
    this.slNo = 1;
    this.data = new LocalDataSource(
      this.appProfile[this.tabs[this.currentTab]][
        this.handlingMap[this.fileHandling]
        ]['processDocModels']
    );
  }

  addNewRule() {
    const dialogRef = this.dialog.open(FileHandlingComponent, {
      width: '350px',
      data: {value: this.fileHandling}
    });
    dialogRef.afterClosed().subscribe(result => {
    });
  }

  changeTab(event) {
    this.updTblSrchTxt();
    this.currentTab = event;
    this.slNo = 1;
    if(this.currentTab !== 2) {
      this.data = new LocalDataSource(
        this.appProfile[this.tabs[this.currentTab]][this.handlingMap[this.fileHandling]]['processDocModels']
      );
    }
  }

  searchForApp() {
    this.disableSetWF = false;
    const URL = `${environment.SEARCH_WORK_FLOW}partnerPkId=${
      this.appProfileForm.value.partnerProfile
    }&applicationPkId=${this.appProfileForm.value.applicationProfile}`;
    this.service.getAppProfile(URL).subscribe(
      res => {
        this.showFlowDtls = true;
        this.appPrflFound = true;
        this.openWorkFlow = true;
        this.noAppPrfl = false;
        this.appProfile = res;
        this.fileHandling = 'MFT';
        this.currentTab = 0;
        this.data = this.appProfile[this.tabs[this.currentTab]][
          this.handlingMap[this.fileHandling]
          ]['processDocModels'];
        this.updTblSrchTxt();
      },
      err => {
        this.showFlowDtls = false;
        if (err.error) {
          if (err.error.errorCode === 404) {
            Swal.fire({
              title: this.translate.instant('SWEET_ALERT.DT_FLW.SERCH_WF.TITLE'),
              text: err.error.errorDescription,
              type: 'error',
              showConfirmButton: true,
            });
            this.noAppPrfl = true;
          } else if(err.status !== 401) {
            Swal.fire(
              this.translate.instant('SWEET_ALERT.DT_FLW.SERCH_WF.TITLE'),
              this.translate.instant('SWEET_ALERT.DT_FLW.SERCH_WF.BODY'),
              'error'
            );
          }
        }
      }
    );
  }

  importWorkFlow() {
    const dialogRef = this.dialog.open(UploadModalComponent, {
      width: '45%',
      data: {
        title: 'Upload Workflow File',
        body: {
          item: {
            workFlow: true,
            applicationProfile: this.appProfileForm.value.applicationProfile,
            partnerProfile: this.appProfileForm.value.partnerProfile,
            URL: `${environment.IMPORT_WORKFLOW}?partnerPkId=${
              this.appProfileForm.value.partnerProfile
            }&applicationPkId=${this.appProfileForm.value.applicationProfile}`,
            uploadField: [
              {
                formControlName: 'file',
                inputType: 'pcm-file',
                validation: ['']
              }
            ]
          }
        }
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result !== '' && !result.error) {
        Swal.fire({
          title: this.translate.instant('SWEET_ALERT.DT_FLW.IMP_WF.TITLE'),
          text: this.translate.instant('SWEET_ALERT.DT_FLW.IMP_WF.BODY'),
          type: 'success',
          showConfirmButton: false,
          timer: 2000
        });
        this.currentTab = 0;
        this.fileHandling = 'MFT';
        this.searchForApp();
      }
    });
  }

  setUPWorkFlow() {
    this.updTblSrchTxt();
    this.openWorkFlow = true;
    this.showFlowDtls = true;
    this.appProfile = this.jsonCopy(this.setAppProfile);
    // this.appProfile = cloneDeep(this.setAppProfile);
    this.data = new LocalDataSource(
      this.appProfile[this.tabs[this.currentTab]][
        this.handlingMap[this.fileHandling]
        ]['processDocModels']
    );
    this.disableSetWF = true;
    // this.data.onAdded().subscribe(row => {
    //   this.appProfile[this.tabs[this.currentTab]][
    //     this.handlingMap[this.fileHandling]
    //   ]['processDocModels'].push(row);
    // });
  }

  reset() {
    this.appProfileForm = this.fb.group(frameFormObj(this.appProfileField));
    this.appPrflFound = false;
    this.openWorkFlow = false;
    this.noAppPrfl = false;
    this.addFlowDisable = false;
    localStorage.removeItem('partnerProfileList');
    localStorage.removeItem('applicationProfileList');
    localStorage.removeItem('applicationProfile');
    localStorage.removeItem('partnerProfile');
    localStorage.removeItem('appProfile');
    localStorage.removeItem('previousURl');
  }

  jsonCopy(src) {
    return JSON.parse(JSON.stringify(src));
  }

  exportWF() {
    swal({
      title: this.translate.instant('SWEET_ALERT.DT_FLW.EXP_WF.TITLE'),
      icon: 'info',
      text: this.translate.instant('SWEET_ALERT.DT_FLW.EXP_WF.BODY'),
      buttons: [this.translate.instant('SWEET_ALERT.DT_FLW.EXP_WF.BTN_CA'), this.translate.instant('SWEET_ALERT.DT_FLW.EXP_WF.BTN_DW')]
    }).then(action => {
      if (action) {
        const URL = `${environment.EXPORT_WORKFLOW}?partnerPkId=${
          this.appProfileForm.value.partnerProfile
        }&applicationPkId=${this.appProfileForm.value.applicationProfile}`;
        this.service.exportWorkFlow(URL);
      }
    });
  }

  copyWF() {
    const dialogRef = this.dialog.open(CopyWorkflowComponent, {
      data: {
        appProfileForm: this.fb.group(frameFormObj(this.appProfileField)),
        appProfileValue: this.appProfileForm.value,
        inboundDoc: this.appProfile['inboundFlow']['docHandlings']['processDocModels'],
        outboundDoc: this.appProfile['outboundFlow']['docHandlings']['processDocModels']
      },
      disableClose: true
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const formValue = result.appProfileForm.value;
        const boundValues = result.boundForm.value;

        this.appProfileForm.controls['partnerProfile'].setValue(formValue['partnerProfile']);
        this.appProfileForm.controls['applicationProfile'].setValue(formValue['applicationProfile']);

        // this.updateSelect({value: {value: formValue['partnerProfile']}}, 'partnerProfile', this.appProfileForm);
        // this.updateSelect({value: {value: formValue['applicationProfile']}}, 'applicationProfile', this.appProfileForm);

        this.appProfile['inboundFlow']['docHandlings']['processDocModels']
          = this.appProfile['inboundFlow']['docHandlings']['processDocModels'].map(flds => {
          flds['receiverId'] = boundValues['inReceiverId'] === '' ? flds['receiverId'] : boundValues['inReceiverId'];
          flds['partnerId'] = boundValues['inSenderId'] === '' ? flds['partnerId'] : boundValues['inSenderId'];
          return flds;
        });

        //   this.appProfile['inboundFlow']['mfts']['processDocModels'].map(flds => {
        //     flds['receiverId'] = boundValues['inReceiverId'];
        //     flds['partnerId'] = boundValues['inSenderId'];
        //     return flds;
        // });

        this.appProfile['outboundFlow']['docHandlings']['processDocModels'].map(flds => {
            flds['receiverId'] = boundValues['outReceiverId'] === '' ? flds['receiverId'] : boundValues['outReceiverId'];
            flds['partnerId'] = boundValues['outSenderId'] === '' ? flds['partnerId'] : boundValues['outSenderId'];
            return flds;
          }
        );

        // this.appProfile['outboundFlow']['mfts']['processDocModels'].map(flds => {
        //   flds['receiverId'] = boundValues['outReceiverId'];
        //   flds['partnerId'] = boundValues['outSenderId'];
        //   return flds;
        // });

        this.data = new LocalDataSource(
          this.appProfile[this.tabs[this.currentTab]][
            this.handlingMap[this.fileHandling]
            ]['processDocModels']
        );

        Swal.fire(
          this.translate.instant('SWEET_ALERT.DT_FLW.COPY_WF.TITLE'),
          this.translate.instant('SWEET_ALERT.DT_FLW.COPY_WF.BODY'),
          'info'
        );
      }
    });
  }

  multipleWF() {
    localStorage.setItem('partnerProfileList', JSON.stringify(this.partnerLst));
    localStorage.setItem('applicationProfileList', JSON.stringify(this.appLst));
    localStorage.setItem('applicationProfile', this.appProfileForm.value['applicationProfile']);
    localStorage.setItem('partnerProfile', this.appProfileForm.value['partnerProfile']);
    this.router.navigate(['/pcm/data-flow/copy-multipleflows']);
  }

  deleteWF() {

    Swal.fire({
      type: 'warning',
      customClass: {
        icon: 'swal2-question-mark'
      },
      title: this.translate.instant('SWEET_ALERT.DT_FLW.DELETE_FLW.TITLE'),
      text: this.translate.instant('SWEET_ALERT.DT_FLW.DELETE_FLW.BODY'),
      inputPlaceholder: 'Cancel',
      showCancelButton: true,
      confirmButtonText: this.translate.instant('SWEET_ALERT.DT_FLW.DELETE_FLW.CNFRM_TXT'),
    }).then(willDelete => {
      if (willDelete.value) {
        const URL = `${environment.DELETE_WORKFLOW}?partnerPkId=${
          this.appProfileForm.value.partnerProfile
        }&applicationPkId=${this.appProfileForm.value.applicationProfile}`;
        this.service
          .deleteWorkLow(URL, this.appProfile)
          .subscribe(res => {
            Swal.fire({
              title: 'Workflow',
              text: res['statusMessage'],
              type: 'success',
              showConfirmButton: false,
              timer: 2000
            });
            this.reset();
          }, (err) => {
            Swal.fire({
              title: 'Workflow',
              text: err['error']['errorDescription'],
              type: 'error',
              showConfirmButton: true,
            });
          });
      }
    });
  }

  cancel() {
    Swal.fire({
      type: 'question',
      customClass: {
        icon: 'swal2-question-mark'
      },
      title: this.translate.instant('SWEET_ALERT.DT_FLW.CANCEL_WF.TITLE'),
      text: this.translate.instant('SWEET_ALERT.DT_FLW.CANCEL_WF.BODY'),
      showCancelButton: true,
      confirmButtonText: this.translate.instant('SWEET_ALERT.DT_FLW.CANCEL_WF.CNFRM')
    }).then((result) => {
      if (!!result.value) {
        this.appPrflFound = false;
        this.openWorkFlow = false;
        this.noAppPrfl = false;
        this.addFlowDisable = false;
        this.fileHandling = 'MFT';
        this.currentTab = 0;
        localStorage.removeItem('partnerProfileList');
        localStorage.removeItem('applicationProfileList');
        localStorage.removeItem('applicationProfile');
        localStorage.removeItem('partnerProfile');
        localStorage.removeItem('appProfile');
        localStorage.removeItem('previousURl');
      }
    });
  }

  framValidationMsg(flowData = [], type) {
    let str = '';
    if (flowData.length !== 0) {
      const val = flowData[0];
      str = type !== 'mft' ? `${val.docType}-${val.docTrans}-${val.partnerId}-${val.receiverId}` : `${val.fileNamePattern}`;
    }
    return str;
  }

  checkFlows() {
    let validationObject = false;
    if (this.appProfile.inboundFlow.mfts.processDocModels.length > 0 ||
      this.appProfile.outboundFlow.mfts.processDocModels.length > 0 ||
      this.appProfile.inboundFlow.docHandlings.processDocModels.length > 0 ||
      this.appProfile.outboundFlow.docHandlings.processDocModels.length > 0) {
      validationObject = true;
    }
    return validationObject;
  }

  checkForRules() {
    let validationObj = {
      valid: true,
      fileName: '',
      DocType: '',
      fileHandling: '',
      flow: ''
    };
    const inMFT = this.appProfile.inboundFlow.mfts.processDocModels.filter(
      val => val.processRulesList && val.processRulesList.length > 0
    );
    if (
      inMFT.length !== this.appProfile.inboundFlow.mfts.processDocModels.length
    ) {
      validationObj = {
        valid: false,
        fileName: this.framValidationMsg(
          this.appProfile.inboundFlow.mfts.processDocModels.filter(
            val => !val.processRulesList || val.processRulesList.length === 0
          ), 'mft'
        ),
        DocType: '',
        fileHandling: 'MFT',
        flow: 'Inbound'
      };
      return validationObj;
    }
    const inDoc = this.appProfile.inboundFlow.docHandlings.processDocModels.filter(
      val => val.processRulesList && val.processRulesList.length > 0
    );
    if (
      inDoc.length !==
      this.appProfile.inboundFlow.docHandlings.processDocModels.length
    ) {
      validationObj = {
        valid: false,
        fileName: this.framValidationMsg(
          this.appProfile.inboundFlow.docHandlings.processDocModels.filter(
            val => !val.processRulesList || val.processRulesList.length === 0
          ), 'doc'
        ),
        DocType: '',
        fileHandling: 'DocHandling',
        flow: 'Inbound'
      };
      return validationObj;
    }
    const outMFT = this.appProfile.outboundFlow.mfts.processDocModels.filter(
      val => val.processRulesList && val.processRulesList.length > 0
    );
    if (
      outMFT.length !==
      this.appProfile.outboundFlow.mfts.processDocModels.length
    ) {
      validationObj = {
        valid: false,
        fileName: this.framValidationMsg(
          this.appProfile.outboundFlow.mfts.processDocModels.filter(
            val => !val.processRulesList || val.processRulesList.length === 0
          ), 'mft'
        ),
        DocType: '',
        fileHandling: 'MFT',
        flow: 'Outbound'
      };
      return validationObj;
    }
    const outDoc = this.appProfile.outboundFlow.docHandlings.processDocModels.filter(
      val => val.processRulesList && val.processRulesList.length > 0
    );
    if (
      outDoc.length !==
      this.appProfile.outboundFlow.docHandlings.processDocModels.length
    ) {
      validationObj = {
        valid: false,
        fileName: this.framValidationMsg(
          this.appProfile.outboundFlow.docHandlings.processDocModels.filter(
            val => !val.processRulesList || val.processRulesList.length === 0
          ), 'doc'
        ),
        DocType: '',
        fileHandling: 'DocHandling',
        flow: 'Outbound'
      };
      return validationObj;
    } else {
      return validationObj;
    }
  }

  setDocModels(flow, hndlng, index) {
    this.appProfile[flow][hndlng]['processDocModels'] =
      this.appProfile[flow][hndlng]['processDocModels']
        .map(doc => {
          if (!doc.index || doc.index === '') {
            index = index + 1;
            return Object.assign(doc, {index});
          } else {
            return doc;
          }
        });
  }

  frameRequest() {
    const docHandlings = ['mfts', 'docHandlings'];
    let index = 0;

    docHandlings.forEach(hndlng => {
      const dataAry = this.appProfile.inboundFlow[hndlng]['processDocModels'].filter(dt => typeof dt.index === 'number');

      if (dataAry.length !== 0) {
        index = Math.max.apply(null, dataAry.map(rl => rl.index));
      }
      this.setDocModels('inboundFlow', hndlng, index);
    });
    index = 0;
    docHandlings.forEach(hndlng => {
      const dataAry = this.appProfile.outboundFlow[hndlng]['processDocModels'].filter(dt => typeof dt.index === 'number');

      if (dataAry.length !== 0) {
        index = Math.max.apply(null, dataAry.map(rl => rl.index));
      }
      this.setDocModels('outboundFlow', hndlng, index);
    });

  }

  saveWorkFlow() {
    this.updTblSrchTxt();
    if (document.querySelector('.ng2-smart-action.ng2-smart-action-add-create') !== null) {
      Swal.fire('Warning', `Please complete Create action on Opened Flow before Save WorFlow.`, 'warning');
    } else if (document.querySelector('.ng2-smart-action.ng2-smart-action-edit-save') !== null) {
      Swal.fire('Warning', 'Please complete Update action on Opened Flow before Save WorkFlow', 'warning');
    } else {
      const flows = this.checkFlows();
      const ruleObj = this.checkForRules();
      if (flows && ruleObj.valid) {
        this.frameRequest();
        const URL = `${environment.CREATE_WORKFLOW}?partnerPkId=${
          this.appProfileForm.value.partnerProfile
        }&applicationPkId=${this.appProfileForm.value.applicationProfile}`;
        this.service.createWorkFlow(URL, this.appProfile).subscribe(
          res => {
            this.fileHandling = 'MFT';
            this.currentTab = 0;
            this.showFlowDtls = false;
            Swal.fire({
              title: 'Success',
              text: 'Work flow saved successful',
              type: 'success',
              showConfirmButton: false,
              timer: 2000
            });
            this.addFlowDisable = false;
            localStorage.removeItem('partnerProfileList');
            localStorage.removeItem('applicationProfileList');
            localStorage.removeItem('applicationProfile');
            localStorage.removeItem('partnerProfile');
            localStorage.removeItem('appProfile');
            localStorage.removeItem('previousURl');
            this.searchForApp();
          },
          err => {
            this.showFlowDtls = true;
            // if(!ruleObj.valid){
            //   this.setUPWorkFlow();
            // }
            Swal.fire('Error',
              err.error.errorDescription ? err.error.errorDescription : 'Work flow saved successful',
              'error');
          }
        );
      } else {
        if (!flows) {
          Swal.fire(
            'Warning',
            `Please create at least one Transaction.`,
            'warning'
          );
        } else {
          Swal.fire(
            'Warning',
            // tslint:disable-next-line:max-line-length
            `Please select at least one Rule under ${ruleObj.flow} - ${ruleObj.fileHandling} for the record ${ruleObj.fileName !== '' ? ruleObj.fileName : ruleObj.DocType}`,
            'warning'
          );
        }

      }
    }
  }

  getSearchFilterOptions(options = [], field) {
    const value = (this.searchFilterCtrl[field].value || '').toLowerCase();
    if (!value) {
      return options;
    }
    return options.filter(option => option.label.toLowerCase().includes(value));
  }

  addFlow() {
    localStorage.setItem('partnerProfileList', JSON.stringify(this.partnerLst));
    localStorage.setItem('applicationProfileList', JSON.stringify(this.appLst));
    localStorage.setItem('applicationProfile', this.appProfileForm.value['applicationProfile']);
    localStorage.setItem('partnerProfile', this.appProfileForm.value['partnerProfile']);
    localStorage.setItem('appProfile', JSON.stringify(this.appProfile));
    this.router.navigate(['/pcm/data-flow/addflow']);
  }
}

