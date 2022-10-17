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

import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {markFormFieldTouched} from '../../utility';
import {SwalComponent} from '@sweetalert2/ngx-sweetalert2';
import {ReportsService} from '../reports.service';
import {DatePipe} from '@angular/common';
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import {ModuleName} from "../../../store/layout/action/layout.action";
import {TranslateService} from "@ngx-translate/core";

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'pcm-volume-partners',
  templateUrl: './volume-partners.component.html',
  styleUrls: ['./volume-partners.component.scss']
})
export class VolumePartnersComponent implements OnInit {
  showPartnerReport = false;
  form: FormGroup;
  @ViewChild('invalidSwal') private invalidSwal: SwalComponent;
  public charts: any = {};
  public hasData: boolean;

  constructor(
    private fb: FormBuilder,
    private reportsService: ReportsService,
    private datePipe: DatePipe,
    private appComponent: AppComponent,
    private store: Store,
    public translate: TranslateService
  ) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.REPORTS'));
    this.appComponent.selected = 'reports';
    this.formGroup();
  }

  formGroup() {
    const date = new Date();
    this.form = this.fb.group({
      start: [new Date(date.setDate(date.getDate() - 1)), Validators.required],
      end: [new Date(date.setDate(date.getDate() + 1)), Validators.required]
    });
  }

  ngOnInit() {
  }

  generateReports() {
    this.showPartnerReport = false;
    const isInvalid = this.form.invalid;
    markFormFieldTouched(this.form);
    if (isInvalid) {
      setTimeout(this.invalidSwal.show, 100);
      return false;
    }
    let {start, end} = this.form.value;
    start =  this.datePipe.transform(new Date(start), 'E MMM dd yyyy HH:mm:ss zzzz'); // moment(start).format('YYYY-mm-DD HH:m:ss');
    end = this.datePipe.transform(new Date(end), 'E MMM dd yyyy HH:mm:ss zzzz'); //  moment(end).format('YYYY-mm-DD HH:m:ss');

    this.reportsService.getPartnerVolume({start, end}).subscribe((res: any) => {
      this.hasData = Object.keys(res).length > 0;
      if (!this.hasData) {
        return false;
      }
      const {partnerVolume = {}} = res || {};
      this.generatePartnerVolume(partnerVolume);
    });
  }


  get partnerVolume() {
    return this.charts.partnerVolume;
  }


  generatePartnerVolume(reports: any = {}) {
    const labels = Object.keys(reports);
    const data = [['Global', null, 0], ...Object.keys(reports).map(item => ([
      item + ': (' + reports[item] + ')', 'Global', reports[item]
    ]))];
    this.charts = {
      ...this.charts,
      partnerVolume: {
        options: {
          responsive: true,
        },
        labels,
        type: 'TreeMap',
        columnNames: ['Name', 'Parent', 'Count'],
        legend: true,
        plugins: [],
        data
      }
    };
    this.showPartnerReport = true;
  }

  reset() {
    this.showPartnerReport = false;
    this.hasData = true;
    this.formGroup();
  }
}

