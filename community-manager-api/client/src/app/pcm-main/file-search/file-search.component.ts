import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {OtherService} from "../../services/other/other.service";
import {AppComponent} from "../../app.component";
import {ModuleName} from "../../../store/layout/action/layout.action";
import {Store} from "@ngxs/store";
import {FileSearchService} from "../../services/file-search/file-search.service";
import Swal from "sweetalert2";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'pcm-file-search',
  templateUrl: './file-search.component.html',
  styleUrls: ['./file-search.component.scss']
})
export class FileSearchComponent implements OnInit {
  myForm: FormGroup;
  range;
  mailboxList = [];
  searchFilterCtrl: any = {};
  displayedColumns: string[] = [
    'filearrived',
    'partner',
    'srcfilename',
    'srcFileSize',
    'xrefName',
    'status',
    'flowinout',
    'senderid'
  ];
  sortBy = 'filearrived';
  sortDir = 'desc';
  page: Number;
  size: Number;
  totalElements: Number;
  totalPages: Number;
  currentPage = 0;
  qryParams: Object = {
    page: 0,
    sortBy: 'filearrived',
    sortDir: 'desc',
    size: 10
  };
  searchResults = [];
  showResults = false;
  reqObj: Object;
  constructor(private fb: FormBuilder,
              private otherService: OtherService,
              private appComponent: AppComponent,
              private service: FileSearchService,
              private store: Store,
              private datePipe: DatePipe,) {
    this.store.dispatch(new ModuleName('File Transfer'));
    this.appComponent.selected = 'fileTransfer';
  }

  ngOnInit() {
    this.searchFilterCtrl['mailbox'] = new FormControl();
    this.otherService.getPartnerMailbox().subscribe(res => {
      this.mailboxList = JSON.parse(JSON.stringify(res));
    });
    this.fileSearchForm();
  }

  fileSearchForm() {
    const date: Date = new Date();
    this.myForm = this.fb.group({
      dateRangeEnd: [new Date(date), [Validators.required]],
      dateRangeStart: [new Date(date.setHours(date.getHours() - 24)), [Validators.required]],
      flowInOut: [''],
      mailbox: ['']
    })
  }

  getSearchFilterOptions(options = [], field) {
    const value = (this.searchFilterCtrl[field].value || '').toLowerCase();
    if (!value) {
      return options;
    }
    return options.filter(option => option.toLowerCase().includes(value));
  }

  onSubmit(form) {
    this.searchResults = [];
    let formObj = form.value;
    Object.keys(formObj).forEach(key => {
      if (key === 'dateRangeEnd' || key === 'dateRangeStart') {
        formObj[key] = this.datePipe.transform(new Date(formObj[key]), 'E MMM dd yyyy HH:mm:ss zzzz');
      }
    });
    this.reqObj = {...formObj};
    this.currentPage = 0;
    this.qryParams = {
      page: 0,
      sortBy: 'filearrived',
      sortDir: 'desc',
      size: 10
    };
    this.showResults = false;
    this.loadSearchResults(this.reqObj);
  }

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
    this.loadSearchResults(this.reqObj, this.qryParams);
  }

  pagination(event) {
    this.page = event.pageIndex + 1;
    this.currentPage = event.pageIndex;
    this.qryParams = {
      ...this.qryParams,
      ...{
        page: Number(this.page) - 1,
        size: event.pageSize,
      }
    };

    this.loadSearchResults(this.reqObj, this.qryParams);
  }

  loadSearchResults(req, qryParams?) {
    this.service.searchFileActivity(req, qryParams).subscribe(res => {
      this.showResults = true;
      this.searchResults = res['content'];
      this.size = res['size'];
      this.page = res['number'];
      this.totalElements = res['totalElements'];
      this.totalPages = res['totalPages'];
    }, (err) => {
      if(err.status !== 401) {
        Swal.fire(
          'File Transfer Search',
          err['error']['errorDescription'],
          'error'
        );
      }
    });
  }

  resetForm() {
    this.appComponent.drawer.toggle(true);
    this.currentPage = 0;
    this.showResults = false;
    this.fileSearchForm();
  }
}
