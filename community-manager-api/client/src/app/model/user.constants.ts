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

import {Validators} from '@angular/forms';
import {removeSpaces} from '../utility';
import {userOptions} from "../services/menu.permissions";
import LANG from './lang';
import USER_TYPE from './user-type';

export const CREATE_USER = {
  createUserField: [
    {
      placeholder: "USERS.CREATE_FIELDS.USER_ID.LABEL",
      formControlName: 'userId',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    // {
    //   placeholder: 'Password',
    //   formControlName: 'password',
    //   inputType: 'pcm-password',
    //   required : true,
    //   validation: ['', [Validators.required, removeSpaces]]
    // },
    {
      placeholder: "USERS.CREATE_FIELDS.FIRST_NAME.LABEL",
      formControlName: 'firstName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: "USERS.CREATE_FIELDS.MIDDLE_NAME.LABEL",
      formControlName: 'middleName',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: "USERS.CREATE_FIELDS.LAST_NAME.LABEL",
      formControlName: 'lastName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: "USERS.CREATE_FIELDS.EMAIL.LABEL",
      formControlName: 'email',
      inputType: 'pcm-text',
      required: true,
      validation: ['', [Validators.required, Validators.email, removeSpaces]]
    },
    {
      placeholder: "USERS.CREATE_FIELDS.PHONE.LABEL",
      formControlName: 'phone',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: "USERS.CREATE_FIELDS.ROLE.LABEL",
      formControlName: 'userRole',
      inputType: 'pcm-select',
      options: [],
      validation: [
        '',
        [Validators.required]
      ]
    },
    {
      placeholder: "USERS.CREATE_FIELDS.STATUS.LABEL",
      formControlName: 'status',
      inputType: 'pcm-select',
      options: [
        {label: 'Inactive', value: false},
        {label: 'Active', value: true}
      ],
      validation: [false]
    },
    {
      placeholder: "USERS.CREATE_FIELDS.SEL_LANG.LABEL",
      formControlName: 'lang',
      inputType: 'pcm-select',
      options: LANG,
      validation: [false]
    },
    {
      placeholder: "USERS.CREATE_FIELDS.USER_TYPE.LABEL",
      formControlName: 'userType',
      inputType: 'pcm-select',
      options: USER_TYPE,
      validation: [false]
    },
    {
      placeholder: "USERS.CREATE_FIELDS.EXTERNAL_ID.LABEL",
      formControlName: 'externalId',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {placeholder: 'Assign Partner', formControlName: 'assignPartner', inputType: 'pcm-list'},
    {placeholder: 'Assign Group', formControlName: 'assignGroup', inputType: 'pcm-list'}
  ],
  manageUserFields: [
    {
      placeholder: "USERS.MANAGE_FIELDS.USER_ID.LABEL",
      formControlName: 'userId',
      inputType: 'pcm-text',
      validations: ['']
    },
    {
      placeholder: "USERS.MANAGE_FIELDS.FIRST_NAME.LABEL",
      formControlName: 'firstName',
      inputType: 'pcm-text',
      validations: ['']
    },
    {
      placeholder: "USERS.MANAGE_FIELDS.LAST_NAME.LABEL",
      formControlName: 'lastName',
      inputType: 'pcm-text',
      validations: ['']
    },
    {
      placeholder: "USERS.MANAGE_FIELDS.EMAIL.LABEL",
      formControlName: 'email',
      inputType: 'pcm-text',
      validations: ['']
    },
    {
      placeholder: "USERS.MANAGE_FIELDS.ROLE.LABEL",
      formControlName: 'userRole',
      inputType: 'pcm-select',
      options: [],
      validation: ['']
    },
    {
      placeholder: "USERS.MANAGE_FIELDS.STATUS.LABEL", formControlName: 'status', inputType: 'pcm-select',
      options: [
        {label: 'Active', value: true},
        {label: 'Inactive', value: false},
      ],
      validation: ['']
    }
  ]
};
