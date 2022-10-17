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

import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";

@Component({
  selector: "pcm-percentage-field",
  template: `
    <label>{{ label }}</label>
    <button class="percent-btn" (click)="decrement()">-</button>
    <input
      class="percent-value"
      type="number"
      step="{{ step }}"
      name="{{ label }}"
      min="{{min}}"
      max="{{max}}"
      (change)="setValue($event)"
      value="{{value}}"
    />
    <span>%</span>
    <button class="percent-btn" (click)="increment()">+</button>
  `,
  styles: [`
  .percent-btn {
    background: #f2f2f2;
    width: 20px;
    height: 22px;
    border: none;
    border-radius: 2px;
    margin: auto 10px;
  }
  .percent-value {
    border: none;
    width: 40px;
    border-bottom: 1px solid;
    text-align: center;
  }
  input:focus {
    outline:none;
  }
  `]
})
// <input type="number" step="step" placeholder="label" [(ngModel)]="value">
export class PcmPercentageFieldComponent implements OnInit {
  constructor() {}

  @Input() step = 0.1;
  @Input() label = "";
  @Input() value = 0.0;
  @Input() min = 0;
  @Input() max = 100;
  @Output() valueChange = new EventEmitter();

  ngOnInit() {}

  increment() {
    this.value = ((this.value * 10) + (this.step * 10)) / 10;
  }

  decrement() {
    this.value = ((this.value * 10) - (this.step * 10)) / 10;
  }

  setValue(e) {
    this.value = e.target.value > 100 ? 100 : e.target.value;
  }
}
