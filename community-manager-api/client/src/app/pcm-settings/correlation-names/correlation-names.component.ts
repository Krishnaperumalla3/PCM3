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
import {FileSearchService} from 'src/app/services/file-search/file-search.service';
import {Router} from '@angular/router';
import Swal from 'sweetalert2';
import {removeSpaces} from '../../utility';
import {AppComponent} from "../../app.component";
import {Store} from "@ngxs/store";
import {ModuleName} from "../../../store/layout/action/layout.action";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-correlation-names',
  templateUrl: './correlation-names.component.html',
  styleUrls: ['./correlation-names.component.scss']
})
export class CorrelationNamesComponent implements OnInit {

  correlationForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private cd: ChangeDetectorRef,
    private fService: FileSearchService,
    private router: Router,
    private appComponent: AppComponent,
    private store: Store,
    public translate: TranslateService
  ) {
    this.store.dispatch(new ModuleName('COMMON.PAGE_TITLE.SETTINGS'));
    this.appComponent.selected = 'settings';
    this.correlationFormGroup();
  }

  correlationFields = [
    {placeholder: "SETTINGS.FIELDS.CORR1", formControlName: 'correlationName1'},
    {placeholder: "SETTINGS.FIELDS.CORR2", formControlName: 'correlationName2'},
    {placeholder: "SETTINGS.FIELDS.CORR3", formControlName: 'correlationName3'},
    {placeholder: "SETTINGS.FIELDS.CORR4", formControlName: 'correlationName4'},
    {placeholder: "SETTINGS.FIELDS.CORR5", formControlName: 'correlationName5'},
    {placeholder: "SETTINGS.FIELDS.CORR6", formControlName: 'correlationName6'},
    {placeholder: "SETTINGS.FIELDS.CORR7", formControlName: 'correlationName7'},
    {placeholder: "SETTINGS.FIELDS.CORR8", formControlName: 'correlationName8'},
    {placeholder: "SETTINGS.FIELDS.CORR9", formControlName: 'correlationName9'},
    {placeholder: "SETTINGS.FIELDS.CORR10", formControlName: 'correlationName10'},
    {placeholder: "SETTINGS.FIELDS.CORR11", formControlName: 'correlationName11'},
    {placeholder: "SETTINGS.FIELDS.CORR12", formControlName: 'correlationName12'},
    {placeholder: "SETTINGS.FIELDS.CORR13", formControlName: 'correlationName13'},
    {placeholder: "SETTINGS.FIELDS.CORR14", formControlName: 'correlationName14'},
    {placeholder: "SETTINGS.FIELDS.CORR15", formControlName: 'correlationName15'},
    {placeholder: "SETTINGS.FIELDS.CORR16", formControlName: 'correlationName16'},
    {placeholder: "SETTINGS.FIELDS.CORR17", formControlName: 'correlationName17'},
    {placeholder: "SETTINGS.FIELDS.CORR18", formControlName: 'correlationName18'},
    {placeholder: "SETTINGS.FIELDS.CORR19", formControlName: 'correlationName19'},
    {placeholder: "SETTINGS.FIELDS.CORR20", formControlName: 'correlationName20'},
    {placeholder: "SETTINGS.FIELDS.CORR21", formControlName: 'correlationName21'},
    {placeholder: "SETTINGS.FIELDS.CORR22", formControlName: 'correlationName22'},
    {placeholder: "SETTINGS.FIELDS.CORR23", formControlName: 'correlationName23'},
    {placeholder: "SETTINGS.FIELDS.CORR24", formControlName: 'correlationName24'},
    {placeholder: "SETTINGS.FIELDS.CORR25", formControlName: 'correlationName25'},
    {placeholder: "SETTINGS.FIELDS.CORR26", formControlName: 'correlationName26'},
    {placeholder: "SETTINGS.FIELDS.CORR27", formControlName: 'correlationName27'},
    {placeholder: "SETTINGS.FIELDS.CORR28", formControlName: 'correlationName28'},
    {placeholder: "SETTINGS.FIELDS.CORR29", formControlName: 'correlationName29'},
    {placeholder: "SETTINGS.FIELDS.CORR30", formControlName: 'correlationName30'},
    {placeholder: "SETTINGS.FIELDS.CORR31", formControlName: 'correlationName31'},
    {placeholder: "SETTINGS.FIELDS.CORR32", formControlName: 'correlationName32'},
    {placeholder: "SETTINGS.FIELDS.CORR33", formControlName: 'correlationName33'},
    {placeholder: "SETTINGS.FIELDS.CORR34", formControlName: 'correlationName34'},
    {placeholder: "SETTINGS.FIELDS.CORR35", formControlName: 'correlationName35'},
    {placeholder: "SETTINGS.FIELDS.CORR36", formControlName: 'correlationName36'},
    {placeholder: "SETTINGS.FIELDS.CORR37", formControlName: 'correlationName37'},
    {placeholder: "SETTINGS.FIELDS.CORR38", formControlName: 'correlationName38'},
    {placeholder: "SETTINGS.FIELDS.CORR39", formControlName: 'correlationName39'},
    {placeholder: "SETTINGS.FIELDS.CORR40", formControlName: 'correlationName40'},
    {placeholder: "SETTINGS.FIELDS.CORR41", formControlName: 'correlationName41'},
    {placeholder: "SETTINGS.FIELDS.CORR42", formControlName: 'correlationName42'},
    {placeholder: "SETTINGS.FIELDS.CORR43", formControlName: 'correlationName43'},
    {placeholder: "SETTINGS.FIELDS.CORR44", formControlName: 'correlationName44'},
    {placeholder: "SETTINGS.FIELDS.CORR45", formControlName: 'correlationName45'},
    {placeholder: "SETTINGS.FIELDS.CORR46", formControlName: 'correlationName46'},
    {placeholder: "SETTINGS.FIELDS.CORR47", formControlName: 'correlationName47'},
    {placeholder: "SETTINGS.FIELDS.CORR48", formControlName: 'correlationName48'},
    {placeholder: "SETTINGS.FIELDS.CORR49", formControlName: 'correlationName49'},
    {placeholder: "SETTINGS.FIELDS.CORR50", formControlName: 'correlationName50'}
  ];

  correlationFormGroup() {
    this.correlationForm = this.fb.group({
      correlationName1: ['', [removeSpaces]],
      correlationName2: ['', [removeSpaces]],
      correlationName3: ['', [removeSpaces]],
      correlationName4: ['', [removeSpaces]],
      correlationName5: ['', [removeSpaces]],
      correlationName6: ['', [removeSpaces]],
      correlationName7: ['', [removeSpaces]],
      correlationName8: ['', [removeSpaces]],
      correlationName9: ['', [removeSpaces]],
      correlationName10: ['', [removeSpaces]],
      correlationName11: ['', [removeSpaces]],
      correlationName12: ['', [removeSpaces]],
      correlationName13: ['', [removeSpaces]],
      correlationName14: ['', [removeSpaces]],
      correlationName15: ['', [removeSpaces]],
      correlationName16: ['', [removeSpaces]],
      correlationName17: ['', [removeSpaces]],
      correlationName18: ['', [removeSpaces]],
      correlationName19: ['', [removeSpaces]],
      correlationName20: ['', [removeSpaces]],
      correlationName21: ['', [removeSpaces]],
      correlationName22: ['', [removeSpaces]],
      correlationName23: ['', [removeSpaces]],
      correlationName24: ['', [removeSpaces]],
      correlationName25: ['', [removeSpaces]],
      correlationName26: ['', [removeSpaces]],
      correlationName27: ['', [removeSpaces]],
      correlationName28: ['', [removeSpaces]],
      correlationName29: ['', [removeSpaces]],
      correlationName30: ['', [removeSpaces]],
      correlationName31: ['', [removeSpaces]],
      correlationName32: ['', [removeSpaces]],
      correlationName33: ['', [removeSpaces]],
      correlationName34: ['', [removeSpaces]],
      correlationName35: ['', [removeSpaces]],
      correlationName36: ['', [removeSpaces]],
      correlationName37: ['', [removeSpaces]],
      correlationName38: ['', [removeSpaces]],
      correlationName39: ['', [removeSpaces]],
      correlationName40: ['', [removeSpaces]],
      correlationName41: ['', [removeSpaces]],
      correlationName42: ['', [removeSpaces]],
      correlationName43: ['', [removeSpaces]],
      correlationName44: ['', [removeSpaces]],
      correlationName45: ['', [removeSpaces]],
      correlationName46: ['', [removeSpaces]],
      correlationName47: ['', [removeSpaces]],
      correlationName48: ['', [removeSpaces]],
      correlationName49: ['', [removeSpaces]],
      correlationName50: ['', [removeSpaces]]
    });
  }

  ngOnInit() {
    this.loadAll();
  }

  trimValue(formControl) {
    formControl.setValue(formControl.value.trimLeft());
  }

  loadAll() {
    this.fService.getCorrelationData().subscribe(res => {
      this.correlationForm.patchValue(res);
    });
  }

  onSubmit(form: FormGroup) {
    const reqObj = form.value;
    this.fService.updateCorrelationData(reqObj).subscribe(res => {
      if (res['statusCode'] === 200) {
        Swal.fire({
          title: this.translate.instant('SWEET_ALERT.SETTINGS.CO_TITLE'),
          text: res['statusMessage'],
          type: 'success',
          showConfirmButton : false,
          timer: 2000
        });
        this.loadAll();
      }
    }, (err) => {
      if(err.status !== 401) {
        Swal.fire(
          this.translate.instant('SWEET_ALERT.SETTINGS.CO_TITLE'),
          err['error']['errorDescription'],
          'error'
        );
      }
    });
  }

  cancel() {
    this.router.navigate(['/pcm']);
  }
}
