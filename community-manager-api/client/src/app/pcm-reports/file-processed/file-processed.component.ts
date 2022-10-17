import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ReportsService} from "../reports.service";
import {DatePipe} from "@angular/common";
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import {ModuleName} from "../../../store/layout/action/layout.action";

@Component({
  selector: 'pcm-file-processed',
  templateUrl: './file-processed.component.html',
  styleUrls: ['./file-processed.component.scss']
})
export class FileProcessedComponent implements OnInit {
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
  last36MonthsFiles: any;
  last31DaysFiles: any;
  last30DaysFiles: any;
  last12MonthsFiles: any;
  last4WeeksFiles: any;

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

    this.service.getLast4WeeksFiles(req).subscribe(val => {
      let data = [];
      let series = [];
      this.showReport = true;
      JSON.parse(JSON.stringify(val)).forEach(itm => {
        data.push(itm['name']);
        series.push(itm['count']);
      });
      this.last4WeeksFiles = {
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
            rotate: 20
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
          show: true
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

    this.service.getLast36monthsFiles(req).subscribe(res => {
      let data = [];
      let series = [];
      this.showReport = true;
      JSON.parse(JSON.stringify(res)).forEach(itm => {
        data.push(itm['name']);
        series.push(itm['count']);
      });
      this.last36MonthsFiles = {
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
          show: true
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

    this.service.getLast31daysFiles(req).subscribe(val => {
      let data = [];
      let series = [];
      this.showReport = true;
      JSON.parse(JSON.stringify(val)).forEach(itm => {
        data.push(itm['name']);
        series.push(itm['count']);
      });
      this.last31DaysFiles = {
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
          show: true
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

    this.service.getLast30daysFiles(req).subscribe(val => {
      let data = [];
      let series = [];
      this.showReport = true;
      JSON.parse(JSON.stringify(val)).forEach(itm => {
        data.push(itm['name']);
        series.push(itm['count']);
      });
      this.last30DaysFiles = {
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
          show: true
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

    this.service.getLast12MonthsFiles(req).subscribe(val => {
      let data = [];
      let series = [];
      this.showReport = true;
      JSON.parse(JSON.stringify(val)).forEach(itm => {
        data.push(itm['name']);
        series.push(itm['count']);
      });
      this.last12MonthsFiles = {
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
          show: true
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
}
