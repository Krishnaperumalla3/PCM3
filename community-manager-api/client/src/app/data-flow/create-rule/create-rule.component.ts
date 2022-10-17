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
import {RuleService} from 'src/app/services/rule.service';
import {ActivatedRoute, Router} from '@angular/router';
import {environment} from "../../../environments/environment";
import {markFormFieldTouched, removeSpaces, stringify} from "../../utility";
import Swal from "sweetalert2";
import {SwalComponent} from "@sweetalert2/ngx-sweetalert2";
import {ModuleName} from "../../../store/layout/action/layout.action";
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import { TranslateService } from '@ngx-translate/core';

export const SEARCH_KEY = 'SEARCH_KEY';

@Component({
  selector: 'app-create-rule',
  templateUrl: './create-rule.component.html',
  styleUrls: ['./create-rule.component.scss']
})
export class CreateRuleComponent implements OnInit {

  searchFilterCtrl: any = {};
  @ViewChild('invalidSwal') private invalidSwal: SwalComponent;
  pkId: any;
  private sub: any;

  constructor(
    private fb: FormBuilder,
    private cd: ChangeDetectorRef,
    private ruleService: RuleService,
    private route: ActivatedRoute,
    private router: Router,
    private appComponent: AppComponent,
    private store: Store,
    private translate: TranslateService
  ) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.DATA_FLW'));
    this.appComponent.selected = 'dataFlow';
    this.createRuleFormGroup();

    this.sub = this.route.params.subscribe(params => {
      this.pkId = params['pkId'];
    });
  }

  createRuleForm: FormGroup;
  businessProcessOptions: any = [];
  createRulePropertyFields = [
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_1', formControlName: 'propertyName1', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_2', formControlName: 'propertyName2', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_3', formControlName: 'propertyName3', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_4', formControlName: 'propertyName4', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_5', formControlName: 'propertyName5', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_6', formControlName: 'propertyName6', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_7', formControlName: 'propertyName7', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_8', formControlName: 'propertyName8', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_9', formControlName: 'propertyName9', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_10', formControlName: 'propertyName10', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_11', formControlName: 'propertyName11', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_12', formControlName: 'propertyName12', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_13', formControlName: 'propertyName13', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_14', formControlName: 'propertyName14', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_15', formControlName: 'propertyName15', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_16', formControlName: 'propertyName16', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_17', formControlName: 'propertyName17', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_18', formControlName: 'propertyName18', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_19', formControlName: 'propertyName19', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_20', formControlName: 'propertyName20', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_21', formControlName: 'propertyName21', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_22', formControlName: 'propertyName22', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_23', formControlName: 'propertyName23', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_24', formControlName: 'propertyName24', inputType: 'pcm-text', validation: ['']},
    {placeholder: 'DATA_FLOW.RULE.FIELDS.PROP_25', formControlName: 'propertyName25', inputType: 'pcm-text', validation: ['']}
  ];

  createRuleFormGroup() {
    this.createRuleForm = this.fb.group({
      ruleName: ['', [removeSpaces, Validators.required]],
      businessProcessId: ['', [Validators.required]],
      propertyName1: ['', [removeSpaces]],
      propertyName2: ['', [removeSpaces]],
      propertyName3: ['', [removeSpaces]],
      propertyName4: ['', [removeSpaces]],
      propertyName5: ['', [removeSpaces]],
      propertyName6: ['', [removeSpaces]],
      propertyName7: ['', [removeSpaces]],
      propertyName8: ['', [removeSpaces]],
      propertyName9: ['', [removeSpaces]],
      propertyName10: ['', [removeSpaces]],
      propertyName11: ['', [removeSpaces]],
      propertyName12: ['', [removeSpaces]],
      propertyName13: ['', [removeSpaces]],
      propertyName14: ['', [removeSpaces]],
      propertyName15: ['', [removeSpaces]],
      propertyName16: ['', [removeSpaces]],
      propertyName17: ['', [removeSpaces]],
      propertyName18: ['', [removeSpaces]],
      propertyName19: ['', [removeSpaces]],
      propertyName20: ['', [removeSpaces]],
      propertyName21: ['', [removeSpaces]],
      propertyName22: ['', [removeSpaces]],
      propertyName23: ['', [removeSpaces]],
      propertyName24: ['', [removeSpaces]],
      propertyName25: ['', [removeSpaces]],
    })
  }

  ngOnInit() {
    this.ruleService.getBusinessProcessList().subscribe(res => {
      this.businessProcessOptions = res;
      this.searchFilterCtrl['businessProcessId'] = new FormControl();
      if (!!res && !!this.pkId) {
        this.ruleService.getRule(environment.GET_RULES, this.pkId).subscribe(res => {
          this.createRuleForm.patchValue(res);
        }, (err) => {
          console.log(err);
        })
      }
    })
  }

  create(form: FormGroup) {
    const isInvalid = form.invalid;
    markFormFieldTouched(this.createRuleForm);
    let hasValidSlashForDirectories = true;
    if (isInvalid) {
      setTimeout(this.invalidSwal.show, 100);
      return false;
    }
    const req = form.value;
    const isEdit = !!this.pkId;
    if (!!isEdit) {
      req['ruleId'] = this.pkId;
    }

    this.ruleService.createRule(req, isEdit).subscribe(res => {
      sessionStorage.setItem(SEARCH_KEY, stringify(req));
      this.router.navigate(['/pcm/data-flow/manage']);
      this.createRuleForm.reset();
      Swal.fire({
        title: this.translate.instant('SWEET_ALERT.DT_FLW.RULE.TITLE'),
        text: res['statusMessage'],
        type: 'success',
        showConfirmButton: false,
        timer: 2000
      });
      this.createRuleForm.reset();
    }, (err) => {
      if(err.status !== 401) {
        Swal.fire({
          title: this.translate.instant('SWEET_ALERT.DT_FLW.RULE.TITLE'),
          text: err['error']['errorDescription'],
          type: 'error',
          showConfirmButton: true
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
      title: this.translate.instant('SWEET_ALERT.CANCEL.TITLE'),
      text: this.translate.instant('SWEET_ALERT.CANCEL.BODY'),
      showCancelButton: true,
      confirmButtonText: this.translate.instant('SWEET_ALERT.CANCEL.CNFRM_TXT')
    }).then((result) => {
      if (!!result.value) {
        this.router.navigate(['/pcm/data-flow/manage']);
      }
    })
  }

  getSearchFilterOptions(options = [], field) {
    const value = (this.searchFilterCtrl[field].value || '').toLowerCase();
    if (!value) {
      return options;
    }
    return options.filter(option => option.name.toLowerCase().includes(value));
  }
}
