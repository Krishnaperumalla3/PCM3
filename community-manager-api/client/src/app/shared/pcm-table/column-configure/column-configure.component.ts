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

import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-column-configure',
  template: `
  <div>
  <h1 mat-dialog-title>{{'COMMON.TABLE.CONFG_COL' | translate}}</h1>
  <div mat-dialog-content>
    <mat-selection-list  #colSelected >
    <mat-list role="list">
      <mat-list-item role="listitem"><b>{{'COMMON.TABLE.SELECT_ALL' | translate}}</b>
      <mat-checkbox *ngIf="colSelected.selectedOptions.selected.length !== data.columns.length" style="margin-left: 46%;"
      (change)="colSelected.selectAll()" >
      </mat-checkbox>
      <mat-checkbox *ngIf="colSelected.selectedOptions.selected.length === data.columns.length" style="margin-left: 46%;"
      (change)="colSelected.deselectAll()" checked="true">
      </mat-checkbox>
      </mat-list-item>
    </mat-list>
      <mat-list-option value="{{cols.key}}" [selected]="cols.selected" *ngFor="let cols of data.columns">
      {{cols.header | translate}}
      </mat-list-option>
    </mat-selection-list>
  </div>
  <div mat-dialog-actions>
    <button mat-button [mat-dialog-close]="colSelected.selectedOptions.selected" cdkFocusInitial>{{'COMMON.TABLE.BTN_OK' | translate}}</button>
  </div>
  </div>
  `,
  styles: []
})
export class ColumnConfigureComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private translate: TranslateService) { }
}
