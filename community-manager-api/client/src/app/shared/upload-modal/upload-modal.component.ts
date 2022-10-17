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

import {ChangeDetectorRef, Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {PartnerService} from '../../services/partner.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {frameFormObj, markFormFieldTouched} from 'src/app/utility';
import {displayFields} from 'src/app/model/displayfields.map';
import Swal from 'sweetalert2';
import { TranslateService } from '@ngx-translate/core';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'pcm-upload-modal',
  templateUrl: './upload-modal.component.html',
  styleUrls: ['./upload-modal.component.scss']
})
export class UploadModalComponent implements OnInit {

  uploadForm: FormGroup;
  uploadWorkFlow: FormGroup;
  uploadFields = [];
  url = '';
  type = '';

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<UploadModalComponent>,
    private cd: ChangeDetectorRef,
    private fb: FormBuilder,
    private service: PartnerService,
    public translate: TranslateService

  ) {
  }

  ngOnInit() {
    if (this.data.body.item.workFlow) {
      this.uploadFields = this.data.body.item.uploadField;
    } else {
      const {item, selected, type} = this.data.body;
      this.uploadFields = type !== 'AS2' ? item['fields'] : item['fields'][selected];
      this.url = item.url[selected];
      this.type = this.data.body.type;
    }
    this.uploadForm = this.fb.group(frameFormObj(this.uploadFields));
    this.uploadWorkFlow = this.fb.group({
      file: [null, Validators.required]
    });
  }

  getFieldName(field) {
    return displayFields[field.formControlName] ? displayFields[field.formControlName] : field.placeholder;
  }

  upload() {
    /**
     * @TODO
     * @Naveen it is for the certificate upload so there is no need to check item.workflow
     */
    const isInvalid = this.uploadForm.invalid;
    if (!!isInvalid) {
      Swal.fire(
        this.translate.instant('SWEET_ALERT.IN_VALID.TITLE'),
        this.translate.instant('SWEET_ALERT.IN_VALID.BODY'),
        'error'
      );
      markFormFieldTouched(this.uploadForm);
      return false;
    }

    if (this.data.body.item.workFlow || true) {
      // console.log(this.data.body.item);
      // console.log(this.uploadForm);
      const payload = {
        file: (this.uploadForm.value.file.files || [])[0]
      };
      const fb = new FormData();
      fb.append('file', payload.file);
      // tslint:disable-next-line:max-line-length
      // console.log(this.uploadForm.value);
      const {certData, certName, certType, keyName, keyStorePassword, privateKeyPassword, verifyAuthChain, verifyValidity} = this.uploadForm.value;
      let url = ((this.data.body.item || {}).url || {})['no'] || this.data.body.item.URL;
      if (this.data.body.selected === 'Yes' || this.data.body.selected === 'HubConnToPartner' || this.data.body.selected === 'PartnerConnToHub' || this.data.body.selected === 'HubConnToApplication' || this.data.body.selected === 'ApplicationConnToHub') {
        if (this.type === 'AS2') {
          fb.append('certName', certName);
          fb.append('certType', certType);
          fb.append('privateKeyPassword', privateKeyPassword);
          url = this.data.body.item.url['yes'];
        } else {
          url = ((this.data.body.item || {}).url || {})['no'] || this.data.body.item.URL;
          if (this.type === 'SFTP' || this.type === 'SFGSFTP') {
            fb.append('keyName', keyName);
          } else {
            fb.append('certName', certName);
          }
        }
      } else {
        if (!!this.data.body.item.applicationProfile) {
          // fb.append('applicationPkId', this.data.body.item.applicationProfile);
          // fb.append('partnerPkId', this.data.body.item.partnerProfile);
          url = this.data.body.item.URL;
        } else {
          url = this.data.body.item.url['no'];
          if (this.type === 'SFTP' || this.type === 'SFGSFTP') {
            fb.append('keyName', keyName);
          } else {
            fb.append('certName', certName);
          }
        }

      }
      this.service.uploadCertificates({fb, url}).subscribe(res => {
        this.dialogRef.close({response: res, error: false});
      }, err => {
        if(err.status !== 401) {
          Swal.fire(
            this.translate.instant('SWEET_ALERT.ACTN_STATUS'),
            err['error']['errorDescription'] ? err['error']['errorDescription'] : err['error']['message'],
            'error'
          );
        }
        //this.dialogRef.close({response: err, error: true});
      });
    }
  }

  changeWoF(event) {
    const reader = new FileReader();

    if (event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      reader.readAsDataURL(file);
      reader.onload = () => {
        this.uploadWorkFlow.patchValue({
          file: reader.result
        });
      };
    }
  }

  onFileChange(event, cntrlFrm) {
    const reader = new FileReader();
    if (event.target.files && event.target.files.length) {
      const [file] = event.target.files;
      reader.readAsDataURL(file);
      console.log(cntrlFrm);
      const obj = {};
      obj[cntrlFrm] = reader.result;
      reader.onload = () => {
        this.uploadForm.patchValue(obj);
        // need to run CD since file load runs outside of zone
        this.cd.markForCheck();
      };
    }
  }
}
