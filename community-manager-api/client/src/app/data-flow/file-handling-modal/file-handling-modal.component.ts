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

import {FormBuilder, FormGroup} from '@angular/forms';
import {Component, Inject} from '@angular/core';
import {DATA_FLOW_OBJ} from 'src/app/model/data-flow.constants';
import {frameFormObj} from 'src/app/utility';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
  selector: 'pcm-file-handling-modal',
  templateUrl: './file-handling-modal.component.html',
  styleUrls: ['./file-handling-modal.component.scss']
})
export class FileHandlingModalComponent {

  constructor(
    public dialogRef: MatDialogRef<FileHandlingModalComponent>,
    private fb: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data
  ) {
    this.paramField = DATA_FLOW_OBJ['MFT'];
    this.paramForm = this.fb.group(frameFormObj(this.paramField));
  }

  paramField = DATA_FLOW_OBJ.MFT;
  paramForm: FormGroup;
  fileHandlings = [];
  fileHandling = 'MFT';

  row: {
    data: 'test';
  };

  addNewRule() {
  }

  onCancelClick(): void {
  }

  changeFileType(e) {
  }
}
