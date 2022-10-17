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

import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {frameFormObj} from 'src/app/utility';
import {PartnerService} from 'src/app/services/partner.service';
import Swal from "sweetalert2";
import { TranslateService } from '@ngx-translate/core';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'pcm-create-as2-relationship',
  templateUrl: './create-as2-relationship.component.html',
  styleUrls: ['./create-as2-relationship.component.scss']
})
export class CreateAs2RelationshipComponent implements OnInit {
  createAs2MappingForm: FormGroup;
  createAs2MappingFields = [
    {
      placeholder: 'PARTNERS.FIELDS.IN_ORG_NME.LABEL', formControlName: 'organizationName', inputType: 'pcm-select', validation: [],
      options: []
    },
    {
      placeholder: 'PARTNERS.FIELDS.IN_PRTR_NME.LABEL', formControlName: 'partnerName', inputType: 'pcm-select', validation: [],
      options: []
    }
  ];

  constructor(private fb: FormBuilder,
              private pService: PartnerService,
              public translate: TranslateService
              ) {
    this.createAs2MappingFormGroup();
  }

  createAs2MappingFormGroup() {
    this.createAs2MappingForm = this.fb.group(frameFormObj(this.createAs2MappingFields));
  }

  ngOnInit() {

    this.pService.getAs2OrgProfilePartnersList().subscribe(res => {
      let as2OrgProfileList: any = res;
      this.createAs2MappingFields = this.createAs2MappingFields.map(val => {
        if (val.formControlName === 'organizationName') {
          val.options = as2OrgProfileList.map(opt => {
            return {
              label: opt.name,
              value: opt.name
            };
          });
        }
        return val;
      });
    });

    this.pService.getAs2PartnersProfilesList().subscribe(res => {
      let as2PartnerProfiles: any = res;
      this.createAs2MappingFields = this.createAs2MappingFields.map(val => {
        if (val.formControlName === 'partnerName') {
          val.options = as2PartnerProfiles.map(opt => {
            return {
              label: opt.name,
              value: opt.name
            };
          });
        }
        return val;
      });
    });

  }

  onSubmit(form) {
    console.log("came to onSubmit");
    const reqObj = form.value;
    console.log(reqObj);
    this.pService.createAs2Relation(reqObj).subscribe(res => {
      Swal.fire({
        title: this.translate.instant('PARTNERS.MODAL.TITLE'),
        text: res['statusMessage'],
        type: 'success',
        showConfirmButton: false,
        timer: 2000
      });
    }, (err) => {
      Swal.fire({
        title: this.translate.instant('PARTNERS.MODAL.TITLE'),
        text: err['error']['errorDescription'],
        type: 'error',
        showConfirmButton: true
      });
    });
  }
}
