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

import {AfterViewInit, Component, HostListener, OnInit, ViewChild} from '@angular/core';
import {DomSanitizer, Title} from '@angular/platform-browser';
import {Select, Store} from '@ngxs/store';
import {Observable} from 'rxjs';
import {MatDialog, MatDrawer, MatIconRegistry} from '@angular/material';
import {LayoutState} from 'src/store/layout/state/layout.state';
import {NavigationEnd, Router} from '@angular/router';
import {UserService} from './services/user.service';
import {ViewDetailModalComponent} from './shared/view-detail-modal/view-detail-modal.component';
import {DEFAULT_INTERRUPTSOURCES, Idle} from '@ng-idle/core';
import {Keepalive} from '@ng-idle/keepalive';
import {getUser, getUserMenu, getUserType} from './services/menu.permissions';
import {SwalComponent} from '@sweetalert2/ngx-sweetalert2';
import 'rxjs-compat/add/observable/fromEvent';
import {TranslateService} from '@ngx-translate/core';
import LANG, {IOptions} from './model/lang';
import {HttpClient} from '@angular/common/http';
import {environment} from 'src/environments/environment';
import {Role} from './model/roles';

const documentElement = document;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements AfterViewInit, OnInit {
  idleState = 'Not started.';
  timedOut = false;
  lastPing?: Date = null;
  selected = '';
  menuLst = [];
  base64Image;
  isUseRadius = true;
  isUseIcon = true;
  isShown = false;
  supportedLang: Array<IOptions>;
  private readonly documentIsAccessible: boolean;
  @Select(LayoutState.getLoader) loader$: Observable<boolean>;
  @Select(LayoutState.getModuleName) moduleName$: Observable<string>;

  @ViewChild('invalidSwal') private invalidSwal: SwalComponent;
  @ViewChild(MatDrawer) drawer: MatDrawer;
  appInfo;
  loading = false;
  isUserLoggedIn = false;
  menuColour: string;
  activeProfile: string;
  constructor(
    private matIconRegistry: MatIconRegistry,
    private domSanitizer: DomSanitizer,
    public userService: UserService,
    private router: Router,
    private store: Store,
    public dialog: MatDialog,
    private idle: Idle,
    private keepalive: Keepalive,
    public translate: TranslateService,
    private titleService: Title,
    private http: HttpClient
  ) {
    this.activeProfile = localStorage.getItem('activeProfile');
    this.matIconRegistry.addSvgIcon(
      'menu-bar',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        'assets/svg/menu-bar.svg'
      )
    );
    this.matIconRegistry.addSvgIcon(
      'home',
      this.domSanitizer.bypassSecurityTrustResourceUrl('assets/svg/home.svg')
    );
    this.matIconRegistry.addSvgIcon(
      'info',
      this.domSanitizer.bypassSecurityTrustResourceUrl('assets/svg/info.svg')
    );
    this.matIconRegistry.addSvgIcon(
      'help',
      this.domSanitizer.bypassSecurityTrustResourceUrl('assets/svg/help.svg')
    );
    this.supportedLang = LANG;
    translate.addLangs(LANG.map(val => val.label));
    if (localStorage.getItem('userLang')) {
      this.translate.use(localStorage.getItem('userLang'));
    } else {
      this.translate.setDefaultLang('en');
      const browserLang = this.translate.getBrowserLang();
      this.translate.use(browserLang.match(/en|fr/) ? browserLang : 'en');
    }
  }

  reset() {
    this.idle.watch();
    this.isShown = false;
    this.idleState = 'Started.';
    this.timedOut = false;
  }

  toggleSideMenu() {
    if (this.drawer) {
      this.drawer.toggle();
    }
  }

  onLangSelect(lng) {
    localStorage.setItem('userLang', lng);
    const req = {'lang': lng, 'userId': getUser().userId};
    this.userService.updateLang(req).subscribe(res => {
      this.translate.use(lng);
    });
    const UPD_USR_LANG = `${environment.UPD_USR_PRFRD_LANG}?lang=${lng}&userId=${JSON.parse(sessionStorage.getItem('PCM_USER')).userId}`;
  }

  private initIdleSession() {
    this.idle.setIdle(300);
    // sets a timeout period of 5 seconds. after 10 seconds of inactivity, the user will be considered timed out.
    this.idle.setTimeout(15);
    // sets the default interrupts, in this case, things like clicks, scrolls, touches to the document
    this.idle.setInterrupts(DEFAULT_INTERRUPTSOURCES);
    this.idle.setKeepaliveEnabled(true);
    this.idle.onIdleEnd.subscribe(() => {
      this.idleState = 'No longer idle.';
      this.reset();
    });

    this.idle.onTimeout.subscribe(() => {
      this.isShown = false;
      this.idleState = 'Timed out!';
      this.timedOut = true;
      this.router.navigate(['/login']);
    });

    this.idle.onIdleStart.subscribe(() => {
      this.idleState = 'You\'ve gone idle!';
      this.idle.setKeepaliveEnabled(true);
      this.isShown = true;
    });

    this.idle.onTimeoutWarning.subscribe((countdown) => {
      this.timedOut = true;
      this.idleState = 'You will time out in ' + countdown + ' seconds!';
    });
    // sets the ping interval to 15 seconds
    this.keepalive.interval(1);

    // this.clicks.subscribe(event => {
    //   this.reset();
    // });

    this.keepalive.onPing.subscribe(() => this.lastPing = new Date());

    // this.userService.getUserLoggedIn().subscribe(userLoggedIn => {
    //   if (userLoggedIn) {
    //     this.idle.watch();
    //     this.timedOut = false;
    //   } else {
    //     this.idle.stop();
    //   }
    // });

    this.reset();

  }

  ngOnInit() {
    this.router.events.subscribe((event: any) => {
      if (event instanceof NavigationEnd) {
        this.isUserLoggedIn = (event.urlAfterRedirects || '').indexOf('login') === -1;
        this.menuLst = getUserMenu(getUserType());
        if (!!sessionStorage.getItem('PCM_USER')) {
          const logoImage = localStorage.getItem('siteLogo');
          if (logoImage === null || logoImage === '') {
            this.base64Image = '../assets/img/PragmaLogo.png';
          } else {
            this.base64Image = this.domSanitizer.bypassSecurityTrustResourceUrl(logoImage);
          }
          if (getUser().userRole === Role.superAdmin && getUser().apiConnect === true) {
            this.menuLst.splice(2, 0, {
              menu: 'API Endpoint',
              iconCls: 'api',
              menuId: 'api',
              roles: 'admin',
              subMenu: [
                {
                  label: 'Create API Endpoint',
                  route: 'pcm/api/create'
                },
                {
                  label: 'Manage API Endpoint',
                  route: 'pcm/api/manage'
                },
                {
                  label: 'Endpoint Flow',
                  route: 'pcm/api/build-flow'
                }
              ]
            });
          }
          this.menuColour = JSON.parse(sessionStorage.getItem('PCM_USER'))['color'];
          this.appInfo = JSON.parse(sessionStorage.getItem('PCM_USER'));
          this.titleService.setTitle(this.appInfo['appCustomName']);
        }
      }
    });
  }

  ngAfterViewInit() {
    this.loader$.subscribe(res => (this.loading = res));
  }

  menuSelected(id) {
    if (id === this.selected) {
      this.selected = '';
    } else {
      this.selected = id;
    }
  }

  selectedMenu(name) {
    // this.store.dispatch(new ModuleName(name));
  }

  doLogout() {
    this.menuLst = [];
    this.userService.logout(true);
  }

  userRole() {
    return this.userService.role;
  }

  passwordChange() {
    const dialogRef = this.dialog.open(ViewDetailModalComponent, {
      width: '470px',
      height: '540px',
      panelClass: 'password_pannel',
      data: {
        page: 'change password',
        title: 'Password Change',
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

}

