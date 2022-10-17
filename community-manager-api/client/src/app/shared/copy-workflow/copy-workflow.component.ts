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

import {FormControl} from '@angular/forms';
import {FormGroup} from '@angular/forms';
import {Inject} from '@angular/core';
import {Component, OnInit} from '@angular/core';
import {COPY_DATA_FLOW, DATA_FLOW_OBJ} from 'src/app/model/data-flow.constants';
import {FormBuilder} from '@angular/forms';
import {frameFormObj, markFormFieldTouched, removeSpaces} from 'src/app/utility';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import cloneDeep from 'lodash.clonedeep';

// const cloneDeep = require('lodash.clonedeep');

@Component({
  selector: 'pcm-copy-workflow',
  template: `
    <mat-card style="width: 850px">
      <div>
        <h3>{{"DATA_FLOW.COPY_DATA_FLOW.TITLE" | translate}}</h3>
      </div>

      <form
        [formGroup]="copyAppProfile"
        class="copy-wf-form"
        style="margin-top: 20px;"
      >
        <ng-container *ngFor="let field of copyAppFlds">
          <div class="field-container" [ngSwitch]="field.inputType">
            <div *ngSwitchCase="'pcm-select'" style="width: 90%">

              <mat-form-field>
                <mat-select
                  formControlName="{{field.formControlName}}" placeholder="{{ getFieldName(field) | translate}}"
                  #singleSelect>
                  <mat-option>
                    <ngx-mat-select-search placeholderLabel="search"
                                           [formControl]="searchFilterCtrl[field.formControlName]"
                                           [noEntriesFoundLabel]="'no data found'"></ngx-mat-select-search>
                  </mat-option>

                  <mat-option *ngFor="let option of getSearchFilterOptions(field.options, field.formControlName)"
                              [value]="option.value">
                    {{option.label}}
                  </mat-option>
                </mat-select>
              </mat-form-field>

            </div>
            <div *ngSwitchCase="'pcm-text'">
              <mat-form-field>
                <input
                  matInput
                  placeholder="{{ field.placeholder | translate}}"
                  formControlName="{{ field.formControlName }}"
                  [required]="field.required"
                />
              </mat-form-field>
            </div>
          </div>
        </ng-container>
      </form>

      <div>
        <!--<ng-container *ngFor="let field of bounfFlds">-->
        <!--<mat-card style="margin-top: 20px">-->
        <!--<div>-->
        <!--<h3>{{field.label}}</h3>-->
        <!--</div>-->
        <!--<form-->
        <!--[formGroup]="boundForm"-->
        <!--class="copy-wf-form"-->
        <!--style="margin-top: 20px;"-->
        <!--&gt;-->
        <!--<ng-container *ngFor="let fld of field.fields">-->
        <!--<div class="field-container" [ngSwitch]="fld.inputType">-->
        <!--<div *ngSwitchCase="'pcm-text'">-->
        <!--<mat-form-field style="width: 90%">-->
        <!--<input matInput placeholder={{fld.placeholder}} formControlName={{fld.formControlName}}/>-->
        <!--</mat-form-field>-->
        <!--</div>-->
        <!--</div>-->
        <!--</ng-container>-->
        <!--</form>-->
        <!--</mat-card>-->
        <!--</ng-container>-->
        <form
          [formGroup]="boundForm"
          class="copy-wf-form"
          style="margin-top: 20px;"
        >
          <mat-card class="col-md-12">
            <div>
              <h3>{{'DATA_FLOW.COPY_DATA_FLOW.IN_BND_LABEL' | translate}}</h3>
            </div>
            <div class="form-row">
              <div class="form-group" class="col-md-12">
                <mat-form-field class="col-md-6">
                  <input matInput type="text" formControlName="inSenderId"
                         placeholder="{{'DATA_FLOW.COPY_DATA_FLOW.FIELDS.IN_SND_ID.LABEL' | translate}}"
                         [required]="data.inboundDoc.length > 0"/>
                  <mat-error
                    *ngIf="boundForm.controls['inSenderId'].errors && boundForm.controls['inSenderId'].errors.required">{{'DATA_FLOW.COPY_DATA_FLOW.FIELDS.IN_SND_ID.REQ_ERR' | translate}}</mat-error>
                </mat-form-field>
                <mat-form-field class="col-md-6">
                  <input matInput type="text" formControlName="inReceiverId"
                         placeholder="{{'DATA_FLOW.COPY_DATA_FLOW.FIELDS.IN_RCR_ID.LABEL' | translate}}"
                         [required]="data.inboundDoc.length > 0"/>
                  <mat-error
                    *ngIf="boundForm.controls['inReceiverId'].errors && boundForm.controls['inReceiverId'].errors.required">{{'DATA_FLOW.COPY_DATA_FLOW.FIELDS.IN_RCR_ID.REQ_ERR' | translate}}</mat-error>
                </mat-form-field>
              </div>
            </div>
          </mat-card>
          <mat-card class="col-md-12" style="margin-top: 20px">
            <div>
              <h3>{{'DATA_FLOW.COPY_DATA_FLOW.OUT_BND_LABEL' | translate}}</h3>
            </div>
            <div class="form-row">
              <div class="form-group" class="col-md-12">
                <mat-form-field class="col-md-6">
                  <input matInput type="text" formControlName="outSenderId"
                         placeholder="{{'DATA_FLOW.COPY_DATA_FLOW.FIELDS.IN_SND_ID.LABEL' | translate}}"
                         [required]="data.outboundDoc.length > 0"/>
                  <mat-error
                    *ngIf="boundForm.controls['outSenderId'].errors && boundForm.controls['outSenderId'].errors.required">{{'DATA_FLOW.COPY_DATA_FLOW.FIELDS.IN_SND_ID.REQ_ERR' | translate}}</mat-error>
                </mat-form-field>
                <mat-form-field class="col-md-6">
                  <input matInput type="text" formControlName="outReceiverId"
                         placeholder="{{'DATA_FLOW.COPY_DATA_FLOW.FIELDS.IN_RCR_ID.LABEL' | translate}}"
                         [required]="data.outboundDoc.length > 0"/>
                  <mat-error
                    *ngIf="boundForm.controls['outReceiverId'].errors && boundForm.controls['outReceiverId'].errors.required">{{'DATA_FLOW.COPY_DATA_FLOW.FIELDS.IN_RCR_ID.REQ_ERR' | translate}}</mat-error>
                </mat-form-field>
              </div>
            </div>
          </mat-card>
          <div class="btn-grp">
            <button
              type="submit"
              class="btn btn-primary"
              (click)="update(boundForm)"
              [disabled]="copyAppProfile.invalid"
            >
              UPDATE
            </button>
            <button
              type="button"
              class="btn btn-info"
              (click)="dialogRef.close()"
            >
              CANCEL
            </button>
          </div>
        </form>
      </div>
    </mat-card>
  `,
  styles: [`
    .copy-wf-form {
      display: flex;
      flex-wrap: wrap;
    }

    .field-container {
      flex: 0 48%;
    }
  `
  ]
})
export class CopyWorkflowComponent implements OnInit {

