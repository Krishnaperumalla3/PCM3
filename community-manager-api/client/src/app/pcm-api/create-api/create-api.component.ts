import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, FormControl, Validators, FormArray} from '@angular/forms';
import {AppComponent} from '../../app.component';
import {Store} from '@ngxs/store';
import {ModuleName} from '../../../store/layout/action/layout.action';
import {markFormFieldTouched} from '../../utility';
import {EndPointService} from '../../services/end-point.service';
import Swal from 'sweetalert2';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'pcm-create-api',
  templateUrl: './create-api.component.html',
  styleUrls: ['./create-api.component.scss']
})
export class CreateApiComponent implements OnInit {
  apiName = new FormControl('', [Validators.required]);
  apiType = new FormControl('CREATE_API', [Validators.required]);
  proxyApi: FormGroup;
  serverApi: FormGroup;
  pkId;
  newRes;
  poolingIntervalList: any = [];
  constructor(private fb: FormBuilder, private appComponent: AppComponent,
              private store: Store, private service: EndPointService,
              private route: ActivatedRoute,
              private router: Router) {
    this.store.dispatch(new ModuleName('Endpoint'));
    this.appComponent.selected = 'api';
    this.route.params.subscribe(params => {
      this.pkId = params['pkId'];
    });
  }

  ngOnInit() {
    this.service.getPoolingInterval().subscribe(res => {
      this.poolingIntervalList = JSON.parse(JSON.stringify(res));
    });
    this.proxyApiForm();
    this.serverApiForm();
    this.apiName.valueChanges.subscribe(val =>  {
      this.proxyApi.get('proxyUrl').setValue(window.location.origin + '/restapi/' + val);
    });
    // this.apiType.valueChanges.subscribe(val => {
    //   if(val === 'CREATE_API') {
    //     this.proxyApiForm();
    //   } else {
    //     this.serverApiForm();
    //   }
    // });
    if (!!this.pkId) {
      this.service.getEndPoint(this.pkId).subscribe(res => {
        this.apiName.patchValue(res['apiName']);
        this.apiType.patchValue(res['apiType']);
         this.newRes = res;
        if (res['apiType'] === 'CREATE_API') {
          const req = {
            proxyUrl: res['proxyUrl'],
            proxyWebMethod: res['proxyWebMethod'],
            serverWebMethod: res['serverWebMethod'],
            serverUrl: res['serverUrl'],
            proxyAuthType: res['proxyApiAuthData']['authType'],
            proxyBasicAuth: res['proxyApiAuthData']['basicAuth'],
            proxyTokenAuth: res['proxyApiAuthData']['tokenAuth'],
            proxyOauth2Auth: res['proxyApiAuthData']['oauth2Auth'],
            serverAuthType: res['serverApiAuthData']['authType'],
            serverBasicAuth: res['serverApiAuthData']['basicAuth'],
            serverTokenAuth: res['serverApiAuthData']['tokenAuth'],
            serverOauth2Auth: res['serverApiAuthData']['oauth2Auth'],
            apiHeaderDataList: (res['apiHeaderDataList'] || []),
            apiParamDataList: (res['apiParamDataList'] || [])
          };
          (req.apiHeaderDataList || []).forEach((itm, i) => {
            if (i > 0) {
              this.addProxyApiServerHeaderNewRow();
            }
          });
          (req.apiParamDataList || []).forEach((itm, i) => {
            if (i > 0) {
              this.addProxyApiServerParamNewRow();
            }
          });
          this.proxyApi.patchValue(req);
        } else {
          const req = {
            serverAuthType: res['serverApiAuthData']['authType'],
            serverWebMethod: res['serverWebMethod'],
            serverUrl: res['serverUrl'],
            poolingInterval: res['poolingInterval'],
            apiHeaderDataList: (res['apiHeaderDataList'] || []),
            apiParamDataList: (res['apiParamDataList'] || []),
            serverBasicAuth: res['serverApiAuthData']['basicAuth'],
            serverTokenAuth: res['serverApiAuthData']['tokenAuth'],
            serverOauth2Auth: res['serverApiAuthData']['oauth2Auth']
          };
          (req.apiHeaderDataList || []).forEach((itm, i) => {
            if (i > 0) {
              this.addServerApiServerHeaderNewRow();
            }
          });
          (req.apiParamDataList || []).forEach((itm, i) => {
            if (i > 0) {
              this.addServerApiServerParamNewRow();
            }
          });
          this.serverApi.patchValue(req);
        }
      });
      }
  }

