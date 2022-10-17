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
import Swal from 'sweetalert2';
import {Injectable} from '@angular/core';
import {tap} from 'rxjs/operators';
import {
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Store} from '@ngxs/store';
import {LoaderState} from 'src/store/layout/action/layout.action';
import {AuthService} from './auth.service';
import {profile} from '../model/profile.constants';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PCMInterceptor implements HttpInterceptor {
  constructor(private store: Store
    , private auth: AuthService
  ) {
  }

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if(request.url !== '/pcm/file/upload/file') {
      this.store.dispatch(new LoaderState(true));
    }
    let updatedRequest = request;
    if (!!this.auth.getToken() && (localStorage.getItem('activeProfile') === profile.PCM ||
      localStorage.getItem('activeProfile') === profile.CM)) {
      if (!request.url.endsWith('.json')) {
        updatedRequest = request.clone({
          headers: request.headers.set(
            'Authorization',
            `Bearer ${this.auth.getToken()}`
          )
        });
      }
    } else if (!!this.auth.getToken() && localStorage.getItem('activeProfile') === profile.SAML) {
      updatedRequest = request.clone({
        headers: request.headers.set(
          'x-auth-token',
          `${this.auth.getToken()}`
        )
      });
    } else if (localStorage.getItem('activeProfile') === profile.SSO_SSP_SEAS) {
      if (!request.url.endsWith('.json')
        && !request.url.includes('get-token-info')
        && !request.url.endsWith('get-ssp-logout-url')
        && !request.url.endsWith('get-logo')
      ) {
        updatedRequest = request.clone({
          headers: request.headers.set(
            'x-auth-token',
            `${this.auth.getToken()}`
          )
        });
      }
    }

    // logging the updated Parameters to browser's console
    return next.handle(updatedRequest).pipe(
      tap(
        event => {
          // console.log({event});
          // logging the http response to browser's console in case of a success
          if (event instanceof HttpResponse) {
            if(request.url !== '/pcm/file/upload/file') {
              this.store.dispatch(new LoaderState(false));
            }
            // this.store.dispatch(new LoaderState(false));
          }
        },
        error => {
          // logging the http response to browser's console in case of a failure
          if(request.url !== '/pcm/file/upload/file') {
            this.store.dispatch(new LoaderState(false));
          }
          // this.store.dispatch(new LoaderState(false));
          if (error instanceof HttpErrorResponse) {
            if (!request.url.includes(environment.LOGOUT)) {
              if (error.status === 401 || error.status === 504 || error.status === 406) {
                if (localStorage.getItem('activeProfile') === profile.SSO_SSP_SEAS &&
                  localStorage.getItem('IS_SSP') && localStorage.getItem('IS_SSP') === 'true') {
                  Swal.fire({
                    title: 'Logout',
                    text: error.error.errorDescription,
                    type: 'error',
                    confirmButtonText: 'Ok'
                  }).then((result) => {
                    if (result.value || result.dismiss) {
                      const sspLogoutURL = localStorage.getItem('SSP_LOGOUT_URL');
                      localStorage.clear();
                      window.location.href = sspLogoutURL;
                    }
                  });
                } else {
                  this.auth.gotoLogin();
                }
              }
            }
          }
        }
      )
    );
  }
}