  copyAppProfile: FormGroup;
  copyAppFlds = cloneDeep(DATA_FLOW_OBJ.build);

  boundForm: FormGroup;
  bounfFlds = COPY_DATA_FLOW;
  searchFilterCtrl: any = {};

  updateObj = {};

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<CopyWorkflowComponent>,
  ) {
    this.createBoundForm();
  }

  createBoundForm() {
    this.boundForm = this.fb.group({
      inSenderId: ['', removeSpaces],
      inReceiverId: ['', removeSpaces],
      outSenderId: ['', removeSpaces],
      outReceiverId: ['', removeSpaces]
    });
  }

  config = {
    displayKey: 'label',
    search: true,
    height: '250px',
    placeholder: 'Select',
    noResultsFound: 'No results found!',
    searchPlaceholder: 'Search',
    searchOnKey: 'label'
  };
  showParam = false;

  getFieldName(field) {
    return field.placeholder;
  }

  ngOnInit() {
    let boundFlds = [];
    this.copyAppProfile = this.data.appProfileForm;
    this.searchFilterCtrl['partnerProfile'] = new FormControl();
    this.searchFilterCtrl['applicationProfile'] = new FormControl();
    // for (const iterator of COPY_DATA_FLOW) {
    //   boundFlds = [...boundFlds, ...iterator.fields];
    // }
    // this.boundForm = this.fb.group(frameFormObj(boundFlds));
    // console.log('this.boundForm', this.boundForm);
    this.showParam = true;
    this.updateObj = {
      appProfileForm: this.copyAppProfile,
      boundForm: this.boundForm
    };
  }

  getSearchFilterOptions(options = [], field) {
    const value = (this.searchFilterCtrl[field].value || '').toLowerCase();
    if (!value) {
      return options;
    }
    return options.filter(option => option.label.toLowerCase().includes(value));
  }

  update(form: FormGroup) {
    console.log('this.boundForm', this.boundForm);
    // if (this.data.inboundDoc.length > 0 && this.boundForm.controls['inSenderId'].value === '' && this.boundForm.controls['inReceiverId'].value === '') {
    //   Swal.fire(
    //     'In Bound',
    //     `sender id and receiver id is required`,
    //     'error'
    //   );
    //   return false;
    // } else if (this.data.outboundDoc.length > 0 && this.boundForm.controls['outSenderId'].value === '' && this.boundForm.controls['outReceiverId'].value === '') {
    //   Swal.fire(
    //     'out Bound',
    //     `sender id and receiver id is required`,
    //     'error'
    //   );
    //   return false;
    // } else {
    //   this.dialogRef.close(this.updateObj);
    // }
    if (form.invalid) {
      markFormFieldTouched(form);
      return false;
    } else {
      this.dialogRef.close(this.updateObj);
    }
  }

}
