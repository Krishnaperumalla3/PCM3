import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {DatePipe} from "@angular/common";
import {ReportsService} from "../reports.service";
import {MatTableDataSource} from "@angular/material/table";
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import {ModuleName} from "../../../store/layout/action/layout.action";

@Component({
  selector: 'pcm-partners',
  templateUrl: './partners.component.html',
  styleUrls: ['./partners.component.scss']
})
export class PartnersComponent implements OnInit {
  form: FormGroup
  showResults = false;
  displayedColumns: string[] = ['S.No', 'profileName', 'emailId', 'phone'];
  dataSource;
  private paginator: MatPaginator;
  private sort: MatSort;
  @ViewChild(MatPaginator) set matPaginator(mp: MatPaginator) {
    this.paginator = mp;
    if(this.showResults) {
      this.dataSource.paginator = this.paginator;
    }
  };
  @ViewChild(MatSort) set matSort(ms: MatSort) {
    this.sort = ms;
    if(this.showResults) {
      this.dataSource.sort = this.sort;
    }
  };
  constructor(
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private service: ReportsService,
    private appComponent: AppComponent,
    private store: Store,) {
    this.store.dispatch(new ModuleName('Reports'));
    this.appComponent.selected = 'reports';
  }

  ngOnInit() {
    this.form = this.fb.group({
      days: [365]
    });
    this.submit();
  }

  submit() {
    this.showResults = false;
    const req = this.form.value;
    this.service.inactivePartners(req).subscribe(res => {
      this.showResults = true;
      this.dataSource = new MatTableDataSource(JSON.parse(JSON.stringify(res)));
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  reset() {}

}
