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
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from 'src/app/services/user.service';
import {ActivatedRoute, Router} from '@angular/router';
import {environment} from '../../../environments/environment';
import {removeSpaces, stringify} from '../../utility';
import {markFormFieldTouched} from 'src/app/utility';
import Swal from 'sweetalert2';
import {ModuleName} from "../../../store/layout/action/layout.action";
import {AppComponent} from '../../app.component';
import {Store} from '@ngxs/store';
import {TranslateService} from "@ngx-translate/core";

export const SEARCH_KEY = 'SEARCH_KEY';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'pcm-create-group',
  templateUrl: './create-group.component.html',
  styleUrls: ['./create-group.component.scss']
})
export class CreateGroupComponent implements OnInit {

  pkId: any;
  private sub: any;

  partnerList = [];
  selectedList = [];
  selectedPartnerList = [];
  selectedGroupList = [];

  createGroupForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private cd: ChangeDetectorRef,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private appComponent: AppComponent,
    private store: Store,
    public translate: TranslateService
  ) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.ACCESS_MNGNT'));
    this.appComponent.selected = 'accessMgt';
    this.createFormGroup();
    this.sub = this.route.params.subscribe(params => {
      this.pkId = params['pkId'];
    });
  }

  createFormGroup() {
    this.createGroupForm = this.fb.group({
      groupName: ['', [Validators.required, removeSpaces]]
    });
  }

  trimValue(formControl) { formControl.setValue(formControl.value.trimLeft()); }

  ngOnInit() {
    this.userService.getPartnerMap()
      .subscribe(res => {
       const partnersMap = JSON.parse(JSON.stringify(res));
        if (!!this.pkId) {
              this.userService.searchGroupBypkId(environment.GET_GROUP_LIST, this.pkId)
              // tslint:disable-next-line:no-shadowed-variable
                .subscribe(res => {
                  this.createGroupForm.patchValue(res);
                  this.partnerList = partnersMap;
                  this.selectedList = [];
                  (this.partnerList || []).forEach( (list, idx) => {
                    this.selectedList = [
                      ...this.selectedList,
                      ...((res['partnerList'] || []).indexOf( list.key ) > -1 ? [list] : [])
                    ];
                  });
                  this.selectedPartnerList = res['partnerList'];
                }, (err) => {
                  console.log(err);
                });
        } else {
          this.partnerList = partnersMap || [];
        }
      });
  }

  onPartnerItemsMoved(eve) {
    this.selectedPartnerList = eve.selected;
  }

  create(form: FormGroup) {
    const isInvalid = this.createGroupForm.invalid;
    markFormFieldTouched(this.createGroupForm);
    const hasValidSlashForDirectories = true;
    if (isInvalid) {
      Swal.fire(
        this.translate.instant("SWEET_ALERT.IN_VALID.TITLE"),
        this.translate.instant("SWEET_ALERT.IN_VALID.BODY"),
        'error'
      );
      return false;
    }
    const reqObj = form.value;
    reqObj['partnerList'] = this.selectedPartnerList;

    const isEdit = !!this.pkId;
    if (isEdit) {
      reqObj['pkId'] = this.pkId;
    }

    this.userService.createGroup(reqObj, isEdit)
      .subscribe(res => {
        // this.toastr.success(res['statusMessage'],'',{disableTimeOut: false});
        Swal.fire({
          title: this.translate.instant('SWEET_ALERT.GROUP.TITLE'),
          text: res['statusMessage'],
          type: 'success',
          showConfirmButton: false,
          timer: 2000
        });
        sessionStorage.setItem(SEARCH_KEY, stringify(reqObj));
        this.router.navigate(['/pcm/accessManagement/manage/group']);
        this.createGroupForm.reset();
        this.selectedList = [];
      }, (err) => {
        if(err.status !== 401) {
          Swal.fire(
            this.translate.instant('SWEET_ALERT.GROUP.TITLE'),
            err['error']['errorDescription'],
            'error'
          );
        }
      });
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
        this.router.navigate(['/pcm/accessManagement/manage/group']);
      }
    });
  }

}
