/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {FileSearchService} from 'src/app/services/file-search/file-search.service';
import {MatDialog} from '@angular/material';
import {ViewDetailModalComponent} from 'src/app/shared/view-detail-modal/view-detail-modal.component';
import Swal from "sweetalert2";
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'pcm-pcm-render-cell',
  template: `
    <span class="pcm-link" matTooltip="{{getValue()}}" (click)="viewFile(rowData, key)">
    {{ getValue() }}
  </span>
  `,
  styles: [``]
})
export class PcmRenderCellComponent implements OnInit {

  constructor(
    private service: FileSearchService,
    public dialog: MatDialog,
    private translate: TranslateService
  ) {
  }

  @Input() title = '';
  @Input() key = '';
  @Input() value: string | number;
  @Input() rowData: any;
  @Output() click: EventEmitter<any> = new EventEmitter();
  isDowloading = false;

  ngOnInit() {
    console.log(this.rowData[this.key], this.title);
  }

  getValue() {
    return this.rowData[this.key];
  }

  viewFile(row, key) {
    if (this.isDowloading) {
      return false;
    }
    const type = key === 'srcfilename' ? 'source' : 'destination';
    this.isDowloading = true;
    this.service.viewFile(row.seqid, type).subscribe(res => {
      console.log(res);
      this.isDowloading = false;
      const dialogRef = this.dialog.open(ViewDetailModalComponent, {
        width: '60%',
        data: {
          page: 'view file',
          type: 'details',
          row: row,
          flowType: type,
          body: res
        }
      });
      dialogRef.afterClosed().subscribe(result => {
        console.log(`Dialog result: ${result}`);
      });
    }, err => {
      this.isDowloading = false;
      console.log(err);
      Swal.fire(
        this.translate.instant('SWEET_ALERT.ERROR.TITLE'),
        err['statusText'],
        'error'
      );
    })
  }

}
