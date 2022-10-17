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

import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {formGroupToggle, frameFormObj, markFormFieldTouched, toggleFormFieldEnable} from '../../utility';
import {CREATE_X2_ENVELOPE_FORM_FIELDS} from '../envelope.constants';
import {EnvelopeService} from '../envelope.service';
import {Subscription} from 'rxjs';
import {SwalComponent} from '@sweetalert2/ngx-sweetalert2';
import {Router, ActivatedRoute} from '@angular/router';
import Swal from 'sweetalert2';
import {PARTNER_KEY} from '../../pcm-partner/create-partner/create-partner.component';
import { TranslateService } from '@ngx-translate/core';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'pcm-create-x12-envelope',
  templateUrl: './create-x12-envelope.component.html',
  styleUrls: ['./create-x12-envelope.component.scss']
})
export class CreateX12EnvelopeComponent implements OnInit {
  @ViewChild('invalidSwal') private invalidSwal: SwalComponent;

  pkId: any;
  private sub: any;
  form: FormGroup;
  formFields: any = {};
  searchFilterCtrl: any = {};
  formFieldsList: any[] = [];
  valuesChangesSubscriptions: Subscription[] = [];

  constructor(
    private fb: FormBuilder,
    private envelopeService: EnvelopeService,
    private route: ActivatedRoute,
    private router: Router,
    private translate: TranslateService
  ) {

  }

  ngOnInit() {
    this.createFormGroup();
    this.fetchDropdownOptions();
    this.checkFormChanges();

    this.sub = this.route.params.subscribe(params => {
      this.pkId = params['pkId'];
      if (!!this.pkId) {
        this.envelopeService.getEnvelopByPkid(this.pkId).subscribe(res => this.extractEditData(res));
      }
    });
  }

  createFormGroup() {
    this.formFieldsList = [];
    this.formFields = {...CREATE_X2_ENVELOPE_FORM_FIELDS};

    this.form = this.fb.group({
      ...Object.keys(this.formFields).reduce((prev, key) => {
        prev[key] = this.fb.group(frameFormObj(this.formFields[key]));
        return prev;
      }, {})
    });
    this.setFormFieldsList();
  }

  extractEditData(data: any = {}) {
    const {
      ediProperties = {},
      isaSegment = {},
      gsSegment = {},
      inBound = {},
      stSegment = {},
      outBound = {}
    } = data;
    const [
      EDI_PROPERTIES, ISA_SEGMENT, GS_SEGMENT, INBOUND_ENVELOPE, ST_SEGMENT, OUTBOUND_ENVELOPE
    ] = [ediProperties, isaSegment, gsSegment, inBound, stSegment, outBound];

    this.form.patchValue({
      EDI_PROPERTIES, ISA_SEGMENT, GS_SEGMENT, INBOUND_ENVELOPE, ST_SEGMENT, OUTBOUND_ENVELOPE
    });
  }

  resetForm(isCreateOrUpdate = false) {
    if (isCreateOrUpdate) {
      this.router.navigate(['/pcm/envelope/manage-x12']);
      return false;
    }
    Swal.fire({
      type: 'question',
      customClass: {
        icon: 'swal2-question-mark'
      },
      title: this.translate.instant('SWEET_ALERT.CANCEL.TITLE'),
      text:  this.translate.instant('SWEET_ALERT.CANCEL.BODY'),
      showCancelButton: true,
      confirmButtonText: 'Yes, cancel it!'
    }).then((result) => {
      if (!!result.value) {
        this.router.navigate(['/pcm/envelope/manage-x12']);
      }
    });
  }

