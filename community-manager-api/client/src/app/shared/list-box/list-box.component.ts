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

import {frameFormObj, markFormFieldTouched} from 'src/app/utility';
import {Component, EventEmitter, Inject, Input, OnInit, Output, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Select, Store} from '@ngxs/store';
import {CommonState} from '../../../store/layout/state/common.state';
import {Observable} from 'rxjs';
import {FileSearchService} from 'src/app/services/file-search/file-search.service';
import {PartnerService} from 'src/app/services/partner.service';
import swal from 'sweetalert';
import {RULES_CONSTANTS} from 'src/app/model/rules.constants';
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";

@Component({
  selector: 'pcm-list-box',
  templateUrl: './list-box.component.html',
  styleUrls: ['./list-box.component.scss']
})
export class ListBoxComponent implements OnInit {

  @ViewChild('invalidSwal') private invalidSwal: SwalComponent;
  @Input() key = 'ruleId';
  @Input() display = 'ruleName';
  // @Input() list$: Observable<any>;
  @Input() fromName = 'AVAILABLE RULES';
  @Input() toName = 'RULES APPLIED';
  @Select(CommonState.getRules) list$: Observable<any>;
  @Select(CommonState.getProtocols) protocollist$: Observable<any>;
  protocolList: any = [];
  mapNameList: any = [];
  @Output() selectedList: EventEmitter<any> = new EventEmitter();
  hide =  true;
  searchFilterCtrl: any = {};
  enableRemove = false;
  enableAdd = false;
  enableEdit = false;
  available = [];
  viewMode: boolean;
  protocolRef: any;
  protocolKey = '';
  mapKey = '';
  propertyForm: FormGroup;
  displayFields = [];
  edited = false;
  @Input() exstngLst = [];
  selected = [];
  selectedItem = {
    ruleName: '',
    businessProcessId: ''
  };
  rulesPanel = true;
  propertyPanel = false;
  @Input() retain = true;
  initialAry = [];
  initSelectAry = [];
  editRule = {
    ruleName: '',
    businessProcessId: ''
  };

