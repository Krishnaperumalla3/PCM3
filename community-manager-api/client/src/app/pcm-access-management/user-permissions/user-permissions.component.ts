import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {environment} from "../../../environments/environment";
import {UserService} from "../../services/user.service";
import {stringify} from "../../utility";
import Swal from "sweetalert2";
import {SEARCH_KEY} from "../create-user/create-user.component";

@Component({
  selector: 'pcm-user-permissions',
  templateUrl: './user-permissions.component.html',
  styleUrls: ['./user-permissions.component.scss']
})
export class UserPermissionsComponent implements OnInit {
  userPermissions: FormGroup;
  pkId: any;
  user = [];
  groupsMap = [];
  mailboxMap = []
  mailboxList = [];
  groupList = [];

  selectedPartnersList = [];
  selectedGroupsList = [];

  selectedPartnerList = [];
  selectedGroupList = [];
  constructor(private fb: FormBuilder, private route: ActivatedRoute,
              private userService: UserService,private router: Router,) {
    this.route.params.subscribe(params => {
      this.pkId = params['id'];
    });
  }

  ngOnInit() {
    this.userPermissions = this.fb.group({
      userName: [this.pkId]
    });

    if(this.pkId) {
      this.userService.getB2bwGroupsMailboxAndUser(this.pkId).subscribe(responseList => {
        const [groups, mailbox, user] = responseList;
        this.groupsMap = JSON.parse(JSON.stringify(groups));
        this.mailboxMap = JSON.parse(JSON.stringify(mailbox));
        this.user = JSON.parse(JSON.stringify(user));
        this.mailboxList = this.mailboxMap.map(val => {
          return {key: val, value: val}
        });
        this.groupList = this.groupsMap.map(val => {
          return {key: val, value: val}
        });
        this.userPermissions.patchValue(user);
        this.selectedPartnersList = [];
        this.selectedPartnerList = user['permissions'];
        (this.mailboxList || []).forEach(list => {
          this.selectedPartnersList = [
            ...this.selectedPartnersList,
            ...(user['permissions'].indexOf(list.key) > -1 ? [list] : [])
          ];
        });

        this.selectedGroupsList = [];
        this.selectedGroupList = user['groups'];
        (this.groupList || []).forEach(list => {
          this.selectedGroupsList = [
            ...this.selectedGroupsList,
            ...(user['groups'].indexOf(list.key) > -1 ? [list] : [])
          ];
        });
      });
    }
  }

  onGroupItemsMoved(eve) {
    this.selectedGroupList = eve.selected;
  }

  onPartnerItemsMoved(eve) {
    this.selectedPartnerList = eve.selected;
  }

  submit(form) {
    const reqObj = {
      userName: form.get('userName').value,
      groups: this.selectedGroupList,
      permissions: this.selectedPartnerList
    }
    this.userService.updateB2bUser(reqObj).subscribe(res => {
      this.router.navigate(['/pcm/accessManagement/manage/user']);
      Swal.fire({
        title: 'User Permissions',
        text: res['statusMessage'],
        type: 'success',
        showConfirmButton: false,
        timer: 2000
      });
    }, (err) => {
      if (err.status !== 401) {
        Swal.fire(
          'User Permissions',
          err['error']['errorDescription'],
          'error'
        );
      }
    });
  }

}