  proxyApiForm() {
    const proxyURI = window.location.origin + '/' + 'restapi/';
    this.proxyApi = this.fb.group({
      proxyUrl: [proxyURI],
      proxyAuthType: ['NO_AUTH'],
      serverAuthType: ['NO_AUTH'],
      proxyWebMethod: ['GET'],
      serverWebMethod: ['GET'],
      serverUrl: ['', [Validators.required]],
      apiParamDataList: this.fb.array([this.initRows()]),
      apiHeaderDataList: this.fb.array([this.initRows()])
    });
    this.proxyApi.get('proxyAuthType').valueChanges.subscribe(val => {
      if (val === 'BASIC_AUTH') {
        this.proxyApi.removeControl('proxyBasicAuth');
        this.proxyApi.removeControl('proxyTokenAuth');
        this.proxyApi.removeControl('proxyOauth2Auth');
        this.proxyApi.addControl('proxyBasicAuth', this.fb.group({
          username: [''],
          password: ['']
        }));
      } else if (val === 'TOKEN_AUTH') {
        this.proxyApi.removeControl('proxyBasicAuth');
        this.proxyApi.removeControl('proxyTokenAuth');
        this.proxyApi.removeControl('proxyOauth2Auth');
        this.proxyApi.addControl('proxyTokenAuth', this.fb.group({
          tokenApiUrl: [''],
          tokenKey: [''],
          tokenPrefix: [''],
          tokenHeader: [''],
          username: [''],
          password: ['']
        }));
      } else if (val === 'OAUTH2_2_0') {
        this.proxyApi.removeControl('proxyBasicAuth');
        this.proxyApi.removeControl('proxyTokenAuth');
        this.proxyApi.removeControl('proxyOauth2Auth');
        this.proxyApi.addControl('proxyOauth2Auth', this.fb.group({
          clientID: [''],
          clientSecret: [''],
          grantType: [''],
          resource: [''],
          scope: [''],
          forOauthToken: this.fb.group({
            tokenApiUrl: [''],
            tokenKey: [''],
            tokenPrefix: [''],
            tokenHeader: ['']
          }),
          username: [''],
          password: ['']
        }));
      } else {
        this.proxyApi.removeControl('proxyBasicAuth');
        this.proxyApi.removeControl('proxyTokenAuth');
        this.proxyApi.removeControl('proxyOauth2Auth');
      }
    });
    this.proxyApi.get('serverAuthType').valueChanges.subscribe(val => {
      if (val === 'BASIC_AUTH') {
        this.proxyApi.removeControl('serverBasicAuth');
        this.proxyApi.removeControl('serverTokenAuth');
        this.proxyApi.removeControl('serverOauth2Auth');
        this.proxyApi.addControl('serverBasicAuth', this.fb.group({
          username: [''],
          password: ['']
        }));
      } else if (val === 'TOKEN_AUTH') {
        this.proxyApi.removeControl('serverBasicAuth');
        this.proxyApi.removeControl('serverTokenAuth');
        this.proxyApi.removeControl('serverOauth2Auth');
        this.proxyApi.addControl('serverTokenAuth', this.fb.group({
          tokenApiUrl: [''],
          tokenKey: [''],
          tokenPrefix: [''],
          tokenHeader: [''],
          username: [''],
          password: ['']
        }));
      } else if (val === 'OAUTH2_2_0') {
        this.proxyApi.removeControl('serverBasicAuth');
        this.proxyApi.removeControl('serverTokenAuth');
        this.proxyApi.removeControl('serverOauth2Auth');
        this.proxyApi.addControl('serverOauth2Auth', this.fb.group({
          clientID: [''],
          clientSecret: [''],
          grantType: [''],
          resource: [''],
          scope: [''],
          forOauthToken: this.fb.group({
            tokenApiUrl: [''],
            tokenKey: [''],
            tokenPrefix: [''],
            tokenHeader: ['']
          }),
          username: [''],
          password: ['']
        }));
      } else {
        this.proxyApi.removeControl('serverBasicAuth');
        this.proxyApi.removeControl('serverTokenAuth');
        this.proxyApi.removeControl('serverOauth2Auth');
      }
    });
  }

