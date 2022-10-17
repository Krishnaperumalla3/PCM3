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
  selector: 'pcm-doctype-reports',
  templateUrl: './doctype-reports.component.html',
  styleUrls: ['./doctype-reports.component.scss']
})
export class DoctypeReportsComponent implements OnInit {
  showDocTypeReport = false;
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
    this.showDocTypeReport = false;
    const isInvalid = this.form.invalid;
    markFormFieldTouched(this.form);
    if (isInvalid) {
      setTimeout(this.invalidSwal.show, 100);
      return false;
    }
    let {start, end} = this.form.value;
    start =  this.datePipe.transform(new Date(start), 'E MMM dd yyyy HH:mm:ss zzzz'); // moment(start).format('YYYY-mm-DD HH:m:ss');
    end = this.datePipe.transform(new Date(end), 'E MMM dd yyyy HH:mm:ss zzzz'); //  moment(end).format('YYYY-mm-DD HH:m:ss');

    this.reportsService.getDoctypeVolume({start, end}).subscribe((res: any) => {
      this.hasData = Object.keys(res).length > 0;
      if (!this.hasData) {
        return false;
      }
      const {docTypeVolume = {}} = res || {};
      this.generateDocTypeVolume(docTypeVolume);
    });
  }


  get docTypeVolume() {
    return this.charts.docTypeVolume;
  }


  generateDocTypeVolume(reports: any = {}) {
    const labels = Object.keys(reports);
    const data = labels.map(key => reports[key]);
    this.charts = {
      ...this.charts,
      docTypeVolume: {
        options: {
          responsive: true,
        },
        labels,
        type: 'pie',
        legend: true,
        plugins: [],
        data
      }
    };
    this.showDocTypeReport = true;
  }

  reset() {
    this.showDocTypeReport = false;
    this.hasData = true;
    this.formGroup();
  }
}
