import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Store} from "@ngxs/store";
import {ModuleName} from "../../../store/layout/action/layout.action";
import {AppComponent} from "../../app.component";
import {FormBuilder, FormGroup} from "@angular/forms";
import {EndPointService} from "../../services/end-point.service";
import {TableConfig} from "../../../utility/table-config";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'pcm-manage-api',
  templateUrl: './manage-api.component.html',
  styleUrls: ['./manage-api.component.scss']
})
export class ManageApiComponent implements OnInit {
  manageApi: FormGroup;
  actionButtons = [
    {
      page: 'manage Api',
      label: "Edit",
      iconCls: '',
      class: 'btn btn-pcm-edit',
      link: '',
      id: 'apiEdit'
    },
    {
      page: 'manage Api',
      label: "Delete",
      iconCls: '',
      class: 'btn btn-pcm-delete',
      link: environment.PROXY_ENDPOINT,
      id: 'apiDelete'
    }
  ];
  reqObj: Object;
  qryParams: Object = {
    page: 0,
    sortBy: 'apiName',
    sortDir: 'asc',
    size: 10
  };
  currentPage = 0;
  showResults = false;
  searchResults = [];
  pageIndex = 3;
  sortBy = 'userid';
  sortDir = 'asc';
  page: number;
  size: Number;
  headerColumns = ['API Name', 'Web Method', 'Proxy URL', 'Server URL'];
  displayedColumnsRef: string[] = ['apiName', 'methodName', 'proxyUrl', 'serverUrl'];
  displayedColumns: String[];
  serachTblConfig: any;
  serachTblConfigRef: any;
  totalElements: number;
  totalPages: number;
  constructor(private store: Store, private appComponent: AppComponent, private fb: FormBuilder,
              private cd: ChangeDetectorRef, private service: EndPointService,
              private tblConf: TableConfig,) {
    this.store.dispatch(new ModuleName('Endpoint'));
    this.appComponent.selected = 'api';
  }

  ngOnInit() {
    this.manageApiForm();
    const req = JSON.parse(localStorage.getItem('manageSearch'));
    if(req) {
      this.manageApi.patchValue(req);
    }
    localStorage.removeItem('manageSearch');
    this.displayedColumns = this.displayedColumnsRef;
    this.serachTblConfig = this.tblConf.formColumn(
      this.displayedColumns,
      this.headerColumns
    );
    this.serachTblConfigRef = this.serachTblConfig;
  }

  manageApiForm() {
    this.manageApi = this.fb.group({
      apiName: [''],
      serverWebMethod: [''],
      serverUrl: [''],
    });
  };

  sort(event) {
    // @ts-ignore
    this.currentPage = this.page;
    this.sortBy = event.active;
    this.sortDir = event.direction;
    this.qryParams = {
      ...this.qryParams,
      ...{
        sortBy: this.sortBy,
        sortDir: this.sortDir,
        page: this.page,
        size: this.size
      }
    };
    this.loadSearchResults(this.manageApi.value, this.qryParams);
  }

  columnsChanged(result) {
    this.showResults = false;
    const selectedColumns = result.map((val) => { return val.value; });
    this.serachTblConfig = this.serachTblConfigRef.slice().filter((col) => { return selectedColumns.indexOf(col.key) > -1; });
    this.displayedColumns = this.serachTblConfig.map((col) => { return col.key; });
    this.cd.detectChanges();
    this.showResults = true;
  }

  pagination(event) {
    this.currentPage = event.pageIndex;
    this.page = event.pageIndex + 1;
    this.qryParams = {
      ...this.qryParams,
      ...{
        page: Number(this.page) - 1,
        size: event.pageSize
      }
    };
    this.loadSearchResults(this.reqObj, this.qryParams);
  }

  loadSearchResults(req, qryParams?) {
    this.service.searchEndPoint(req, qryParams).subscribe(res => {
      this.showResults = true;
      this.searchResults = JSON.parse(JSON.stringify(res['content']));
      this.size = res['size'];
      this.page = res['number'];
      this.currentPage = this.page;
      this.totalElements = res['totalElements'];
      this.totalPages = res['totalPages'];
    });
  }

  searchAPI(){
    this.searchResults = [];
    this.reqObj = this.manageApi.value;
    this.currentPage = 0;
    this.showResults = false;
    localStorage.setItem('manageSearch',JSON.stringify(this.manageApi.value));
    this.loadSearchResults(this.reqObj);

  }

  resFromTable(eve) {
    if (eve.statusCode === 200) {
      this.loadSearchResults(this.manageApi.value, this.qryParams);
    }
  }

  cancel() {
    this.manageApiForm();
  }
}
