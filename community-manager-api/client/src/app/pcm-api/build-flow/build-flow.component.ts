import {Component, OnInit} from '@angular/core';
import {frameFormObj} from '../../utility';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {ENDPOINT_FLOW_OBJ} from '../../model/data-flow.constants';
import Swal from 'sweetalert2';
import {LocalDataSource} from 'ng2-smart-table';
import {DataFlowService} from '../../services/data-flow.service';
import {ListBoxComponent} from '../../shared/list-box/list-box.component';
import {MatDialog} from '@angular/material';
import {FileSearchService} from '../../services/file-search/file-search.service';
import {EndPointService} from "../../services/end-point.service";
import {EndpointFlowService} from "../../services/endpoint-flow/endpoint-flow.service";
import {environment} from "../../../environments/environment";

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'pcm-build-flow',
  templateUrl: './build-flow.component.html',
  styleUrls: ['./build-flow.component.scss']
})
export class BuildFlowComponent implements OnInit {
  endpointList: any;
  data: LocalDataSource;
  buildFlow: FormGroup;
  searchFilterCtrl = {};
  appPrflFound = false;
  noAppPrfl = false;
  openWorkFlow = false;
  disableSetWF = false;
  showFlowDtls = false;
  formFields = ENDPOINT_FLOW_OBJ.build;
  settingsEndpoint = {
    filter: false,
    hideSubHeader: false,
    actions: {
      delete: {
        confirmDelete: true
      },
      add: {
        confirmCreate: true,
        inputClass: 'add_flow',
        addButtonContent: 'Add Flow'
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
      addButtonContent: 'Add Flow'
    },
    edit: {
      confirmSave: true
    },
    attr: {
      class: 'mat-table'
    },
    columns: {
      index: {
        title: 'S_NO',
        width: '65px',
        editable: false,
        filter: false,
        valuePrepareFunction: function (value, row, cell) {
          return value;
        }
      },
      methodName: {
        title: 'Method Name *',
        filter: true
      },
      filter: {
        title: 'Filter',
        filter: true
      },
      description: {
        title: 'Description',
        filter: true
      }
    },
    pager: {
      display: true,
      perPage: 10
    },
    noDataMessage: 'No Data Found'
  };
  slNo = 1;

  appProfile: any = {
    processDocApiModel: []
  };
  setAppProfile = {
    processDocApiModel: []
  };
  availableRules = [];
  mapNameList: any;
  protocolList: any = [];

  constructor(private fb: FormBuilder,
              private service: DataFlowService,
              private endpointService: EndPointService,
              public dialog: MatDialog,
              private fService: FileSearchService,
              private endFlowService: EndpointFlowService) {
  }

  getSearchFilterOptions(options, field) {
    if (options === void 0) {
      options = [];
    }
    const value = (this.searchFilterCtrl[field].value || '').toLowerCase();
    if (!value) {
      return options;
    }
    return options.filter(function (option) {
      return option.label.toLowerCase().includes(value);
    });
  }

  ngOnInit() {
    this.buildFlow = this.fb.group(Object(frameFormObj)(this.formFields));
    this.endpointService.getEndpointsList().subscribe(res => {
      this.endpointList = res;
      const appForm = this.formFields.map(fld => {
        this.searchFilterCtrl[fld.formControlName] = new FormControl();
        if (fld.formControlName === 'endpointProfile') {
          fld.options = this.endpointList.map(val => {
            return {label: val.value, value: val.key};
          });
        }
        return fld;
      });
      // this.getRules();
      this.buildFlow = this.fb.group(frameFormObj(appForm));
      const previousURl = localStorage.getItem('previousURl');
      if (previousURl === '/pcm/data-flow/addflow' && this.service.getMultpleCopyWorkFlow) {
        const data = this.service.getCopyForm['appProfileForm'].value;
        this.buildFlow.controls['endpointProfile'].setValue(
          data['endpointProfile']
        );
      }
    });
  }

  onCreateConfirm(event) {
    this.checkForValidFields(event);
  }

  onDeleteConfirm(event) {
    Swal.fire({
      type: 'question',
      customClass: {
        icon: 'swal2-question-mark'
      },
      title: 'Are you sure?',
      text: 'your want to delete this flow',
      inputPlaceholder: 'Cancel',
      showCancelButton: true,
      confirmButtonText: 'Yes! Delete it'
    })
      .then((result) => {
        if (result.value) {
          const index = event.source.data.indexOf(event.data);
          this.appProfile['processDocApiModel'].splice(index, 1);
          this.data = new LocalDataSource(
            this.appProfile['processDocApiModel']
          );
          event.confirm.resolve();
        } else {
          event.confirm.reject();
        }
      });
  }

  onSaveConfirm(event) {
    this.checkForValidFields(event);
  }

  checkMandatory(e) {
    const newData = e.newData;
    return newData.methodName.trim() !== '';
  }

  checkForValidFields(event) {
    if(this.checkMandatory(event)) {
      event.confirm.resolve(event.newData);
      this.slNo = 1;
    } else {
        this.mandatoryMessage();
        event.confirm.reject();
    }
  }

  mandatoryMessage() {
    Swal.fire(
      '',
      'Please fill in the Mandatory fields marked with (*).',
      'warning'
    );
  }

  searchForApp() {
    const URL = `${environment.ENDPOINT_FLOW}?profileId=${this.buildFlow.value.endpointProfile}`;
    this.endFlowService.getEndpointFlow(URL).subscribe(res => {
      if(res['seqId']) {
        this.noAppPrfl = false;
        this.showFlowDtls = true;
        this.appPrflFound = true;
        this.openWorkFlow = true;
        this.appProfile = JSON.parse(JSON.stringify(res));
        this.data = this.appProfile.processDocApiModel;
        this.updTblSrchTxt();
      } else {
        this.noAppPrfl = true;
      }
    },
      err => {
        this.showFlowDtls = false;
        if (err.error) {
          if (err.error.status === 404) {
            this.noAppPrfl = true;
          }
        }
      });
  }

  setUPWorkFlow() {
    this.updTblSrchTxt();
    this.openWorkFlow = true;
    this.disableSetWF = true;
    this.appProfile = this.jsonCopy(this.setAppProfile);
    this.data = new LocalDataSource(this.appProfile.processDocApiModel);
  }

  setApiModels(index) {
    this.appProfile['processDocApiModel'] =
      this.appProfile['processDocApiModel']
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
    let index = 0;
    const dataAry = this.appProfile['processDocApiModel'].filter(dt => typeof dt.index === 'number');

    if (dataAry.length !== 0) {
      index = Math.max.apply(null, dataAry.map(rl => rl.index));
    }
    this.setApiModels(index);
  }

  frameValidationMsg(flowData = []) {
    let str = '';
    if (flowData.length !== 0) {
      const val = flowData[0];
      str = `${val.fileNamePattern}`;
    }
    return str;
  }

  checkFlows() {
    let validationObject = false;
    if (this.appProfile.processDocApiModel.length > 0) {
      validationObject = true;
    }
    return validationObject;
  }

  checkForRules() {
    let validationObj = {
      valid: true,
      fileName: '',
      DocType: ''
    };
    const inEnd = this.appProfile.processDocApiModel.filter(
      val => val.processRulesList && val.processRulesList.length > 0
    );
    if (
      inEnd.length !== this.appProfile.processDocApiModel.length
    ) {
      validationObj = {
        valid: false,
        fileName: this.frameValidationMsg(
          this.appProfile.processDocApiModel.filter(
            val => !val.processRulesList || val.processRulesList.length === 0
          ),
        ),
        DocType: ''
      };
      return validationObj;
    } else {
      return validationObj;
    }
  }

  saveWorkFlow() {
    const flows = this.checkFlows();
    const ruleObj = this.checkForRules();
    if(flows && ruleObj.valid) {
      this.frameRequest();
      this.appProfile['profileId'] = this.buildFlow.value.endpointProfile;
      this.endFlowService.createFlow(this.appProfile).subscribe(res => {
        this.showFlowDtls = false;
        Swal.fire({
          title: 'Success',
          text: res['statusMessage'],
          type: 'success',
          showConfirmButton: false,
          timer: 2000
        });
        this.searchForApp();
      },err => {
        this.showFlowDtls = true;
        Swal.fire('Error',
          err.error.errorDescription,
          'error'
        );
      });
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
          `Please select at least one Rule under for the record ${ruleObj.fileName !== '' ? ruleObj.fileName : ruleObj.DocType}`,
          'warning'
        );
      }
    }
  }

  rules(event) {
    this.getRules(event);
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

  openRulesModal(rowData) {
    const dialogRef = this.dialog.open(ListBoxComponent, {
      data: {
        rowData,
        mapNameList: this.mapNameList,
        availableRules: this.availableRules,
        protocolList: this.protocolList,
        flow: 'endPoint',
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

  updTblSrchTxt() {
    setTimeout(() =>
      document.querySelectorAll('input-filter').forEach(val => val.getElementsByTagName('input')[0].placeholder = 'Search...'), 10);
  }

  jsonCopy(src) {
    return JSON.parse(JSON.stringify(src));
  }

  reset() {
    this.buildFlow = this.fb.group(Object(frameFormObj)(this.formFields));
    this.appPrflFound = false;
    this.openWorkFlow = false;
    this.noAppPrfl = false;
    this.disableSetWF = false;
  }

  deleteWF() {
    Swal.fire({
      type: 'warning',
      customClass: {
        icon: 'swal2-question-mark'
      },
      title:  "Are you sure?",
      text: "you want to delete this Flow?",
      inputPlaceholder: 'Cancel',
      showCancelButton: true,
      confirmButtonText: "Delete"
    }).then(willDelete => {
      if (willDelete.value) {
        const URL = `${environment.ENDPOINT_FLOW}?seqId=${this.appProfile.seqId}`;
        this.endFlowService
          .deleteWorkLow(URL)
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
}
