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

import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'pcm-pcm-tbl-action',
  template: `
  <mat-icon [matMenuTriggerFor]="menu" svgIcon="menu-bar">{{page}}</mat-icon>
  <mat-menu #menu="matMenu">
    <mat-list>
      <ng-container [ngSwitch]="page">
        <ng-container *ngSwitchCase="'manage partner'">
          <div  *ngFor="let item of actionMenuButtons">
            <mat-list-item *ngIf="item.id != 'partDeactivate' && row.status === 'Inactive'">
              <button class="{{item.class}}" (click)="menuClick(item)" mat-menu-item>{{item.label}}</button>
            </mat-list-item>
            <mat-list-item *ngIf="item.id != 'partActivate' && row.status === 'Active'">
              <button class="{{item.class}}" (click)="menuClick(item)" mat-menu-item>{{item.label}}</button>
            </mat-list-item>
          </div>
        </ng-container>
        <ng-container *ngSwitchCase="'manage user'">
          <div  *ngFor="let item of actionMenuButtons">
            <mat-list-item *ngIf="item.id != 'userDeactivate' && row.status === 'Inactive'">
              <button class="{{item.class}}" (click)="menuClick(item)" mat-menu-item>{{item.label}}</button>
            </mat-list-item>
            <mat-list-item *ngIf="item.id != 'userActivate' && row.status === 'Active'">
              <button class="{{item.class}}" (click)="menuClick(item)" mat-menu-item>{{item.label}}</button>
            </mat-list-item>
          </div>
        </ng-container>
        <ng-container *ngSwitchCase="'manage application'">
          <div  *ngFor="let item of actionMenuButtons">
            <mat-list-item *ngIf="item.id != 'appDeactivate' && row.appIsActive === 'Inactive'">
              <button class="{{item.class}}" (click)="menuClick(item)" mat-menu-item>{{item.label}}</button>
            </mat-list-item>
            <mat-list-item *ngIf="item.id != 'appActivate' && row.appIsActive === 'Active'">
              <button class="{{item.class}}" (click)="menuClick(item)" mat-menu-item>{{item.label}}</button>
            </mat-list-item>
          </div>
        </ng-container>
        <ng-container *ngSwitchDefault>
          <div  *ngFor="let item of actionMenuButtons">
            <mat-list-item >
              <button class="{{item.class}}" (click)="menuClick(item)" mat-menu-item>{{item.label}}</button>
            </mat-list-item>
          </div>
        </ng-container>
      </ng-container>
    </mat-list>
  </mat-menu>
  `,
  styles: []
})
export class PcmTblActionComponent implements OnInit {

  constructor() { }

  @Input() value: string | number;
  @Input() rowData: any;
  @Output() click: EventEmitter<any> = new EventEmitter();
  @Input() page = '';
  @Input() actionMenuButtons = [];
  @Input() actionBtn = false;

  ngOnInit() {
    }


  menuClick(itm) {
    console.log(itm);
    const row = {
      item: itm,
      row: this.rowData
    }
    this.click.emit(row);
  }
}
