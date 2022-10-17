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

import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormGroup, FormBuilder, FormControl, Validators, FormArray} from "@angular/forms";
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";
import {ModuleName} from "../../../store/layout/action/layout.action";
import {ScheduleService} from "../../services/schedule.service";
import Swal from 'sweetalert2';
import { RxwebValidators } from "@rxweb/reactive-form-validators"

@Component({
  selector: 'pcm-scheduler',
  templateUrl: './scheduler.component.html',
  styleUrls: ['./scheduler.component.scss']
})
export class SchedulerComponent implements OnInit {
  @ViewChild('form') formRef: ElementRef;
  schedulerForm: FormGroup;
  searchFilterCtrl: FormControl = new FormControl();
  pkId: string;
  cloudList: any;
  regionList: any;
  scheduleDetails: any;
  constructor(
    private fb: FormBuilder,
    private appComponent: AppComponent,
    private store: Store,
    public translate: TranslateService,
    private scheduleService: ScheduleService) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.SETTINGS'));
    this.appComponent.selected = 'settings';
    this.scheduleGroup();
  }

  scheduleGroup() {
    this.schedulerForm = this.fb.group({
      fileAge: [''],
      cloudName: [''],
      active: [false],
    })
  }

  ngOnInit() {
    this.loadSchedule();
    this.scheduleService.cloudList().subscribe(res => {
      this.cloudList = res['content'];
    });
    this.scheduleService.regionList().subscribe(res => {
      this.regionList = res['content'];
    });
    this.schedulerForm.get('cloudName').valueChanges.subscribe(val => {
      if(val === 'AWS-S3') {
        this.schedulerForm.addControl('s3SchedulerConfig', this.fb.group({
          region: ['', Validators.required],
          bucketName: ['', Validators.required],
          accessKeyId: ['', Validators.required],
          secretKeyId: ['', Validators.required],
          filesPathDetails: this.fb.array([this.initialPathDetails()])
        }));
        (this.scheduleDetails['s3SchedulerConfig']['filesPathDetails'] || []).forEach((itm, i) => {
          if(i > 0) {
            this.addPathDetails();
          }
        });
        this.schedulerForm.get('s3SchedulerConfig').patchValue(this.scheduleDetails['s3SchedulerConfig']);
      } else {
        this.schedulerForm.removeControl('s3SchedulerConfig');
      }
    });
  }

  get filesPathDetails() {
    return this.schedulerForm.get('s3SchedulerConfig').get('filesPathDetails') as FormArray;
  }

  initialPathDetails() {
    return this.fb.group({
      sourcePath: ['', [Validators.required, RxwebValidators.unique()]],
      destPath: ['', Validators.required]
    });
  }

  addPathDetails() {
    this.filesPathDetails.push(this.initialPathDetails());
  }

  deletePathDetails(i) {
    this.filesPathDetails.removeAt(i);
  }

  getSearchFilterOptions(options = []) {
    const value = (this.searchFilterCtrl.value || '').toLowerCase();
    if (!value) {
      return options;
    }
    return options.filter(option => option.value.toLowerCase().includes(value));
  }

  loadSchedule() {
    this.scheduleService.schedule().subscribe(res => {
      this.scheduleDetails = res;
      if(this.scheduleDetails) {
        this.schedulerForm.patchValue(this.scheduleDetails);
        this.pkId = res['pkId'];
      }
    });
  }

  trimValue(formControl) {
    formControl.setValue(formControl.value.trimLeft());
  }

  numberOnly(event): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }

  onSubmit(form: FormGroup) {
    if(form.invalid) {
      this.validateAllFormFields(form);
      return false;
    }
    this.scheduleService.create(form.value, this.pkId).subscribe(res=> {
      // this.loadSchedule();
      Swal.fire({
        title: this.translate.instant('SWEET_ALERT.SETTINGS.FILE_JOB_SCH'),
        text: res['statusMessage'],
        type: 'success',
        showConfirmButton: false,
        timer: 2000
      });
    },err =>{
      Swal.fire({
        title: this.translate.instant('SWEET_ALERT.SETTINGS.FILE_JOB_SCH'),
        text: err['error']['errorDescription'],
        type: 'error'
      });
    })
  }

  delete(form: FormGroup) {
    this.scheduleService.delete().subscribe(res => {
      this.loadSchedule();
      Swal.fire({
        title: this.translate.instant('SWEET_ALERT.SETTINGS.FILE_JOB_SCH'),
        text: res['statusMessage'],
        type: 'success',
        showConfirmButton: false,
        timer: 2000
      });
    },err =>{
      Swal.fire({
        title: this.translate.instant('SWEET_ALERT.SETTINGS.FILE_JOB_SCH'),
        text: err['error']['errorDescription'],
        type: 'error'
      });
    })
  }

  markFormFieldTouched(fb: FormGroup, el: ElementRef) {
    const form = fb;
    if (!fb || !fb.controls) {
      return false;
    }
    Object.keys(form.controls).forEach(field => {
      const control = form.get(field);
      if (control instanceof FormControl && control.invalid) {
        control.markAsTouched({ onlySelf: true });
          el.nativeElement.querySelector('[formControlName="' + field + '"]').focus();
          return false;
      } else if (control instanceof FormGroup) {
        this.markFormFieldTouched(control, this.formRef);
      }
    });
  }

  validateAllFormFields(formGroup: FormGroup) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        control.markAsTouched({ onlySelf: true });
        return false;
      } else if (control instanceof FormGroup) {
        this.validateAllFormFields(control);
      } else if(control instanceof FormArray) {
        control.controls.forEach(i => {
          if (i instanceof FormGroup) {
            this.validateAllFormFields(i);
          }
        });
      }
    });
  }
}
