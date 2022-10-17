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
import {ModuleName} from "../../../store/layout/action/layout.action";
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import {TranslateService} from "@ngx-translate/core";

// import * as CanvasJS from '../../../assets/js/canvasjs.min';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'pcm-volume-reports',
  templateUrl: './volume-reports.component.html',
  styleUrls: ['./volume-reports.component.scss']
})
export class VolumeReportsComponent implements OnInit {
  form: FormGroup;
  @ViewChild('invalidSwal') private invalidSwal: SwalComponent;
  public charts: any = {};
  showSuccessReport = false;
  showInandOutReport = false;
  showTransactionReport = false;
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
    /* let chart = new CanvasJS.Chart("chartContainer", {
      theme: "light2",
      animationEnabled: true,
      exportEnabled: true,
      data: [{
        type: "pie",
        showInLegend: true,
        toolTipContent: "<b>{name}</b>: ${y} (#percent%)",
        indexLabel: "{y} - #percent%",
        dataPoints: [
          { y: 450, name: "Inbound" },
          { y: 120, name: "Oubound" },
        ]
      }]
    });

    chart.render();

    let chart1 = new CanvasJS.Chart("chartContainer1", {
      theme: "light2",
      animationEnabled: true,
      exportEnabled: true,
      data: [{
        type: "pie",
        showInLegend: true,
        toolTipContent: "<b>{name}</b>: ${y} (#percent%)",
        indexLabel: "{y} - #percent%",
        dataPoints: [
          { y: 6, name: "204" },
          { y: 7, name: "214" },
          { y: 3, name: "810" },
          { y: 3, name: "820" },
          { y: 21, name: "850" },
          { y: 7, name: "855" },
          { y: 3, name: "856" },
          { y: 6, name: "940" },
          { y: 5, name: "946" },
          { y: 8, name: "997" },
        ]
      }]
    });

    chart1.render();

    let chart2 = new CanvasJS.Chart("chartContainer2", {
      animationEnabled: true,
      exportEnabled: true,
      data: [{
        type: "column",
        dataPoints: [
          { y: 71, label: "Failed" },
          { y: 55, label: "Reprocessed" },
          { y: 50, label: "Success" }
        ]
      }]
    });

    chart2.render(); */

  }

  generateReports() {
    this.showInandOutReport = false;
    this.showSuccessReport = false;
    this.showTransactionReport = false;
    const isInvalid = this.form.invalid;
    markFormFieldTouched(this.form);
    if (isInvalid) {
      setTimeout(this.invalidSwal.show, 100);
      return false;
    }
    let {start, end} = this.form.value;
    start =  this.datePipe.transform(new Date(start), 'E MMM dd yyyy HH:mm:ss zzzz'); // moment(start).format('YYYY-mm-DD HH:m:ss');
    end = this.datePipe.transform(new Date(end), 'E MMM dd yyyy HH:mm:ss zzzz'); //  moment(end).format('YYYY-mm-DD HH:m:ss');

    this.reportsService.getTransactionVolume({start, end}).subscribe((res: any) => {
      this.hasData = Object.keys(res).length > 0;
      if (!this.hasData) {
        return false;
      }
      const {statusVolume = {}, flowInOutVolume = {}, docTransVolume = {}} = res || {};
      this.generateTransactionReports(docTransVolume);
      this.generateSuccessAndFailureStatus(statusVolume);
      this.generateInAndOutTransactions(flowInOutVolume);
    });
  }

  get successAndFailure() {
    return this.charts.successAndFailure;
  }

  get inAndOutBound() {
    return this.charts.inAndOutBound;
  }

  get transactionReports() {
    return this.charts.transactionReports;
  }

  generateSuccessAndFailureStatus(data: any = {}) {
    const {Success = 0, Failed = 0} = data;
    this.charts = {
      ...this.charts,
      successAndFailure: {
        options: {
          responsive: true,
        },
        labels: ['Success vs Failure'],
        type: 'bar',
        legend: true,
        plugins: [],
        data: [
          {data: [Success], label: 'Success'},
          {data: [Failed], label: 'Failure'}
        ]
      }
    };
    this.showSuccessReport = true;
  }

  generateInAndOutTransactions(data: any = {}) {
    const {inbound = 0, outbound = 0} = data;
    this.charts = {
      ...this.charts,
      inAndOutBound: {
        options: {
          responsive: true,
        },
        labels: ['InBound', 'OutBound'],
        type: 'pie',
        legend: true,
        plugins: [],
        data: [inbound, outbound],
        chartHover: {
          event: MouseEvent,
          active: {}
        }
      }
    };
    this.showInandOutReport = true;
  }

  generateTransactionReports(reports: any = {}) {
    const labels = Object.keys(reports);
    const data = labels.map(key => reports[key]);
    this.charts = {
      ...this.charts,
      transactionReports: {
        options: {
          responsive: true,
        },
        labels,
        type: 'pie',
        legend: true,
        plugins: [],
        data: data,
        chartHover: {
          event: MouseEvent,
          active: {}
        }
      }
    };
    this.showTransactionReport = true;
  }

  reset() {
    this.formGroup();
    this.showInandOutReport = false;
    this.showSuccessReport = false;
    this.showTransactionReport = false;
    this.hasData = true;
  }

  chartHovered({ event, active }: { event: MouseEvent, active: {}[] }): void {
  }
}
