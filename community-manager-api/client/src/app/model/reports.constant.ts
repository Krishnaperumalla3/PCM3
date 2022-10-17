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

import {createFormControl} from '../utility';

const rulePropertyFields = [
  ['ruleValue', "REPORTS.FIELDS.RULE_PRP_NME.LABEL", 'select', false,[]],
  ['rulePropertyValue', "REPORTS.FIELDS.RULE_PRP_VAL.LABEL", 'text', false]
].map(field=> createFormControl(...field));

const dataFlowFields = [
  // formControlName, placeholder = '', inputType, validation = false, options = []
  ['partnerName', "REPORTS.FIELDS.PARTNER_NAME.LABEL", 'text', false],
  ['applicationName', "REPORTS.FIELDS.APP_NAME.LABEL", 'text', false],
  ['seqType', "REPORTS.FIELDS.SEQ_TYPE.LABEL", 'select', false, 'options'],
  ['flow', "REPORTS.FIELDS.FLOW.LABEL", 'select', false, 'options'],
  ['fileType', "REPORTS.FIELDS.FILE_NAME.LABEL", 'text', false],
  ['docType', "REPORTS.FIELDS.DOC_TYPE.LABEL", 'text', false],
  ['transaction', "REPORTS.FIELDS.TRANSACTION.LABEL", 'text', false],
  ['senderId', "REPORTS.FIELDS.SENDER_ID.LABEL", 'text', false],
  ['receiverId', "REPORTS.FIELDS.RECEIVER_ID.LABEL", 'text', false],
  ['ruleName', "REPORTS.FIELDS.RULE_NAME.LABEL", 'text', false],
  ['ruleValue', "REPORTS.FIELDS.RULE_VALUE.LABEL", 'text', false],

].map(field => createFormControl(...field));

const overFlowFields = [
  // formControlName, placeholder = '', inputType, validation = false, options = []
  ['fileArrived', 'File Arrived', 'text', false],
  ['partner', "REPORTS.FIELDS.PARTNER.LABEL", 'text', false],
  ['transaction', "REPORTS.FIELDS.TRANSACTION.LABEL", 'text', false],
  ['destFileName', "REPORTS.FIELDS.DEST_FILE_NAME.LABEL", 'text', false],
  ['status', "REPORTS.FIELDS.STATUS.LABEL", 'text', false],
  ['senderId', "REPORTS.FIELDS.SENDER_ID.LABEL", 'text', false],
  ['receiverId', "REPORTS.FIELDS.RECEIVER_ID.LABEL", 'text', false],
  ['gsCount', "REPORTS.FIELDS.GS_COUNT_ID.LABEL", 'text', false],

].map(field => createFormControl(...field));

export const REPORT_FORM = {
  DATA_FLOW: dataFlowFields,
  OVERFLOW: overFlowFields,
  PROPERTY: rulePropertyFields
};
