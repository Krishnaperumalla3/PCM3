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

import {ChangeDetectorRef, Component, OnInit, ViewChild} from '@angular/core';
import {AbstractControl, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {adapterFields, partnerProtocols, protocolAPIS} from 'src/app/model/protocols.constants';
import {displayFields} from 'src/app/model/displayfields.map';
import {PartnerService} from 'src/app/services/partner.service';
import {HttpErrorResponse} from '@angular/common/http';
import {
  addValidators,
  frameFormObj,
  frameMultiFormObj,
  markFormFieldTouched,
  phoneNumValidation,
  toggleFormFieldEnable
} from 'src/app/utility';
import {FileSearchService} from 'src/app/services/file-search/file-search.service';
import {ToastrService} from 'ngx-toastr';
import {UploadModalComponent} from '../../shared/upload-modal/upload-modal.component';
import {MatDialog} from '@angular/material';
import {ActivatedRoute, Router} from '@angular/router';
import {environment} from '../../../environments/environment';
import {SwalComponent} from '@sweetalert2/ngx-sweetalert2';
import Swal from 'sweetalert2';
import {ViewDetailModalComponent} from 'src/app/shared/view-detail-modal/view-detail-modal.component';
import {AppComponent} from '../../app.component';
import {Store} from '@ngxs/store';
import {ModuleName} from '../../../store/layout/action/layout.action';
import {Subscription} from 'rxjs';
import 'rxjs/add/observable/interval';
import {TranslateService} from '@ngx-translate/core';
import {getUser} from "../../services/menu.permissions";

export const PARTNER_KEY = 'PARTNER_KEY';
const controlKeys = ['inMailBox', 'inDirectory', 'mailbox'];

@Component({
  selector: 'app-view-partner',
  templateUrl: './view-partner.component.html',
  styleUrls: ['./view-partner.component.scss']
})
export class ViewPartnerComponent implements OnInit {
  @ViewChild('invalidSwal') private invalidSwal: SwalComponent;
  hide = true;
  pkId: any;
  protocol: any;
  searchFilterCtrl: any = {};
  certFilterCtrl: any = {};
  private partnerDetails: any = null;
  activityDetails: any;
  displayedColumns: string[] = ['activityDt', 'userName', 'activity'];
  sortBy = 'activityDt';
  sortDir = 'desc';
  page: Number;
  size: Number;
  totalElements: Number;
  totalPages: Number;
  currentPage = 0;
  private valuesChangesSubscriptions: Subscription[] = [];

  qryParams: Object = {
    page: 0,
    sortBy: 'activityDt',
    pkId: this.pkId,
    sortDir: 'desc',
    size: 10
  };

  constructor(
    private fb: FormBuilder,
    private cd: ChangeDetectorRef,
    private service: PartnerService,
    private fservice: FileSearchService,
    private toastr: ToastrService,
    public dialog: MatDialog,
    private route: ActivatedRoute,
    private router: Router,
    private appComponent: AppComponent,
    private store: Store,
    public translate: TranslateService
  ) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.PARTNER'));
    this.appComponent.selected = 'partner';
    this.partnerFormGroup();
    this.route.params.subscribe(params => {
      this.pkId = params['pkId'];
      this.protocol = params['protocol'];
    });
    // this.sub = Observable.interval(10000).subscribe((val) => { });

  }

  partnerForm: FormGroup;
  protocolForm: FormGroup;
  uploadForm: FormGroup;
  adapterForm: FormGroup;
  adapterFields = [];
  subFieldEle = [];
  showSubFields = true;
  subForms = [];
  formObj: any;
  orgSelected = 'Yes';
  orgHandling: string[] = ['Yes', 'No'];

  sfgTypeSelected = 'HubConnToPartner';
  sfgTypeHandling: string[] = ['HubConnToPartner', 'PartnerConnToHub'];

  showProtocol = false;
  showAdapter = false;
  showUpload = false;

  protocolFields = [];
  poolingInterval: any;
  adapterNames: any;
  caCertMap: any;
  cipherSuitesMap: any;
  systemCertMap: any;
  kHostKeyMap: any;
  sshKeyPair: any;
  partnerFields = partnerProtocols.PartnerFields;
  proLst: any;
  protocolRef: any;
  userListMap: any;

  partnerFormGroup() {
    const formObj = frameFormObj(this.partnerFields);
    this.partnerForm = this.fb.group(formObj);
  }

  getPkIdDetails(protocol, pkId) {
    const protocolType = protocolAPIS[protocol];
    const url = protocolType === 'remote-ftp' || protocolType === 'remote-as2' ? `/pcm/si/partner/${protocolType}/${pkId}` : `${environment.CREATE_PARTNER_PROTOCOL}${protocolType}/${pkId}`;
    this.service.getPkIdDetails(url).subscribe(res => {
      this.partnerDetails = res;
      this.partnerForm.patchValue(res);
      this.adapterForm.patchValue(res);
      this.orgSelected = this.orgHandling[0] === res['hubInfo'] ? this.orgHandling[0] : this.orgHandling[1];
      this.sfgTypeSelected = this.sfgTypeHandling[0] === res['hubInfo'] ? this.sfgTypeHandling[0] : this.sfgTypeHandling[1];
      if (this.partnerForm.value.protocol === 'AS2' || this.partnerForm.value.protocol === 'SFG_CONNECT_DIRECT') {
        this.orgSelected = res['hubInfo'] === true ? 'Yes' : 'No';
        this.changeFileType({value: this.orgSelected});
      } else if (this.partnerForm.value.protocol === 'SFGFTP' ||
        this.partnerForm.value.protocol === 'SFGFTPS' || this.partnerForm.value.protocol === 'SFGSFTP') {
        this.sfgTypeSelected = res['hubInfo'] === true ? 'PartnerConnToHub' : 'HubConnToPartner';
        this.changeSFGFileType({value: this.sfgTypeSelected});
      }
      setTimeout(() => this.patchPKDetailsValue(res), 1000);
    }, (err) => {
      Swal.fire(
        this.translate.instant('SWEET_ALERT.PARTNER.TITLE'),
        err['error']['errorDescription'],
        'error'
      );
    });

  }

  patchPKDetailsValue(res) {
    let copyCipher = [];
    let authorizedUserKeys = [];
    let caCertName = [];
    let caCertificateNames = [];
    let knownHostKeyNames= [];
    if (res.cipherSuits && res.cipherSuites !== null) {
      res.cipherSuits.forEach(val => {
        copyCipher.push(val.cipherSuiteName);
      });
      delete res.cipherSuits;
      res['cipherSuits'] = copyCipher;
    }
    if (res.authorizedUserKeys && res.authorizedUserKeys !== []) {
      res.authorizedUserKeys.forEach(val => {
        authorizedUserKeys.push(val.name);
      });
      delete res.authorizedUserKeys;
      res['authorizedUserKeys'] = authorizedUserKeys;
    }
    if (res.cdMainFrameModel && res.cdMainFrameModel !== []) {
      Object.keys(res.cdMainFrameModel).forEach(key => {
        switch (key) {
          case 'dcbParam':
            res['dcbParam'] = res.cdMainFrameModel[key];
            break;
          case 'dnsName':
            res['dnsName'] = res.cdMainFrameModel[key];
            break;
          case 'unit':
            res['unit'] = res.cdMainFrameModel[key];
            break;
          case 'storageClass':
            res['storageClass'] = res.cdMainFrameModel[key];
            break;
          case 'space':
            res['space'] = res.cdMainFrameModel[key];
            break;
        }
      });
      delete res.cdMainFrameModel;
    }
    if (res.caCertName && res.caCertName !== null) {
      res.caCertName.forEach(val => {
        caCertName.push(val.caCertName);
      });
      delete res.caCertName;
      res['caCertName'] = caCertName;
    }
    if (res.caCertificateNames && res.caCertificateNames !== null) {
      res.caCertificateNames.forEach(val => {
        caCertificateNames = val.name
      });
      delete res.caCertificateNames;
      res['caCertificateNames'] = caCertificateNames;
    }
    if (res.knownHostKeyNames && res.knownHostKeyNames !== null) {
      res.knownHostKeyNames.forEach(val => {
        knownHostKeyNames = val.name
      });
      delete res.knownHostKeyNames;
      res['knownHostKeyNames'] = knownHostKeyNames;
    }

    if (res.protocol !== 'AS2' && res.caCertificate && res.caCertificate !== null) {
      res.caCertificate.forEach(val => {
        caCertName.push(val.caCertName);
      });
      delete res.caCertName;
      res['caCertificate'] = caCertName;
    }
    const {protocolForm, formObj, subForms} = this;
    /**
     *  const  protocolForm = this.protocolForm
     *  const  formObj = this.formObj
     */
    Object.keys(this.protocolForm.controls).forEach(key => {
      const control = this.protocolForm.get(key);
      if (control instanceof AbstractControl) {
        control.setValue(res[key]);
      }
    });

    if (this.subForms) {
      this.subForms.forEach(form => {
        form.patchValue(res);
      });
    }
  }

  changeValue(form) {
    if (form.value.protocol === 'AS2') {
      this.protocolForm.controls['profileName'].patchValue(form.value.profileName);
      // this.protocolForm.controls['profileId'].patchValue(form.value.profileId);
      // // tslint:disable-next-line:no-unused-expression
      // this.protocolForm.controls['emailId'] ? this.protocolForm.controls['emailId'].patchValue(this.partnerForm.value.emailId) : '';
    }
  }

  trimValue(formControl) {
    formControl.setValue(formControl.value.trimLeft());
  }

  ngOnInit() {
    this.partnerFields.map(fld => {
      this.searchFilterCtrl[fld.formControlName] = new FormControl();
    });
    if (!!this.pkId) {
      this.loadSearchResults(this.pkId);
    }
    this.getProtocolList();
    this.getAdapterNames();
    this.partnerForm.get('protocol').valueChanges.subscribe(val => {
      if ((val === 'SFGFTP' || val === 'SFGFTPS' || val === 'SFGSFTP')  && getUser().sfgEnabled === false) {
        Swal.fire({
          title: 'Partner',
          text: 'SFG Profiles , can\'t be created through Community Manager',
          type: 'warning',
        });
        this.showProtocol = false;
        return false;
      }
      this.formObj = {};
      this.protocolFields.forEach(fld => {
        this.protocolForm.removeControl(fld.formControlName);
      });
      this.protocol = val;
      this.orgSelected = 'Yes';
      this.sfgTypeSelected = 'HubConnToPartner';
      this.protocolFields = [];
      this.subForms = [];
      this.showProtocol = false;
      this.showAdapter = false;
      this.showUpload = false;
      if (val === '') {
        this.showProtocol = false;
        this.showAdapter = false;
        return false;
      } else {
        this.adapterFields = [...adapterFields[val] || []];
        const formObj = frameFormObj(this.adapterFields);
        this.adapterForm = this.fb.group(formObj);
        this.showAdapter = true;
        this.getPoolingInterval(this.adapterFields);
      }
      if (val === 'AS2') {
        [...partnerProtocols[val] || []].filter(fld => fld.selected === this.orgSelected).map(fld => {
          this.protocolFields = [...fld.subFields];
        });
      } else if (val === 'SFGFTP' || val === 'SFGFTPS' || val === 'SFGSFTP') {
        [...partnerProtocols[val] || []].filter(fld => fld.selected === this.sfgTypeSelected).map(fld => {
          this.protocolFields = [...fld.subFields];
        });
      } else {
        this.protocolFields = [...partnerProtocols[val] || []];
      }
      this.showProtocol = true;
      this.formObj = frameMultiFormObj(this.protocolFields);
      this.protocolForm = this.fb.group(this.formObj['formObj']);
      this.subFieldEle = this.protocolFields.filter(fld => fld.inputType === 'pcm-card').map(fld => {
        let cards = [...(fld.cards || [])];
        if (val === 'AS2') {
          cards = [...(fld.cards || [])].filter(card => card.selected.indexOf(this.orgSelected) > -1);
        } else if (val === 'SFGSFTP') {
          cards = [...(fld.cards || [])].filter(card => card.selected.indexOf(this.sfgTypeSelected) > -1);
        }
        return {
          ...fld,
          cards
        };
      });
      this.showSubFields = true;
      if (val === 'AS2') {
        this.showProtocol = false;
        if (this.orgSelected === 'Yes') {
          this.showSubFields = true;
          this.protocolFields = addValidators(this.protocolFields, controlKeys);
          this.protocolForm = this.fb.group(frameFormObj(this.protocolFields));
          this.showProtocol = true;
          if (this.partnerForm.value.profileName !== '') {
            this.protocolForm.controls['profileName'].patchValue(this.partnerForm.value.profileName);
            // this.protocolForm.controls['profileId'].patchValue(this.partnerForm.value.profileId);
            // this.protocolForm.controls['emailId'].patchValue(this.partnerForm.value.emailId);
          }
        } else {
          partnerProtocols[val].filter(fld => fld.selected === this.orgSelected).map(fld => {
            this.protocolFields = [...fld.subFields];
          });
          this.protocolFields = addValidators(this.protocolFields, controlKeys);
          this.protocolForm = this.fb.group(frameFormObj(this.protocolFields));
          this.showSubFields = true;
          this.showProtocol = true;
          if (this.partnerForm.value.profileName !== '') {
            this.protocolForm.patchValue(this.partnerForm.value.profileName);
            // this.protocolForm.patchValue(this.partnerForm.value.profileId);
          }
        }
      }

      if (val === 'SFGFTP') {
        this.showProtocol = false;
        if (this.sfgTypeSelected === 'HubConnToPartner') {
          this.protocolFields = addValidators(this.protocolFields, controlKeys);
          this.protocolForm = this.fb.group(frameFormObj(this.protocolFields));
          this.showProtocol = true;
          this.showSubFields = false;
          this.adapterForm.controls['adapterName'].setValue(this.adapterNames['sfgFtpClientAdapterName']);
        } else {
          partnerProtocols['SFGFTP'].filter(fld => fld.selected === this.sfgTypeSelected).map(fld => {
            this.protocolFields = [...fld.subFields];
          });
          this.protocolFields = addValidators(this.protocolFields, controlKeys);
          this.protocolForm = this.fb.group(frameFormObj(this.protocolFields));
          this.showProtocol = true;
          this.showSubFields = false;
          this.adapterForm.controls['adapterName'].setValue(this.adapterNames['sfgFtpServerAdapterName']);
        }
      }

      if (val === 'SFGFTPS') {
        // this.subForms = [];
        this.showProtocol = false;
        if (this.sfgTypeSelected === 'HubConnToPartner') {
          this.protocolForm = this.fb.group(frameFormObj(this.protocolFields));
          this.showProtocol = true;
          this.showSubFields = true;
          this.adapterForm.controls['adapterName'].setValue(this.adapterNames['sfgFtpsClientAdapterName']);
        } else {
          partnerProtocols['SFGFTPS'].filter(fld => fld.selected === this.sfgTypeSelected).map(fld => {
            this.protocolFields = [...fld.subFields];
          });
          this.protocolForm = this.fb.group(frameFormObj(this.protocolFields));
          this.showProtocol = true;
          this.showSubFields = false;
          this.adapterForm.controls['adapterName'].setValue(this.adapterNames['sfgFtpsServerAdapterName']);
        }
      }

      if (val === 'SFGSFTP') {
        this.subForms = [];
        this.showProtocol = false;
        if (this.sfgTypeSelected === 'HubConnToPartner') {
          this.protocolForm = this.fb.group(frameFormObj(this.protocolFields));
          this.showProtocol = true;
          this.showSubFields = true;
          this.adapterForm.controls['adapterName'].setValue(this.adapterNames['sfgSftpClientAdapterName']);
        } else {
          [...partnerProtocols[val] || []].filter(fld => fld.selected === this.sfgTypeSelected).map(fld => {
            this.protocolFields = [...fld.subFields];
          });
          this.protocolForm = this.fb.group(frameFormObj(this.protocolFields));
          this.showProtocol = true;
          this.showSubFields = true;
          this.adapterForm.controls['adapterName'].setValue(this.adapterNames['sfgSftpServerAdapterName']);
        }
      }

      const adapterNameControl = this.adapterForm.controls['adapterName'];
      switch (val) {
        case 'SFG_CONNECT_DIRECT':
          adapterNameControl.setValue(this.adapterNames['cdClientAdapterName']);
          this.getCaCertMap(this.protocolFields, val);
          this.getCipherSuites(this.protocolFields);
          break;
        case 'CONNECT_DIRECT':
          adapterNameControl.setValue(this.adapterNames['cdClientAdapterName']);
          this.getCaCertMap(this.protocolFields, val);
          this.getCipherSuites(this.protocolFields);
          break;
        case 'FileSystem':
          adapterNameControl.setValue(this.adapterNames['fsAdapter']);
          break;
        case 'FTP':
          adapterNameControl.setValue(this.adapterNames['ftpServerAdapterName']);
          break;
        case 'FTPS':
          adapterNameControl.setValue(this.adapterNames['ftpsServerAdapterName']);
          this.getCaCertMap(this.protocolFields, val);
          break;
        case 'SFTP':
          adapterNameControl.setValue(this.adapterNames['sftpServerAdapterName']);
          this.getKhostKeyMap(this.protocolFields);
          this.getSshKeyPair(this.protocolFields);
          break;
        case 'HTTP':
          adapterNameControl.setValue(this.adapterNames['httpServerAdapterName']);
          break;
        case 'HTTPS':
          adapterNameControl.setValue(this.adapterNames['httpsServerAdapterName']);
          this.getCaCertMap(this.protocolFields, val);
          break;
        case 'MQ':
          adapterNameControl.setValue(this.adapterNames['mqAdapterName']);
          break;
        case 'SFGFTP':
          adapterNameControl.setValue(this.adapterNames['sfgFtpClientAdapterName']);
          break;
        case 'SFGFTPS':
          adapterNameControl.setValue(this.adapterNames['sfgFtpsClientAdapterName']);
          this.getCaCertMap(this.protocolFields, val);
          break;
        case 'SFGSFTP':
          adapterNameControl.setValue(this.adapterNames['sfgSftpClientAdapterName']);
          this.getSshKeyPair(this.protocolFields);
          this.getKhostKeyMap(this.protocolFields);
          break;
        case 'Webservice':
          adapterNameControl.setValue(this.adapterNames['wsServerAdapterName']);
          this.getCaCertMap(this.protocolFields, val);
          break;
      }

      this.showProtocol = true;

      if (val === 'ExistingConnection') {
        this.protocolForm.get('ecProtocol').valueChanges.subscribe(key => {
          if (key !== '') {
            this.protocolForm.get('ecProtocolReference').setValue('');
            this.service.getPartnersByProtocol(key).subscribe(res => {
              this.protocolRef = res;
              (this.protocolFields || []).forEach(field => {
                if (field.formControlName === 'ecProtocolReference') {
                  field.options = this.protocolRef.map(opt => {
                    return {
                      label: opt.value,
                      value: opt.key
                    };
                  });
                }
              });
            });
          }
        });
      }

      // tslint:disable-next-line:max-line-length
      if (val === 'AS2') {
        this.getSystemOrTrustedCertMap(this.protocolFields);
      }

      this.protocolFields.map(fld => {
        this.searchFilterCtrl[fld.formControlName] = new FormControl();
      });

      this.adapterFields.map(fld => {
        this.searchFilterCtrl[fld.formControlName] = new FormControl();
      });

      if (this.subFieldEle.length > 0) {
        this.subFieldEle = this.subFieldEle[0]['cards'];
        this.formObj['subFormObj'].forEach(forms => {
          this.subForms.push(this.fb.group(forms.subForm));
        });
        this.subFieldEle.map(fld => {
          fld['subFields'].filter(item => {
            this.certFilterCtrl[item.formControlName] = new FormControl();
          });
        });
      }

      const operatingSystemField = this.protocolForm.get('operatingSystem');
      if (operatingSystemField) {
        this.bindValueChangesSubscription(operatingSystemField, value => {
          const dcb = this.protocolForm.get('dcbParam');
          const dsn = this.protocolForm.get('dnsName');
          const unit = this.protocolForm.get('unit');
          const storageClass = this.protocolForm.get('storageClass');
          const space = this.protocolForm.get('space');
          toggleFormFieldEnable(dcb, value === 'Mainframe(Z/OS)', true);
          toggleFormFieldEnable(dsn, value === 'Mainframe(Z/OS)', true);
          toggleFormFieldEnable(unit, value === 'Mainframe(Z/OS)', true);
          toggleFormFieldEnable(storageClass, value === 'Mainframe(Z/OS)', true);
          toggleFormFieldEnable(space, value === 'Mainframe(Z/OS)', true);
        });
      }
      const secureField = this.protocolForm.get('secure');
      const securePlus = this.protocolForm.get('securePlus');
      if (secureField) {
        this.bindValueChangesSubscription(secureField, value => {
          const caCert = this.subForms['0'].get('caCertName');
          const cipher = this.subForms['0'].get('cipherSuits');
          toggleFormFieldEnable(caCert, value === true, true);
          toggleFormFieldEnable(cipher, value === true, true);
        });
      }
      if (securePlus) {
        this.bindValueChangesSubscription(securePlus, value => {
          const caCert = this.subForms['0'].get('caCertificate');
          const cipher = this.subForms['0'].get('cipherSuits');
          toggleFormFieldEnable(caCert, value === true, true);
          toggleFormFieldEnable(cipher, value === true, true);
        });
      }
    });
  }

  getProtocolList() {
    this.fservice.getProtocolList().subscribe(res => {
      this.proLst = res;
      this.partnerFields = this.partnerFields.map(val => {
        if (val.formControlName === 'protocol') {
          val.options = this.proLst.map(opt => {
            return {
              label: opt.value,
              value: opt.key
            };
          });
        }
        return val;
      });

      if (!!this.protocol && !!this.pkId) {
        this.getPkIdDetails(this.protocol, this.pkId);
      }

      for (const prtcl in partnerProtocols) {
        if (partnerProtocols[prtcl]) {
          partnerProtocols[prtcl].forEach(field => {
            if (field.formControlName === 'ecProtocol') {
              field.options = this.proLst.map(opt => {
                return {
                  label: opt.value,
                  value: opt.key
                };
              });
            }
          });
        }
      }
    });
  }

  getPoolingInterval(formFields) {
    this.service.getPoolingInterval().subscribe(resp => {
      // const pollingInt: any = resp;
      this.poolingInterval = resp;
      formFields.forEach(field => {
        if (field.formControlName === 'poolingInterval') {
          if ((this.partnerForm.value.protocol === 'SFGFTP' || this.partnerForm.value.protocol === 'SFGFTPS' || this.partnerForm.value.protocol === 'SFGSFTP') && this.sfgTypeSelected === 'HubConnToPartner') {
            field.options = this.poolingInterval.map(opt => {
              return {
                label: opt.interval,
                value: opt.pkId
              };
            }).filter(type => (type.value !== 'ON'));
          } else {
            field.options = this.poolingInterval.map(opt => {
              return {
                label: opt.interval,
                value: opt.pkId
              };
            });
          }

        }
      });
    });
  }

  getKhostKeyMap(formFields) {
    this.service.getSSHkhostList().subscribe(res => {
      this.kHostKeyMap = res;
      const controls = ['knownHostKey', 'knownHostKeyNames'];
      formFields.forEach(field => {
        if (field.inputType === 'pcm-card') {
          field.cards = field.cards.map(card => {
            if (card.title === 'CERTIFICATE.CERT_TITL') {
              card.subFields = card.subFields.map(subField => {
                if (controls.indexOf(subField.formControlName) > -1) {
                  subField.options = [{label: 'Select', value: ''}];
                  this.kHostKeyMap.map(opt => {
                    subField.options.push({
                      label: opt.name,
                      value: opt.name
                    });
                  });
                }
                return subField;
              });
            }
            return card;
          });

        } else if (field.formControlName === 'knownHostKey') {
          field.options = [{label: 'Select', value: ''}];
          this.kHostKeyMap.map(opt => {
            field.options.push({
              label: opt.name,
              value: opt.name
            });
          });
        }
      });
    }, (err) => {
      console.log(err);
    });
  }

  getSshKeyPair(formFields) {
    this.service.getSshKeyPair().subscribe(res => {
      this.sshKeyPair = res;
      const controls = ['userIdentityKey', 'sshIdentityKeyName'];
      formFields.forEach(field => {
        if (field.inputType === 'pcm-card') {
          field.cards = field.cards.map(card => {
            if (card.title === 'CERTIFICATE.CERT_TITL') {
              card.subFields = card.subFields.map(subField => {
                if (controls.indexOf(subField.formControlName) > -1) {
                  subField.options = [{label: 'Select', value: ''}];
                  this.sshKeyPair.map(opt => {
                    subField.options.push({
                      label: opt.name,
                      value: opt.name
                    });
                  });
                }
                return subField;
              });
            }
            return card;
          });

        } else if (field.formControlName === 'userIdentityKey') {
          field.options = [{label: 'Select', value: ''}];
          this.sshKeyPair.map(opt => {
            field.options.push({
              label: opt.name,
              value: opt.name
            });
          });
        }
      });
    }, (err) => {
      console.log(err);
    });
  }

  getCaCertMap(formFields, protocol) {
    this.service.getCaCertMap().subscribe(res => {
      this.caCertMap = res;
      const controls = ['caCertificate', 'certificate', 'caCertName'];
      formFields.forEach(field => {
        if (field && field.inputType === 'pcm-card') {
          field.cards = field.cards.map(card => {
            if (card.title === 'CERTIFICATE.CERT_TITL') {
              card.subFields = card.subFields.map(subField => {
                if (subField.formControlName === 'certificateId' || subField.formControlName === 'caCertificateNames') {
                  subField.options = [{label: 'Select', value: ''}];
                  this.caCertMap.map(opt => {
                    subField.options.push({
                      label: opt.value,
                      value: protocol === 'SFGFTPS' ? opt.value : opt.key
                    });
                  });
                } else if (controls.indexOf(subField.formControlName) > -1) {
                  subField.options = [];
                  this.caCertMap.map(opt => {
                    subField.options.push({
                      label: opt.value,
                      value: opt.value
                    });
                  });
                }
                return subField;
              });
            }
            return card;
          });
        } else if (field.formControlName === 'certificateId') {
          field.options = [{label: 'Select', value: ''}];
          this.caCertMap.map(opt => {
            field.options.push({
              label: opt.value,
              value: opt.key
            });
          });
        }
      });
    }, (err) => {
      console.log(err);
    });
  }

  getCipherSuites(formFields) {
    this.service.getCipherSuitesMap().subscribe(res => {
      this.cipherSuitesMap = res['content'];
      formFields.filter(field => {
        if (field && field.inputType === 'pcm-card') {
          field.cards = field.cards.map(card => {
            if (card.title === 'CERTIFICATE.CERT_TITL') {
              card.subFields = card.subFields.map(subField => {
                if (subField.formControlName === 'cipherSuits') {
                  subField.options = [{label: 'Select', value: ''}];
                  this.cipherSuitesMap.map(opt => {
                    subField.options.push({
                      label: opt.value,
                      value: opt.value
                    });
                  });
                }
                return subField;
              });
            }
            return card;
          });
        }
      });
    })
  }

  getSystemOrTrustedCertMap(formFields) {
    const url = this.orgSelected === 'Yes' ? environment.SYSTEM_CERT_MAP : environment.GET_TRUSTED_MAP;
    this.service.getCertMap(url).subscribe(res => {
      this.systemCertMap = res;
      const controls = ['exchangeCertificate', 'signingCertification'];
      formFields.forEach(field => {
        if (field.inputType === 'pcm-card') {
          field.cards = field.cards.map(card => {
            if (card.title === 'CERTIFICATE.CERT_TITL') {
              card.subFields = card.subFields.map(subField => {
                if (controls.indexOf(subField.formControlName) > -1) {
                  subField.options = [];
                  this.systemCertMap.map(opt => {
                    subField.options.push({
                      label: !!opt.value ? opt.value : opt.name,
                      value: !!opt.key ? opt.key : opt.name
                    });
                  });
                }
                return subField;
              });
            }
            return card;
          });
        }
      });

    });
  }

  getSshUserList(formFields) {
    this.service.getSshUserMap().subscribe(res => {
      this.userListMap = res['content'];
      const controls = ['authorizedUserKeys'];
      formFields.forEach(field => {
        if (field.inputType === 'pcm-card') {
          field.cards = field.cards.map(card => {
            if (card.title === 'CERTIFICATE.CERT_TITL') {
              card.subFields = card.subFields.map(subField => {
                if (controls.indexOf(subField.formControlName) > -1) {
                  // subField.options = [{label: 'Select', value: ''}];
                  this.userListMap.map(opt => {
                    subField.options.push({
                      label: opt.name,
                      value: opt.name
                    });
                  });
                }
                return subField;
              });
            }
            return card;
          });

        }
      });
    }, (err) => {
      console.log(err);
    });
  }

  getAdapterNames() {
    this.service.getAdapterNames().subscribe(res => {
      this.adapterNames = res;
    }, (err) => {
      console.log(err);
    });
  }

  changeFileType(e) {
    this.orgSelected = e.value;
    // this.protocolFields.forEach(fld => {
    //   this.protocolForm.removeControl(fld.formControlName);
    // });
    // this.protocolFields = [];
    if (this.partnerForm.value.protocol === 'AS2') {
      this.protocolFields = [];
      this.subFieldEle = [];
      this.subForms = [];
      this.showProtocol = false;
      if (this.orgSelected === 'Yes') {
        this.showSubFields = true;
        partnerProtocols['AS2'].filter(fld => fld.selected === this.orgSelected).map(fld => {
          this.protocolFields = [...fld.subFields];
        });
        this.formObj = frameMultiFormObj(this.protocolFields);
        this.protocolFields = addValidators(this.protocolFields, controlKeys);
        this.protocolForm = this.fb.group(frameFormObj(this.protocolFields));
        this.showProtocol = true;
        this.subFieldEle = this.protocolFields.filter(fld => fld.inputType === 'pcm-card').map(fld => {
          const cards = [...(fld.cards || [])].filter(card => card.selected.indexOf(this.orgSelected) > -1);
          return {
            ...fld,
            cards
          };
        });
        if (this.partnerForm.value.profileName !== '') {
          this.protocolForm.controls['profileName'].patchValue(this.partnerForm.value.profileName);
          // this.protocolForm.controls['profileId'].patchValue(this.partnerForm.value.profileId);
          // this.protocolForm.controls['emailId'].patchValue(this.partnerForm.value.emailId);
        }

      } else {
        partnerProtocols['AS2'].filter(fld => fld.selected === this.orgSelected).map(fld => {
          this.protocolFields = [...fld.subFields];
        });
        this.formObj = frameMultiFormObj(this.protocolFields);
        this.protocolFields = addValidators(this.protocolFields, controlKeys);
        this.protocolForm = this.fb.group(frameFormObj(this.protocolFields));
        this.showProtocol = true;
        this.showSubFields = true;
        this.subFieldEle = this.protocolFields.filter(fld => fld.inputType === 'pcm-card');
        if (this.partnerForm.value.profileName !== '') {
          this.protocolForm.controls['profileName'].patchValue(this.partnerForm.value.profileName);
          // this.protocolForm.controls['profileId'].patchValue(this.partnerForm.value.profileId);
        }
      }
      if (this.partnerDetails != null && this.protocol === this.partnerDetails['protocol']) {
        this.protocolForm.patchValue(this.partnerDetails);
      }
      if (this.subFieldEle.length > 0) {
        this.subFieldEle = this.subFieldEle[0]['cards'];
        this.formObj['subFormObj'].forEach(forms => {
          const form = this.fb.group(forms.subForm);
          if (this.partnerDetails != null) {
            form.patchValue(this.partnerDetails);
          }
          this.subForms.push(form);
        });
      }
      this.getSystemOrTrustedCertMap(this.protocolFields);
      this.getCaCertMap(this.protocolFields, this.partnerForm.value.protocol);
      this.showSubFields = true;
      this.subFieldEle.map(fld => {
        fld['subFields'].filter(item => {
          this.certFilterCtrl[item.formControlName] = new FormControl();
        });
      });
    } else if (this.partnerForm.value.protocol === 'SFG_CONNECT_DIRECT') {
      this.protocolFields = [...partnerProtocols[this.partnerForm.value.protocol] || []];
      if (this.partnerDetails != null) {
        this.protocolForm.patchValue(this.partnerDetails);
      }
    }
  }

  changeSFGFileType(e) {
    const protocol = this.partnerForm.value.protocol;
    this.subForms = [];
    this.showProtocol = false;
    this.sfgTypeSelected = e.value;
    if (protocol === 'SFGFTP') {
      if (this.sfgTypeSelected === 'HubConnToPartner') {
        partnerProtocols[protocol].filter(fld => fld.selected === this.sfgTypeSelected).map(fld => {
          this.protocolFields = [...fld.subFields];
        });
        this.protocolFields = addValidators(this.protocolFields, controlKeys);
        this.protocolForm = this.fb.group(frameFormObj(this.protocolFields));
        this.showProtocol = true;
        this.showSubFields = false;
        this.getPoolingInterval(this.adapterFields)
        this.adapterForm.controls['adapterName'].setValue(this.adapterNames['sfgFtpClientAdapterName']);
      } else {
        partnerProtocols[protocol].filter(fld => fld.selected === this.sfgTypeSelected).map(fld => {
          this.protocolFields = [...fld.subFields];
        });
        this.formObj = frameMultiFormObj(this.protocolFields);
        this.protocolFields = addValidators(this.protocolFields, controlKeys);
        this.protocolForm = this.fb.group(frameFormObj(this.protocolFields));
        this.showProtocol = true;
        this.showSubFields = false;
        this.getPoolingInterval(this.adapterFields)
        this.adapterForm.controls['adapterName'].setValue(this.adapterNames['sfgFtpServerAdapterName']);
      }
    } else if (protocol === 'SFGFTPS') {
      this.showProtocol = false;
      this.sfgTypeSelected = e.value;
      if (this.sfgTypeSelected === 'HubConnToPartner') {
        partnerProtocols[protocol].filter(fld => fld.selected === this.sfgTypeSelected).map(fld => {
          this.protocolFields = [...fld.subFields];
        });
        this.formObj = frameMultiFormObj(this.protocolFields);
        this.protocolForm = this.fb.group(frameFormObj(this.protocolFields));
        this.showProtocol = true;
        this.showSubFields = true;
        this.subFieldEle = this.protocolFields.filter(fld => fld.inputType === 'pcm-card').map(fld => {
          const cards = [...(fld.cards || [])].filter(card => card.selected.indexOf(this.sfgTypeSelected) > -1);
          return {
            ...fld,
            cards
          };
        });
        // this.getCaCertMap(this.protocolFields, this.partnerForm.value.protocol);
        this.getPoolingInterval(this.adapterFields);
        this.adapterForm.controls['adapterName'].setValue(this.adapterNames['sfgFtpsClientAdapterName']);
      } else {
        this.protocolFields = [];
        partnerProtocols[protocol].filter(fld => fld.selected === this.sfgTypeSelected).map(fld => {
          this.protocolFields = [...fld.subFields];
        });
        this.formObj = frameMultiFormObj(this.protocolFields);
        this.protocolForm = this.fb.group(frameFormObj(this.protocolFields));
        this.showProtocol = true;
        this.showSubFields = false;
        this.subFieldEle = [];
        this.getPoolingInterval(this.adapterFields);
        this.adapterForm.controls['adapterName'].setValue(this.adapterNames['sfgFtpsServerAdapterName']);
      }
    } else if (protocol === 'SFGSFTP') {
      this.subFieldEle = [...[]];
      this.showProtocol = false;
      this.sfgTypeSelected = e.value;
      if (this.sfgTypeSelected === 'HubConnToPartner') {
        partnerProtocols[protocol].filter(fld => fld.selected === this.sfgTypeSelected).map(fld => {
          this.protocolFields = [...fld.subFields];
        });
        this.formObj = frameMultiFormObj(this.protocolFields);
        this.protocolForm = this.fb.group(frameFormObj(this.protocolFields));
        this.showProtocol = true;
        this.showSubFields = true;
        this.subFieldEle = [...this.protocolFields || []].filter(fld => fld.inputType === 'pcm-card').map(fld => {
          const cards = [...(fld.cards || [])].filter(card => card.selected.indexOf(this.sfgTypeSelected) > -1);
          return {
            ...fld,
            cards
          };
        });
        this.getPoolingInterval(this.adapterFields)
        this.adapterForm.controls['adapterName'].setValue(this.adapterNames['sfgSftpClientAdapterName']);
      } else {
        this.subFieldEle = [...[]];
        partnerProtocols[protocol].filter(fld => fld.selected === this.sfgTypeSelected).map(fld => {
          this.protocolFields = [...fld.subFields];
        });
        this.formObj = frameMultiFormObj(this.protocolFields);
        this.getSshUserList(this.protocolFields);
        this.protocolForm = this.fb.group(frameFormObj(this.protocolFields));
        this.showProtocol = true;
        this.showSubFields = true;
        this.subFieldEle = [...this.protocolFields || []].filter(fld => fld.inputType === 'pcm-card').map(fld => {
          const cards = [...(fld.cards || [])].filter(card => card.selected.indexOf(this.sfgTypeSelected) > -1);
          return {
            ...fld,
            cards
          };
        });
        this.getPoolingInterval(this.adapterFields)
        this.adapterForm.controls['adapterName'].setValue(this.adapterNames['sfgSftpServerAdapterName']);
      }
      // this.protocolForm.get('preferredAuthenticationType').valueChanges.subscribe(fld => {
      //   if (fld === 'PASSWORD') {
      //     this.protocolForm.controls['password'].setErrors({'required': true});
      //   } else {
      //     this.protocolForm.controls['password'].setErrors(null);
      //   }
      // });
    }

    if (this.partnerDetails != null && this.protocol === this.partnerDetails['protocol']) {
      this.protocolForm.patchValue(this.partnerDetails);
      this.adapterForm.patchValue(this.partnerDetails);
    }

    if (this.subFieldEle.length > 0) {
      this.subFieldEle = this.subFieldEle[0]['cards'];
      this.formObj['subFormObj'].forEach(forms => {
        const form = this.fb.group(forms.subForm);
        if (this.partnerDetails != null) {
          form.patchValue(this.partnerDetails);
        }
        this.subForms.push(form);
      });

      this.showSubFields = true;
      this.subFieldEle.map(fld => {
        fld['subFields'].filter(item => {
          this.certFilterCtrl[item.formControlName] = new FormControl();
        });
      });
    }
  }

  getFieldName(field) {
    return this.translate.instant(displayFields[field.formControlName] ? displayFields[field.formControlName] : field.placeholder);
  }

  uploadCertificate(item) {
    const dialogRef = this.dialog.open(UploadModalComponent, {
      width: '45%',
      data: {
        title: 'Upload File',
        body: {
          item,
          selected: this.orgSelected,
          type: this.partnerForm.value.protocol,
        }
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result && !result.error) {
        Swal.fire({
          title: this.translate.instant('SWEET_ALERT.CERTIFICATE.TITLE'),
          text: result['response']['statusMessage'],
          type: 'success',
          showConfirmButton: false,
          timer: 2000
        });

        switch (this.partnerForm.value.protocol) {
          case 'AS2':
            this.getSystemOrTrustedCertMap(this.protocolFields);
            this.getCaCertMap(this.protocolFields, this.partnerForm.value.protocol);
            break;
          case 'SFTP':
            this.getKhostKeyMap(this.protocolFields);
            this.getSshKeyPair(this.protocolFields);
            break;
          case 'FTPS':
            this.getCaCertMap(this.protocolFields, this.partnerForm.value.protocol);
            break;
          case 'HTTPS':
            this.getCaCertMap(this.protocolFields, this.partnerForm.value.protocol);
            break;
          case 'SFGFTPS':
            this.getCaCertMap(this.protocolFields, this.partnerForm.value.protocol);
            break;
          case 'SFGSFTP':
            if (this.sfgTypeSelected === 'HubConnToPartner') {
              this.getSshKeyPair(this.protocolFields);
              this.getKhostKeyMap(this.protocolFields);
            } else {
              this.getSshUserList(this.protocolFields);
            }
            break;
          case 'Webservice':
            this.getCaCertMap(this.protocolFields, this.partnerForm.value.protocol);
            break;
          case 'SFG_CONNECT_DIRECT':
            this.getCaCertMap(this.protocolFields, this.partnerForm.value.protocol);
            this.getCipherSuites(this.protocolFields);
            break;
          case 'CONNECT_DIRECT':
            this.getCaCertMap(this.protocolFields, this.partnerForm.value.protocol);
            this.getCipherSuites(this.protocolFields);
            break;
        }
      }
    });
  }

  submitPartner() {
    if (this.partnerForm.value.protocol === 'SFTP' && this.protocolForm.value.sshAuthentication === 'PASSWORD' && !this.protocolForm.value.password) {
      this.protocolForm.controls['password'].setErrors({'required': true});
      markFormFieldTouched(this.protocolForm);
      setTimeout(this.invalidSwal.show, 100);
      return false;
    } else if (this.partnerForm.value.protocol === 'SFTP' && this.protocolForm.value.sshAuthentication !== 'PASSWORD') {
      this.protocolForm.controls['password'].setErrors(null);
    }

    if (this.partnerForm.value.protocol === 'SFGSFTP' && this.sfgTypeSelected === 'HubConnToPartner' && this.protocolForm.value.preferredAuthenticationType === 'PASSWORD' && !this.protocolForm.value.password) {
      this.protocolForm.controls['password'].setErrors({'required': true});
      markFormFieldTouched(this.protocolForm);
      setTimeout(this.invalidSwal.show, 100);
      return false;
    } else if (this.partnerForm.value.protocol === 'SFGSFTP' && this.sfgTypeSelected === 'HubConnToPartner' && this.protocolForm.value.preferredAuthenticationType !== 'PASSWORD') {
      this.protocolForm.controls['password'].setErrors(null);
    } else if (this.partnerForm.value.protocol === 'SFGSFTP' && this.sfgTypeSelected === 'PartnerConnToHub' && this.protocolForm.value.preferredAuthenticationType === 'PASSWORD' && !this.protocolForm.value.password) {
      this.protocolForm.controls['password'].setErrors({'required': true});
    }
    const slash = ['inMailBox', 'outMailBox', 'inDirectory', 'outDirectory', 'mailbox'];
    const isInvalid = this.partnerForm.invalid || this.protocolForm.invalid || this.adapterForm.invalid;
    if (this.subForms.length > 0) {
      !this.subForms.filter(frm => {
        markFormFieldTouched(frm);
        return frm.invalid;
      }).length;

    }
    markFormFieldTouched(this.partnerForm);
    markFormFieldTouched(this.protocolForm);
    markFormFieldTouched(this.adapterForm);
    let hasValidSlashForDirectories = true;
    if (isInvalid) {
      setTimeout(this.invalidSwal.show, 100);
      return false;
    }
    Object.keys(this.protocolForm.value).filter(k => slash.indexOf(k) !== -1).forEach(k => {
      const value = this.protocolForm.value[k];
      if ((!!value && value.charAt(0) !== '/') && this.partnerForm.value.protocol !== 'FileSystem') {
        hasValidSlashForDirectories = false;
      }
    });

    if (this.partnerForm.value['emailId'] && this.partnerForm.value['emailId'] !== '') {
      // tslint:disable-next-line:max-line-length
      const pattern = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9\-]+\.)+([a-zA-Z0-9\-\.]+)+([,]([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9\-]+\.)+([a-zA-Z0-9\-\.]+))*$/;
      if (!pattern.test(this.partnerForm.value['emailId'])) {
        markFormFieldTouched(this.partnerForm);
        Swal.fire(
          this.translate.instant('SWEET_ALERT.PARTNER.INVLD_EML.TITLE'),
          this.translate.instant('SWEET_ALERT.PARTNER.INVLD_EML.BODY'),
          'error'
        );
        return false;
      }
    }

    if (this.partnerForm.value.protocol === 'AS2' && this.protocolForm.value['emailId'] && this.protocolForm.value['emailId'] !== '') {
      // tslint:disable-next-line:max-line-length
      const pattern = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9\-]+\.)+([a-zA-Z0-9\-\.]+)+([,]([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9\-]+\.)+([a-zA-Z0-9\-\.]+))*$/;
      if (!pattern.test(this.protocolForm.value['emailId'])) {
        markFormFieldTouched(this.protocolForm);
        Swal.fire(
          this.translate.instant('SWEET_ALERT.PARTNER.INVLD_EML.TITLE'),
          this.translate.instant('SWEET_ALERT.PARTNER.INVLD_EML.BODY'),
          'error'
        );
        return false;
      }
    }

    if (this.partnerForm.value['phone'] && this.partnerForm.value['phone'] !== '') {
      if (!phoneNumValidation(this.partnerForm.value['phone'])) {
        markFormFieldTouched(this.partnerForm);
        Swal.fire(
          this.translate.instant('SWEET_ALERT.PARTNER.INVLD_PHONE.TITLE'),
          this.translate.instant('SWEET_ALERT.PARTNER.INVLD_PHONE.BODY'),
          'error'
        );
        return false;
      }
    }

    if (this.subForms.length > 0) {
      let isValid = false;
      this.subForms.forEach(frm => {
        if (frm.status === 'INVALID') {
          isValid = true;
          markFormFieldTouched(frm);
        }
      });
      if (isValid) {
        setTimeout(this.invalidSwal.show, 100);
        return false;
      }
    }

    if (!hasValidSlashForDirectories) {
      Swal.fire(
        this.translate.instant('SWEET_ALERT.PARTNER.DIREC.TITLE'),
        this.translate.instant('SWEET_ALERT.PARTNER.DIREC.BODY'),
        'error'
      );
      return false;
    }

    if (!!this.protocol && !!this.pkId) {
      const prtcl = protocolAPIS[this.partnerForm.value.protocol] ? protocolAPIS[this.partnerForm.value.protocol] : (this.partnerForm.value.protocol).toLocaleLowerCase();
      const partner = this.partnerForm ? this.partnerForm.value : {};
      partner['pkId'] = this.pkId;
      const protocol = this.protocolForm ? this.protocolForm.value : {};
      const adapter = this.adapterForm ? this.adapterForm.value : {};
      const upload = this.uploadForm ? this.uploadForm.value : {};


      let req = {...partner, ...protocol, ...adapter, ...upload};

      if (req.operatingSystem && req.operatingSystem === 'Mainframe(Z/OS)') {
        req['cdMainFrameModel'] = {
          dcbParam: req.dcbParam,
          dnsName: req.dnsName,
          unit: req.unit,
          storageClass: req.storageClass,
          space: req.space
        };
        delete req.dcbParam;
        delete req.dnsName;
        delete req.unit;
        delete req.storageClass;
        delete req.space
      } else {
        if (partner['protocol'] === 'SFG_CONNECT_DIRECT') {
          req['cdMainFrameModel'] = {
            dcbParam: null,
            dnsName: null,
            unit: null,
            storageClass: null,
            space: null
          };
          delete req.dcbParam;
          delete req.dnsName;
          delete req.unit;
          delete req.storageClass;
          delete req.space
        }
      }
      if (partner['protocol'] === 'SFGFTP' || partner['protocol'] === 'SFGSFTP' || partner['protocol'] === 'SFGFTPS') {
        req = {...req, ...{hubInfo: this.sfgTypeSelected !== this.sfgTypeHandling[0]}};
        if (req.hubInfo === true) {
          req.createUserInSI = true;
        }
      } else if (partner['protocol'] === 'AS2' || partner['protocol'] === 'SFG_CONNECT_DIRECT') {
        req = {...req, ...{hubInfo: this.orgSelected === this.orgHandling[0]}};
      } else {
        req = {...req, ...{hubInfo: false}};
      }
      if (this.subForms.length > 0) {
        this.subForms.forEach(frm => {
          req = {...req, ...frm.value};
        });
      }
      let copyCipher = [];
      let authorizedUserKeys = [];
      let caCertName = [];
      let caCertificateNames = [];
      let knownHostKeyNames= [];
      if (req.cipherSuits) {
        req.cipherSuits.forEach(val => {
          copyCipher.push({cipherSuiteName: val});
        });
        delete req.cipherSuits;
        req['cipherSuits'] = copyCipher;
      } else if (!req.cipherSuits) {
        copyCipher.push({cipherSuiteName: null});
        delete req.cipherSuits;
        req['cipherSuits'] = copyCipher;
      }

      if (req.authorizedUserKeys) {
        req.authorizedUserKeys.forEach(val => {
          authorizedUserKeys.push({name: val});
        });
        delete req.authorizedUserKeys;
        req['authorizedUserKeys'] = authorizedUserKeys;
      } else if (!req.authorizedUserKeys) {
        authorizedUserKeys.push({name: null});
        delete req.authorizedUserKeys;
        req['authorizedUserKeys'] = authorizedUserKeys;
      }

      if(req.caCertificateNames) {
        caCertificateNames.push({name: req.caCertificateNames});
        delete req.caCertificateNames;
        req['caCertificateNames'] = caCertificateNames;
      } else if(req.caCertificateNames === '') {
        caCertificateNames.push({name: ''});
        delete req.caCertificateNames;
        req['caCertificateNames'] = caCertificateNames;
      }

      if(req.knownHostKeyNames) {
        knownHostKeyNames.push({name: req.knownHostKeyNames});
        delete req.knownHostKeyNames;
        req['knownHostKeyNames'] = knownHostKeyNames;
      } else if(!req.knownHostKeyNames) {
        knownHostKeyNames.push({name: ''});
        delete req.knownHostKeyNames;
        req['knownHostKeyNames'] = knownHostKeyNames;
      }

      if (req['protocol'] === 'SFG_CONNECT_DIRECT') {
        if (req.caCertName) {
          req.caCertName.forEach(val => {
            caCertName.push({caCertName: val});
          });
          delete req.caCertName;
          req['caCertName'] = caCertName;
        } else if (!req.caCertName) {
          caCertName.push({caCertName: null});
          delete req.caCertName;
          req['caCertName'] = caCertName;
        }
      }

      if (req['protocol'] === 'CONNECT_DIRECT') {
        if (req.caCertificate) {
          req.caCertificate.forEach(val => {
            caCertName.push({caCertName: val});
          });
          delete req.caCertificate;
          req['caCertificate'] = caCertName;
        } else if (!req.caCertificate) {
          caCertName.push({caCertName: null});
          delete req.caCertificate;
          req['caCertificate'] = caCertName;
        }
      }

      req.createMailBoxInSI = !!req.createMailBoxInSI;
      req.createUserInSI = !!req.createUserInSI;
      req.deleteAfterCollection = !!req.deleteAfterCollection;
      this.service.updatePartner(prtcl, req).subscribe(res => {
          sessionStorage.setItem(PARTNER_KEY, JSON.stringify(partner));
          this.router.navigate(['/pcm/partner/manage']);
          this.partnerForm.reset();
          this.protocolForm.reset();
          Swal.fire({
            title: this.translate.instant('SWEET_ALERT.PARTNER.TITLE'),
            text: res['statusMessage'],
            type: 'success',
            showConfirmButton: false,
            timer: 2000
          });
        },
        (err: HttpErrorResponse) => {
          Swal.fire(
            this.translate.instant('SWEET_ALERT.PARTNER.TITLE'),
            err['error']['errorDescription'],
            'error'
          );
        });
    } else {
      const prtcl = protocolAPIS[this.partnerForm.value.protocol] ? protocolAPIS[this.partnerForm.value.protocol] : (this.partnerForm.value.protocol).toLocaleLowerCase();
      const partner = this.partnerForm ? this.partnerForm.value : {};
      const protocol = this.protocolForm ? this.protocolForm.value : {};
      const adapter = this.adapterForm ? this.adapterForm.value : {};
      const uplaod = this.uploadForm ? this.uploadForm.value : {};

      let req = {...partner, ...protocol, ...adapter, ...uplaod};

      if (req.operatingSystem && req.operatingSystem === 'Mainframe(Z/OS)') {
        req['cdMainFrameModel'] = {
          dcbParam: req.dcbParam,
          dnsName: req.dnsName,
          unit: req.unit,
          storageClass: req.storageClass,
          space: req.space
        };
        delete req.dcbParam;
        delete req.dnsName;
        delete req.unit;
        delete req.storageClass;
        delete req.space
      } else {
        if (partner['protocol'] === 'SFG_CONNECT_DIRECT') {
          req['cdMainFrameModel'] = {
            dcbParam: null,
            dnsName: null,
            unit: null,
            storageClass: null,
            space: null
          };
          delete req.dcbParam;
          delete req.dnsName;
          delete req.unit;
          delete req.storageClass;
          delete req.space
        }
      }
      if ((partner['protocol'] === 'SFGFTP' || partner['protocol'] === 'SFGSFTP' || partner['protocol'] === 'SFGFTPS') && getUser().sfgEnabled === false){
        Swal.fire({
          title: 'Partner',
          text: 'SFG Profiles , can\'t be created through Community Manager.',
          type: 'warning'
        });
        return false;
      }
      if (partner['protocol'] === 'SFGFTP' || partner['protocol'] === 'SFGSFTP' || partner['protocol'] === 'SFGFTPS') {
        req = {...req, ...{hubInfo: this.sfgTypeSelected !== this.sfgTypeHandling[0]}};
      } else if (partner['protocol'] === 'AS2' || partner['protocol'] === 'SFG_CONNECT_DIRECT') {
        req = {...req, ...{hubInfo: this.orgSelected === this.orgHandling[0]}};
      } else {
        req = {...req, ...{hubInfo: false}};
      }
      if (this.subForms.length > 0) {
        this.subForms.forEach(frm => {
          req = {...req, ...frm.value};
        });
      }
      let copyCipher = [];
      let authorizedUserKeys = [];
      let caCertName = [];
      let caCertificateNames = [];
      let knownHostKeyNames = [];

      if (req.cipherSuits && req.cipherSuites !== null) {
        req.cipherSuits.forEach(val => {
          copyCipher.push({cipherSuiteName: val});
        });
        delete req.cipherSuits;
        req['cipherSuits'] = copyCipher;
      } else {
        copyCipher.push({cipherSuiteName: null});
        delete req.cipherSuits;
        req['cipherSuits'] = copyCipher;
      }

      if(req.caCertificateNames) {
        caCertificateNames.push({name: req.caCertificateNames});
        delete req.caCertificateNames;
        req['caCertificateNames'] = caCertificateNames;
      } else if(!req.caCertificateNames) {
        caCertificateNames.push({name: ''});
        delete req.caCertificateNames;
        req['caCertificateNames'] = caCertificateNames;
      }
      if(req.knownHostKeyNames) {
        knownHostKeyNames.push({name: req.knownHostKeyNames});
        delete req.knownHostKeyNames;
        req['knownHostKeyNames'] = knownHostKeyNames;
      } else if(!req.knownHostKeyNames) {
        knownHostKeyNames.push({name: ''});
        delete req.knownHostKeyNames;
        req['knownHostKeyNames'] = knownHostKeyNames;
      }


      if (req.authorizedUserKeys) {
        req.authorizedUserKeys.forEach(val => {
          authorizedUserKeys.push({name: val});
        });
        delete req.authorizedUserKeys;
        req['authorizedUserKeys'] = authorizedUserKeys;
      } else if (!req.authorizedUserKeys) {
        authorizedUserKeys.push({name: null});
        delete req.authorizedUserKeys;
        req['authorizedUserKeys'] = authorizedUserKeys;
      }

      if (req['protocol'] === 'SFG_CONNECT_DIRECT') {
        if (req.caCertName) {
          req.caCertName.forEach(val => {
            caCertName.push({caCertName: val});
          });
          delete req.caCertName;
          req['caCertName'] = caCertName;
        } else if (!req.caCertName) {
          caCertName.push({caCertName: null});
          delete req.caCertName;
          req['caCertName'] = caCertName;
        }
      }

      if (req['protocol'] === 'CONNECT_DIRECT') {
        if (req.caCertificate) {
          req.caCertificate.forEach(val => {
            caCertName.push({caCertName: val});
          });
          delete req.caCertificate;
          req['caCertificate'] = caCertName;
        } else if (!req.caCertificate) {
          caCertName.push({caCertName: null});
          delete req.caCertificate;
          req['caCertificate'] = caCertName;
        }
      }

      req.createMailBoxInSI = !!req.createMailBoxInSI;
      req.createUserInSI = !!req.createUserInSI;
      req.deleteAfterCollection = !!req.deleteAfterCollection;
      this.service.createPartner(prtcl, req).subscribe(res => {
          sessionStorage.setItem(PARTNER_KEY, JSON.stringify(partner));
          this.router.navigate(['/pcm/partner/manage']);
          this.partnerForm.reset();
          this.protocolForm.reset();
          Swal.fire({
            title: this.translate.instant('SWEET_ALERT.PARTNER.TITLE'),
            text: res['statusMessage'],
            type: 'success',
            showConfirmButton: false,
            timer: 2000
          });
        },
        (err: HttpErrorResponse) => {
          Swal.fire(
            this.translate.instant('SWEET_ALERT.PARTNER.TITLE'),
            err['error']['errorDescription'],
            'error'
          );
        });
    }
  }

  showPassword() {
    if (!!this.pkId && !!this.hide) {
      const dialogRef = this.dialog.open(ViewDetailModalComponent, {
        width: '450px',
        height: '250px',
        panelClass: 'password_pannel',
        data: {
          page: 'password show'
        }
      });
      dialogRef.afterClosed().subscribe(result => {
        if (JSON.parse(result['statusCode']) === 200) {
          this.hide = false;
        }
      });
    } else if (!!this.pkId && this.hide === false) {
      this.hide = true;
    } else {
      this.hide = this.hide === false;
    }
  }

  getSearchFilterOptions(options = [], field) {
    const value = (this.searchFilterCtrl[field].value || '').toLowerCase();
    if (!value) {
      return options;
    }
    return options.filter(option => option.label.toLowerCase().includes(value));
  }

  getCertFilterOptions(options = [], field) {
    const value = (this.certFilterCtrl[field].value || '').toLowerCase();
    if (!value) {
      return options;
    }
    return options.filter(option => option.label.toLowerCase().includes(value));
  }

  cancel() {
    Swal.fire({
      type: 'question',
      customClass: {
        icon: 'swal2-question-mark'
      },
      title: this.translate.instant('SWEET_ALERT.CANCEL.TITLE'),
      text: this.translate.instant('SWEET_ALERT.CANCEL.BODY'),
      showCancelButton: true,
      confirmButtonText: this.translate.instant('SWEET_ALERT.CANCEL.CNFRM_TXT')
    }).then((result) => {
      if (!!result.value) {
        this.router.navigate(['/pcm/partner/manage']);
      }
    });
  }

  sort(event) {
    // @ts-ignore
    this.currentPage = this.page;
    this.sortBy = event.active;
    this.sortDir = event.direction;
    this.qryParams = {
      ...this.qryParams,
      ...{
        sortBy: this.sortBy,
        sortDir: this.sortDir,
        page: this.page,
        size: this.size
      }
    };
    this.loadSearchResults(this.pkId, this.qryParams);
  }

  pagination(event) {
    this.page = event.pageIndex + 1;
    this.currentPage = event.pageIndex;
    this.qryParams = {
      ...this.qryParams,
      ...{
        page: Number(this.page) - 1,
        size: event.pageSize,
      }
    };

    this.loadSearchResults(this.pkId, this.qryParams);
  }

  loadSearchResults(pkId, qryParams?) {
    this.activityDetails = [];
    this.service.getActivityHistory(pkId, qryParams).subscribe(res => {
      this.activityDetails = res['content'];
      this.size = res['size'];
      this.page = res['number'];
      this.totalElements = res['totalElements'];
      this.totalPages = res['totalPages'];
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
