import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {ReportsService} from "../reports.service";
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import {ModuleName} from "../../../store/layout/action/layout.action";

@Component({
  selector: 'pcm-file-size-processed',
  templateUrl: './file-size-processed.component.html',
  styleUrls: ['./file-size-processed.component.scss']
})
export class FileSizeProcessedComponent implements OnInit {
  searchReport: FormGroup;
  constructor(
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private service: ReportsService,
    private appComponent: AppComponent,
    private store: Store,) {
    this.store.dispatch(new ModuleName('Reports'));
    this.appComponent.selected = 'reports';
  }

  showReport = false;
  last36MonthsFileSize: any;
  last30DaysFileSize: any;
  last12MonthsFileSize: any;

  ngOnInit() {
    const date = new Date();
    this.searchReport = this.fb.group({
      dateRangeEnd: [new Date(date.setDate(date.getDate())), [Validators.required]],
      dateRangeStart: [new Date(date.setDate(date.getDate() - 30)), [Validators.required]],
      direction: [''],
      transferType: [''],
      docType: [''],
      status: [''],
      partner: [''],
      transaction: ['']
    });
    this.submit();
  }

  submit() {
    this.showReport = false;
    let req = this.searchReport.value;
    Object.keys(req).forEach(key => {
      if (key === 'dateRangeEnd' || key === 'dateRangeStart') {
        req[key] = this.datePipe.transform(req[key], 'yyyy-MM-dd hh:mm:ss');
        // this.reqObj[key] = this.datePipe.transform(new Date(this.reqObj[key]), 'E MMM dd yyyy HH:mm:ss zzzz');
      }
    });

    this.service.getLast36MonthsFileSize(req).subscribe(res => {
      let data = [];
      let series = [];
      this.showReport = true;
      JSON.parse(JSON.stringify(res)).forEach(itm => {
        data.push(itm['name']);
        series.push(this.formatBytes(itm['count']));
      });
      this.last36MonthsFileSize = {
        title: {
          show: series.length === 0,
          textStyle: {
            color: "grey",
            fontSize: 22
          },
          text: 'No data',
          left: 'center',
          top: 'center'
        },
        grid: {
          left: '15%',
          bottom: 100
        },
        xAxis: {
          show: series.length !== 0,
          type: 'category',
          data: data,
          axisTick: {
            alignWithLabel: true
          },
          axisLabel: {
            inside: false,
            interval: 'auto',
            rotate: 30
          }
        },
        yAxis: {
          show: series.length !== 0,
          type: 'value'
        },
        series: [
          {
            data: series,
            type: 'line'
          }
        ],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'line'
          },
          show: true,
          formatter: function(params) {
            return `${params[0].name}<br />
              ${params[0].value} GB<br />`;
          }
        },
        toolbox: {
          show: series.length !== 0,
          feature: {
            // saveAsImage: {
            //   title: ''
            // },
            magicType: {
              type: ['line', 'bar'],
              title: ''
            },
          }
        },
      }
    });

    this.service.getLast30daysFileSize(req).subscribe(val => {
      let data = [];
      let series = [];
      this.showReport = true;
      JSON.parse(JSON.stringify(val)).forEach(itm => {
        data.push(itm['name']);
        series.push(this.formatBytes(itm['count']));
      });
      this.last30DaysFileSize = {
        title: {
          show: series.length === 0,
          textStyle: {
            color: "grey",
            fontSize: 22
          },
          text: 'No data',
          left: 'center',
          top: 'center'
        },
        grid: {
          left: '15%',
          bottom: 100
        },
        xAxis: {
          show: series.length !== 0,
          type: 'category',
          data: data,
          axisTick: {
            alignWithLabel: true
          },
          axisLabel: {
            inside: false,
            interval: 'auto',
            rotate: 30
          }
        },
        yAxis: {
          show: series.length !== 0,
          type: 'value'
        },
        series: [
          {
            data: series,
            type: 'bar'
          }
        ],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'line'
          },
          show: true,
          formatter: function(params) {
            return `${params[0].name}<br />
                ${params[0].value} GB<br />`;
          }
        },
        toolbox: {
          show: series.length !== 0,
          feature: {
            // saveAsImage: {
            //   title: ''
            // },
            magicType: {
              type: ['line', 'bar'],
              title: ''
            },
          }
        },
      }
    });

    this.service.getLast12MonthsFileSize(req).subscribe(val => {
      let data = [];
      let series = [];
      this.showReport = true;
      JSON.parse(JSON.stringify(val)).forEach(itm => {
        data.push(itm['name']);
        series.push(this.formatBytes(itm['count']));
      });
      this.last12MonthsFileSize = {
        title: {
          show: series.length === 0,
          textStyle: {
            color: "grey",
            fontSize: 22
          },
          text: 'No data',
          left: 'center',
          top: 'center'
        },
        grid: {
          left: '15%',
          bottom: 100
        },
        xAxis: {
          show: series.length !== 0,
          type: 'category',
          data: data,
          axisTick: {
            alignWithLabel: true
          },
          axisLabel: {
            inside: false,
            interval: 'auto',
            rotate: 30
          }
        },
        yAxis: {
          show: series.length !== 0,
          type: 'value'
        },
        series: [
          {
            data: series,
            type: 'line'
          }
        ],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'line'
          },
          show: true,
          formatter: function(params) {
            return `${params[0].name}<br />
              ${params[0].value} GB<br />`;
          }
        },
        toolbox: {
          show: series.length !== 0,
          feature: {
            // saveAsImage: {
            //   title: ''
            // },
            magicType: {
              type: ['line', 'bar'],
              title: ''
            },
          }
        },
      }
    });
  }

  formatBytes(bytes, decimals = 2) {
    if (bytes === 0) return '0';

    const k = 1024;
    const dm = decimals < 0 ? 0 : decimals;

    const i = Math.floor(Math.log(bytes) / Math.log(k));

    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm));
  }
}
