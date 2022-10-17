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

import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { UserService } from '../services/user.service';
import Swal from 'sweetalert2';
import {getUserMenu, getUserType} from "../services/menu.permissions";
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private userService: UserService, private router: Router, private translate: TranslateService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const currentUser = this.userService.getUser();
    let redirectUrl = route['_routerState']['url'];
    if(currentUser) {
      const isUserRole = localStorage.getItem('activeProfile') === 'saml'? route.data.roles.indexOf(currentUser.authorities[0]['authority']) === -1 : route.data.roles.indexOf(currentUser['userRole']) === -1;
      if (route.data.roles && isUserRole) {
        const menu = getUserMenu(getUserType());
        const route = menu[0].subMenu[0]['route'];
        // role not authorised so redirect to home page
        Swal.fire(
          this.translate.instant('SWEET_ALERT.LOGIN.UN_AUTH.TITLE'),
          this.translate.instant('SWEET_ALERT.LOGIN.UN_AUTH.BODY'),
          'error'
        );
        // this.router.navigateByUrl(route);
        return false;
      }
      return true;
    }

    if (this.userService.isLogged()) {
      return true;
    }

    this.router.navigateByUrl(
      this.router.createUrlTree(
        ['/login'], {
          queryParams: {
            redirectUrl
          }
        }
      )
    );

    return false;
  }
}
