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

import {displayFields} from 'src/app/model/displayfields.map';
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";

export function frameFormObj(fields) {
  const formObj = {};
  fields.forEach(field => {
    formObj[field.formControlName] = field.validation;
  });
  return formObj;
}

export function frameMultiFormObj(fields) {
  const subFormObj = [];
  const formObj = {};
  fields.forEach(field => {
    if (field.formControlName) {
      formObj[field.formControlName] = field.validation;
    } else if (field.inputType === 'pcm-card') {
      field.cards.forEach(card => {
        const subForm = {};
        card.subFields.forEach(subflds => {
          if (subflds.formControlName) {
            subForm[subflds.formControlName] = subflds.validation;
          }
        });
        subFormObj.push({title: card.title, subForm});
      });
    }
  });
  return {formObj, subFormObj};
}


export function getFieldName(field) {
  return displayFields[field];
}

export const stringify = (json) => JSON.stringify(json);
export const parseJson = (str) => JSON.parse(str);

export const markFormFieldTouched = (fb: FormGroup) => {
  const form = fb;
  if (!fb || !fb.controls) {
    return false;
  }
  Object.keys(form.controls).forEach(key => {
    const fieldControl = form.get(key);
    fieldControl.markAsDirty();
    fieldControl.markAsTouched();
    if (fieldControl instanceof FormGroup) {
      markFormFieldTouched(fieldControl);
    }
  });
};

export const addValidators = (fields = [], controlKeys = []) => {
  return fields.map(field => {
    const item = {...field};
    if (controlKeys.indexOf(item.formControlName) !== -1) {
      item['validation'] = ['', [Validators.required, removeSpaces]];
      item['required'] = true;
    }
    return item;
  });
};

export const createFormControl = (...args) => {
  const [formControlName, placeholder = '', inputType = 'text', validation = false, options = []] = args;
  return {
    placeholder,
    formControlName,
    inputType: `pcm-${inputType}`,
    options,
    validation: ['', validation ? [Validators.required] : []],
    required: validation
  };
};

export const toggleFormFieldEnable = (field: any, enable = false, clearValue = false) => {
  if (enable) {
    field.enable();
  } else {
    field.disable();
  }

  if (clearValue) {
    field.setValue(null);
  }
};


export const formGroupToggle = (formGroup: any, enable = true, clearValue = false) => {
  Object.keys(formGroup.controls).forEach(controlkey => {
    toggleFormFieldEnable(formGroup.controls[controlkey], enable, clearValue);
  });
};

export const numberDropdownList = (n) => (new Array(24)).fill(1).map((number, index) => {
  const value = (index + 1) * number;
  return {
    label: value, value
  };
});

export function removeSpaces(control: FormControl) {
  if (control && control.value && !control.value.replace(/\s/g, '').length) {
    control.setValue('');
  }
  return null;
}

export function phoneNumValidation(control) {
  return new RegExp('^(?=.{10,15}$)\\+?[0-9]{1,3}[\\s-]?(?:\\(0?[0-9]{1,5}\\)|[0-9]{1,5})[-\\s]?[0-9][\\d-\\s]{5,7}\\s?(?:x[\\d-]{0,4})?$').test(control);
  // return new RegExp('^(\\(?\\+?[0-9]*\\)?)?[0-9_\\- .\\(\\)]*$').test(control);
}