  createX12Request() {
    const isInvalid = this.form.invalid;
    markFormFieldTouched(this.form);
    if (isInvalid) {
      setTimeout(this.invalidSwal.show, 100);
      return false;
    }

    const values = {...this.form.value};
    const {
      EDI_PROPERTIES,
      ISA_SEGMENT,
      GS_SEGMENT,
      ST_SEGMENT,
      INBOUND_ENVELOPE,
      OUTBOUND_ENVELOPE
    } = values;
    const [
      ediProperties = {}, isaSegment = {}, gsSegment = {}, stSegment = {}, inBound = {}, outBound = {}, pkId = ''
    ] = [EDI_PROPERTIES, ISA_SEGMENT, GS_SEGMENT, ST_SEGMENT, INBOUND_ENVELOPE, OUTBOUND_ENVELOPE];
    const payload = {
      ediProperties, isaSegment, gsSegment, stSegment, inBound, outBound
    };
    if (!!this.pkId) {
      payload['pkId'] = this.pkId;
    }
    payload.ediProperties['partnerPkId'] = this.getPartnerKeyByName(ediProperties.partnerName);
    const envelope = {
      partnerName: payload.ediProperties['partnerName'],
      direction: payload.ediProperties['direction'],
      version: payload.isaSegment['interVersion'],
      isaSenderId: payload.isaSegment['isaSenderId'],
      isaReceiverId: payload.isaSegment['isaReceiverId'],
      transaction: payload.stSegment['acceptLookAlias'],
      gsSenderId: payload.gsSegment['gsSenderId'],
      gsReceiverId: payload.gsSegment['gsReceiverId'],
      stSenderId: payload.stSegment['stSenderId'],
      stReceiverId: payload.stSegment['stReceiverId']


    };
    this.envelopeService
      .createX12(payload, !!payload['pkId'])
      .subscribe(response => {
        sessionStorage.setItem('ENVELOPKEY', JSON.stringify(envelope));
        Swal.fire({
          title: this.translate.instant('SWEET_ALERT.ENVELOP.TITLE'),
          text: response['statusMessage'],
          type: 'success',
          showConfirmButton: false,
          timer: 2000
        });
        this.resetForm(true);
      }, error => {
        Swal.fire(
          this.translate.instant('SWEET_ALERT.PARTNER.TITLE'),
          error['error']['errorDescription'],
          'error'
        );
      });
  }

  setFormFieldsList() {
    this.formFieldsList = [
      {title: 'EDI Properties', key: 'EDI_PROPERTIES'},
      {title: 'ISA Segment', key: 'ISA_SEGMENT'},
      {title: 'GS Segment', key: 'GS_SEGMENT'},
      {title: 'InBound Envelope Properties', key: 'INBOUND_ENVELOPE'},
      {title: 'ST Segment', key: 'ST_SEGMENT'},
      {title: 'OutBound Envelope Properties', key: 'OUTBOUND_ENVELOPE'},
    ];
  }

  checkFormChanges() {
    this.clearFormChangeSubscription();

    this.checkDirectionFieldValueChanges();
    this.checkInvokeBPForISA();
  }

  checkDirectionFieldValueChanges() {
    const directionField = this.form.get('EDI_PROPERTIES').get('direction');
    this.bindValueChangesSubscription(directionField, (value: string) => {
      const inBoundForm = this.form.get('INBOUND_ENVELOPE');
      const outBoundForm = this.form.get('OUTBOUND_ENVELOPE');

      formGroupToggle(inBoundForm, value === 'Inbound', value !== 'Inbound');
      formGroupToggle(outBoundForm, value === 'Outbound', value !== 'Outbound');

      this.checkComplianceCheck();
      this.checkExpectAcknowledgement();
    });
  }

  checkInvokeBPForISA() {
    const field = this.form.get('ISA_SEGMENT').get('invokeBPForIsa');
    this.bindValueChangesSubscription(field, value => {
      const businessProcessField = this.form.get('ISA_SEGMENT').get('businessProcess');
      toggleFormFieldEnable(businessProcessField, value === 'YES', true);
    });
  }

  checkComplianceCheck() {
    const field = this.form.get('INBOUND_ENVELOPE').get('complianceCheck');
    this.bindValueChangesSubscription(field, value => {
      const complianceCheckMapField = this.form.get('INBOUND_ENVELOPE').get('complianceCheckMap');
      toggleFormFieldEnable(complianceCheckMapField, value === 'YES', true);
    });
  }

