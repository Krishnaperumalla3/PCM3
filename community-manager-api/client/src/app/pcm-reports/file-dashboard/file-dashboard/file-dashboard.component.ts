import { Component, OnInit } from '@angular/core';
import {AppComponent} from "../../../app.component";
import {ModuleName} from "../../../../store/layout/action/layout.action";
import {Store} from "@ngxs/store";
import {ReportsService} from "../../reports.service";

@Component({
  selector: 'pcm-file-dashboard',
  templateUrl: './file-dashboard.component.html',
  styleUrls: ['./file-dashboard.component.scss']
})
export class FileDashboardComponent implements OnInit {
  uploadData: any;
  downloadData: any;
  constructor(
    private appComponent: AppComponent,
    private store: Store,
    private service: ReportsService) {
    this.store.dispatch(new ModuleName('Reports/Dashboard'));
    this.appComponent.selected = 'reports';
  }

  ngOnInit() {
    this.service.getFileUploadReport().subscribe(res => {
      this.uploadData = JSON.parse(JSON.stringify(res));
    });

    this.service.getFileDownloadReport().subscribe(res => {
      this.downloadData = JSON.parse(JSON.stringify(res));
    });
  }

}
