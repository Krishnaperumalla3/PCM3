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

// [formControlName, placeholder = '', inputType, validation = false, options = []]

import {createFormControl, numberDropdownList} from '../utility';

const trueOrFalseOptions = [
  {label: 'YES', value: 'YES'},
  {label: 'NO', value: 'NO'},
];

const useMailOrBPList = [
  {label: 'Use Mailbox', value: 'Use Mailbox'},
  {label: 'Use BP', value: 'Use BP'},
];

const useCorrelationList = [
  {label: 'All', value: 'All'},
  {label: 'Default', value: 'Default'},
  {label: 'Wildcard', value: 'Wildcard'},
  {label: 'Only', value: 'Only'},
  {label: 'NO', value: 'NO'},
];

const ackDetailLevelList = [
  {label: 'Data Element', value: 'Data Element'},
  {label: 'Group', value: 'Group'},
  {label: 'Segment', value: 'Segment'},
  {label: 'Transaction', value: 'Transaction'},
];

const hoursDropdownList = numberDropdownList(24);
const minutesDropdownList = numberDropdownList(60);

const EDI_PROPERTIES = [
  ['partnerName', 'Partner Profile', 'select', true],
  ['direction', 'Direction', 'select', true, [{label: 'Inbound', value: 'Inbound'}, {
    label: 'Outbound',
    value: 'Outbound'
  }]],
  ['validateInput', 'Validate Input', 'select', true, trueOrFalseOptions],
  ['validateOutput', 'Validate Output', 'select', true, trueOrFalseOptions],
  ['useIndicator', 'Test Indicator', 'select', true, [
    {label: 'Information', value: 'I'},
    {label: 'Production', value: 'P'},
    {label: 'Test', value: 'T'}]],
].map(field => createFormControl(...field));

const ISA_SEGMENT = [
  ['isaSenderIdQal', 'Sender ID Qualifier(ISA05)'],
  ['isaReceiverIdQal', 'Receiver ID Qualifier(ISA07)'],
  ['isaSenderId', 'Sender ID(ISA06)', 'text', true],
  ['isaReceiverId', 'Receiver ID(ISA08)', 'text', true],
  ['interVersion', 'Interchange Version(ISA12)', 'text', true],
  ['globalContNo', 'Global Control Number', 'select', true, trueOrFalseOptions],
  ['invokeBPForIsa', 'Invoke BP For ISA', 'select', true, trueOrFalseOptions],
  ['businessProcess', 'Business Process', 'select', true],
  ['perContNumCheck', 'Perform Control Number Sequence Check', 'select', true, trueOrFalseOptions],
  ['perDupNumCheck', 'Perform Duplicate Control Number Check', 'select', true, trueOrFalseOptions],
  ['isaAcceptLookAlias', 'Accepter Lookup Alias', 'text', true],
].map(field => createFormControl(...field));

const GS_SEGMENT = [
  ['gsSenderId', 'Sender ID(GS02)', 'text', true],
  ['gsReceiverId', 'Receiver ID(GS03)', 'text', true],
  ['functionalIdCode', 'Functional ID Code(GS01)', 'text', true],
  ['respAgencyCode', 'Responsible Agency Code(GS07)', 'text', true],
  ['groupVersion', 'Group Version(GS08)', 'text', true],
].map(field => createFormControl(...field));

const INBOUND_ENVELOPE = [
  ['complianceCheck', 'Compliance Check', 'select', true, trueOrFalseOptions],
  ['complianceCheckMap', 'Compliance Check Map', 'select', true, trueOrFalseOptions],
  ['retainEnv', 'Retain Enclosing Envelope', 'select', true, trueOrFalseOptions],
  ['genInboundAck', 'Generate an Acknowledgement', 'select', true, [
    {label: 'When Requested', value: 'When Requested'},
    ...trueOrFalseOptions
  ]],
  ['ackDetailLevel', 'Acknowledgement Detail Level', 'select', true, ackDetailLevelList],
].map(field => createFormControl(...field));

