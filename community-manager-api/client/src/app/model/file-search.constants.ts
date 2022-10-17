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

import { Validators } from '@angular/forms';

export const FILE_TRANSFER = {
    searchFields : [
        {placeholder: 'Direction', formControlName : 'flowInOut', inputType: 'pcm-select', options:
        [
            {label: 'Inbound', value: 'Inbound'},
            {label: 'Outbound', value: 'Outbound'}
        ]},
        {placeholder: 'Transfer Type', formControlName : 'typeOfTransfer', inputType: 'pcm-select', options:
        [
            {label: 'MFT', value: 'MFT'},
            {label: 'DocHandling', value: 'DocHandling'}
        ]},
        {placeholder: 'Doc Type', formControlName : 'doctype', inputType: 'pcm-select', options:
        [
            {label: 'EDIFACT', value: 'EDIFACT'},
            {label: 'FLATFILE', value: 'FLATFILE'},
            {label: 'XML', value: 'XML'},
            {label: 'X12', value: 'X12'}
        ]},
        {placeholder: 'Status', formControlName : 'status', inputType: 'pcm-select', options:
        [
            {label: 'Success', value: 'Success'},
            {label: 'Failed', value: 'Failed'},
            {label: 'Reprocessed', value: 'Reprocessed'},
            // {label: 'ManualReprocess', value: 'ManualReprocess'},
            // {label: 'MarkedSuccess', value: 'MarkedSuccess'},
            {label: 'No Action Required', value: 'No Action Required'}
        ]},
        { placeholder: 'Source Protocol', formControlName : 'srcprotocol', inputType: 'pcm-select', options: []},
        { placeholder: 'Dest. Protocol', formControlName : 'destprotocol', inputType: 'pcm-select', options: []},
        { placeholder: 'Partner' , formControlName : 'partner', inputType: 'pcm-text', validation: ['', [Validators.required]]},
        { placeholder: 'Application' , formControlName : 'application', inputType: 'pcm-text', validation: ['', [Validators.required]]},
        { placeholder: 'Sender Id' , formControlName : 'senderId', inputType: 'pcm-text', validation: ['', [Validators.required]]},
        { placeholder: 'Receiver Id' , formControlName : 'reciverId', inputType: 'pcm-text', validation: ['', [Validators.required]]},
        { placeholder: 'Transaction' , formControlName : 'transfile', inputType: 'pcm-text', validation: ['', [Validators.required]]},
        { placeholder: 'Error Status' , formControlName : 'errorstatus', inputType: 'pcm-text', validation: ['', [Validators.required]]},
        { placeholder: 'Src.File Name' , formControlName : 'srcFileName', inputType: 'pcm-text', validation: ['', [Validators.required]]},
        { placeholder: 'Dest.File Name' , formControlName : 'destFileName', inputType: 'pcm-text', validation: ['', [Validators.required]]},
        ]
};
