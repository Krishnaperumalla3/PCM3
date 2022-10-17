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

import {FormBuilder, FormGroup} from "@angular/forms";
import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {DATA_FLOW_OBJ} from "src/app/model/data-flow.constants";
import {frameFormObj} from "src/app/utility";

@Component({
  selector: "pcm-file-handling",
  template: `
    <div>
      <h1 mat-dialog-title>{{ data.value }} File Handling</h1>
      <div mat-dialog-content>
        <div>
          <form [formGroup]="paramForm" class="data-flow-form">
            <ng-container *ngFor="let field of paramField">
              <div [ngSwitch]="field.inputType">
                <div class="field-container" *ngSwitchCase="'pcm-text'">
                  <mat-form-field>
                    <input
                      matInput
                      placeholder="{{ field.placeholder }}"
                      formControlName="{{ field.formControlName }}"
                    />
                  </mat-form-field>
                </div>
              </div>
            </ng-container>
            <div mat-dialog-actions class="btn-grp" style="justify-content: center;">
              <button
                type="button"
                (click)="onCancelClick()"
                class="btn btn-info"
              >
                CANCEL
              </button>
              <button
                type="button"
                [disabled]="paramForm.invalid"
                class="btn btn-primary"
                [mat-dialog-close]="row"
                cdkFocusInitial
              >
                ADD
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  `,
  styles: []
})
export class FileHandlingComponent {
  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<FileHandlingComponent>,
    private fb: FormBuilder
  ) {
    this.paramField = DATA_FLOW_OBJ[this.data.value];
    this.paramForm = this.fb.group(frameFormObj(this.paramField));
  }

  paramField = DATA_FLOW_OBJ.MFT;
  paramForm: FormGroup;

  row = {
    data: "test"
  };

  onCancelClick(): void {
    this.dialogRef.close();
  }
}