const ST_SEGMENT = [
  ['stSenderId', 'Sender ID', 'text', true],
  ['stReceiverId', 'Receiver ID', 'text', true],
  ['trnSetIdCode', 'Transaction Set Id Code', 'text', true],
  ['acceptLookAlias', 'Accepter Lookup Alias', 'text', true],
].map(field => createFormControl(...field));

const OUTBOUND_ENVELOPE = [
  ['segTerm', 'Segment Terminator', 'select', true],
  ['eleTerm', 'Data Element Terminator', 'select', true], // Need to ask for key
  ['subEleTerm', 'Component Element Terminator', 'select', true], // Need to ask for key
  ['releaseCharacter', 'Release Character', 'select', true],
  ['useCorrelation', 'Use Correlation Overrides', 'select', true, useCorrelationList],
  ['dataExtraction', 'Data Extraction', 'select', true, useMailOrBPList],
  ['extractionMailBox', 'Extraction Mailbox', 'text', true],
  ['extractionMailBoxBp', 'Extraction Mailbox BP', 'select', true],
  ['expectAck', 'Expect Acknowledgement', 'select', true, trueOrFalseOptions],
  ['intAckReq', 'Interchange Acknowledgement Requested', 'select', true, trueOrFalseOptions],
  ['ackOverDueHr', 'Acknowledgement Overdue Time (Hours)', 'select', true, hoursDropdownList],
  ['ackOverDueMin', 'Acknowledgement Overdue Time (Minutes)', 'select', true, minutesDropdownList],
].map(field => createFormControl(...field));

const payload = {
  'acceptLookAlias': 'string',
  'ackDetailLevel': 'string',
  'ackOverDueHr': 'string',
  'ackOverDueMin': 'string',
  'businessProcess': 'string',
  'complianceCheck': 'string',
  'complianceCheckMap': 'string',
  'dataExtraction': 'string',
  'direction': 'string',
  'eleTerm': 'string',
  'expectAck': 'string',
  'extractionMailBox': 'string',
  'extractionMailBoxBp': 'string',
  'functionalIdCode': 'string',
  'genInboundAck': 'string',
  'globalContNo': 'string',
  'groupVersion': 'string',
  'gsReceiverId': 'string',
  'gsSenderId': 'string',
  'intAckReq': 'string',
  'interVersion': 'string',
  'invokeBPForIsa': 'string',
  'isaAcceptLookAlias': 'string',
  'isaReceiverId': 'string',
  'isaReceiverIdQal': 'string',
  'isaSenderId': 'string',
  'isaSenderIdQal': 'string',
  'partnerName': 'string',
  'partnerPkId': 'string',
  'perContNumCheck': 'string',
  'perDupNumCheck': 'string',
  'pkId': 'string',
  'releaseCharacter': 'string',
  'respAgencyCode': 'string',
  'retainEnv': 'string',
  'segTerm': 'string',
  'stReceiverId': 'string',
  'stSenderId': 'string',
  'subEleTerm': 'string',
  'trnSetIdCode': 'string',
  'useCorrelation': 'string',
  'useIndicator': 'string',
  'validateInput': 'string',
  'validateOutput': 'string'
};

export const CREATE_X2_ENVELOPE_FORM_FIELDS = {
  EDI_PROPERTIES,
  ISA_SEGMENT,
  GS_SEGMENT,
  INBOUND_ENVELOPE,
  ST_SEGMENT,
  OUTBOUND_ENVELOPE
};

export const MANAGE_X2_ENVELOPE_FORM_FIELDS = [
  ['partnerName', 'Partner Profile', 'select'],
  ['direction', 'Direction', 'select', false, [{label: 'InBound', value: 'Inbound'}, {
    label: 'OutBound',
    value: 'Outbound'
  }]],
  ['version', 'Version'],
  ['transaction', 'Transaction'],
  ['isaSenderId', 'ISA Sender ID'],
  ['isaReceiverId', 'ISA Receiver  ID'],
  ['gsSenderId', 'GS Sender ID'],
  ['gsReceiverId', 'GS Receiver ID'],
  ['stSenderId', 'ST Sender ID'],
  ['stReceiverId', 'ST Receiver ID'],
].map(field => createFormControl(...field));
