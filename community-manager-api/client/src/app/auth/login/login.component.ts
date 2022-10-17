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
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from 'src/app/services/auth.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from 'src/app/services/user.service';
import {SwalComponent} from '@sweetalert2/ngx-sweetalert2';
import {MatDialog} from '@angular/material';
import {ViewDetailModalComponent} from '../../shared/view-detail-modal/view-detail-modal.component';
import {getUserMenu, getUserType} from 'src/app/services/menu.permissions';
import {HttpClient} from '@angular/common/http';
import {HttpErrorResponse} from '@angular/common/http/src/response';
import {TranslateService} from '@ngx-translate/core';
import {profile} from '../../model/profile.constants';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'pcm-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  isLogout;
  @ViewChild('invalidSwal') private invalidSwal: SwalComponent;
  hide: true;
  public loginForm: FormGroup;
  public isSMLogin = false;
  public isSSP = false;
  public smUserName = '';
  public LoginError = '';
  loading = false;
  activeProfile: string;
  showUnauthorizedMessage: boolean;
  public ssoSeasUserName = '';
  public ssoSeasToken = '';

  apiResult;
  logoutSuccess: boolean;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
    private userService: UserService,
    private fb: FormBuilder,
    public dialog: MatDialog,
    private httpClient: HttpClient,
    public translate: TranslateService
  ) {
    this.isLogout = localStorage.getItem('isLogout');
    localStorage.clear();
    if (this.route.snapshot.queryParams.user && this.route.snapshot.queryParams.user.length > 0) {
      this.ssoSeasUserName = JSON.parse(JSON.stringify(this.route.snapshot.queryParams.user));
      this.ssoSeasToken = JSON.parse(JSON.stringify(this.route.snapshot.queryParams.SSOTOKEN));
      if (this.ssoSeasToken != null && this.ssoSeasToken.length > 1) {
        this.isSSP = true;
        localStorage.setItem('IS_SSP', 'true');
        const baseURL = window.location.origin;
        this.userService.getSspLogOutURL().subscribe(value => {
          localStorage.setItem('SSP_LOGOUT_URL', baseURL + value['data']);
        });
      } else {
        const logoutUrl = localStorage.getItem('SSP_LOGOUT_URL');
        localStorage.clear();
        window.location.href = logoutUrl;
      }
    } else {
      localStorage.setItem('IS_SSP', 'false');
    }
  }

  ngOnInit() {
    this.checkSMLogin();
    this.getLogo();
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  get isUsernameValid() {
    return this.getValidControl('username');
  }

  get isPasswordValid() {
    return this.getValidControl('password');
  }

  getValidControl(key) {
    if (!this.loginForm) {
      return true;
    }
    const control = this.loginForm.get(key);
    return control.dirty && control.valid;
  }

  checkSMLogin() {
    this.authService.checkSMLogin().subscribe(res => {
      this.activeProfile = res['activeProfile'];
      localStorage.setItem('activeProfile', this.activeProfile);
      if (this.activeProfile === profile.SSO_SSP_SEAS) {
        this.isSMLogin = res.smlogin;
        localStorage.setItem('is_sm_login', JSON.stringify(res));
        if (this.isSSP) {
          this.authService.getSeasTokenInfo(this.ssoSeasUserName, this.ssoSeasToken).subscribe(resp => {
            if (resp && !!resp['token']) {
              this.translate.use(resp['lang'] || 'en');
              this.userService.saveUser(resp);
              const menu = getUserMenu(getUserType());
              const route = menu[0].subMenu[0]['route'];
              this.router.navigateByUrl(route);
            }
          });
        }
      } else if (res['activeProfile'] === 'saml') {
        this.httpClient.get('/auth/token').subscribe(
          resp => this.handleTokenSuccess(resp),
          error => this.handleTokenError(error)
        );
      } else if ((this.activeProfile === 'pcm' || this.activeProfile === 'cm') && res['smlogin'] === false) {
        this.isSMLogin = res.smlogin;
        localStorage.setItem('is_sm_login', JSON.stringify(res));
        if (!!this.isSMLogin && !this.isLogout) {
          this.authService.login(null, null).subscribe(resp => {
            if (resp && resp.token) {
              this.translate.use(resp.lang || 'en');
              this.userService.saveUser(resp);
              // this.getLogo();
              const menu = getUserMenu(getUserType());
              const route = menu[0].subMenu[0]['route'];
              this.router.navigateByUrl(route);
            }
          }, error => {
            console.log(error);
            const _errMsg = this.translate.instant((error && error.error) || {}).errorDescription || 'Internal Server Error';
            this.LoginError = this.translate.instant(_errMsg);
            setTimeout(this.invalidSwal.show, 100);
          });
        }
      }
    });
  }

  getLogo() {
    this.userService.getLogo().subscribe(res => {
      const logoImage = res['logoData'];
      localStorage.setItem('siteLogo', logoImage);
    });
  }

  doLogin(isSM) {
    this.markFormDirty();
    if (this.loginForm.invalid && !isSM) {
      return false;
    }
    this.loading = true;
    const {username, password} = this.loginForm.value;
    this.authService.login(username, password).subscribe(res => {
      if (res && res.token) {
        localStorage.setItem('userLang', res.lang);
        this.translate.use(res.lang || 'en');
        this.loading = false;
        this.userService.saveUser(res);
        const menu = getUserMenu(getUserType());
        const route = menu[0].subMenu[0]['route'];
        this.router.navigateByUrl(route);
      }
    }, error => {
      console.log(error);
      const _errMsg = ((error && error.error) || {}).errorDescription || 'Internal Server Error';
      this.LoginError = this.translate.instant(_errMsg);
      setTimeout(this.invalidSwal.show, 100);
      this.loading = false;
    });

  }

  markFormDirty() {
    for (const key in this.loginForm.controls) {
      if (this.loginForm.controls.hasOwnProperty(key)) {
        this.loginForm.get(key).markAsDirty();
      }
    }
  }

  forgotPassword() {
    const dialogRef = this.dialog.open(ViewDetailModalComponent, {
      width: '470px',
      height: '620px',
      panelClass: 'password_pannel',
      data: {
        page: 'forgot password',
        title: 'Forgot Password'
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

  handleTokenSuccess(resp: any) {
    this.userService.saveUser(resp.payLoad);
    this.callApi();
  }

  handleTokenError(error: HttpErrorResponse) {
    if (error.status === 401 || error.status === 403) {
      this.showUnauthorizedMessage = true;
      setTimeout(() => window.location.replace('/saml/login?isWeb=true'), 2000);
    }
  }

  callApi() {
    const menu = getUserMenu(getUserType());
    const route = menu[0].subMenu[0]['route'];
    this.router.navigateByUrl(route);
  }

}
