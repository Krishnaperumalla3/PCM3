import {Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {OtherService} from "../../services/other/other.service";
import {BytePipe} from "../../shared/file-drag-drop/byte.pipe";
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import {ModuleName} from "../../../store/layout/action/layout.action";
import {MatPaginator} from "@angular/material/paginator";
import Swal from "sweetalert2";
import {FileUploaderService} from "../../services/mft-upload/file-uploader.service";

@Component({
  selector: 'pcm-file-download',
  templateUrl: './file-download.component.html',
  styleUrls: ['./file-download.component.scss']
})
export class FileDownloadComponent implements OnInit {
  typesOfMailbox: string[];
  selectedMailbox;
  displayedColumns = ['fileName', 'fileSize', 'action'];
  mailboxActivityColumns = ['activityDt', 'activityBy', 'bpname', 'details'];
  dataSource;
  activityDataSource;
  showResults = false;
  showActivityResults = false;

  @ViewChild('paginator') set matPaginator(mp: MatPaginator) {
    if (this.showResults) {
      this.dataSource.paginator = mp;
    }
  }

  @ViewChild('pagination') set matPagination(mp: MatPaginator) {
    if (this.showActivityResults) {
      this.activityDataSource.paginator = mp;
    }
  }

  constructor(
    private service: OtherService,
    private appComponent: AppComponent,
    private store: Store,
    public uploader: FileUploaderService) {
    this.store.dispatch(new ModuleName('MFT / File Download'));
    this.appComponent.selected = 'mft';
  }

  ngOnInit() {
    this.getMailbox();
  }

  getMailbox() {
    this.service.getPartnerMailbox().subscribe(res => {
      this.typesOfMailbox = JSON.parse(JSON.stringify(res));
    });
  }

  submit(val) {
    this.selectedMailbox = val;
    this.showResults = false;
    this.showActivityResults = false;
    this.getDownloadMailbox(val);
    this.getActivity(val);
  }

  getDownloadMailbox(mailbox) {
    this.service.getMailboxById(mailbox).subscribe(res => {
      this.showResults = true;
      const data = JSON.parse(JSON.stringify(res)).map(item => {
          return {fileName: item.fileName, fileSize: new BytePipe().transform(parseInt(item.fileSizeInBytes))}
        }
      );
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.matPaginator;
    });
  }

  getActivity(val) {
    this.uploader.mailboxActivity(val, 'DOWNLOAD').subscribe(res => {
      this.showActivityResults = true;
      this.activityDataSource = new MatTableDataSource(JSON.parse(JSON.stringify(res)));
      this.activityDataSource.paginator = this.matPagination;
    });
  }

  download(ele) {
    const req = {fileName: ele.fileName, mailbox: this.selectedMailbox};
    this.service.downloadMailbox(req).subscribe(res => {
      this.download_file(res, req);
      this.getDownloadMailbox(this.selectedMailbox);
      this.getActivity(this.selectedMailbox)
    }, err => {
      Swal.fire(
        'Download File',
        err['error']['errorDescription'],
        'error'
      ).then();
    });
  }

  download_file(res, row) {
    const blob = new Blob([res], {type: 'application/octet-stream'});
    const fileName = row.fileName;
    if (window.navigator && window.navigator.msSaveBlob) {
      window.navigator.msSaveBlob(blob);
    }
    const a = document.createElement('a');
    document.body.appendChild(a);
    const url = window.URL.createObjectURL(blob);
    a.href = url;
    a.download = fileName;
    a.target = '_self';
    a.click();
    window.URL.revokeObjectURL(url);
  }

}
