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

import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {CREATE_USER} from 'src/app/model/user.constants';
import {frameFormObj, markFormFieldTouched, phoneNumValidation, stringify} from 'src/app/utility';
import {UserService} from 'src/app/services/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {environment} from '../../../environments/environment';
import Swal from 'sweetalert2';
import {MatDialog} from '@angular/material';
import {AppComponent} from '../../app.component';
import {Store} from '@ngxs/store';
import {ModuleName} from "../../../store/layout/action/layout.action";
import {Role} from "../../model/roles";
import {TranslateService} from "@ngx-translate/core";

export const SEARCH_KEY = 'SEARCH_KEY';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.scss']
})
export class CreateUserComponent implements OnInit {
  hide = true;
  partnerList = [];
  groupList = [];
  transList = [];
  roles: any;
  rolesList: any;
  partnerMaps = [];
  groupMaps = [];
  transMaps = [];
  userId: any;
  private sub: any;

  selectedPartnersList = [];
  selectedGroupsList = [];
  selectedTransList = [];

  selectedPartnerList = [];
  selectedGroupList = [];
  selectTransList = [];
  createUserForm: FormGroup;

  userFields = CREATE_USER.createUserField;
  public user: any = {};

  activeProfile: any;

  constructor(
    private fb: FormBuilder,
    private cd: ChangeDetectorRef,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    public dialog: MatDialog,
    private appComponent: AppComponent,
    private store: Store,
    public translate: TranslateService
  ) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.ACCESS_MNGNT'));
    this.appComponent.selected = 'accessMgt';
    this.sub = this.route.params.subscribe(params => {
      this.userId = params['userId'];
    });
    this.getRolesList();
    this.activeProfile = localStorage.getItem("activeProfile");
  }

  trimValue(formControl) {
    formControl.setValue(formControl.value.trimLeft());
  }

  ngOnInit() {
    this.roles = Role;
  }

  getRolesList() {
    this.userService.getRoles().subscribe(res => {
      this.rolesList = res;
      this.userFields = this.userFields.map(val => {
        if (val.formControlName === 'userRole') {
          val.options = this.rolesList.map(opt => {
            return {
              label: opt.value,
              value: opt.key
            };
          });
        }
        return val;
      });
      this.createUserFormGroup();
    });
  }

  createUserFormGroup() {
    this.createUserForm = this.fb.group(frameFormObj(this.userFields)
    );

    if (!!this.userId) {
      this.userService.getGroupPartnersAndUser(environment.CREATE_USER, this.userId).subscribe(responseList => {
        const [groupMaps, partnerMaps, res] = responseList;
        this.groupMaps = JSON.parse(JSON.stringify(groupMaps));
        this.partnerMaps = JSON.parse(JSON.stringify(partnerMaps));
        // this.transMaps = this.transList = JSON.parse(JSON.stringify(res)).map(item => {
        //   return {"key": item, "value": item};
        // });
        this.user = res;
        this.partnerList = this.partnerMaps;
        this.groupList = this.groupMaps;
        // this.transList = this.transMaps;
        this.createUserForm.patchValue(res);
        this.selectedPartnersList = [];
        this.selectedPartnerList = res['partnersList'];
        (this.partnerList || []).forEach(list => {
          this.selectedPartnersList = [
            ...this.selectedPartnersList,
            ...(res['partnersList'].indexOf(list.key) > -1 ? [list] : [])
          ];
        });

        this.selectedGroupsList = [];
        this.selectedGroupList = res['groupsList'];
        (this.groupList || []).forEach(list => {
          this.selectedGroupsList = [
            ...this.selectedGroupsList,
            ...(res['groupsList'].indexOf(list.key) > -1 ? [list] : [])
          ];
        });

        // this.selectedTransList = [];
        // this.selectTransList = res['transList'];
        // (this.transList || []).forEach(list => {
        //   this.selectedTransList = [
        //     ...this.selectedTransList,
        //     ...(res['transList'].indexOf(list.key) > -1 ? [list] : [])
        //   ];
        // });
      });
    } else {
      this.createUserForm.controls['lang'].setValue('en', {onlySelf: true});
      this.createUserForm.get('userRole').valueChanges.subscribe(val => {
        if (val !== Role.superAdmin && val !== Role.admin && val !== '') {
          this.userService.getPartnerMap()
            .subscribe(res => {
              this.partnerList = JSON.parse(JSON.stringify(res));
            });
          this.userService.getGroupList()
            .subscribe(res => {
              this.groupList = JSON.parse(JSON.stringify(res));
            });
          // this.userService.transactions().subscribe(res => {
          //   this.transList = JSON.parse(JSON.stringify(res)).map(item => {
          //     return {"key": item, "value": item};
          //   })
          // });
        }
      });
    }

    if (localStorage.getItem("activeProfile") === 'sso-ssp-seas') {
      this.createUserForm.controls['userType'].setValue('EXTERNAL', {onlySelf: true});
    } else {
      this.createUserForm.controls['userType'].setValue('INTERNAL', {onlySelf: true});
    }
  }

  onPartnerItemsMoved(eve) {
    this.selectedPartnerList = eve.selected;
  }

  onGroupItemsMoved(eve) {
    this.selectedGroupList = eve.selected;
  }

  onTransItemsMoved(eve) {
    this.selectTransList = eve.selected;
  }

  create(form: FormGroup) {
    if (this.createUserForm.value['phone'] && this.createUserForm.value['phone'] !== '') {
      if (!phoneNumValidation(this.createUserForm.value['phone'])) {
        markFormFieldTouched(this.createUserForm);
        Swal.fire(
          this.translate.instant("SWEET_ALERT.IN_VALID_PHONE.TITLE"),
          this.translate.instant("SWEET_ALERT.IN_VALID_PHONE.BODY"),
          'error'
        );
        return false;
      }
    }

    if (this.createUserForm.invalid) {
      Swal.fire(
        this.translate.instant("SWEET_ALERT.IN_VALID.TITLE"),
        this.translate.instant("SWEET_ALERT.IN_VALID.BODY"),
        'error'
      );
      markFormFieldTouched(this.createUserForm);
    } else {
      const reqObj = form.value;
      reqObj['partnersList'] = this.selectedPartnerList;
      reqObj['groupsList'] = this.selectedGroupList;

      const isEdit = !!this.userId;
      if (isEdit) {
        reqObj['userId'] = this.userId;
      } else {
        reqObj['b2bUser'] = true;
      }

      this.userService.createUser(reqObj, isEdit).subscribe(res => {
          sessionStorage.setItem(SEARCH_KEY, stringify(reqObj));
          this.router.navigate(['/pcm/accessManagement/manage/user']);
          Swal.fire({
            title: 'User',
            text: res['statusMessage'],
            type: 'success',
            showConfirmButton: false,
            timer: 2000
          });
        }, (err) => {
          if (err.status !== 401) {
            Swal.fire(
              'Create User',
              err['error']['errorDescription'],
              'error'
            );
          }
      });
    }
  }

  cancel() {
    Swal.fire({
      type: 'question',
      customClass: {
        icon: 'swal2-question-mark'
      },
      title: this.translate.instant("SWEET_ALERT.CANCEL.TITLE"),
      text: this.translate.instant("SWEET_ALERT.CANCEL.BODY"),
      showCancelButton: true,
      confirmButtonText: this.translate.instant("SWEET_ALERT.CANCEL.CNFRM_TXT")
    }).then((result) => {
      if (!!result.value) {
        this.router.navigate(['/pcm/accessManagement/manage/user']);
      }
    });
  }

  //TODO
  // showPassword() {
  //   if (!!this.userId && !!this.hide) {
  //     const dialogRef = this.dialog.open(ViewDetailModalComponent, {
  //       width: '450px',
  //       height: '250px',
  //       panelClass: 'password_pannel',
  //       data: {
  //         page: 'password show'
  //       }
  //     });
  //     dialogRef.afterClosed().subscribe(result => {
  //       if (JSON.parse(result['statusCode']) === 200) {
  //         this.hide = false;
  //       }
  //     });
  //   } else if (!!this.userId && this.hide === false) {
  //     this.hide = true;
  //   } else {
  //     this.hide = this.hide === false;
  //   }
  // }

}

