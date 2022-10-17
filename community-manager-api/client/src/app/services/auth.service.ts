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
import {HttpClient} from '@angular/common/http';
import {environment} from 'src/environments/environment';
import {Observable} from 'rxjs';
import {UserService} from './user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(
    private http: HttpClient,
    private userService: UserService
  ) {
  }


  login(userName: string, password: string): Observable<any> {
    return this.http.post<any>(`${environment.USER_LOGIN}`, {
      password,
      userName
    });
  }

  ssoSeasLogin(userName: string, password: string): Observable<any> {
    return this.http.post<any>(`${environment.SEAS_TOKEN_GEN}`, {
      password,
      userName
    });
  }

  getSeasTokenInfo(user, token) {
    const url = `${environment.SEAS_TOKEN_INFO}?token=${token}&userName=${user}`;
    return this.http.get(url);
  }

  checkSMLogin(): Observable<any> {
    return this.http.get(environment.ACTIVE_PROFILE);
  }

  getToken() {
    return this.userService.token;
  }

  gotoLogin() {
    this.userService.gotoLogin();
  }


}
