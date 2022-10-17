import { Component, OnInit } from '@angular/core';
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import {ModuleName} from "../../../store/layout/action/layout.action";
import {FormBuilder, FormGroup} from "@angular/forms";
import {ReportsService} from "../reports.service";

@Component({
  selector: 'pcm-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  hour: any;
  day: any;
  week: any;
  month: any;
  searchReport: FormGroup;
  showReport = false;

  constructor(
    private fb: FormBuilder,
    private appComponent: AppComponent,
    private store: Store,
    private service: ReportsService) {
    this.store.dispatch(new ModuleName('Reports'));
    this.appComponent.selected = 'reports';
  }

  ngOnInit() {
    this.searchReport = this.fb.group({
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
    const req = this.searchReport.value;
    this.service.getDashboard(req).subscribe(res => {
      this.showReport = true;
      const [hour, day, week, month] = res;
      this.hour = hour;
      this.day = day;
      this.week = week;
      this.month = month;
    })
  }
}
