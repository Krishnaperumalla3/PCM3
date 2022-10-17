import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {OtherService} from "../../services/other/other.service";
import Swal from "sweetalert2";
import {markFormFieldTouched} from "../../utility";
import {TranslateService} from "@ngx-translate/core";
import {ModuleName} from "../../../store/layout/action/layout.action";
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import {ChunkFileUploadService} from "../../services/chunk-file-upload/chunk-file-upload.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {Observable} from "rxjs";
import {FileQueueObject, FileUploaderService} from "../../services/mft-upload/file-uploader.service";

@Component({
  selector: 'pcm-file-drop',
  templateUrl: './file-drop.component.html',
  styleUrls: ['./file-drop.component.scss']
})
export class FileDropComponent implements OnInit {
  fileDropForm: FormGroup;
  searchFilterCtrl: any = {};
  typesOfMailbox: string[] = [];
  selectedItem;
  queue: Observable<FileQueueObject[]>;
  displayedColumns = ['name', 'size', 'progress', 'action'];
  uploadData = [];
  mailboxActivityColumns = ['activityDt', 'activityBy', 'bpname', 'details'];
  dataSource;
  activityDataSource;
  files: any[] = [];
  showResults = false;
  private paginator: MatPaginator;
  @ViewChild(MatPaginator) set matPaginator(mp: MatPaginator) {
    this.paginator = mp;
    if(this.showResults) {
      this.activityDataSource.paginator = this.paginator;
    }
  };
  @ViewChild('fileDropRef') private fileDropRef: ElementRef;
  constructor(private fb: FormBuilder,
              private service: OtherService,
              private chunkService: ChunkFileUploadService,
              public translate: TranslateService,
              private appComponent: AppComponent,
              private store: Store,
              public uploader: FileUploaderService) {
    this.store.dispatch(new ModuleName('MFT / File Upload'));
    this.appComponent.selected = 'mft';
  }

  ngOnInit() {
    this.fileDropFormGroup();
    this.queue = this.uploader.queue;
    this.queue.subscribe(res => {
      this.dataSource = new MatTableDataSource(res);
      this.dataSource.data.forEach(data => {
        if(data.isSuccess()) {
          this.getActivity(data.file.mailbox);
        }
      })
    }, error => {
      console.log(error);
    });
    this.searchFilterCtrl['partnerPkId'] = new FormControl();
    // this.getPartnerList();
    this.service.getPartnerMailbox().subscribe(res => {
      this.typesOfMailbox = JSON.parse(JSON.stringify(res));
    });
  }

  onFileDropped($event) {
    this.prepareFilesList($event);
  }

  fileBrowseHandler(eve) {
    this.prepareFilesList(eve.target.files);
  }

  prepareFilesList(files: Array<any>) {
    if(!this.selectedItem) {
      Swal.fire(
        'Upload File',
        'Please select mailbox.',
        'warning'
      ).then();
      this.fileDropRef.nativeElement.value = '';
      return false;
    }
    for (const item of files) {
      item.mailbox = this.selectedItem;
      this.files.push(item);
    }
    this.uploader.addToQueue(files);
    this.fileDropRef.nativeElement.value = '';
    // this.uploadData[0] = {name: files[0].name, size: files[0].size, progress: 'Inprogress'};
    //
    // this.chunkService.upload(files[0], this.selectedItem).subscribe(res => {
    //   this.uploadData.length = 0;
    //   this.uploadData = [];
    //   this.getActivity(this.selectedItem);
    //   Swal.fire({
    //     title: 'File Drop',
    //     text: res['statusMessage'],
    //     type: 'success',
    //     showConfirmButton: false,
    //     timer: 2000
    //   }).then();
    //   this.clear();
    // }, err => {
    //   Swal.fire(
    //     'File Drop',
    //     JSON.parse(err['error'])['errorDescription'],
    //     'error'
    //   ).then();
    // });
  }

  formatBytes(bytes, decimals?) {
    if (bytes === 0) {
      return '0 Bytes';
    }
    const k = 1024;
    const dm = decimals <= 0 ? 0 : decimals || 2;
    const sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
  }

  fileDropFormGroup() {
    this.fileDropForm = this.fb.group({
      // mailbox: ['', [Validators.required]],
      partnerPkId: ['', [Validators.required]],
      files: [null, [Validators.required]]
    });
  }

  getSearchFilterOptions(options = [], field) {
    const value = (this.searchFilterCtrl[field].value || '').toLowerCase();
    if (!value) {
      return options;
    }
    return options.filter(option => option.value.toLowerCase().includes(value));
  }

  getActivity(val) {
    this.uploader.mailboxActivity(val, 'UPLOAD').subscribe(res => {
      this.showResults = true;
      this.activityDataSource = new MatTableDataSource(JSON.parse(JSON.stringify(res)));
      this.activityDataSource.paginator = this.paginator;
    });
  }

  selectMailbox(val) {
    this.showResults = false;
    this.getActivity(val);
    if(!!this.selectedItem) {
      this.fileDropRef.nativeElement.value = '';
      this.files.length = 0;
      this.uploader._files.length = 0
    }
  }

  onSubmit() {
    if (this.fileDropForm.invalid) {
      Swal.fire(
        this.translate.instant('SWEET_ALERT.IN_VALID.TITLE'),
        this.translate.instant('SWEET_ALERT.IN_VALID.BODY'),
        'error'
      ).then();
      markFormFieldTouched(this.fileDropForm);
      return false;
    }
    const fb = new FormData();
    this.fileDropForm.value.files.files.forEach(val => {
      fb.append('files', val);
    });
    fb.append('partnerPkId', this.fileDropForm.value.partnerPkId);
    let headers = new Headers();
    headers.append('Content-Type', 'multipart/form-data');

    // this.chunkService.upload(this.fileDropForm.value.files.files[0]).subscribe(res => {
    //   console.log(res)
    //   Swal.fire({
    //     title: 'File Drop',
    //     text: res['statusMessage'],
    //     type: 'success',
    //     showConfirmButton: false,
    //     timer: 2000
    //   }).then();
    //   this.clear();
    // }, err => {
    //   Swal.fire(
    //     'File Drop',
    //     JSON.parse(err['error'])['errorDescription'],
    //     'error'
    //   ).then();
    // });
  }

  clear() {
    this.fileDropForm.reset();
    this.fileDropForm.get('partnerPkId').clearValidators();
    this.fileDropForm.get('files').clearValidators();
  }

}
