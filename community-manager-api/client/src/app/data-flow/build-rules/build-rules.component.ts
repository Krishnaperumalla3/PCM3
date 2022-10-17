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

import {Component, Inject, OnInit} from '@angular/core';
import {CommonState} from 'src/store/layout/state/common.state';
import {Select} from '@ngxs/store';
import {Observable} from 'rxjs';
import {MAT_DIALOG_DATA} from '@angular/material';

@Component({
  selector: 'pcm-build-rules',
  templateUrl: './build-rules.component.html',
  styleUrls: ['./build-rules.component.scss']
})
export class BuildRulesComponent implements OnInit {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.exstngLst = this.data.rules;
  }

  @Select(CommonState.getRules) rules$: Observable<any>;
  key = 'ruleId';
  display = 'ruleName';
  exstngLst: Array<any> = [];
  selected = [];

  ngOnInit() {
  }

  selectedRules(list) {
    this.selected = list;
  }
}
