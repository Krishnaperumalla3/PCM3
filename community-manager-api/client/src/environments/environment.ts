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

// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: true,

  SEAS_TOKEN_INFO: '/pcm/seas/get-token-info',
  SEAS_TOKEN_GEN: '/pcm/seas/generate-token',
  SSP_LOGOUT_URL: '/pcm/seas/get-ssp-logout-url',

  CHANGE_LANG: '/pcm/user/lang',
  GET_LOGO: 'pcm/utility/get-logo',
  ACTIVE_PROFILE: '/pcm/utility/active-profile',
  SAML_LOGOUT: '/saml/logout?local=true',
  IS_VALID: '/pcm/utility/is-valid',
  GET_PARTNER_LIST: '/pcm/partner/partners-list',
  GET_PROTOCOL_LIST: '/pcm/utility/protocols-list',
  GET_PROTOCOL_MAP: '/pcm/utility/protocols-map',
  GET_AS2_ORG_PROFILES_LIST: '/pcm/partner/org-profile/partners-list',
  GET_AS2_PARTNER_PROFILES_LIST: '/pcm/partner/partner-profile/partners-list',
  GET_CORRELATION: '/pcm/correlation',
  GET_ADAPTER_NAMES: '/pcm/utility/adapter-names',
  GET_CA_CERT_MAP: '/pcm/utility/ca-cert-map',
  GET_SSH_KEY_PAIR: '/pcm/utility/ssh-key-pair',
  GET_MAP_NAME: '/pcm/utility/si-map-list',
  SYSTEM_CERT_MAP: 'pcm/utility/system-cert-map',
  GET_TRUSTED_LIST: '/pcm/utility/trusted-cert-list',
  GET_TRUSTED_MAP: '/pcm/utility/trusted-cert-map',
  GET_IS_MFT_DUPLICATE: '/pcm/utility/is-mft-duplicate',
  GET_USERID: '/pcm/general/forgot-password/',
  UPDATE_PASSWORD: '/pcm/general/forgot/update-password',
  CHANGE_PASSWORD: '/pcm/user/change-password',

  GET_BP_LIST: '/pcm/utility/si-bp-list',
  GET_TERMINATORS_MAP_LIST: '/pcm/utility/envelop-terminators-map-list',
  CREATE_PARTNER_PROTOCOL: '/pcm/partner/',
  SEARCH_PARTNER: 'pcm/partner/search',
  STATUS_OF_PARTNER: '/pcm/partner/status',
  DELETE_PARTNER: '/pcm/partner/',
  GET_PARTNERS_BY_PROTOCOL: '/pcm/partner/find-by-protocol',
  GET_PARTNER_ACTIVITY: '/pcm/partner/activity/',
  GET_APPLICATION_BY_PROTOCOL: '/pcm/application/applications-by-protocol',

  CREATE_APPLICATION_PROTOCOL: '/pcm/application/search',
  STATUS_OF_APPLICATION: '/pcm/application/status',
  CREATE_APPLICATION: '/pcm/application/',
  GET_APPLICATION_ACTIVITY: '/pcm/application/activity/',
  SSH_KHOST_LIST: '/pcm/utility/ssh-khost-key-cert-list',

  SEARCH_FILES: '/pcm/reports/search',
  FILE_TRANSFER_ACTIVITY: '/pcm/reports/activity',
  FILE_TRANSFER_DROP_AGAIN: '/pcm/reports/drop-again',
  FILE_TRANSFER_PICK_NOW: '/pcm/reports/pickup-now',
  FILE_TRANSFER_REPROCESS: '/pcm/reports/reprocess',
  FILE_TRANSFER_PEM: '/pcm/reports/pem/',
  FILE_DOWNLOAD: '/pcm/reports/download-file',
  FILE_VIEW: '/pcm/reports/view-file',
  MULTI_PROCESS: '/pcm/reports/multi-reprocess',
  FILE_STATUS_CHANGE: '/pcm/reports/file/status-change',
  UPLOAD_FILE: '/pcm/reports/upload-file',
  ACTIVITY_DF: '/pcm/reports/activity/download-file',

  SEARCH_WORK_FLOW: '/pcm/workflow?',

  GET_POOLING_INTERVAL: '/pcm/pooling-interval',
  GET_PARTNER_MAP: '/pcm/partner/partners-map',
  GET_APP_LIST: '/pcm/application/application-map',
  GET_RULES: '/pcm/rule',

  DELETE_WORKFLOW: '/pcm/workflow',
  IMPORT_WORKFLOW: '/pcm/workflow/import',
  EXPORT_WORKFLOW: '/pcm/workflow/export',
  COPY_WORKFLOW: '/pcm/workflow',
  GET_ACTIVITY: '/pcm/workflow/activity/',

  CREATE_RULE: '/pcm/rule',
  SEARCH_RULE: '/pcm/rule/search',
  DELETE_RULE: '/pcm/rule',


  CREATE_WORKFLOW: '/pcm/workflow/create',

  GET_GROUP_LIST: '/pcm/group',
  SEARCH_GROUP: '/pcm/group/search',
  FIND_GROUP: '/pcm/group',

  ROLES: '/pcm/user/roles',
  CREATE_USER: '/pcm/user',
  SEARCH_USER: '/pcm/user/search',
  DELETE_USER: '/pcm/user',
  CHANGE_USER_STATUS: '/pcm/user/status',
  PCM_POOLING_INTERVAL: '/pcm/pooling-interval',
  UPD_USR_PRFRD_LANG: '/pcm/user/lang',

  UPLOAD_SYSTEM_CERT: '/pcm/certificate/upload-system-cert',
  UPLOAD_TRUSTED_CERT: '/pcm/certificate/upload-trusted-cert',
  UPLOAD_CA_CERT: '/pcm/certificate/upload-ca-cert',
  UPLOAD_KHOST_KEY: '/pcm/certificate/upload-ssh-known-host-key',
  UPLOAD_UID_KEY: '/pcm/certificate/upload-SSH-UID-key',
  UPLOAD_SSH_AUTHORIZED_KEY: '/pcm/certificate/upload-SSH-authorized-user-key',

  USER_LOGIN: '/pcm/generate-token',
  USER_SM_LOGIN: '/pcm/utility/is-sm-login',

  SEARCH_FLOWS: '/pcm/workflow/search-transactions',
  SEARCH_APPLIED_RULES: '/pcm/workflow/search-applied-rules',

  REPORTS: {
    GET_RULE_NAMES: '/pcm/rule',
    GET_RULE_PROPERTIES: '/pcm/rule/ruleProperties',
    EXPORT_ALL: '/pcm/workflow/export-all',
    DATA_FLOW: '/pcm/workflow/search',
    OVER_DUE: '/pcm/reports/overdue',
    PRODUCERDATA: '/pcm/new/reports/producerData',
    CONSUMERDATA: '/pcm/new/reports/consumerData',
    UIDDATA: '/pcm/new/reports/uidData',
    APP: '/pcm/new/reports/app-data',
    BU: '/pcm/new/reports/bu-data',

    SFGGRAPHAPI: '/pcm/new/reports/graphData',
    BARCHART: '/pcm/new/reports/getProducerConsumerData',
    LINECHART: '/pcm/new/reports/getDateFileCountFileSizeData',

    INT_EXTCOUNTS: '/pcm/new/reports/external-internal-count-data',
    TOPCHARGEBACK: '/pcm/new/reports/top-chargeback-data',
    TOPFILESIZE: '/pcm/new/reports/top-file-size-data',
    TOTALCOUNTSALL: '/pcm/new/reports/totalCountData',
    SEARCH_CHARGE_BACKS: '/pcm/new/reports/reportTotalCounts',

    MONTHLYAPI: '/pcm/new/reports/monthly-data',
    QUARTERLYAPI: '/pcm/new/reports/quarterly-data',
    SNODE: '/pcm/new/reports/snode-data',
    PNODE: '/pcm/new/reports/pnode-data',
    SRCDESTFILEDATA: '/pcm/new/reports/srcfilename-destfilename-count-data',
    TOTALPRODUCERCONSUMERCOUNT: '/pcm/new/reports/producer-consumer-count-data',
    // tslint:disable-next-line:max-line-length
    TRANSACTION_VOLUME: ({
                           start,
                           end
                         }) => `/pcm/reports/transaction-volume?start=${encodeURIComponent(start)}&end=${encodeURIComponent(end)}`,
    DOCTYPE_VOLUME: ({
                       start,
                       end
                     }) => `/pcm/reports/doctype-volume?start=${encodeURIComponent(start)}&end=${encodeURIComponent(end)}`,
    PARTNER_VOLUME: ({
                       start,
                       end
                     }) => `/pcm/reports/partner-volume?start=${encodeURIComponent(start)}&end=${encodeURIComponent(end)}`,
    LAST_36MONTHS_FILES: '/pcm/reports/files-processed/by-month/last-36-months',
    LAST_31DAYS_FILES: '/pcm/reports/files-processed/peak-hours/last-31-days',
    LAST_30DAYS_FILES: '/pcm/reports/files-processed/by-hour/last-30-days',
    LAST_12MONTHS_FILES: '/pcm/reports/files-processed/by-day/last-12-months',
    LAST_4WEEKS_FILES: '/pcm/reports/files-processed/by-day-week/avg-last-4-weeks-by-day',
    INACTIVE_PARTNERS: '/pcm/reports/inactive-partners/last-n-days',

    THIS_HOUR: '/pcm/reports/files-processed/this-hour',
    THIS_DAY: '/pcm/reports/files-processed/this-day',
    THIS_WEEK: '/pcm/reports/files-processed/this-week',
    THIS_MONTH: '/pcm/reports/files-processed/this-month',

    LAST_30DAYS_FILESIZE: '/pcm/reports/files-size-processed/by-hour/last-30-days',
    LAST_12MONTHS_FILESIZE: '/pcm/reports/files-size-processed/by-day/last-12-months',
    LAST_36MONTHS_FILESIZE: '/pcm/reports/files-size-processed/by-month/last-36-months'
  },

  ENVELOPE: {
    CREATE_X12: '/pcm/envelope',
    MANAGE_X12: '/pcm/envelope/search',
    ACTIVITY: '/pcm/envelope/activity/'
  },

  IS_B2B_ACTIVE: '/pcm/utility/is-b2b-active',
  AS2_PROFILES_MAPPING: '/pcm/partner/as2/org-profile/mapping',
  GET_TIME_RANGE: '/pcm/utility/search-date-range',
  GET_CIPHER_SUITES: '/pcm/utility/cd/get-cipher-suites',
  GET_SSH_USER_LIST: '/pcm/utility/get-ssh-user-key-list',

  GET_SCHEDULE: '/pcm/file/scheduler/config',
  GET_CLOUD_LST: 'pcm/utility/get-cloud-list',
  GET_REGIONS: 'pcm/utility/get-cloud-aws-regions',

  GET_TRANSACTIONS: '/pcm/transaction-names',

  POST_FILE_DROP: '/pcm/mailbox/file-drop',
  CHUNK_UPLOAD: '/pcm/file/upload/chunk',
  CHUNK_UPLOAD_START: '/pcm/file/upload/start',
  CHUNK_UPLOAD_END: '/pcm/file/upload/end-mailbox',
  PARTNER_MAILBOX: '/pcm/partner-mailbox/getPartnerMailboxes',
  GET_MAILBOX: '/pcm/partner-mailbox/getFilesFromMailboxes',
  DOWNLOAD_MAILBOX: '/pcm/partner-mailbox/downloadFile',
  MFT_FILE_UPLOAD: '/pcm/file/upload/file',
  MFT_MB_ACTIVITY: '/pcm/file/activity/',
  FILE_REPORTS: '/pcm/file-operator/reports/activity/',
  FILE_OPERATOR_SEARCH: '/pcm/file-operator/reports/search',

  B2B_GROUPS: '/pcm/si/group/get-names-list',
  B2B_MAILBOX: '/pcm/si/mailbox/get-list',
  B2B_USER: '/pcm/si/user/get-gp-per',
  B2B_UPDATE_USER: '/pcm/si/user/assign-gp-per',

  PROXY_ENDPOINT: '/pcm/api/proxy-endpoint',
  GET_ENDPOINT_MAP: 'pcm/api/proxy-endpoint/endpoints-map',
  ENDPOINT_FLOW: '/pcm/api/workflow',
  CREATE_ENDPOINT_FLOW: '/pcm/api/workflow/create',
  DELETE_ENDPOINT_FLOW: '/pcm/api/workflow',

  // Charge back API's
  GET_CHARGES: '/pcm/chargeback/chargeback-data',
  UPDATE_CHARGES: '/pcm/chargeback/update-chargeback',

  USER_ACTIVITY: '/pcm/user-activity',
  USER_UNLOCK: '/pcm/user/unlock-user',

  LOGOUT: '/pcm/logout'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