  checkExpectAcknowledgement() {
    const field = this.form.get('OUTBOUND_ENVELOPE').get('expectAck');
    this.bindValueChangesSubscription(field, value => {
      const intAckReqField = this.form.get('OUTBOUND_ENVELOPE').get('intAckReq');
      toggleFormFieldEnable(intAckReqField, value === 'YES', true);
      this.checkInterchangeAcknowledgementRequested();
    });
  }

  checkInterchangeAcknowledgementRequested() {
    const field = this.form.get('OUTBOUND_ENVELOPE').get('intAckReq');
    this.bindValueChangesSubscription(field, value => {
      const ackOverDueHrField = this.form.get('OUTBOUND_ENVELOPE').get('ackOverDueHr');
      const ackOverDueMinField = this.form.get('OUTBOUND_ENVELOPE').get('ackOverDueMin');
      toggleFormFieldEnable(ackOverDueHrField, value === 'YES', true);
      toggleFormFieldEnable(ackOverDueMinField, value === 'YES', true);
    });
  }

  clearFormChangeSubscription() {
    this.valuesChangesSubscriptions.forEach(subscription => subscription.unsubscribe());
    this.valuesChangesSubscriptions = [];
  }

  getPartnerKeyByName(name: string): string {
    let selectItem = {options: []};
    (this.formFields['EDI_PROPERTIES'] || [])
      .forEach(item => {
        if (item.formControlName === 'partnerName') {
          selectItem = item;
        }
      });

    return ((selectItem.options || []).filter(item => item.value === name)[0] || {}).key || '';
  }

  fetchDropdownOptions() {
    this.fetchPartnerProfileDropdownList();
    this.fetchBusinessProcessDropdownList();
    this.fetchCheckMapDropdownList();
    this.fetchTerminatorsMapDropdownList();
  }

  fetchPartnerProfileDropdownList() {
    this.envelopeService.getPartnerMap().subscribe((data: any) => {
      (this.formFields['EDI_PROPERTIES'] || [])
        .forEach(item => {
          if (item.formControlName === 'partnerName') {
            item.options = data.map(listItem => {
              return {
                value: listItem.value,
                label: listItem.value,
                key: listItem.key
              };
            });
          }
        });
    });
  }

  fetchBusinessProcessDropdownList() {
    this.envelopeService.getBusinessProcessList().subscribe((data: any) => {
      const options = data.map(listItem => {
        return {
          value: listItem.name,
          label: listItem.name,
        };
      });
      this.formFields['ISA_SEGMENT'].forEach(item => {
        if (item.formControlName === 'businessProcess') {
          item.options = [...options];
        }
      });

      this.formFields['OUTBOUND_ENVELOPE'].forEach(item => {
        if (item.formControlName === 'extractionMailBoxBp') {
          item.options = [...options];
        }
      });
    });
  }

  fetchCheckMapDropdownList() {
    this.envelopeService.getMapNameList().subscribe((data: any) => {
      (this.formFields['INBOUND_ENVELOPE'] || [])
        .forEach(item => {
          if (item.formControlName === 'complianceCheckMap') {
            item.options = data.map(listItem => {
              return {
                value: listItem.name,
                label: listItem.name,
              };
            });
          }
        });
    });
  }

  fetchTerminatorsMapDropdownList() {
    this.envelopeService.getTerminatorsMapList().subscribe((data: any) => {
      const formControllers = ['segTerm', 'eleTerm', 'subEleTerm', 'releaseCharacter'];
      (this.formFields['OUTBOUND_ENVELOPE'] || [])
        .forEach(item => {
          if (formControllers.indexOf(item.formControlName) !== -1) {
            item.options = data.map(listItem => {
              return {
                value: listItem.key,
                label: listItem.value,
              };
            });
          }
        });
    });
  }

  bindValueChangesSubscription(field, callback) {
    const subscription = field.valueChanges.subscribe(value => {
      callback(value);
    });
    callback(field.value);
    this.valuesChangesSubscriptions.push(subscription);
  }
}
