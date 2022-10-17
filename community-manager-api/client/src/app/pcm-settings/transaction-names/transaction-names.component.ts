import { Component, OnInit } from '@angular/core';
import {FormGroup, FormBuilder, FormControl, FormArray, Validators} from "@angular/forms";
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import {ModuleName} from "../../../store/layout/action/layout.action";
import {UserService} from "../../services/user.service";
import Swal from 'sweetalert2';

@Component({
  selector: 'pcm-transaction-names',
  templateUrl: './transaction-names.component.html',
  styleUrls: ['./transaction-names.component.scss']
})
export class TransactionNamesComponent implements OnInit {
  transactionForm: FormGroup;

  constructor(private fb: FormBuilder,
              private appComponent: AppComponent,
              private store: Store, private service: UserService) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.SETTINGS'));
    this.appComponent.selected = 'settings';
  }

  ngOnInit() {
    this.transactionForm = this.fb.group({
      transactions: this.fb.array([
        this.fb.group({
          transactionName: ['']
        })
      ])
    });
    let req = [];

    this.service.transactions().subscribe(res => {
      JSON.parse(JSON.stringify(res)).forEach((val, i) => {
        if(i > 0) {
          this.addTransaction();
        }
        req.push({"transactionName": val});
      });
      this.transactionName.patchValue(req);
    })
  }

  get transactionName() : FormArray {
    return this.transactionForm.get("transactions") as FormArray
  }

  addTransaction() {
    let fg = this.fb.group({
      transactionName: ['']
    });
    this.transactionName.push(fg);
  }

  removeTransaction(i:number) {
    this.transactionName.removeAt(i);
  }

  onSubmit(form) {
    const req = form.value.transactions.map(val => {
      return val.transactionName;
    });
    this.service.createTransaction(req).subscribe(res=> {
      Swal.fire({
        title: 'Transactions',
        text: res['statusMessage'],
        type: 'success',
        showConfirmButton: false,
        timer: 2000
      });
    },err =>{
      Swal.fire({
        title: 'Transactions',
        text: err['error']['errorDescription'],
        type: 'error'
      });
    });
  }
}
