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
import {removeSpaces} from '../utility';

export const DATA_FLOW_OBJ = {
  build: [
    {
      placeholder: 'DATA_FLOW.FIELDS.PRTNR_PRDCR.LABEL',
      formControlName: 'partnerProfile',
      inputType: 'pcm-select',
      options: [],
      required: true,
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'DATA_FLOW.FIELDS.APP_CUST.LABEL',
      formControlName: 'applicationProfile',
      inputType: 'pcm-select',
      options: [],
      required: true,
      validation: ['', [Validators.required]]
    }
  ],
  MFT: [
    {
      placeholder: 'DATA_FLOW.MFT.IN_FL_NME_REG_EXP.LABEL',
      formControlName: 'fileName',
      inputType: 'pcm-text',
      required: true,
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'DATA_FLOW.MFT.IN_FLTR.LABEL',
      formControlName: 'filter',
      inputType: 'pcm-text',
      validation: ['']
    }
  ],
  DocHandling: [
    {
      placeholder: 'DATA_FLOW.DocHandling.IN_FLTR.LABEL',
      formControlName: 'fileName',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'DATA_FLOW.DocHandling.IN_DOC_TYPE.LABEL',
      formControlName: 'documentType',
      inputType: 'pcm-text',
      required: true,
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'DATA_FLOW.DocHandling.IN_VER.LABEL',
      formControlName: 'version',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'DATA_FLOW.DocHandling.IN_TRNS.LABEL',
      formControlName: 'transaction',
      inputType: 'pcm-text',
      required: true,
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'DATA_FLOW.DocHandling.IN_SND_ID.LABEL',
      formControlName: 'senderId',
      inputType: 'pcm-text',
      required: true,
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'DATA_FLOW.DocHandling.IN_RCR_ID.LABEL',
      formControlName: 'receiverId',
      inputType: 'pcm-text',
      required: true,
      validation: ['', [Validators.required]]
    }
  ]
};

export const COPY_DATA_FLOW =  [
  {
    label: 'DATA_FLOW.COPY_DATA_FLOW.IN_BND_LABEL',
    type: 'card',
    fields: [{
      placeholder: 'DATA_FLOW.COPY_DATA_FLOW.FIELDS.IN_SND_ID.LABEL',
      formControlName: 'inSenderId',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'DATA_FLOW.COPY_DATA_FLOW.FIELDS.IN_RCR_ID.LABEL',
      formControlName: 'inReceiverId',
      inputType: 'pcm-text',
      validation: ['']
    }]
  },
  {
    label: 'DATA_FLOW.COPY_DATA_FLOW.OUT_BND_LABEL',
    type: 'card',
    fields: [{
      placeholder: 'DATA_FLOW.COPY_DATA_FLOW.FIELDS.IN_SND_ID.LABEL',
      formControlName: 'outSenderId',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'DATA_FLOW.COPY_DATA_FLOW.FIELDS.IN_RCR_ID.LABEL',
      formControlName: 'outReceiverId',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    }
  ]
}];


export const SEARCH_WORKFLOW = {
  searchField: [
    {
      placeholder: 'DATA_FLOW.SEARCH_WORKFLOW.IN_PRT_NME.LABEL',
      formControlName: 'partnerName',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'DATA_FLOW.SEARCH_WORKFLOW.IN_APP_NME.LABEL',
      formControlName: 'applicationName',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'DATA_FLOW.SEARCH_WORKFLOW.IN_SEQ_TYPE.LABEL',
      formControlName: 'seqType',
      inputType: 'pcm-select',
      required: true,
      validation: [''],
      options: [
        {label: 'MFT', value: 'MFT'},
        {label: 'DocHandling', value: 'DocHandling'}
      ]
    },
    {
      placeholder: 'DATA_FLOW.SEARCH_WORKFLOW.IN_FLW.LABEL',
      formControlName: 'flow',
      inputType: 'pcm-select',
      required: true,
      validation: [''],
      options: [
        {label: 'Inbound', value: 'Inbound'},
        {label: 'Outbound', value: 'Outbound'}
      ]
    },
    {
      placeholder: 'DATA_FLOW.SEARCH_WORKFLOW.IN_FL_NME.LABEL',
      formControlName: 'fileType',
      inputType: 'pcm-text',
      // required: true,
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'DATA_FLOW.SEARCH_WORKFLOW.IN_DOC_TYPE.LABEL',
      formControlName: 'docType',
      inputType: 'pcm-text',
      // required: true,
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'DATA_FLOW.SEARCH_WORKFLOW.IN_TRNS.LABEL',
      formControlName: 'transaction',
      inputType: 'pcm-text',
      // required: true,
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'DATA_FLOW.SEARCH_WORKFLOW.IN_SND_ID.LABEL',
      formControlName: 'senderId',
      inputType: 'pcm-text',
      // required: true,
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'DATA_FLOW.SEARCH_WORKFLOW.IN_RCR_ID.LABEL',
      formControlName: 'receiverId',
      inputType: 'pcm-text',
      // required: true,
      validation: ['', [removeSpaces]]
    }
  ]
};

export const ENDPOINT_FLOW_OBJ = {
  build: [
    {
      placeholder: 'Frontend Gateway',
      formControlName: 'endpointProfile',
      inputType: 'pcm-select',
      options: [],
      required: true,
      validation: ['', [Validators.required]]
    }
  ]
};
