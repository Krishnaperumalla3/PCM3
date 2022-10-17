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

import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { ViewCell } from 'ng2-smart-table';
import {MatDialog} from '@angular/material';
import { BuildRulesComponent } from './build-rules/build-rules.component';

@Component({
  template: `
  <mat-icon [matMenuTriggerFor]="menu" svgIcon="menu-bar"></mat-icon>
    <mat-menu #menu="matMenu">
      <button mat-menu-item (click)="add.emit(rowData)">
        <mat-icon svgIcon="add_box"></mat-icon>
        ADD
      </button>
      <button mat-menu-item (click)="add.emit(rowData)">
        <mat-icon svgIcon="delete"></mat-icon>
        DELETE
      </button>
      <button mat-menu-item (click)="add.emit(rowData)">
        <mat-icon svgIcon="horizontal_split"></mat-icon>
        RULES
      </button>
    </mat-menu>
  `,
})
export class TableRenderComponent implements ViewCell, OnInit {

    constructor(
        public dialog: MatDialog
    ) {}

  @Input() value: string | number;
  @Input() rowData: any;

  @Output() add: EventEmitter<any> = new EventEmitter();

  ngOnInit() {
    console.log(this.rowData);
  }

  addRules() {
    const dialogRef = this.dialog.open(BuildRulesComponent, {
      data: {
        rules: this.rowData.processRulesList
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      const ruleLst = JSON.parse(result);
      this.rowData.processRulesList = ruleLst;
      console.log(`Dialog result: ${ruleLst}`);
    });
  }
}