  maxIndex = 0;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    public dialRef: MatDialogRef<ListBoxComponent>,
    private fService: FileSearchService,
    private service: PartnerService,
    private store: Store
  ) {
    this.exstngLst = this.data.rowData.processRulesList;
    this.available = this.data.availableRules;
    this.viewMode = this.data.viewMode;
    this.propertyForm = this.fb.group({});
  }

  selectOptionList = [
    'protocol',
    'mapname',
    'protocolreference',
    'protocolre ference',
    'map name',
    'file_type',
    'file type',
    'process_type',
    'process type',
    'due_day',
    'due day',
    'delivery_type',
    'delivery type',
    'schedule_type',
    'schedule type',
    'first_mb_verify',
    'firstmbverify',
    'client_code_verify',
    'clientcodeverify',
    'null_file_verify',
    'nullfileverify',
    'size_verify',
    'sizeverify',
    'filename_verify',
    'filenameverify',
    'recipient_type',
    'recipienttype',
    'zip_file_count_type',
    'zipfilecounttype',
    'file_status',
    'filestatus',
    'adhoc_file',
    'adhocfile',
    'pgp_ascii_armor',
    'pgpasciiarmor',
    'pgp_compress',
    'pgpcompress',
    'pgp_text_mode',
    'pgptextmode',
    'pgp_sign',
    'pgpsign',
    'file_type(*)',
    'delivery_type(*)',
    'process_type(*)',
    'schedule_type(*)',
    'first_mb_verify(*)',
    'client_code_verify(*)',
    'null_file_verify(*)',
    'size_verify(*)',
    'filename_verify(*)',
    'recipient_type(*)',
    'zip_file_count_type(*)',
    'file_status(*)',
    'adhoc_file(*)',
    'pgp_ascii_armor(*)',
    'pgp_compress(*)',
    'pgp_text_mode(*)',
    'pgp_sign(*)',
    'password'
  ];

  trimValue(formControl) {
    formControl.setValue(formControl.value.trimLeft());
  };

  ngOnInit() {
    this.exstngLst = this.exstngLst ? this.exstngLst : [];
    this.selected = [];
    this.initSelectAry = [];
    this.exstngLst.forEach(val => {
      val['ruleId'] = Math.random();
      this.selected.push(val);
    });
    if (this.selected.length > 0) {
      this.maxIndex = Math.max.apply(null, this.selected.map(rul => rul.index));
    }
    this.initSelectAry = this.selected;
    this.initialAry = this.available;
    this.protocolList = this.data.protocolList;
  }

  fromAction() {
    this.enableAdd = false;
    this.enableRemove = true;
  }

  toAction() {
    this.enableAdd = true;
    this.enableRemove = false;
  }

  selectItem(item, list) {
    this.edited = true;
    const selectedItem = this.data['availableRules'].filter(rl => rl.ruleName === item['ruleName'])[0];
    this.selectedItem = Object.assign({}, selectedItem, item);
    this.enableEdit = false;
    this.editRule = {
      ruleName: '',
      businessProcessId: ''
    };
    if (list === 'from') {
      this.toAction();
    } else {
      this.fromAction();
    }
  }

  editSlected(item) {
    this.edited = true;
    this.enableEdit = true;
    this.enableRemove = true;
    this.enableAdd = false;
    const selectedItem = this.data['availableRules'].filter(rl => rl.ruleName === item['ruleName'])[0];
    this.editRule = Object.assign({}, selectedItem, item);
    this.selectedItem = {
      ruleName: '',
      businessProcessId: ''
    };
    this.framePropertyEditForm();
  }

  togglePanel() {
    this.propertyPanel = !this.propertyPanel;
    this.rulesPanel = !this.rulesPanel;
  }

  searchThru(e, lst) {
    const qry = e.target.value.toLowerCase();
    if (lst === 'from') {
      if (qry !== '') {
        this.available = this.initialAry.filter(rule => rule[this.display].toLowerCase().indexOf(qry) > -1);
      } else {
        this.available = this.initialAry;
      }
    } else {
      if (qry !== '') {
        this.selected = this.initSelectAry.filter(rule => rule[this.display].toLowerCase().indexOf(qry) > -1);
      } else {
        this.selected = this.initSelectAry;
      }
    }

  }

  framePropertyForm() {
    this.displayFields = [];
    this.selectedItem = {...this.selectedItem, ...{index: this.maxIndex + 1}};
    if (this.mapNameList.length === 0) {
      this.checkForSpecialFields(this.selectedItem);
    }
    for (const key in this.selectedItem) {
      if (this.selectedItem.hasOwnProperty(key) && key.indexOf('propertyName') !== -1) {
        if (this.selectedItem[key] !== null && this.selectedItem[key] != '') {
          let obj = {};
          if (this.selectOptionList.indexOf(this.selectedItem[key].toLowerCase()) === -1) {
            obj = {
              placeholder: this.selectedItem[key],
              formControlName: 'propertyValue' + key.replace('propertyName_', ''),
              inputType: 'pcm-text',
              validation: ['']
            };
          } else {
            obj = this.getSpecialFields(key, this.selectedItem);
          }
          this.displayFields.push(obj);
        }
      }
    }
    this.propertyForm = this.fb.group(frameFormObj(this.displayFields));
    if (this.protocolKey !== '') {
      this.loadProtocolRef();
    }
    this.displayFields.map(fld => {
      this.searchFilterCtrl[fld.formControlName] = new FormControl();
    });
  }

  async checkForSpecialFields(field) {
    for (const key in field) {
      if (field.hasOwnProperty(key) && key.indexOf('propertyName') !== -1 && field[key] !== null) {
        if (field[key].toLowerCase() === 'map name' || field[key].toLowerCase() === 'mapname') {
          if (this.mapNameList.length === 0) {
            await this.fService.getMapNameListPromise().then(res => {
              this.mapNameList = res;
              this.displayFields.forEach(val => {
                if (val.formControlName === this.mapKey) {
                  val.options = this.mapNameList.map(opt => {
                    return {
                      label: opt.name,
                      value: opt.name
                    };
                  });
                }
              });
            });
          }
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

  getSpecialFields(key, rule, value?) {
    let obj = {};
    switch (rule[key].toLowerCase()) {
      case 'password':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-password',
          validation: [value ? value : '']
        };
        break;
      case 'protocol':
        this.populateProtclref(value);
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: (rule[key].toLowerCase() === 'protocol' ? this.protocolList : []).map(opt => {
            return {
              label: opt.value,
              value: opt.key
            };
          })
        };
        if (rule[key].toLowerCase() === 'protocol') {
          this.protocolKey = 'propertyValue' + key.replace('propertyName_', '');
        }
        break;
      case 'protocolreference':
      case 'protocol reference':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: (rule[key].toLowerCase() === 'protocol' ? this.protocolList : []).map(opt => {
            return {
              label: opt.value,
              value: opt.key
            };
          })
        };
        break;
      case 'mapname':
      case 'map name':
        this.mapKey = 'propertyValue' + key.replace('propertyName_', '');
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: ((rule[key].toLowerCase() === 'mapname' || rule[key].toLowerCase() === 'map name') ?
            this.mapNameList : []).map(opt => {
            return {
              label: opt.name,
              value: opt.name
            };
          })
        };
        break;
      case 'file_type':
      case 'file type':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: RULES_CONSTANTS.File_Type.map(opt => {
            return {
              label: opt,
              value: opt
            };
          })
        };
        break;
      case 'file_type(*)':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : '', [Validators.required]],
          options: RULES_CONSTANTS.File_Type.map(opt => {
            return {
              label: opt,
              value: opt
            };
          })
        };
        break;
      case 'process_type':
      case 'process type':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: RULES_CONSTANTS.Process_Type.map(opt => {
            return {
              label: opt,
              value: opt
            };
          })
        };
        break;
      case 'process_type(*)':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : '', [Validators.required]],
          options: RULES_CONSTANTS.Process_Type.map(opt => {
            return {
              label: opt,
              value: opt
            };
          })
        };
        break;
      case 'due_day':
      case 'due day':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: RULES_CONSTANTS.Due_Day.map(opt => {
            return {
              label: opt,
              value: opt
            };
          })
        };
        break;
      case 'delivery_type':
      case 'delivery type':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: RULES_CONSTANTS.Delivery_Type.map(opt => {
            return {
              label: opt,
              value: opt
            };
          })
        };
        break;
      case 'delivery_type(*)':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : '', [Validators.required]],
          options: RULES_CONSTANTS.Delivery_Type.map(opt => {
            return {
              label: opt,
              value: opt
            };
          })
        };
        break;
      case 'schedule_type':
      case 'schedule type':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: RULES_CONSTANTS.Schedule_Type.map(opt => {
            return {
              label: opt,
              value: opt
            };
          })
        };
        break;
      case 'schedule_type(*)':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : '', [Validators.required]],
          options: RULES_CONSTANTS.Schedule_Type.map(opt => {
            return {
              label: opt,
              value: opt
            };
          })
        };
        break;
      case 'first_mb_verify':
      case 'firstmbverify':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'first_mb_verify(*)':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : '', [Validators.required]],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'client_code_verify':
      case 'clientcodeverify':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'client_code_verify(*)':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : '', [Validators.required]],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'null_file_verify':
      case 'nullfileverify':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'null_file_verify(*)':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : '', [Validators.required]],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'size_verify':
      case 'sizeverify':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'size_verify(*)':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : '', [Validators.required]],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'filename_verify':
      case 'filenameverify':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'filename_verify(*)':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : '', [Validators.required]],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'recipient_type':
      case 'recipienttype':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: [
            {label: 'Select', value: ''},
            {label: 'Mailbox', value: 'Mailbox'},
            {label: 'FileSystem', value: 'FileSystem'},
            {label: 'Email', value: 'Email'},
            {label: 'SecureEmail', value: 'SecureEmail'},
            {label: 'Push', value: 'Push'},
            {label: 'Scheduled Push', value: 'Scheduled Push'},
            {label: 'BreakOutFile', value: 'BreakOutFile'},
            {label: 'Embedded', value: 'Embedded'},
            {label: 'SXC Pull', value: 'SXC Pull'}
          ]
        };
        break;
      case 'recipient_type(*)':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : '', [Validators.required]],
          options: [
            {label: 'Select', value: ''},
            {label: 'Mailbox', value: 'Mailbox'},
            {label: 'FileSystem', value: 'FileSystem'},
            {label: 'Email', value: 'Email'},
            {label: 'SecureEmail', value: 'SecureEmail'},
            {label: 'Push', value: 'Push'},
            {label: 'Scheduled Push', value: 'Scheduled Push'},
            {label: 'BreakOutFile', value: 'BreakOutFile'},
            {label: 'Embedded', value: 'Embedded'},
            {label: 'SXC Pull', value: 'SXC Pull'}
          ]
        };
        break;
      case 'zip_file_count_type':
      case 'zipfilecounttype':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: [
            {label: 'Select', value: ''},
            {label: 'Fixed', value: 'Fixed'},
            {label: 'Variable', value: 'Variable'}
          ]
        };
        break;
      case 'zip_file_count_type(*)':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : '', [Validators.required]],
          options: [
            {label: 'Select', value: ''},
            {label: 'Fixed', value: 'Fixed'},
            {label: 'Variable', value: 'Variable'}
          ]
        };
        break;
      case 'file_status':
      case 'filestatus':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: [
            {label: 'Select', value: ''},
            {label: 'Active', value: 'Active'},
            {label: 'Inactive', value: 'Inactive'},
            {label: 'Pending', value: 'Pending'}
          ]
        };
        break;
      case 'file_status(*)':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : '', [Validators.required]],
          options: [
            {label: 'Select', value: ''},
            {label: 'Active', value: 'Active'},
            {label: 'Inactive', value: 'Inactive'},
            {label: 'Pending', value: 'Pending'}
          ]
        };
        break;
      case 'adhoc_file':
      case 'adhocfile':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'adhoc_file(*)':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : '', [Validators.required]],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'pgp_ascii_armor':
      case 'pgpasciiarmor':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'pgp_ascii_armor(*)':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : '', [Validators.required]],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'pgp_compress':
      case 'pgpcompress':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'pgp_compress(*)':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : '', [Validators.required]],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'pgp_text_mode':
      case 'pgptextmode':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'pgp_text_mode(*)':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : '', [Validators.required]],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'pgp_sign':
      case 'pgpsign':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      case 'pgp_sign(*)':
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : '', [Validators.required]],
          options: [
            {label: 'True', value: '1'},
            {label: 'False', value: '0'}
          ]
        };
        break;
      default:
        obj = {
          placeholder: rule[key],
          formControlName: 'propertyValue' + key.replace('propertyName_', ''),
          inputType: 'pcm-select',
          validation: [value ? value : ''],
          options: []
        };
        break;
    }
    return obj;

  }

  framePropertyEditForm() {
    this.displayFields = [];
    if (this.mapNameList.length === 0) {
      this.checkForSpecialFields(this.editRule);
    }
    for (const key in this.editRule) {
      if (this.editRule.hasOwnProperty(key) && key.indexOf('propertyName') !== -1) {
        if (this.editRule[key] !== null) {
          const fldValue = this.editRule['propertyValue' + key.replace('propertyName_', '')] ?
            this.editRule['propertyValue' + key.replace('propertyName_', '')] :
            '';
          let obj = {};
          if (this.selectOptionList.indexOf(this.editRule[key].toLowerCase()) === -1) {
            obj = {
              placeholder: this.editRule[key],
              formControlName: 'propertyValue' + key.replace('propertyName_', ''),
              inputType: 'pcm-text',
              validation: [fldValue ? fldValue : '']
            };
          } else {
            obj = this.getSpecialFields(key, this.editRule, fldValue);
          }
          this.displayFields.push(obj);
        }
      }
    }
    this.propertyForm = this.fb.group(frameFormObj(this.displayFields));
    if (this.protocolKey !== '') {
      this.loadProtocolRef();
    }
    this.displayFields.map(fld => {
      this.searchFilterCtrl[fld.formControlName] = new FormControl();
    });
  }

  onCancelClick() {
    this.togglePanel();
  }

  // Protocol Refernce
  loadProtocolRef() {
    this.propertyForm.get(this.protocolKey).valueChanges.subscribe(val => {
      this.populateProtclref(val);
    });
  }

  populateProtclref(val) {
    if (val !== '') {
      this.service.getTypeByProtocol(val, this.data.flow).subscribe(res => {
        this.protocolRef = res;
        (this.displayFields || []).forEach(fld => {
          if (fld.placeholder.toLowerCase() === 'protocolreference') {
            fld.options = this.protocolRef.map(opt => {
              return {
                label: opt.value,
                value: opt.key
              };
            });
          }
        });
      });
    }
  }

  // MapName

  saveProperty() {
    const isInvalid = this.propertyForm.invalid;
    if (isInvalid) {
      setTimeout(this.invalidSwal.show, 100);
      markFormFieldTouched(this.propertyForm);
      return false;
    }

    if (this.retain) {
      if (this.enableEdit) {
        const newRule = Object.assign({}, this.editRule, this.propertyForm.value);
        const index = this.selected.findIndex(rl => rl[this.key] === this.editRule[this.key]);
        this.selected[index] = newRule;
      } else {
        this.selectedItem[this.key] = Math.random();
        const newRule = Object.assign({}, this.selectedItem, this.propertyForm.value);
        this.selected.push(newRule);
      }
    } else {
      this.selected.push(this.selectedItem);
      this.available = this.available.filter(val => val[this.key] !== this.selectedItem[this.key]);
    }
    this.fromAction();
    this.selectedList.emit(this.selected);
    this.initSelectAry = this.selected;
    this.togglePanel();
    this.enableRemove = false;
    this.enableEdit = false;
    this.enableAdd = false;
  }

  addTo() {
    this.togglePanel();
    this.framePropertyForm();
  }

  removeFrom() {
    if (this.retain) {
      this.selected = this.selected.filter(val => val[this.key] !== this.editRule[this.key]);
    } else {
      this.available.push(this.editRule);
      this.selected = this.selected.filter(val => val[this.key] !== this.editRule[this.key]);
    }
    this.toAction();
    this.selectedList.emit(this.selected);
    this.initSelectAry = this.selected;
    this.enableRemove = false;
    this.enableEdit = false;
    this.enableAdd = false;

  }

  move(move) {
    const index = this.selected.findIndex(itm => itm[this.key] === this.editRule[this.key]);
    let newIndex = 0;
    if (move === 'up') {
      newIndex = index - 1;
    } else {
      newIndex = index + 1;
    }
    if (newIndex > -1 && newIndex < this.selected.length) {
      const removedElement = this.selected.splice(index, 1)[0];
      this.selected.splice(newIndex, 0, removedElement);
    }
    this.selectedList.emit(this.selected);
    this.initSelectAry = this.selected;
  }

  closeModal() {
    if (this.edited && !this.viewMode) {
      swal({
        title: 'Warning',
        icon: 'warning',
        text: 'Are you sure, you want to cancel the changes',
        buttons: ['NO', 'YES']
      }).then(action => {
        if (action) {
          this.dialRef.close();
        }
      });
    } else {
      this.dialRef.close();
    }
  }
}

