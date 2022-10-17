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

import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {PartnerService} from 'src/app/services/partner.service';
import {RuleService} from '../../services/rule.service';
import {UserService} from '../../services/user.service';
import {ToastrService} from 'ngx-toastr';
import {FormBuilder, FormControl, FormGroup, FormGroupDirective, NgForm, Validators} from '@angular/forms';
import {FileSearchService} from 'src/app/services/file-search/file-search.service';
import Swal from 'sweetalert2';
import {ErrorStateMatcher} from '@angular/material/core';
import {MatDialog} from '@angular/material/dialog';
import {HttpParams} from '@angular/common/http';
import {TranslateService} from '@ngx-translate/core';
import {RichEditorComponent} from "../rich-editor/rich-editor.component";
import {ChunkFileUploadService} from "../../services/chunk-file-upload/chunk-file-upload.service";

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const invalidCtrl = !!(control && control.invalid && control.parent.dirty);
    const invalidParent = !!(control && control.parent && control.parent.invalid && control.parent.dirty);

    return (invalidCtrl || invalidParent);
  }
}

@Component({
  selector: 'app-view-detail-modal',
  templateUrl: 'view-detail-modal.component.html',
  styleUrls: ['./view-detail-modal.component.scss']
})

export class ViewDetailModalComponent implements OnInit {
  @ViewChild(RichEditorComponent) private richText: RichEditorComponent;
  ctrl = new FormControl();
  editorInitValue: any;
  public matcher = new MyErrorStateMatcher();
  checked = false;
  userName = new FormControl();
  password = new FormControl();
  myForm: FormGroup;
  changePasswordForm: FormGroup;
  forgotPasswordForm: FormGroup;
  viewDetailsList = [
    {label: 'DISPLAYFIELDS.pickbpid'},
    {label: 'DISPLAYFIELDS.corebpid'},
    {label: 'DISPLAYFIELDS.deliverybpid'},
    {label: 'DISPLAYFIELDS.srcprotocol'},
    {label: 'DISPLAYFIELDS.destprotocol'},
    {label: 'DISPLAYFIELDS.transfile'},
    {label: 'DISPLAYFIELDS.doctype'},
    {label: 'DISPLAYFIELDS.errorstatus'},
    {label: 'DISPLAYFIELDS.adverrorstatus'},
    {label: 'DISPLAYFIELDS.statusComments'}
  ].map(col => {
    return {label: this.translate.instant(col.label)}
  });
  updatePasswordForm: FormGroup;
  userId: any;
  loading = false;
  disable = true;
  isLargeFile = false;
  isUpload = false;
  myFiles: string [] = [];
  pwdTip = 'Combination of at least two-character types: uppercase[A-Z], lowercase[a-z], number[0-9], special characters\n' +
    'The valid non-alphabetic characters include the following characters hyphen (-), underscore (_), ' +
    'period (.), and special characters such as !@#$%&';

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private partnerService: PartnerService,
    private ruleService: RuleService,
    private userService: UserService,
    private fileService: FileSearchService,
    public dialogRef: MatDialogRef<ViewDetailModalComponent>,
    private toastr: ToastrService,
    private fb: FormBuilder,
    private dialog: MatDialog,
    private translate: TranslateService,
    private chunkFile: ChunkFileUploadService
  ) {
    this.myFormGroup();
    this.changePasswordForm = this.fb.group({
      oldPassword: [''],
      passwordGroup: this.fb.group({
        newPassword: [''],
        // (?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$
        confirmPassword: ['']
      }, {
        validator: this.checkPasswords
      })
    });
    this.forgotPasswordForm = this.fb.group({
      userId: [''],
      emailId: ['', [Validators.required, Validators.email]]
    });
    this.updatePasswordForm = this.fb.group({
      userId: ['', Validators.required],
      passwordGroup: this.fb.group({
        newPassword: [''],
        confirmPassword: ['']
      }, {
        validator: this.checkPasswords
      }),
      otp: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(6)]]
    });
  }

  myFormGroup() {
    this.myForm = this.fb.group({
      userName: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  ngOnInit() {
    if (this.data.body === 'The file size is too large to view, please download the file to view.') {
      this.isLargeFile = true;
    }
    this.editorInitValue = {
      'ops': [
        {'insert': this.data.body}
      ]
    };
    this.ctrl.setValue(this.editorInitValue);
  }

  onFileChange(event) {
    for (let i = 0; i < event.target.files.length; i++) {
      this.myFiles.push(event.target.files[i]);
    }
  }

  upLoad(eve) {
    const fileName = this.data.flowType === 'source' ? this.data.row.srcfilename : this.data.row.destfilename;
    const blob = new Blob([eve]);
    const file = new File([blob], fileName);
    let formData = new FormData();
    formData.append('type', this.data.flowType);
    formData.append('seqId', this.data.row.seqid);
    formData.append('file', file);
    let headers = new Headers();
    headers.append('Content-Type', 'multipart/form-data');
    this.fileService.uploadFile(formData, headers).subscribe(res => {
      Swal.fire({
        title: 'Upload file',
        text: res['statusMessage'],
        type: 'success',
        showConfirmButton: false,
        timer: 2000
      }).then();
      this.richText.disabled = false;
    }, error => {
      Swal.fire(
        'Upload file',
        error['error']['errorDescription'],
        'error'
      ).then();
    });
  }

  uploadFile() {
    // let formData = new FormData();
    // formData.append('type', this.data.flowType);
    // formData.append('seqId', this.data.row.seqid);
    // formData.append('file', this.ctrl.value.files[0]);
    // let headers = new Headers();
    // headers.append('Content-Type', 'multipart/form-data');
    this.chunkFile.upload(this.ctrl.value.files[0]).subscribe(res => {
      Swal.fire({
        title: 'Upload file',
        text: res['statusMessage'],
        type: 'success',
        showConfirmButton: false,
        timer: 2000
      }).then();
    }, error => {
      Swal.fire(
        'Upload file',
        error['error']['errorDescription'],
        'error'
      ).then();
    });
  }

  downloadFile(row, type) {
    this.fileService.downloadFile(row.seqid, type).subscribe(res => {
      this.download_file(res, row, type);
    }, (err) => {
      console.log(err);
    });
  }

  download_file(res, row, type) {
    const blob = new Blob([res], {type: 'application/octet-stream'});
    const filePath = type === 'source' ? row.srcarcfileloc : row.destarcfileloc;
    const paths = (filePath || '').split('\\');
    const fileName = paths[paths.length - 1];
    if (window.navigator && window.navigator.msSaveBlob) {
      window.navigator.msSaveBlob(blob);
    }
    const a = document.createElement('a');
    document.body.appendChild(a);
    const url = window.URL.createObjectURL(blob);
    a.href = url;
    a.download = fileName;
    a.target = '_self';
    a.click();
    window.URL.revokeObjectURL(url);
    this.dialogRef.close();
  }

  viewPassword(form: FormGroup) {
    // const sm__info = JSON.parse(sessionStorage.getItem('is_sm_login'));
    const userInfo = JSON.parse(sessionStorage.getItem('PCM_USER'));
    const req = form.value;
    req['accessMode'] = userInfo['userRole'];
    req['smLogin'] = userInfo['smlogin'];
    this.partnerService.isValid(req).subscribe(res => {
      console.log(res);
      this.dialogRef.close(res);
    }, (err) => {
      if (err.status !== 401) {
        Swal.fire(
          this.translate.instant('SWEET_ALERT.LOGIN.PWD.TITLE'),
          err['error']['errorDescription'],
          'error'
        ).then();
      }
    });
  }

  checkPasswords(group: FormGroup) { // here we have the 'passwords' group
    const pass = group.controls.newPassword.value;
    const confirmPass = group.controls.confirmPassword.value;

    return pass === confirmPass ? null : {passwordNotMatch: true};
  }

  getUser(group: FormGroup) {
    this.loading = true;
    const email = group.value.emailId;
    if (group.valid) {
      this.userService.getUserIdByEmail(email).subscribe(res => {
        this.loading = false;
        if (res !== null) {
          this.userId = res;
          this.updatePasswordForm.controls.userId.patchValue(this.userId);
          this.updatePasswordForm.controls.userId.disable();
        }
      }, (err) => {
        this.loading = false;
        const err_json = JSON.parse(err['error']);
        if (err.status !== 401) {
          Swal.fire(
            'Forgot Password',
            err_json['errorDescription'],
            'error'
          ).then();
        }
      });
    }
  }

  updatePassword(updatePasswordForm: FormGroup) {
    const params = new HttpParams()
      .set('userId', this.userId)
      .set('password', updatePasswordForm.value.passwordGroup.confirmPassword)
      .set('otp', updatePasswordForm.value.otp);
    if (this.updatePasswordForm.valid) {
      this.userService.updatePassword(params).subscribe(res => {
        this.dialogRef.close(res);
        Swal.fire(
          'Change Password',
          'Password Successfully updated',
          'success'
        ).then();

      }, (err) => {
        const err_json = JSON.parse(err['error']);
        if (err.status !== 401) {
          Swal.fire(
            'Change Password',
            err_json['errorDescription'],
            'error'
          ).then();
        }
      });
    }
  }

  doChangePassword(changePasswordForm: FormGroup) {
    const userInfo = JSON.parse(sessionStorage.getItem('PCM_USER'));
    const oldPwd = changePasswordForm.value.oldPassword;
    const newPwd = changePasswordForm.value.passwordGroup.confirmPassword;
    const params = new HttpParams()
      .set('pkId', userInfo['userId'])
      .set('oldPassword', oldPwd)
      .set('newPassword', newPwd);
    if (changePasswordForm.valid) {
      if (oldPwd === newPwd) {
        Swal.fire(
          'Change Password',
          'Old And New Password should not be same',
          'error'
        ).then();
      } else {
        this.userService.changePassword(params).subscribe(res => {
          this.dialogRef.close(res);
          if (res['statusCode'] === 200) {
            Swal.fire(
              'Change Password',
              res['statusMessage'],
              'success'
            ).then();
            this.userService.logout(true);
          }
        }, (err) => {
          if (err.status !== 401) {
            Swal.fire(
              'Change Password',
              err['error']['errorDescription'],
              'error'
            ).then();
          }
        });
      }
    }
  }

  view(req) {
    this.fileService.activityFile(req.trimLeft()).subscribe(res => {
      this.dialog.open(ViewDetailModalComponent, {
        width: '70%',
        data: {
          page: 'read file',
          body: res
        }
      });
    }, err => {
      console.log(err)
      Swal.fire(
        this.translate.instant('View File'),
        JSON.parse(err['error'])['errorDescription'],
        'error'
      ).then();
    });
  }
}
