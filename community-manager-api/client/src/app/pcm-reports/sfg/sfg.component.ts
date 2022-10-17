import {AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatTableDataSource} from "@angular/material/table";
import {HttpClient} from "@angular/common/http";
import {ReportsService} from "../reports.service";
import {DatePipe} from "@angular/common";
import {ChartType} from "chart.js";
import { MatPaginator } from '@angular/material/paginator';

import * as am4core from "@amcharts/amcharts4/core";
import * as am4charts from "@amcharts/amcharts4/charts";
import am4themes_animated from "@amcharts/amcharts4/themes/animated";
import * as $ from 'jquery';
import {forkJoin} from "rxjs";
import {ModuleName} from "../../../store/layout/action/layout.action";
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import {TranslateService} from "@ngx-translate/core";


@Component({
  selector: 'pcm-sfg',
  templateUrl: './sfg.component.html',
  styleUrls: ['./sfg.component.scss']
})
export class SfgComponent implements OnInit {

  isVisible: boolean;
  form: FormGroup;
  ChargeBacks: any;
  producerData: any;
  consumerData:any;
  UIDData:any;
  totalFileCount:any;
  totalFileSize:any;
  totalChargeback:any;
  totalCountsData:any;
  BarData:any;
  LineData:any;
  reqObj: Object;
  displayedColumnsProducer: string[] = ['Sno', 'name'];
  displayedColumnsConsumers: string[] = ['Sno', 'name'];
  displayedColumnsFileSize: string[] = ['Sno', 'name'];
  displayedColumnsChargeBack: string[] = ['Sno', 'name'];
  displayedColumnsMonthlyData: string[] = ['Sno', 'date', 'fileCount', 'fileSize', 'chargeback'];
  displayedColumnsQuarterlyData: string[] = ['Sno', 'date', 'fileCount', 'fileCountPercentage', 'fileSize', 'fileSizePercentage', 'chargeback', 'chargebackPercentage'];
  public lineChartType = 'line';
  public barChartType: ChartType = 'bar';
  destFileName;
  status;
  application;
  uid;
  restrict = false;
  snode;
  pnode;
  dataSourceProducer: any;
  dataSourceConsumer: any;
  dataSourceFileSize: any;
  dataSourceChargeBack: any;
  dataSourceMonthlyData:any;
  dataSourceQuarterlyData:any;
  GPData : any;
  lineChartData1:any;
  totalIntExtCountsData:any;
  topFileSizeAPIData:any;
  topChargeBackAPIData:any;
  BU:any;
  APP:any;
  monthlyData:any;
  quarterlyData:any;