  serverApiForm() {
    this.serverApi = this.fb.group({
      serverAuthType: ['NO_AUTH'],
      serverWebMethod: ['GET'],
      serverUrl: ['', [Validators.required]],
      apiParamDataList: this.fb.array([this.initRows()]),
      apiHeaderDataList: this.fb.array([this.initRows()])
    });
    // this.serverApi.get('serverWebMethod').valueChanges.subscribe(val => {
    //   if (val === 'GET') {
    //     this.serverApi.addControl('poolingInterval',this.fb.control(''));
    //   } else {
    //     this.serverApi.removeControl('poolingInterval');
    //   }
    // });
    this.serverApi.get('serverAuthType').valueChanges.subscribe(val => {
      if (val === 'BASIC_AUTH') {
        this.serverApi.removeControl('serverBasicAuth');
        this.serverApi.removeControl('serverTokenAuth');
        this.serverApi.removeControl('serverOauth2Auth');
        this.serverApi.addControl('serverBasicAuth', this.fb.group({
          username: [''],
          password: ['']
        }));
      } else if (val === 'TOKEN_AUTH') {
        this.serverApi.removeControl('serverBasicAuth');
        this.serverApi.removeControl('serverTokenAuth');
        this.serverApi.removeControl('serverOauth2Auth');
        this.serverApi.addControl('serverTokenAuth', this.fb.group({
          tokenApiUrl: [''],
          tokenKey: [''],
          tokenPrefix: [''],
          tokenHeader: [''],
          username: [''],
          password: ['']
        }));
      } else if (val === 'OAUTH2_2_0') {
        this.serverApi.removeControl('serverBasicAuth');
        this.serverApi.removeControl('serverTokenAuth');
        this.serverApi.removeControl('serverOauth2Auth');
        this.serverApi.addControl('serverOauth2Auth', this.fb.group({
          clientID: [''],
          clientSecret: [''],
          grantType: [''],
          resource: [''],
          scope: [''],
          forOauthToken: this.fb.group({
            tokenApiUrl: [''],
            tokenKey: [''],
            tokenPrefix: [''],
            tokenHeader: ['']
          }),
          username: [''],
          password: ['']
        }));
      } else {
        this.serverApi.removeControl('serverBasicAuth');
        this.serverApi.removeControl('serverTokenAuth');
        this.serverApi.removeControl('serverOauth2Auth');
      }
    });
    this.changeServerWebMethod({value: 'GET'});
  }

  changeServerWebMethod(eve) {
    if (eve.value === 'GET') {
      this.serverApi.addControl('poolingInterval', this.fb.control(''));
    } else {
      this.serverApi.removeControl('poolingInterval');
    }
  }

  get poolingInterval() {
    return this.serverApi.get('poolingInterval') as FormControl;
  }

  get proxyParamsArr(): FormArray {
    return this.proxyApi.get('apiParamDataList') as FormArray;
  }

  get proxyHeadersArr(): FormArray {
    return this.proxyApi.get('apiHeaderDataList') as FormArray;
  }

  get serverParamsArr(): FormArray {
    return this.serverApi.get('apiParamDataList') as FormArray;
  }

  get serverHeadersArr(): FormArray {
    return this.serverApi.get('apiHeaderDataList') as FormArray;
  }

  initRows() {
    return this.fb.group({
      key: [''],
      value: [''],
      description: [''],
      required: [false],
      dynamicValue: [false]
    });
  }

  addProxyApiServerHeaderNewRow() {
    this.proxyHeadersArr.push(this.initRows());
  }

  addProxyApiServerParamNewRow() {
    this.proxyParamsArr.push(this.initRows());
  }

  deleteProxyApiServerHeaderRow(index) {
    this.proxyHeadersArr.removeAt(index);
  }

  deleteProxyApiServerParamRow(index) {
    this.proxyParamsArr.removeAt(index);
  }

  addServerApiServerHeaderNewRow() {
    this.serverHeadersArr.push(this.initRows());
  }

  addServerApiServerParamNewRow() {
    this.serverParamsArr.push(this.initRows());
  }

  deleteServerApiServerHeaderRow(index) {
    this.serverHeadersArr.removeAt(index);
  }

