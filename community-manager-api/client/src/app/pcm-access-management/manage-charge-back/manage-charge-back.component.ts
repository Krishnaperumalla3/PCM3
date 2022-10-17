import { Component, OnInit } from '@angular/core';
import Swal from "sweetalert2";
import {FormBuilder, FormGroup} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {ModuleName} from "../../../store/layout/action/layout.action";
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'pcm-manage-charge-back',
  templateUrl: './manage-charge-back.component.html',
  styleUrls: ['./manage-charge-back.component.scss']
})
export class ManageChargeBackComponent implements OnInit {

  charge;
  createChargebackForm : FormGroup;
  constructor(private fb: FormBuilder,
              private userService: UserService,
              private appComponent: AppComponent,
              private store: Store,
              public translate: TranslateService)
              {
                this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.ACCESS_MNGNT'));
                this.appComponent.selected = 'accessMgt';
              }

  ngOnInit() {
    this.formGroup();
    setTimeout(() => {
      this.getChargesOnRange()
    }, 2000);
    this.createChargebackForm.disable();
  }

  formGroup() {
    this.createChargebackForm = this.fb.group({
      minCharge: '',
      flat1: '',
      rate110: '',
      flat10: '',
      rate1025: '',
      flat25: '',
      rateAbove25: '',
    });
  }

  getChargesOnRange() {
    this.userService.getCharges().subscribe(res =>{
      this.charge = res;
      console.log(this.charge);
      this.createChargebackForm.patchValue(this.charge);
    })
  }

  updateChargeBacks(form) {
    this.userService.UpdateCharges(form).subscribe(res => {
      Swal.fire({
        title: 'Manage Charge Back',
        text: res['statusMessage'],
        type: 'success',
        showConfirmButton: false,
        timer: 2000
      });
    }, (err) => {
      if (err.status !== 401) {
        Swal.fire(
          'Manage Charge Back',
          err['error']['errorDescription'],
          'error'
        );
      }
    });
    console.log(this.createChargebackForm.value);
  }
}
