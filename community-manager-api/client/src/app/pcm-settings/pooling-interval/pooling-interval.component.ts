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

import {Component, OnInit, OnChanges } from '@angular/core';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';
import {FileSearchService} from 'src/app/services/file-search/file-search.service';
import Swal from 'sweetalert2';
import swal from 'sweetalert';
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import {ModuleName} from "../../../store/layout/action/layout.action";
import {TranslateService} from "@ngx-translate/core";

/**
 * @title Drag&Drop sorting
 */
@Component({
  selector: 'pcm-pooling-interval',
  templateUrl: './pooling-interval.component.html',
  styleUrls: ['./pooling-interval.component.scss'],
  providers: [FileSearchService]
})
export class PoolingIntervalComponent implements OnInit {
  update_btn = this.translate.instant("SETTINGS.BUTTONS.UPDATE");
  cancel_btn = this.translate.instant("SETTINGS.BUTTONS.CANCEL");
  constructor(
    private fService: FileSearchService,
    private appComponent: AppComponent,
    private store: Store,
    public translate: TranslateService
  ) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.SETTINGS'));
    this.appComponent.selected = 'settings';
    this.translate.onLangChange.subscribe(val => {
      this.update_btn = this.translate.instant("SETTINGS.BUTTONS.UPDATE");
      this.cancel_btn = this.translate.instant("SETTINGS.BUTTONS.CANCEL");
    });
  }
  selectedInterval = {};
  newInterval = {
    seq: 0,
    pkId: null,
    interval: null,
    description: null
  };
  intervalObj = {
    seq: 0,
    pkId: '',
    interval: '',
    description: ''
  };
  addNew = false;
  poolingInterVal: any = [];
  selected = -1;

  trimValue(event) { event.target.value = event.target.value.trimLeft(); }

  ngOnInit() {
    this.getPoolingInterval();
  }

  getPoolingInterval() {
    this.fService.getPoolingInterval().subscribe(res => {
      this.poolingInterVal = res;
    });
  }

  drop(event: CdkDragDrop<string[]>) {
    moveItemInArray(this.poolingInterVal, event.previousIndex, event.currentIndex);
  }

  onAddNew() {
    this.newInterval = Object.assign({}, this.intervalObj);
    this.newInterval.seq = this.poolingInterVal.length + 1;
    this.addNew = true;
  }

  onAdd() {
    this.newInterval.pkId = this.newInterval.pkId.trimLeft();
    this.newInterval.interval = this.newInterval.interval.trimLeft();
    this.newInterval.description = this.newInterval.description.trimLeft();
    if (this.newInterval.pkId != null && this.newInterval.interval) {
      this.poolingInterVal.push(this.newInterval);
      this.addNew = false;
    } else {
      Swal.fire({
        title: this.translate.instant('SWEET_ALERT.CERTIFICATE.TITLE'),
        text: this.translate.instant('SWEET_ALERT.CERTIFICATE.WARN.BODY'),
        type: 'warning'
      });
    }
  }

  onEdit(i) {
    if (this.selected === -1) {
      this.selected = i;
      this.selectedInterval = Object.assign({}, this.poolingInterVal[i]);
    } else {
      this.selected = -1;
    }
  }

  onDelete(index, selectd) {
    if (selectd === -1) {
      swal({
        title: this.translate.instant('SWEET_ALERT.POLLING_INT.DELETE.TITLE'),
        text: this.translate.instant('SWEET_ALERT.POLLING_INT.DELETE.BODY'),
        icon: 'warning',
        buttons: [this.translate.instant('SWEET_ALERT.POLLING_INT.DELETE.CNFRM_TXT'), true],
        dangerMode: true,
      })
        .then((willDelete) => {
          if (willDelete) {
            this.poolingInterVal.splice(index, 1);

          }
        });
    } else {
      this.poolingInterVal[this.selected] = this.selectedInterval;
      this.selected = -1;
    }
  }

  savePoolingInterval() {
    const req = this.poolingInterVal;
    this.fService.savePoolingInterval(req).subscribe(res => {
      if (res) {
        Swal.fire({
          title: this.translate.instant('SWEET_ALERT.POLLING_INT.TITLE'),
          text: res['statusMessage'],
          type: 'success',
          showConfirmButton: false,
          timer: 2000
        });
        this.getPoolingInterval();
      }
    }, (err) => {
      if(err.status !== 401) {
        Swal.fire(
          this.translate.instant('SWEET_ALERT.POLLING_INT.TITLE'),
          err['error']['errorDescription'],
          'error'
        );
      }
    });
  }
}
