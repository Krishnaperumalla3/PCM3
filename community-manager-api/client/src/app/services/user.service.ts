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

import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Router} from '@angular/router';
import {forkJoin, Observable, Subject} from 'rxjs';
import {profile} from '../model/profile.constants';

const AUTH_TOKEN = 'PCM_AUTH_TOKEN';
const PCM_USER = 'PCM_USER';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userLoggedIn = new Subject<boolean>();
  private user = null;
  logoutSuccess: boolean;

  constructor(
    private http: HttpClient,
    private router: Router
  ) {
    this.user = JSON.parse(localStorage.getItem(PCM_USER));
    this.userLoggedIn.next(false);
  }

  getRoles() {
    return this.http.get(environment.ROLES);
  }

  getPartnerMap() {
    return this.http.get(environment.GET_PARTNER_MAP);
  }

  getGroupList() {
    return this.http.get(environment.GET_GROUP_LIST);
  }

  createUser(req, isEdit = false) {
    return isEdit ? this.updateUser(req) : this.http.post(environment.CREATE_USER, req);
  }

  updateUser(req) {
    return this.http.put(environment.CREATE_USER, req);
  }

  searchUser(req, qryParams?) {
    let URL = environment.SEARCH_USER;
    if (qryParams) {
      URL = `${environment.SEARCH_USER}?size=${qryParams.size}&page=${
        qryParams.page
      }&sort=${qryParams.sortBy},${qryParams.sortDir.toUpperCase()}`;
    }
    return this.http.post(URL, req);
  }

  statusPartner(url, id, status) {
    let params;
    if (status === 'Active') {
      params = new HttpParams()
        .set('pkId', id)
        .set('status', 'false');
    } else {
      params = new HttpParams()
        .set('pkId', id)
        .set('status', 'true');
    }
    return this.http.post(url, params);
  }

  createGroup(req, isEdit = false) {
    return isEdit ? this.updateGroup(req) : this.http.post(environment.GET_GROUP_LIST, req);
  }

  updateGroup(req) {
    return this.http.put(environment.GET_GROUP_LIST, req);
  }

  searchGroup(req, qryParams?) {
    let URL = environment.SEARCH_GROUP;
    if (qryParams) {
      URL = `${environment.SEARCH_GROUP}?size=${qryParams.size}&page=${
        qryParams.page
      }&sort=${qryParams.sortBy},${qryParams.sortDir.toUpperCase()}`;
    }
    return this.http.post(URL, req);
  }

  searchUserByuserId(url, id) {
    return this.http.get(url + '/' + id);
  }

  searchGroupBypkId(url, id) {
    return this.http.get(url + '/' + id);
  }

  deleteUser(url, id) {
    return this.http.delete(url + '/' + id);
  }

  saveUser(user) {
    this.user = user;
    sessionStorage.setItem(PCM_USER, JSON.stringify(user));
    if (user && !!user.token) {
      this.setToken(user.token);
      this.setUserLoggedIn(true);
    }
  }

  setUserLoggedIn(userLoggedIn: boolean) {
    this.userLoggedIn.next(userLoggedIn);
  }

  getUserLoggedIn(): Observable<boolean> {
    return this.userLoggedIn.asObservable();
  }

  getUser() {
    return this.user;
  }

  get role(): string {
    if (localStorage.getItem('activeProfile') === 'saml') {
      return (this.user || {}).authorities[0]['authority'] || 'user';
    } else {
      return (this.user || {}).userRole || 'user';
    }
  }

  get name(): string {
    if (localStorage.getItem('activeProfile') === profile.SAML) {
      return (this.user || {}).username || 'PCMADMIN';
    } else {
      return (this.user || {}).userName || 'PCMADMIN';
    }
  }

  setToken(token: string): void {
    sessionStorage.setItem(AUTH_TOKEN, token);
  }

  get token(): string {
    return sessionStorage.getItem(AUTH_TOKEN);
  }

  isLogged() {
    return this.token != null;
  }

  logout(isLogout) {
    if (localStorage.getItem('activeProfile') === profile.SAML) {
      localStorage.clear();
      this.http.get(environment.SAML_LOGOUT).subscribe(() => this.logoutSuccess = true);
    } else if (localStorage.getItem('activeProfile') === profile.SSO_SSP_SEAS
      && localStorage.getItem('IS_SSP')
      && localStorage.getItem('IS_SSP') === 'true'
    ) {
      if (!!isLogout) {
        this.http.get(environment.LOGOUT).subscribe(() => this.logoutSuccess = true);
      }
      const sspLogoutURL = localStorage.getItem('SSP_LOGOUT_URL');
      localStorage.clear();
      window.location.href = sspLogoutURL;
    } else {
      if (!!isLogout) {
        this.http.get(environment.LOGOUT).subscribe(() => this.logoutSuccess = true);
      }
      localStorage.clear();
      this.router.navigateByUrl('/login');
    }
  }

  public getGroupPartnersAndUser(url, userId): Observable<any[]> {
    const groupMaps = this.getGroupList();
    const partnerMaps = this.getPartnerMap();
    // const transMaps = this.transactions();
    const user = this.searchUserByuserId(url, userId);
    // Observable.forkJoin (RxJS 5) changes to just forkJoin() in RxJS 6
    return forkJoin([groupMaps, partnerMaps, user]);
  }

  gotoLogin() {
    this.logout(false);
  }

  getUserIdByEmail(query: any) {
    return this.http.get(environment.GET_USERID + query, {responseType: 'text'});
  }

  // To Update Password in Forgot Password Scenerio using UserID, New Passowrd and OTP
  updatePassword(params: any) {
    return this.http.post(environment.UPDATE_PASSWORD, params, {responseType: 'text', observe: 'response'});
  }

  // To Update Password in Change Password Scenerio using UserID, old Password and New Password
  changePassword(params: any) {
    return this.http.post(environment.CHANGE_PASSWORD, params);
  }

  getLogo() {
    return this.http.get(environment.GET_LOGO);
  }

  updateLang(req) {
    return this.http.post(environment.CHANGE_LANG, req);
  }

  transactions() {
    return this.http.get(environment.GET_TRANSACTIONS);
  }

  createTransaction(req) {
    return this.http.put(environment.GET_TRANSACTIONS, req);
  }

  getSspLogOutURL() {
    return this.http.get(environment.SSP_LOGOUT_URL);
  }


  // For Manage Chargeback

  //  Charge back Service
  getCharges() {
    return this.http.get(environment.GET_CHARGES);
  }

  UpdateCharges(req) {
    return this.http.put(environment.UPDATE_CHARGES, req);
  }

  getB2bGroupList() {
    return this.http.get(environment.B2B_GROUPS)
  }

  getB2bMailboxList() {
    return this.http.get(environment.B2B_MAILBOX);
  }

  getB2bUser(id) {
    return this.http.get(`${environment.B2B_USER}?userName=${id}`);
  }

  getB2bwGroupsMailboxAndUser (id) {
    const groups = this.getB2bGroupList();
    const mailbox = this.getB2bMailboxList();
    const user = this.getB2bUser(id);

    return forkJoin([groups, mailbox, user]);
  }

  updateB2bUser(req) {
    return this.http.post(environment.B2B_UPDATE_USER, req);
  }

  userActivity(url) {
    return this.http.get(url);
  }
  unlockUser(url) {
    return this.http.get(url);
  }
}