  @ViewChild('paginator') paginator: MatPaginator;
  @ViewChild('paginator1') paginator1: MatPaginator;
  @ViewChild('paginator2') paginator2: MatPaginator;
  @ViewChild('paginator3') paginator3: MatPaginator;
  @ViewChild('paginator4') paginator4: MatPaginator;
  @ViewChild('paginator5') paginator5: MatPaginator;
  @ViewChild('paginator6') paginator6: MatPaginator;
  @ViewChild('paginatorRef') paginatorRef: MatPaginator;
  constructor(private fb: FormBuilder,
              private http: HttpClient,
              private datePipe: DatePipe,
              private reportService: ReportsService,
              private appComponent: AppComponent,
              private store: Store,
              public translate: TranslateService

  ) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.REPORTS'));
    this.appComponent.selected = 'reports';
    this.formGroup();
  }

  ngOnInit() {
    this.toGenerateReport(this.form);
  }



  dataSource1() {
    this.dataSourceProducer.paginator = this.paginator;
    this.dataSourceConsumer.paginator = this.paginator1;
    this.dataSourceFileSize.paginator = this.paginator2;
    this.dataSourceChargeBack.paginator = this.paginator3;
    this.dataSourceMonthlyData.paginator = this.paginator4;
    this.dataSourceQuarterlyData.paginator = this.paginator5;
    $(".paginatordiv > div > div > div:nth-child(2) > div").css("display","contents");
  }

  ngAfterViewInit() {
  }


  formGroup() {
    const date = new Date();
    this.form = this.fb.group({
      dateRangeStart: [new Date(date.setDate(date.getDate() - 7)), Validators.required],
      dateRangeEnd: [new Date(date.setDate(date.getDate() + 7)), Validators.required],
      application: [],
      uid: [],
      partner: [],
      app: [],
      bu: [],
      srcFileName : ''
    });
  }


  toGenerateReport(form: FormGroup) {
    this.reqObj = form.value;
    Object.keys(this.reqObj).forEach(key => {
      if (key === 'dateRangeEnd' || key === 'dateRangeStart') {
        this.reqObj[key] = this.datePipe.transform(this.reqObj[key], 'yyyy-MM-dd hh:mm:ss');
      }
    });

    let req = {
      ...this.form.value, ...{
        "application": this.form.get('application').value ? this.form.get('application').value : [],
        "destFileName": this.destFileName ? this.destFileName : '',
        "partner": this.form.get('partner').value ? this.form.get('partner').value : [],
        "pnode": this.pnode ? this.pnode : [],
        "snode": this.snode ? this.snode : [],
        "srcFileName": this.form.get('srcFileName').value ? this.form.get('srcFileName').value : '',
        "status": this.status ? this.status : '',
        "uid": this.form.get('uid').value ? this.form.get('uid').value : [],
        "app": this.form.get('app').value ? this.form.get('app').value : [],
        "bu": this.form.get('bu').value ? this.form.get('bu').value : [],
        "reportType": "SFG",
      }
    }
    console.log(req);
    const produverAPI = this.reportService.getProducerData(req);
    const consumerAPI = this.reportService.getConsumerData(req);
    const UIDAPI = this.reportService.getUIDData(req);
    const barChartAPI = this.reportService.getBarData(req);
    const lineChartAPI = this.reportService.getLineChartData(req);
    const totalCountAPI = this.reportService.getTotalCountsData(req);
    const intExtAPI = this.reportService.getExtIntData(req);
    const topChargeBackAPI = this.reportService.getTopChargeback(req);
    const topFileSizeAPI = this.reportService.getTopFileSize(req);
    const APP = this.reportService.getAPP(req);
    const BU = this.reportService.getBU(req);
    const MONTHDATA = this.reportService.getMonthlyData(req);
    const QUARTERLYDATA = this.reportService.getQuarterlyData(req);

    forkJoin([produverAPI, consumerAPI, UIDAPI, barChartAPI, lineChartAPI, totalCountAPI, intExtAPI, topChargeBackAPI, topFileSizeAPI, APP, BU, MONTHDATA, QUARTERLYDATA])
      .subscribe(result => {
        this.producerData = result[0];
        this.consumerData= result[1];
        this.UIDData = result[2];
        this.BarData = result[3];
        this.lineChartData1 = result[4];
        this.totalCountsData = result[5];
        this.totalIntExtCountsData = result[6];
        this.topChargeBackAPIData = result[7];
        this.topFileSizeAPIData = result[8];
        this.APP = result[9];
        this.BU = result[10];
        this.monthlyData = result[11];
        this.quarterlyData = result[12];
        console.log(this.monthlyData);
        console.log(this.quarterlyData);
        this.dataSourceProducer = new MatTableDataSource(this.producerData.sort((a, b) => parseFloat(b.count) - parseFloat(a.count)));
        this.dataSourceConsumer = new MatTableDataSource(this.consumerData.sort((a, b) => parseFloat(b.count) - parseFloat(a.count)));
        this.dataSourceFileSize = new MatTableDataSource(this.topFileSizeAPIData);
        this.dataSourceChargeBack = new MatTableDataSource(this.topChargeBackAPIData);
        this.dataSourceMonthlyData = new MatTableDataSource(this.monthlyData);
        this.dataSourceQuarterlyData = new MatTableDataSource(this.quarterlyData);
        console.log(this.dataSourceMonthlyData);
        console.log(this.dataSourceQuarterlyData);
        if(this.producerData.length == 0 && this.consumerData.length == 0 && this.BarData.length == 0 && this.lineChartData1.length == 0 && this.topChargeBackAPIData.length == 0 && this.topFileSizeAPIData.length == 0 && this.APP.length == 0 && this.BU.length == 0 && this.monthlyData.length == 0 && this.quarterlyData.length == 0){
          this.restrict = true;
          this.form.controls['application'].reset();
          this.form.controls['partner'].reset();
          this.form.controls['app'].reset();
          this.form.controls['bu'].reset();
          this.form.controls['srcFileName'].reset();
          console.log(this.restrict);
          console.log(this.topFileSizeAPIData);
          console.log(this.topChargeBackAPIData);
        }else{
          this.restrict = false;
        }
        this.GPData = this.BarData.map(({ keymap, ...object }) => ({  ...object, ...keymap }));
        this.lineChartData1.sort(function compare(a, b) {
          let dateA = new Date(a.date);
          let dateB = new Date(b.date);
          // @ts-ignore
          return dateA - dateB;
        });
        console.log(this.BarData);
        let allFileCount = this.quarterlyData.map(a => parseInt(a.fileCount));
        this.totalFileCount = allFileCount.reduce(function (a, b) {
          return a + b;
        });

        let allFileSize = this.quarterlyData.map(a => parseInt(a.fileSize));
        this.totalFileSize = allFileSize.reduce(function (a, b) {
          return a + b;
        });


        let allChargeback = this.quarterlyData.map(a => parseInt(a.chargeback));
        this.totalChargeback = allChargeback.reduce(function (a, b) {
          return a + b;
        });

        setTimeout(() => {this.barChartHandler(), this.lineChartHandler(), this.dataSource1()}, 2000);
      });
  }

  barChartHandler() {
    // Bar Chart
    if(this.GPData) {
      am4core.useTheme(am4themes_animated);

      var chart = am4core.create("chartdiv", am4charts.XYChart);

      // Add data
      chart.data = this.GPData;
      console.log(chart.data);

      // Create axes
      var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
      categoryAxis.dataFields.category = "key";
      categoryAxis.title.text = "Consumer";
      categoryAxis.renderer.grid.template.location = 0;
      categoryAxis.renderer.minGridDistance = 10;
      categoryAxis.renderer.cellStartLocation = 0.1;
      categoryAxis.renderer.cellEndLocation = 0.9;
      // Setting up label rotation
      if(this.GPData.length > 5) {
        categoryAxis.renderer.labels.template.rotation = 270;
      }
      var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
      valueAxis.min = 0;
      valueAxis.title.text = "File Count";

      // Create series
      // @ts-ignore
      function createSeries(field, name, stacked) {
        var series = chart.series.push(new am4charts.ColumnSeries());
        series.dataFields.valueY = field;
        series.dataFields.categoryX = "key";
        series.name = name;
        series.columns.template.tooltipText = "{name}: [bold]{valueY}[/]";
        series.stacked = stacked;
        series.columns.template.width = am4core.percent(95);
      }


      var partnerList:[];
      partnerList = this.producerData.map(v => v.name);
      for(let i = 0; i<=partnerList.length-1; i++){
        createSeries(partnerList[i], partnerList[i], true);
      }


      //Add legend
      //chart.legend = new am4charts.Legend();

      //Removing AmCharts Logo
      $("#chartdiv > div > svg > g > g:nth-child(2) > g:nth-child(2) > g > g:nth-child(3)").hide();
    }
  }

  lineChartHandler() {
    // Line Chart
    if (this.lineChartData1){
      am4core.useTheme(am4themes_animated);
      // Themes end

      // Create chart instance
      var lineChart = am4core.create("lineChartdiv", am4charts.XYChart);

      // Add data
      lineChart.data = this.lineChartData1;
      console.log(lineChart.data);
      // Create axes
      var dateAxis = lineChart.xAxes.push(new am4charts.DateAxis());
      dateAxis.renderer.grid.template.location = 0;
      dateAxis.renderer.minGridDistance = 50;

      var valueAxis = lineChart.yAxes.push(new am4charts.ValueAxis());

      // Create fileSize series
      var series = lineChart.series.push(new am4charts.LineSeries());
      series.dataFields.valueY = "fileSize";
      series.dataFields.dateX = "date";
      series.tensionX = 0.8;
      series.strokeWidth = 3;
      series.name = "File Size";

      //Create FileCount Series
      var series2 = lineChart.series.push(new am4charts.LineSeries());
      series2.dataFields.valueY = "fileCount";
      series2.dataFields.dateX = "date";
      series2.tensionX = 0.8;
      series2.strokeWidth = 3;
      series2.name = "File Count";

      //Create Chargebacks Series
      var series3 = lineChart.series.push(new am4charts.LineSeries());
      series3.dataFields.valueY = "chargeback";
      series3.dataFields.dateX = "date";
      series3.tensionX = 0.8;
      series3.strokeWidth = 3;
      series3.name = "Charge Back";

      // Add cursor
      lineChart.cursor = new am4charts.XYCursor();
      lineChart.cursor.fullWidthLineX = true;
      lineChart.cursor.xAxis = dateAxis;
      lineChart.cursor.lineX.strokeWidth = 0;
      lineChart.cursor.lineX.fill = am4core.color("#000");
      lineChart.cursor.lineX.fillOpacity = 0.1;

      // Add scrollbar
      lineChart.scrollbarX = new am4core.Scrollbar();

      // Fix axis scale on load
      lineChart.events.on("ready", function(ev) {
        valueAxis.min = valueAxis.minZoomed;
        valueAxis.max = valueAxis.maxZoomed;
      });
      $("#lineChartdiv > div > svg > g > g:nth-child(2) > g:nth-child(2) > g > g:nth-child(3)").hide();
      // Legend
      lineChart.legend = new am4charts.Legend();

      //adding Bullets for Filecount
      var circleBullet = series.bullets.push(new am4charts.CircleBullet());
      circleBullet.circle.stroke = am4core.color("#fff");
      circleBullet.circle.strokeWidth = 2;
      circleBullet.tooltipText = "Value: [bold]{fileSize}[/]";

      var labelBullet = series.bullets.push(new am4charts.LabelBullet());
      //labelBullet.label.text = "{fileSize}";
      labelBullet.label.dy = -20;

      //adding Bullets for Filecount
      var circleBullet1 = series2.bullets.push(new am4charts.CircleBullet());
      circleBullet1.circle.stroke = am4core.color("#fff");
      circleBullet1.circle.strokeWidth = 2;
      circleBullet1.tooltipText = "Value: [bold]{fileCount}[/]";

      var labelBullet1 = series2.bullets.push(new am4charts.LabelBullet());
      //labelBullet1.label.text = "{fileCount}";
      labelBullet1.label.dy = -20;

      //adding Bullets for charge back
      var circleBullet2 = series3.bullets.push(new am4charts.CircleBullet());
      circleBullet2.circle.stroke = am4core.color("#fff");
      circleBullet2.circle.strokeWidth = 2;
      circleBullet2.tooltipText = "Value: [bold]{chargeback}[/]";

      var labelBullet2 = series3.bullets.push(new am4charts.LabelBullet());
      //labelBullet2.label.text = "{chargeback}";
      labelBullet2.label.dy = -20;

    }
  }

  reset() {
    this.formGroup();
    this.ChargeBacks = '';
    console.log(this.ChargeBacks);
  }
}