  deleteServerApiServerParamRow(index) {
    this.serverParamsArr.removeAt(index);
  }

  proxyWebSelection(eve) {
    this.proxyApi.get('serverWebMethod').setValue(eve.value);
  }

  submitApi() {
    if (this.apiType.value === 'CREATE_API' && (this.apiName.invalid || this.proxyApi.invalid)) {
      this.apiName.markAsTouched();
      markFormFieldTouched(this.proxyApi);
      return false;
    } else if (this.apiType.value === 'INVOKE_API' && (this.apiName.invalid || this.serverApi.invalid)) {
      this.apiName.markAsTouched();
      markFormFieldTouched(this.serverApi);
      return false;
    }
    const proxyReq = {
      pkId: this.pkId ? this.pkId : '',
      apiName: this.apiName.value,
      apiType: this.apiType.value,
      proxyUrl: this.proxyApi.value.proxyUrl,
      proxyWebMethod: this.proxyApi.value.proxyWebMethod,
      serverUrl: this.proxyApi.value.serverUrl,
      serverWebMethod: this.proxyApi.value.serverWebMethod,
      proxyApiAuthData: {
        authType: this.proxyApi.value.proxyAuthType,
        basicAuth: this.proxyApi.value.proxyBasicAuth,
        tokenAuth: this.proxyApi.value.proxyTokenAuth,
        oauth2Auth: this.proxyApi.value.proxyOauth2Auth
      },
      serverApiAuthData: {
        authType: this.proxyApi.value.serverAuthType,
        basicAuth: this.proxyApi.value.serverBasicAuth,
        tokenAuth: this.proxyApi.value.serverTokenAuth,
        oauth2Auth: this.proxyApi.value.serverOauth2Auth
      },
      apiHeaderDataList: this.proxyApi.value.apiHeaderDataList,
      apiParamDataList: this.proxyApi.value.apiParamDataList
    };

    const serverReq = {
      pkId: this.pkId ? this.pkId : '',
      apiName: this.apiName.value,
      apiType: this.apiType.value,
      serverUrl: this.serverApi.value.serverUrl,
      serverWebMethod: this.serverApi.value.serverWebMethod,
      poolingInterval: this.serverApi.value.poolingInterval,
      serverApiAuthData: {
        authType: this.serverApi.value.serverAuthType,
        basicAuth: this.serverApi.value.serverBasicAuth,
        tokenAuth: this.serverApi.value.serverTokenAuth,
        oauth2Auth: this.serverApi.value.serverOauth2Auth
      },
      apiHeaderDataList: this.serverApi.value.apiHeaderDataList,
      apiParamDataList: this.serverApi.value.apiParamDataList
    };

    if (this.apiType.value === 'CREATE_API') {
      this.service.createEndPoint(proxyReq, this.pkId).subscribe(res => {
        Swal.fire({
          title: 'End Point',
          text: res['statusMessage'],
          showConfirmButton: false,
          timer: 3000,
          type: 'success'
        });
        const req = {
          apiName: this.apiName.value,
          serverWebMethod: this.proxyApi.value.serverWebMethod,
          serverUrl: this.proxyApi.value.serverUrl
        };
        localStorage.setItem('manageSearch', JSON.stringify(req));
        this.cancel();
      }, err => {
        Swal.fire({
          title: 'End Point',
          text: err.error.errorDiscription,
          type: 'error',
        });
      });
    } else {
      this.service.createEndPoint(serverReq, this.pkId).subscribe(res => {
        Swal.fire({
          title: 'End Point',
          text: res['statusMessage'],
          showConfirmButton: false,
          timer: 3000,
          type: 'success'
        });
        const req = {
          apiName: this.apiName.value,
          serverWebMethod: this.proxyApi.value.serverWebMethod,
          serverUrl: this.proxyApi.value.serverUrl
        };
        localStorage.setItem('manageSearch', JSON.stringify(req));
        this.cancel();
      }, err => {
        Swal.fire({
          title: 'End Point',
          text: err.error.errorDiscription,
          type: 'error',
        });
      });
    }
  }

  cancel() {
    this.router.navigate(['/pcm/api/manage']);
    this.apiName.patchValue('');
    this.proxyApiForm();
  }

}
