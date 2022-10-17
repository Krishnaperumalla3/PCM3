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
import {environment} from '../../environments/environment';
import {phoneNumValidation, removeSpaces} from '../utility';

export const protocolAPIS = {
  SFGFTP: 'remote-ftp',
  SFGFTPS: 'remote-ftp',
  SFGSFTP: 'remote-ftp',
  HTTP: 'http',
  HTTPS: 'http',
  SFG_CONNECT_DIRECT: 'remote-connect-direct',
  CONNECT_DIRECT: 'connect-direct',
  FTP: 'ftp',
  FTPS: 'ftp',
  SFTP: 'ftp',
  ExistingConnection: 'ec',
  Mailbox: 'mailbox',
  FileSystem: 'filesystem',
  MQ: 'mq',
  SAP: 'sap',
  AS2: 'remote-as2',
  Webservice: 'webservice',
  AWS_S3: 'aws-s3',
  CUSTOM_PROTOCOL: 'customprotocol',
  GOOGLE_DRIVE: 'google-drive'
};

export const partnerProtocols = {
  PartnerFields: [
    {
      placeholder: "PARTNERS.FIELDS.IN_PARTNR_NME.LABEL",
      formControlName: 'profileName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: "PARTNERS.FIELDS.IN_PARTNR_ID.LABEL",
      formControlName: 'profileId',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: "PARTNERS.FIELDS.IN_EMAIL.LABEL",
      formControlName: 'emailId',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: "PARTNERS.FIELDS.IN_PHONE.LABEL",
      formControlName: 'phone',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: "PARTNERS.FIELDS.IN_PGP_INFO.LABEL",
      formControlName: 'pgpInfo',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: "PARTNERS.FIELDS.IN_PROTOCOL.LABEL",
      formControlName: 'protocol',
      inputType: 'pcm-select',
      validation: ['', [Validators.required]],
      options: [
        {label: 'HTTP', value: 'HTTP'},
        {label: 'HTTPS', value: 'HTTPS'},
        {label: 'AS2', value: 'AS2'},
        {label: 'FTP', value: 'FTP'}
      ]
    },
    {
      placeholder: "PARTNERS.FIELDS.IN_STATUS.LABEL",
      formControlName: 'status',
      inputType: 'pcm-select',
      validation: [false, [Validators.required]],
      options: [
        {label: 'Inactive', value: false},
        {label: 'Active', value: true}
      ]
    },
    {
      placeholder: "PARTNERS.FIELDS.IN_ADDR_LINE1.LABEL",
      formControlName: 'addressLine1',
      inputType: 'pcm-textarea',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: "PARTNERS.FIELDS.IN_ADDR_LINE2.LABEL",
      formControlName: 'addressLine2',
      inputType: 'pcm-textarea',
      validation: ['', [removeSpaces]]
    }
  ],
  CONNECT_DIRECT: [
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_LCL_ND_NME.LABEL',
      formControlName: 'localNodeName',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_RMTE_ND_NME.LABEL',
      formControlName: 'remoteNodeName',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_RMTE_HST.LABEL',
      formControlName: 'remoteHost',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_RMTE_PRT.LABEL',
      formControlName: 'remotePort',
      inputType: 'pcm-numeric',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_RMTE_USR.LABEL',
      formControlName: 'remoteUser',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_RMOTE_PWD.LABEL',
      formControlName: 'remotePassword',
      inputType: 'pcm-password',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_DCB_PARAMS.LABEL',
      formControlName: 'dcb',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_LCL_X.LABEL',
      formControlName: 'localXLate',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_SYS_OPTS.LABEL',
      formControlName: 'sysOpts',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_TRNSFR_NME.LABEL',
      formControlName: 'transferType',
      inputType: 'pcm-select',
      options: [
        {label: 'Binary', value: 'Binary'},
        {label: 'ASCII', value: 'ASCII'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_CD_PG_FRM.LABEL',
      formControlName: 'codePageFrom',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_CD_PG_TO.LABEL',
      formControlName: 'codePageTo',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_SEC_PRTCL.LABEL',
      formControlName: 'securityProtocol',
      inputType: 'pcm-select',
      validation: [''],
      options: [
        {label: 'TLS 1.0', value: 'TLS 1.0'},
        {label: 'TLS 1.1', value: 'TLS 1.1'},
        {label: 'TLS 1.2', value: 'TLS 1.2'}
      ],
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_SEC.LABEL',
      formControlName: 'securePlus',
      inputType: 'pcm-select',
      validation: [''],
      options: [
        {label: 'Enable', value: true},
        {label: 'Disable', value: false}
      ]
    },
    {
      formControlName: '',
      inputType: 'pcm-space',
      validation: ['']
    },
    {
      inputType: 'pcm-card',
      cards: [
        {
          title: 'CERTIFICATE.CERT_TITL',
          subFields: [
            {
              placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_CA_CERT.LABEL',
              formControlName: 'caCertificate',
              inputType: 'pcm-select',
              options: [],
              validation: [''],
              buttons: [
                {
                  title: 'CERTIFICATE.BUTTONS.UPLOAD_CA_CRT',
                  className: 'btn btn-info'
                }
              ],
              modal: {
                url: {
                  no: environment.UPLOAD_CA_CERT
                },
                fields: [
                  {
                    inputType: 'pcm-file',
                    placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                    formControlName: 'file',
                    validation: ['']
                  },
                  {
                    inputType: 'pcm-text',
                    placeholder: 'CERTIFICATE.IN_CA_CERT_NME.LABEL',
                    formControlName: 'certName',
                    validation: ['', [removeSpaces]]
                  }
                ]
              }
            },
            {
              placeholder: 'CERTIFICATE.IN_CIPHER_SUITS.LABEL',
              formControlName: 'cipherSuits',
              inputType: 'pcm-select',
              options: [],
              validation: ['', []]
            }
          ]
        }
      ]
    },
  ],
  GOOGLE_DRIVE:[

    {
      placeholder: 'Project Id',
      formControlName: 'projectId',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]],
      readonly: 'true'
    },

    {
      placeholder: 'Client Id',
      formControlName: 'clientId',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]],
      readonly: 'true'
    },
    {
      placeholder: 'Client Email',
      formControlName: 'clientEmail',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]],
      readonly: 'true'
    },

    {
      placeholder: 'In Directory',
      formControlName: 'inDirectory',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'Out Directory',
      formControlName: 'outDirectory',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'File Type',
      formControlName: 'fileType',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'Delete After Collection',
      formControlName: 'deleteAfterCollection',
      inputType: 'pcm-checkbox',
      validation: [false]
    },

  ],
  FTP: [
    {
      placeholder: 'PROTOCOLS.FTP.IN_HST_NME.LABEL',
      formControlName: 'hostName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_PRT_NUM.LABEL',
      formControlName: 'portNumber',
      inputType: 'pcm-numeric',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTP.TRBSFR_TYPE.LABEL',
      formControlName: 'transferType',
      inputType: 'pcm-select',
      options: [
        {label: 'Binary', value: 'Binary'},
        {label: 'ASCII', value: 'ASCII'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_USR_ID.LABEL',
      formControlName: 'userName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_PWD.LABEL',
      formControlName: 'password',
      inputType: 'pcm-password',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_CWD_UP.LABEL',
      formControlName: 'cwdUp',
      inputType: 'pcm-select',
      options: [
        {label: '0', value: '0'},
        {label: '1', value: '1'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_QUOTE.LABEL',
      formControlName: 'quote',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_SITE.LABEL',
      formControlName: 'site',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_CONCTN_TYPE.LABEL',
      formControlName: 'connectionType',
      inputType: 'pcm-select',
      options: [
        {label: 'Active', value: 'ACTIVE'},
        {label: 'Passive', value: 'PASSIVE'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_DESTN_MAIL.LABEL',
      formControlName: 'mbDestination',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_DESTN_MAIL_LKUP.LABEL',
      formControlName: 'mbDestinationLookup',
      inputType: 'pcm-select',
      options: [
        {label: '0', value: '0'},
        {label: '1', value: '1'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_CRTE_USR_SI.LABEL',
      formControlName: 'createUserInSI',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_IN_DIR_REQ.LABEL',
      formControlName: 'inDirectory',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_OUT_DIR.LABEL',
      formControlName: 'outDirectory',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_CRTE_DIR_SI.LABEL',
      formControlName: 'createDirectoryInSI',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_FL_TYPE.LABEL',
      formControlName: 'fileType',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      formControlName: '',
      inputType: 'pcm-space',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_DEL_COL.LABEL',
      formControlName: 'deleteAfterCollection',
      inputType: 'pcm-checkbox',
      validation: [false]
    }
  ],
  FTPS: [
    {
      placeholder: 'PROTOCOLS.FTPS.IN_HST_NME.LABEL',
      formControlName: 'hostName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_PRT_NUM.LABEL',
      formControlName: 'portNumber',
      inputType: 'pcm-numeric',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTPS.TRBSFR_TYPE.LABEL',
      formControlName: 'transferType',
      inputType: 'pcm-select',
      options: [
        {label: 'Binary', value: 'Binary'},
        {label: 'ASCII', value: 'ASCII'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_USR_ID.LABEL',
      formControlName: 'userName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_PWD.LABEL',
      formControlName: 'password',
      inputType: 'pcm-password',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_CWD_UP.LABEL',
      formControlName: 'cwdUp',
      inputType: 'pcm-select',
      options: [
        {label: '0', value: '0'},
        {label: '1', value: '1'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_QUOTE.LABEL',
      formControlName: 'quote',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_SITE.LABEL',
      formControlName: 'site',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_CONCTN_TYPE.LABEL',
      formControlName: 'connectionType',
      inputType: 'pcm-select',
      options: [
        {label: 'Active', value: 'ACTIVE'},
        {label: 'Passive', value: 'PASSIVE'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_DESTN_MAIL.LABEL',
      formControlName: 'mbDestination',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_DESTN_MAIL_LKUP.LABEL',
      formControlName: 'mbDestinationLookup',
      inputType: 'pcm-select',
      options: [
        {label: '0', value: '0'},
        {label: '1', value: '1'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_SSL_SOC.LABEL',
      formControlName: 'sslSocket',
      inputType: 'pcm-select',
      options: [
        {label: 'SSL_IMPLICIT', value: 'SSL_IMPLICIT'},
        {label: 'SSL_EXPLICIT', value: 'SSL_EXPLICIT'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_IN_DIR_REQ.LABEL',
      formControlName: 'inDirectory',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_OUT_DIR.LABEL',
      formControlName: 'outDirectory',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_CRTE_USR_SI.LABEL',
      formControlName: 'createUserInSI',
      inputType: 'pcm-checkbox'
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_FL_TYPE.LABEL',
      formControlName: 'fileType',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_SSL_CIPHER.LABEL',
      formControlName: 'sslCipher',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_CRTE_DIR_SI.LABEL',
      formControlName: 'createDirectoryInSI',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      formControlName: '',
      inputType: 'pcm-space'
    },
    {
      formControlName: '',
      inputType: 'pcm-space'
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_DEL_COL.LABEL',
      formControlName: 'deleteAfterCollection',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      inputType: 'pcm-card',
      cards: [
        {
          title: 'CERTIFICATE.CERT_TITL',
          subFields: [
            {
              placeholder: 'PROTOCOLS.FTPS.IN_CRT_ID.LABEL',
              formControlName: 'certificateId',
              inputType: 'pcm-select',
              validation: ['', [Validators.required]],
              options: [{label: 'Select', value: ''}],
              buttons: [
                {
                  title: 'CERTIFICATE.BUTTONS.UPLOAD_CERT',
                  className: 'btn btn-info'
                }
              ],
              modal: {
                url: {
                  yes: environment.UPLOAD_CA_CERT,
                  no: environment.UPLOAD_CA_CERT
                },
                fields: [
                  {
                    inputType: 'pcm-file',
                    placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                    formControlName: 'file',
                    validation: ['']
                  },
                  {
                    inputType: 'pcm-text',
                    placeholder: 'CERTIFICATE.IN_CERT_NME.LABEL',
                    formControlName: 'certName',
                    validation: ['', [removeSpaces]]
                  }
                ]
              }
            }
          ]
        }
      ]
    },
  ],
  SFTP: [
    {
      placeholder: 'PROTOCOLS.SFTP.IN_HST_NME.LABEL',
      formControlName: 'hostName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_PRT_NUM.LABEL',
      formControlName: 'portNumber',
      inputType: 'pcm-numeric',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.TRBSFR_TYPE.LABEL',
      formControlName: 'transferType',
      inputType: 'pcm-select',
      options: [
        {label: 'Binary', value: 'Binary'},
        {label: 'ASCII', value: 'ASCII'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_USR_ID.LABEL',
      formControlName: 'userName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_PWD.LABEL',
      formControlName: 'password',
      inputType: 'pcm-password',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_SSH_CIPHER.LABEL',
      formControlName: 'sshCipher',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_SSH_COMP.LABEL',
      formControlName: 'sshCompression',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_SSH_MAC.LABEL',
      formControlName: 'sshMac',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_CWD_UP.LABEL',
      formControlName: 'cwdUp',
      inputType: 'pcm-select',
      options: [
        {label: '0', value: '0'},
        {label: '1', value: '1'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_QUOTE.LABEL',
      formControlName: 'quote',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_SITE.LABEL',
      formControlName: 'site',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_CONCTN_TYPE.LABEL',
      formControlName: 'connectionType',
      inputType: 'pcm-select',
      options: [
        {label: 'Active', value: 'ACTIVE'},
        {label: 'Passive', value: 'PASSIVE'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_DESTN_MAIL.LABEL',
      formControlName: 'mbDestination',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_DESTN_MAIL_LKUP.LABEL',
      formControlName: 'mbDestinationLookup',
      inputType: 'pcm-select',
      options: [
        {label: '0', value: '0'},
        {label: '1', value: '1'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_CRTE_USR_SI.LABEL',
      formControlName: 'createUserInSI',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_IN_DIR_REQ.LABEL',
      formControlName: 'inDirectory',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_OUT_DIR.LABEL',
      formControlName: 'outDirectory',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_CRTE_DIR_SI.LABEL',
      formControlName: 'createDirectoryInSI',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_FL_TYPE.LABEL',
      formControlName: 'fileType',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_SSH_AUTH.LABEL',
      formControlName: 'sshAuthentication',
      inputType: 'pcm-select',
      options: [
        {label: 'Password', value: 'PASSWORD'},
        {label: 'Public Key', value: 'PUBLIC KEY'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_DEL_COL.LABEL',
      formControlName: 'deleteAfterCollection',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      inputType: 'pcm-card',
      cards: [
        {
          title: 'CERTIFICATE.CERT_TITL',
          subFields: [
            {
              placeholder: 'PROTOCOLS.SFTP.IN_KNWN_HST_KY.LABEL',
              formControlName: 'knownHostKey',
              inputType: 'pcm-select',
              validation: [''],
              options: [],
              buttons: [
                {
                  title: 'CERTIFICATE.BUTTONS.UPLOAD_CERT',
                  className: 'btn btn-info'
                }
              ],
              modal: {
                url: {
                  yes: environment.UPLOAD_KHOST_KEY,
                  no: environment.UPLOAD_KHOST_KEY
                },
                fields: [
                  {
                    inputType: 'pcm-file',
                    placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                    formControlName: 'file',
                    validation: ['']
                  },
                  {
                    inputType: 'pcm-text',
                    placeholder: 'CERTIFICATE.IN_CERT_NME.LABEL',
                    formControlName: 'keyName',
                    validation: ['', [removeSpaces]]
                  }
                ]
              }
            },
            {
              placeholder: 'PROTOCOLS.SFTP.IN_SSH_IDNTY_KY.LABEL',
              formControlName: 'sshIdentityKeyName',
              inputType: 'pcm-select',
              validation: [''],
              options: [{label: 'Select', value: ''}],
              buttons: [
                {
                  title: 'CERTIFICATE.BUTTONS.UPLOAD_CERT',
                  className: 'btn btn-info'
                }
              ],
              modal: {
                url: {
                  yes: environment.UPLOAD_UID_KEY,
                  no: environment.UPLOAD_UID_KEY
                },
                fields: [
                  {
                    inputType: 'pcm-file',
                    placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                    formControlName: 'file',
                    validation: ['']
                  },
                  {
                    inputType: 'pcm-text',
                    placeholder: 'CERTIFICATE.IN_USR_IDNT_KY_NME.LABEL',
                    formControlName: 'keyName',
                    validation: ['', [removeSpaces]]
                  }

                ]
              }
            }
          ]
        }
      ]
    },
  ],
  HTTP: [
    {
      placeholder: 'PROTOCOLS.HTTP.IN_IN_MIL_BX.LABEL',
      formControlName: 'inMailBox',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.HTTP.IN_OUTBND_URL.LABEL',
      formControlName: 'outBoundUrl',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    }
  ],
  HTTPS: [
    {
      placeholder: 'PROTOCOLS.HTTPS.IN_IN_MIL_BX.LABEL',
      formControlName: 'inMailBox',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.HTTPS.IN_OUTBND_URL.LABEL',
      formControlName: 'outBoundUrl',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      inputType: 'pcm-card',
      cards: [
        {
          title: 'CERTIFICATE.CERT_TITL',
          subFields: [
            {
              placeholder: 'PROTOCOLS.HTTPS.IN_CERT_ID.LABEL',
              formControlName: 'certificate',
              inputType: 'pcm-select',
              validation: ['', [Validators.required]],
              options: [{label: 'Select', value: ''}],
              buttons: [
                {
                  title: 'CERTIFICATE.BUTTONS.UPLOAD_CERT',
                  className: 'btn btn-info'
                }
              ],
              modal: {
                url: {
                  yes: environment.UPLOAD_CA_CERT,
                  no: environment.UPLOAD_CA_CERT
                },
                fields: [
                  {
                    inputType: 'pcm-file',
                    placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                    formControlName: 'file',
                    validation: ['']
                  },
                  {
                    inputType: 'pcm-text',
                    placeholder: 'CERTIFICATE.IN_CERT_NME.LABEL',
                    formControlName: 'certName',
                    validation: ['', [removeSpaces]]
                  }
                ]
              }
            }
          ]
        }
      ]
    },
  ],
  SFG_CONNECT_DIRECT: [
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_LCL_ND_NME.LABEL',
      formControlName: 'localNodeName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_ND_NME.LABEL',
      formControlName: 'nodeName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_RMT_FL_NME.LABEL',
      formControlName: 'remoteFileName',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_SNDID.LABEL',
      formControlName: 'sNodeId',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_SNDPW.LABEL',
      formControlName: 'sNodeIdPassword',
      inputType: 'pcm-password',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_HSTNME_IP.LABEL',
      formControlName: 'hostName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_PRT.LABEL',
      formControlName: 'port',
      inputType: 'pcm-numeric',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_DIR_FLDR.LABEL',
      formControlName: 'directory',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_CN_TYPE.LABEL',
      formControlName: 'inboundConnectionType',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_CHK_PT.LABEL',
      formControlName: 'checkPoint',
      inputType: 'pcm-select',
      validation: [''],
      options: [
        {label: '0', value: '0'},
        {label: '100 MB', value: '100 MB'},
        {label: '1 GB', value: '1 GB'}
      ],
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.OUT_DIR_FLDR.LABEL',
      formControlName: 'outDirectory',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.OUT_CN_TYPE.LABEL',
      formControlName: 'outboundConnectionType',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_OPR_SYS.LABEL',
      formControlName: 'operatingSystem',
      inputType: 'pcm-select',
      validation: ['', [Validators.required]],
      options: [
        {label: 'Windows', value: 'Windows'},
        {label: 'Unix', value: 'Unix'},
        {label: 'Mainframe(Z/OS)', value: 'Mainframe(Z/OS)'}
      ],
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_DCB_PAR.LABEL',
      formControlName: 'dcbParam',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_DSN_NME.LABEL',
      formControlName: 'dnsName',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_UNT.LABEL',
      formControlName: 'unit',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_STRG_CLS.LABEL',
      formControlName: 'storageClass',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_SPC.LABEL',
      formControlName: 'space',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_CPY_SYS.LABEL',
      formControlName: 'copySisOpts',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_COMP_LVL.LABEL',
      formControlName: 'compressionLevel',
      inputType: 'pcm-select',
      validation: [''],
      options: [
        {label: '0', value: '0'},
        {label: '1', value: '1'},
        {label: '2', value: '2'},
        {label: '3', value: '3'},
        {label: '4', value: '4'},
        {label: '5', value: '5'},
        {label: '6', value: '6'},
        {label: '7', value: '7'},
        {label: '8', value: '8'},
        {label: '9', value: '9'}
      ],
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_DISP.LABEL',
      formControlName: 'disposition',
      inputType: 'pcm-select',
      validation: [''],
      options: [
        {label: 'New', value: 'New'},
        {label: 'Mod', value: 'Mod'},
        {label: 'Rpl', value: 'Rpl'}
      ],
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_SUB.LABEL',
      formControlName: 'submit',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_RN_JB.LABEL',
      formControlName: 'runJob',
      inputType: 'pcm-textarea',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_RN_TSK.LABEL',
      formControlName: 'runTask',
      inputType: 'pcm-textarea',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_SRTY_PROT.LABEL',
      formControlName: 'securityProtocol',
      inputType: 'pcm-select',
      validation: [''],
      options: [
        {label: 'TLS 1.0', value: 'TLS 1.0'},
        {label: 'TLS 1.1', value: 'TLS 1.1'},
        {label: 'TLS 1.2', value: 'TLS 1.2'}
      ],
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_SEC.LABEL',
      formControlName: 'secure',
      inputType: 'pcm-select',
      validation: ['', [Validators.required]],
      options: [
        {label: 'Enable', value: true},
        {label: 'Disable', value: false}
      ]
    },
    {
      inputType: 'pcm-card',
      cards: [
        {
          title: 'CERTIFICATE.CERT_TITL',
          subFields: [
            {
              placeholder: 'CERTIFICATE.IN_CA_CERT.LABEL',
              formControlName: 'caCertName',
              inputType: 'pcm-select',
              options: [],
              validation: [''],
              buttons: [
                {
                  title: 'CERTIFICATE.BUTTONS.UPLOAD_CA_CRT',
                  className: 'btn btn-info'
                }
              ],
              modal: {
                url: {
                  no: environment.UPLOAD_CA_CERT
                },
                fields: [
                  {
                    inputType: 'pcm-file',
                    placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                    formControlName: 'file',
                    validation: ['']
                  },
                  {
                    inputType: 'pcm-text',
                    placeholder: 'CERTIFICATE.IN_CA_CERT_NME.LABEL',
                    formControlName: 'certName',
                    validation: ['', [removeSpaces]]
                  }
                ]
              }
            },
            {
              placeholder: 'CERTIFICATE.IN_CIPHER_SUITS.LABEL',
              formControlName: 'cipherSuits',
              inputType: 'pcm-select',
              options: [],
              validation: ['', []]
            }
          ]
        }
      ]
    },
  ],
  FileSystem: [
    {
      placeholder: 'PROTOCOLS.FileSystem.IN_USR_NME.LABEL',
      formControlName: 'userName',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FileSystem.IN_PW.LABEL',
      formControlName: 'password',
      inputType: 'pcm-password',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FileSystem.IN_FL_TYP.LABEL',
      formControlName: 'fileType',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FileSystem.IN_IN_DIR_REQ.LABEL',
      formControlName: 'inDirectory',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.FileSystem.IN_OUT_DIR.LABEL',
      formControlName: 'outDirectory',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FileSystem.IN_DEL_AFTR_COLL.LABEL',
      formControlName: 'deleteAfterCollection',
      inputType: 'pcm-checkbox',
      validation: [false]
    }
  ],
  ExistingConnection: [
    {
      placeholder: 'PROTOCOLS.ExistingConnection.IN_PRTCL.LABEL',
      formControlName: 'ecProtocol',
      inputType: 'pcm-select',
      validation: ['', [Validators.required]],
      options: []
    },
    {
      placeholder: 'PROTOCOLS.ExistingConnection.IN_PRTCL_REF.LABEL',
      formControlName: 'ecProtocolReference',
      inputType: 'pcm-select',
      validation: ['', [Validators.required]],
      options: []
    }
  ],
  AS2: [
    {
      selected: 'Yes',
      subFields: [
        {
          placeholder: 'PROTOCOLS.AS2.IN_PRFL_NME.LABEL',
          formControlName: 'profileName',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]], readonly: true
        },
        {
          placeholder: 'PROTOCOLS.AS2.IN_AS2_IDNFR.LABEL',
          formControlName: 'as2Identifier',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        // {
        //   placeholder: 'PROTOCOLS.AS2.IN_EML_ADDR.LABEL',
        //   formControlName: 'emailAddress',
        //   inputType: 'pcm-text',
        //   validation: ['', [removeSpaces]]
        // },
        {
          formControlName: '',
          inputType: 'pcm-space'
        },
        {
          inputType: 'pcm-card',
          cards: [
            {
              title: 'CERTIFICATE.CERT_TITL',
              selected: 'Yes',
              subFields: [
                {
                  selected: 'Yes',
                  placeholder: 'CERTIFICATE.XCHGE_CRT.LABEL',
                  formControlName: 'exchangeCertificate',
                  inputType: 'pcm-select',
                  validation: ['', [Validators.required]],
                  options: [{label: 'Select', value: ''}],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_EXCH_CERT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      yes: environment.UPLOAD_SYSTEM_CERT,
                      no: environment.UPLOAD_TRUSTED_CERT
                    },
                    fields: {
                      Yes: [
                        {
                          inputType: 'pcm-file',
                          placeholder: 'CERTIFICATE.IN_XCHNGE_CERT_UPLD.LABEL',
                          formControlName: 'file',
                          validation: ['']
                        },
                        {
                          inputType: 'pcm-text',
                          placeholder: 'CERTIFICATE.IN_CERT_NME.LABEL',
                          formControlName: 'certName',
                          validation: ['', [removeSpaces]]
                        },
                        {
                          inputType: 'pcm-select',
                          placeholder: 'CERTIFICATE.IN_KEY_CERT.LABEL',
                          formControlName: 'certType',
                          validation: ['keyCert', [Validators.required]],
                          options: [
                            {label: 'Key Certificate', value: 'keyCert'},
                            {label: 'PKCS12 Certificate', value: 'pkcs12'}
                          ]
                        },
                        {
                          inputType: 'pcm-password',
                          placeholder: 'CERTIFICATE.IN_PVT_KEY_CERT.LABEL',
                          formControlName: 'privateKeyPassword',
                          validation: ['', [Validators.required, removeSpaces]]
                        }
                      ]
                    }
                  }
                },
                {
                  selected: 'Yes',
                  placeholder: 'CERTIFICATE.SIGN_CERT.LABEL',
                  formControlName: 'signingCertification',
                  inputType: 'pcm-select',
                  options: [{label: 'Select', value: ''}],
                  url: environment.UPLOAD_SYSTEM_CERT,
                  validation: ['', [Validators.required]],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_SYS_CERT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      yes: environment.UPLOAD_SYSTEM_CERT,
                      no: environment.UPLOAD_TRUSTED_CERT
                    },
                    fields: {
                      Yes: [
                        {
                          inputType: 'pcm-file',
                          placeholder: 'CERTIFICATE.IN_SING_CRT_UPLD.LABEL',
                          formControlName: 'file',
                          validation: ['']
                        },
                        {
                          inputType: 'pcm-text',
                          placeholder: 'CERTIFICATE.IN_CERT_NME.LABEL',
                          formControlName: 'certName',
                          validation: ['', [removeSpaces]]
                        },
                        {
                          inputType: 'pcm-select',
                          placeholder: 'CERTIFICATE.IN_KEY_CERT.LABEL',
                          formControlName: 'certType',
                          validation: ['keyCert', [Validators.required]],
                          options: [
                            {label: 'Key Certificate', value: 'keyCert'},
                            {label: 'PKCS12 Certificate', value: 'pkcs12'}
                          ]
                        },
                        {
                          inputType: 'pcm-password',
                          placeholder: 'CERTIFICATE.IN_PVT_KEY_CERT.LABEL',
                          formControlName: 'privateKeyPassword',
                          validation: ['', [Validators.required, removeSpaces]]
                        }
                      ]
                    }
                  }
                }
              ]
            }
          ]
        },
      ]
    },
    {
      selected: 'No',
      subFields: [
        {
          placeholder: 'PROTOCOLS.AS2.IN_PRFL_NME.LABEL',
          formControlName: 'profileName',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]], readonly: true
        },
        {
          placeholder: 'PROTOCOLS.AS2.IN_AS2_IDNFR.LABEL',
          formControlName: 'as2Identifier',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.SENDER_ID.LABEL',
          formControlName: 'senderId',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.SENDER_QLFR.LABEL',
          formControlName: 'senderQualifier',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.END_PT.LABEL',
          formControlName: 'endPoint',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.RESP_TIMEOUT.LABEL',
          units: 'Seconds',
          formControlName: 'responseTimeout',
          inputType: 'pcm-numeric',
          validation: ['', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.HTTP_CLNT_ADPTR.LABEL',
          formControlName: 'httpclientAdapter',
          inputType: 'pcm-text',
          validation: ['HTTPClientAdapter', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.CMPRESS_DATA.LABEL',
          formControlName: 'compressData',
          inputType: 'pcm-select',
          options: [
            {label: 'Default', value: 'default'},
            {label: 'High', value: 'high'},
            {label: 'Low', value: 'low'},
            {label: 'Medium', value: 'medium'},
            {label: 'None', value: 'none'}],
          validation: ['default', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.PAYLOAD_TYPE.LABEL',
          formControlName: 'payloadType',
          inputType: 'pcm-select',
          options: [
            {label: 'Plain Text', value: 'Plain Text'},
            {label: 'Signed Detached', value: 'Signed Detached'},
            {label: 'Encrypted', value: 'Encrypted'},
            {label: 'Signed Detached Encrypted', value: 'Signed Detached Encrypted'}],
          validation: ['Plain Text', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.MIME_TYPE.LABEL',
          formControlName: 'mimeType',
          inputType: 'pcm-select',
          options: [
            {label: 'Application', value: 'Application'},
            {label: 'Text', value: 'Text'},
            {label: 'Message', value: 'Message'},
            {label: 'Image', value: 'Image'},
            {label: 'Video', value: 'Video'},
            {label: 'Audio', value: 'Audio'}
          ],
          validation: ['Application', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.MIME_SUB_TYPE.LABEL',
          formControlName: 'mimeSubType',
          inputType: 'pcm-select',
          options: [
            {label: 'Plain', value: 'Plain'},
            {label: 'EDI-X12', value: 'EDI-X12'},
            {label: 'EDIFACT', value: 'EDIFACT'},
            {label: 'EDI-Consent', value: 'EDI-Consent'},
            {label: 'Octet-Stream', value: 'Octet-Stream'},
            {label: 'XML', value: 'XML'}
          ],
          validation: ['Plain', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.CIPHER_STRNGTH.LABEL',
          formControlName: 'cipherStrength',
          inputType: 'pcm-select',
          options: [
            {label: 'All', value: 'ALL'},
            {label: 'Strong', value: 'STRONG'},
            {label: 'Weak', value: 'WEAK'}
          ],
          validation: ['ALL', [Validators.required]]
        },
        // {
        //   placeholder: 'Retry Interval',
        //   formControlName: 'retryInterval',
        //   inputType: 'pcm-numeric',
        //   required: true,
        //   validation: [0, [Validators.required]]
        // },
        // {
        //   placeholder: 'Number Of Retries',
        //   formControlName: 'numberOfRetries',
        //   inputType: 'pcm-numeric',
        //   required: true,
        //   validation: [0, [Validators.required]]
        // },
        {
          inputType: 'pcm-card',
          cards: [
            {
              title: 'CERTIFICATE.CERT_TITL',
              selected: 'No',
              subFields: [
                {
                  selected: 'No',
                  placeholder: 'CERTIFICATE.IN_XCHANGE_CERT.LABEL',
                  formControlName: 'exchangeCertificate',
                  inputType: 'pcm-select',
                  validation: [''],
                  options: [{label: 'Select', value: ''}],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_EXCH_CERT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      yes: environment.UPLOAD_SYSTEM_CERT,
                      no: environment.UPLOAD_TRUSTED_CERT
                    },
                    fields: {
                      No: [
                        {
                          inputType: 'pcm-file',
                          placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                          formControlName: 'file',
                          validation: ['']
                        },
                        {
                          inputType: 'pcm-text',
                          placeholder: 'CERTIFICATE.IN_EXCERT_NME.LABEL',
                          formControlName: 'certName',
                          validation: ['', [removeSpaces]]
                        }
                      ]
                    }
                  }
                },
                {
                  selected: 'No',
                  placeholder: 'CERTIFICATE.SIGN_CERT.LABEL',
                  formControlName: 'signingCertification',
                  inputType: 'pcm-select',
                  options: [{label: 'Select', value: ''}],
                  url: environment.UPLOAD_SYSTEM_CERT,
                  validation: ['', [Validators.required]],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_SYS_CERT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      yes: environment.UPLOAD_SYSTEM_CERT,
                      no: environment.UPLOAD_TRUSTED_CERT
                    },
                    fields: {
                      No: [
                        {
                          inputType: 'pcm-file',
                          placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                          formControlName: 'file',
                          validation: ['']
                        },
                        {
                          inputType: 'pcm-text',
                          placeholder: 'CERTIFICATE.IN_SIGN_CERT_NME.LABEL',
                          formControlName: 'certName',
                          validation: ['', [removeSpaces]]
                        }
                      ]
                    }
                  }
                },
                {
                  selected: 'No',
                  placeholder: 'CERTIFICATE.IN_CERT_NME.LABEL',
                  formControlName: 'caCertificate',
                  inputType: 'pcm-select',
                  options: [{label: 'Select', value: ''}],
                  validation: [''],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_CA_CRT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      no: environment.UPLOAD_CA_CERT
                    },
                    fields: {
                      No: [
                        {
                          inputType: 'pcm-file',
                          placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                          formControlName: 'file',
                          validation: ['']
                        },
                        {
                          inputType: 'pcm-text',
                          placeholder: 'CERTIFICATE.IN_CA_CERT_NME.LABEL',
                          formControlName: 'certName',
                          validation: ['', [removeSpaces]]
                        }
                      ]
                    }
                  }
                }
              ]
            },
            {
              title: 'Encryption',
              selected: 'No',
              subFields: [
                {
                  placeholder: 'CERTIFICATE.IN_KEY_CERT_PASS.LABEL',
                  formControlName: 'keyCertificatePassphrase',
                  inputType: 'pcm-text',
                  validation: ['', [removeSpaces]],
                  options: []
                },
                {
                  placeholder: 'CERTIFICATE.IN_SSL_TYPE.LABEL',
                  formControlName: 'sslType',
                  inputType: 'pcm-select',
                  options: [
                    {label: 'None', value: 'SSL_NONE'},
                    {label: 'Optional', value: 'SSL_OPTIONAL'},
                    {label: 'Must', value: 'SSL_MUST'}
                  ],
                  validation: ['SSL_MUST', [Validators.required]]
                },
                {
                  placeholder: 'CERTIFICATE.IN_PAYLOAD_SEC.LABEL',
                  formControlName: 'payloadSecurity',
                  inputType: 'pcm-select',
                  options: [
                    {label: 'Plain Text', value: 'Plain Text'},
                    {label: 'Signed', value: 'Signed'},
                    {label: 'Encrypted', value: 'Encrypted'},
                    {label: 'Signed and Encrypted', value: 'Signed and Encrypted'}
                  ],
                  validation: ['Plain Text', [Validators.required]]
                },
                {
                  placeholder: 'CERTIFICATE.IN_ENCRYPT_ALG.LABEL',
                  formControlName: 'encryptionAlgorithm',
                  inputType: 'pcm-select',
                  options: [
                    {
                      label: 'Triple DES 168 CBC with PKCS5 padding',
                      value: 'Triple DES 168 CBC with PKCS5 padding'
                    },
                    {
                      label: '56-bit DES CBC with PKCS5 padding',
                      value: '56-bit DES CBC with PKCS5 padding'
                    },
                    {
                      label: '128-bit RC2 CBC with PKCS5 padding',
                      value: '128-bit RC2 CBC with PKCS5 padding'
                    },
                    {
                      label: '40-bit RC2 CBC with PKCS5 padding',
                      value: '40-bit RC2 CBC with PKCS5 padding'
                    }
                  ],
                  validation: [
                    'Triple DES 168 CBC with PKCS5 padding',
                    [Validators.required]
                  ]
                },
                {
                  placeholder: 'CERTIFICATE.IN_SIGN_ALG.LABEL',
                  formControlName: 'signatureAlgorithm',
                  inputType: 'pcm-select',
                  options: [
                    {label: 'MD5', value: 'MD5'},
                    {label: 'SHA1', value: 'SHA1'},
                    {label: 'SHA256', value: 'SHA256'},
                    {label: 'SHA384', value: 'SHA384'},
                    {label: 'SHA512', value: 'SHA512'},
                    {label: 'SHA-1', value: 'SHA-1'},
                    {label: 'SHA-256', value: 'SHA-256'},
                    {label: 'SHA-384', value: 'SHA-384'},
                    {label: 'SHA-512', value: 'SHA-512'}
                  ],
                  validation: ['MD5', [Validators.required]]
                }
              ]
            },
            {
              title: 'MDN',
              selected: 'No',
              subFields: [
                {
                  placeholder: 'CERTIFICATE.IN_MDN_REQ.LABEL',
                  formControlName: 'mdn',
                  inputType: 'pcm-radio',
                  options: [
                    {label: 'Yes', value: true},
                    {label: 'No', value: false}
                  ],
                  validation: [true, [Validators.required]]
                },
                {
                  placeholder: 'CERTIFICATE.IN_MDN_TYPE.LABEL',
                  formControlName: 'mdnType',
                  inputType: 'pcm-select',
                  options: [
                    {label: 'Synchronous', value: 'Synchronous'},
                    {label: 'Asynchronous HTTP', value: 'Asynchronous HTTP'},
                    {label: 'Asynchronous HTTPS', value: 'Asynchronous HTTPS'},
                    {label: 'Asynchronous SMTP', value: 'Asynchronous SMTP'}
                  ],
                  validation: ['', [Validators.required]]
                },
                {
                  placeholder: 'CERTIFICATE.IN_MDN_ENCRYPT.LABEL',
                  formControlName: 'mdnEncryption',
                  inputType: 'pcm-select',
                  options: [
                    {label: 'NONE', value: 'NONE'},
                    {label: 'MD5', value: 'MD5'},
                    {label: 'SHA1', value: 'SHA1'},
                    {label: 'SHA256', value: 'SHA256'},
                    {label: 'SHA384', value: 'SHA384'},
                    {label: 'SHA512', value: 'SHA512'},
                    {label: 'SHA-1', value: 'SHA-1'},
                    {label: 'SHA-256', value: 'SHA-256'},
                    {label: 'SHA-384', value: 'SHA-384'},
                    {label: 'SHA-512', value: 'SHA-512'}
                  ],
                  validation: ['MD5', [Validators.required]]
                },
                {
                  placeholder: 'CERTIFICATE.IN_RECPT_ADDR.LABEL',
                  formControlName: 'receiptToAddress',
                  inputType: 'pcm-textarea',
                  validation: ['', [removeSpaces]]
                }
              ]
            }
          ]
        }]
    }
  ],
  Webservice: [
    {
      placeholder: 'PROTOCOLS.Webservice.IN_NME.LABEL',
      formControlName: 'name',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      formControlName: '',
      inputType: 'pcm-space',
      validation: ['']
    },
    {
      formControlName: '',
      inputType: 'pcm-space',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.Webservice.IN_OUT_BND_URL.LABEL',
      formControlName: 'outBoundUrl',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      formControlName: '',
      inputType: 'pcm-space',
      validation: ['']
    },
    {
      formControlName: '',
      inputType: 'pcm-space',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.Webservice.IN_IN_MLBX.LABEL',
      formControlName: 'inMailBox',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.Webservice.IN_CER_ID.LABEL',
      formControlName: 'certificateId',
      inputType: 'pcm-select',
      validation: ['', [Validators.required]],
      options: [{label: 'Select', value: ''}]
    }
  ],
  Mailbox: [
    {
      placeholder: 'PROTOCOLS.Mailbox.IN_IN_MLBX_REQ.LABEL',
      formControlName: 'inMailBox',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.Mailbox.IN_OUT_MLBX.LABEL',
      formControlName: 'outMailBox',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.Mailbox.IN_CRTE_MLBX_IN_SI.LABEL',
      formControlName: 'createMailBoxInSI',
      inputType: 'pcm-checkbox'
    },
    {
      placeholder: 'PROTOCOLS.Mailbox.IN_FLTR.LABEL',
      formControlName: 'filter',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
      // validation: ['', [Validators.required, removeSpaces]], required: true
    }
  ],
  MQ: [
    {
      placeholder: 'PROTOCOLS.MQ.IN_HST_NME.LABEL',
      formControlName: 'hostName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.MQ.IN_FL_TYP.LABEL',
      formControlName: 'fileType',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.MQ.IN_PORT.LABEL',
      formControlName: 'port',
      inputType: 'pcm-numeric',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.MQ.CH_NME.LABEL',
      formControlName: 'channelName',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.MQ.IN_USR_NME.LABEL',
      formControlName: 'userId',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.MQ.IN_PWD.LABEL',
      formControlName: 'password',
      inputType: 'pcm-password',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.MQ.IN_QUE_MNGR.LABEL',
      formControlName: 'queueManager',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.MQ.IN_QUE_NME.LABEL',
      formControlName: 'queueName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  SAP: [
    {
      placeholder: 'PROTOCOLS.SAP.IN_ADPTR_NME.LABEL',
      formControlName: 'adapterName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SAP.IN_SAP_RTE.LABEL',
      formControlName: 'sapRoute',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  SMTP: [
    {
      placeholder: 'PROTOCOLS.SMTP.IN_NME.LABEL',
      formControlName: 'name',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_ACS_PRTCL.LABEL',
      formControlName: 'accessProtocol',
      inputType: 'pcm-select',
      options: [
        {label: 'POP3', value: 'POP3'},
        {label: 'IMAP', value: 'IMAP'}
      ],
      validation: ['', []]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_CON_RETRS.LABEL',
      formControlName: 'connectionRetries',
      inputType: 'pcm-numeric',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_USR_NME.LABEL',
      formControlName: 'userName',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_PWD.LABEL',
      formControlName: 'password',
      inputType: 'pcm-password',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_RTRY_INTRVL.LABEL',
      formControlName: 'retryInterval',
      inputType: 'pcm-numeric',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_MAIL_SVR.LABEL',
      formControlName: 'mailServer',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_MAIL_SVR_PRT.LABEL',
      formControlName: 'mailServerPort',
      inputType: 'pcm-numeric',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_MX_MSG.LABEL',
      formControlName: 'userName',
      inputType: 'pcm-numeric',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_RMV_MAIL.LABEL',
      formControlName: 'removeMails',
      inputType: 'pcm-select',
      options: [{label: 'Yes', value: true}, {label: 'No', value: false}],
      validation: ['', []]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_SSL.LABEL',
      formControlName: 'ssl',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_KY_CRT_PS.LABEL',
      formControlName: 'keyCertPassPhrase',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_CPHR_STRGT.LABEL',
      formControlName: 'cipherStrength',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_KEY_CRT.LABEL',
      formControlName: 'keyCertificate',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_CA_CRT.LABEL',
      formControlName: 'caCertificates',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_URI_NME.LABEL',
      formControlName: 'uriname',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  SFGFTP: [
    {
      selected: 'HubConnToPartner',
      subFields: [
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_CON_TYPE.LABEL',
          formControlName: 'connectionType',
          inputType: 'pcm-select',
          options: [
            {label: 'Active', value: 'active'},
            {label: 'Passive', value: 'passive'}
          ],
          validation: ['', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_REMT_HST.LABEL',
          formControlName: 'remoteHost',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_REMT_PRT.LABEL',
          formControlName: 'remotePort',
          inputType: 'pcm-numeric',
          validation: ['', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_USR_NME.LABEL',
          formControlName: 'userName',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_PTH_PWD.LABEL',
          formControlName: 'password',
          inputType: 'pcm-password',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          formControlName: '',
          inputType: 'pcm-space'
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_IN_DIR_REQ.LABEL',
          formControlName: 'inDirectory',
          inputType: 'pcm-text',
          validation: ['', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_OUT_DIR.LABEL',
          formControlName: 'outDirectory',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          formControlName: '',
          inputType: 'pcm-space'
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_NUM_RTRY.LABEL',
          formControlName: 'noOfRetries',
          inputType: 'pcm-numeric',
          validation: [0, [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_RTRY_INVT.LABEL',
          formControlName: 'retryInterval',
          inputType: 'pcm-numeric',
          validation: [0, [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_DEL_COL.LABEL',
          formControlName: 'deleteAfterCollection',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_REMT_FL_PTRN.LABEL',
          formControlName: 'fileType',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        }
      ]
    },
    {
      selected: 'PartnerConnToHub',
      subFields: [
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_USR_ID.LABEL',
          formControlName: 'userName',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_PWD.LABEL',
          formControlName: 'password',
          inputType: 'pcm-password',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_MRG_USR.LABEL',
          formControlName: 'mergeUser',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_IN_DIR_REQ.LABEL',
          formControlName: 'inDirectory',
          inputType: 'pcm-text',
          validation: ['', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_OUT_DIR.LABEL',
          formControlName: 'outDirectory',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_CRTE_MLBX_SI.LABEL',
          formControlName: 'createDirectoryInSI',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_TRNSFR_TYPE.LABEL',
          formControlName: 'transferType',
          inputType: 'pcm-select',
          validation: ['', [Validators.required]],
          options: [
            {label: 'Binary', value: 'Binary'},
            {label: 'ASCII', value: 'ASCII'}
          ]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_FLE_TYPE.LABEL',
          formControlName: 'fileType',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_DL_COL.LABEL',
          formControlName: 'deleteAfterCollection',
          inputType: 'pcm-checkbox',
          validation: [false]
        }
      ]
    }
  ],
  SFGFTPS: [
    {
      selected: 'HubConnToPartner',
      subFields: [
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_CON_TYPE.LABEL',
          formControlName: 'connectionType',
          inputType: 'pcm-select',
          options: [
            {label: 'Active', value: 'active'},
            {label: 'Passive', value: 'passive'}
          ],
          validation: ['', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_REMT_HST.LABEL',
          formControlName: 'remoteHost',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_REMT_PRT.LABEL',
          formControlName: 'remotePort',
          inputType: 'pcm-numeric',
          validation: ['', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_ENCRYPT_STRNGTH.LABEL',
          formControlName: 'encryptionStrength',
          inputType: 'pcm-select',
          options: [
            {label: 'All', value: 'ALL'},
            {label: 'Strong', value: 'STRONG'},
            {label: 'Weak', value: 'WEAK'}
          ],
          validation: ['', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_USR_NME.LABEL',
          formControlName: 'userName',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_PTH_PWD.LABEL',
          formControlName: 'password',
          inputType: 'pcm-password',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_IN_DIR_REQ.LABEL',
          formControlName: 'inDirectory',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_OUT_DIR.LABEL',
          formControlName: 'outDirectory',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_US_CCC.LABEL',
          formControlName: 'useCCC',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_RTRY_INTVL.LABEL',
          formControlName: 'retryInterval',
          inputType: 'pcm-numeric',
          validation: [0, [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_NUM_RTRY.LABEL',
          formControlName: 'noOfRetries',
          inputType: 'pcm-numeric',
          validation: [0, [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_USE_IMPLCT_SSL.LABEL',
          formControlName: 'useImplicitSSL',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_FL_TYP.LABEL',
          formControlName: 'fileType',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          formControlName: '',
          inputType: 'pcm-space'
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_DEL_COL.LABEL',
          formControlName: 'deleteAfterCollection',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          inputType: 'pcm-card',
          cards: [
            {
              title: 'CERTIFICATE.CERT_TITL',
              selected: 'HubConnToPartner',
              subFields: [
                {
                  placeholder: 'CERTIFICATE.IN_CERT_ID.LABEL',
                  formControlName: 'caCertificateNames',
                  inputType: 'pcm-select',
                  validation: ['', [Validators.required]],
                  options: [{label: 'Select', value: ''}],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_CERT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      yes: environment.UPLOAD_CA_CERT,
                      no: environment.UPLOAD_CA_CERT
                    },
                    fields: [
                      {
                        inputType: 'pcm-file',
                        placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                        formControlName: 'file',
                        validation: ['']
                      },
                      {
                        inputType: 'pcm-text',
                        placeholder: 'CERTIFICATE.IN_CERT_NME.LABEL',
                        formControlName: 'certName',
                        validation: ['', [removeSpaces]]
                      }
                    ]
                  }
                }
              ]
            }
          ]
        }
      ]
    },
    {
      selected: 'PartnerConnToHub',
      subFields: [
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_USR_ID.LABEL',
          formControlName: 'userName',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_PWD.LABEL',
          formControlName: 'password',
          inputType: 'pcm-password',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_MRG_USR.LABEL',
          formControlName: 'mergeUser',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_IN_DIR_REQ.LABEL',
          formControlName: 'inDirectory',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_OUT_DIR.LABEL',
          formControlName: 'outDirectory',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_CRTE_MLBX_IN_SI.LABEL',
          formControlName: 'createDirectoryInSI',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_TRNSFR_TYP.LABEL',
          formControlName: 'transferType',
          inputType: 'pcm-select',
          validation: ['', [Validators.required]],
          options: [
            {label: 'Binary', value: 'Binary'},
            {label: 'ASCII', value: 'ASCII'}
          ]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_FL_TYP.LABEL',
          formControlName: 'fileType',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_DEL_COL.LABEL',
          formControlName: 'deleteAfterCollection',
          inputType: 'pcm-checkbox',
          validation: [false]
        }
      ]
    }
  ],
  SFGSFTP: [
    {
      selected: 'HubConnToPartner',
      subFields: [
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_REMT_HST.LABEL',
          formControlName: 'remoteHost',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_REMT_PRT.LABEL',
          formControlName: 'remotePort',
          inputType: 'pcm-numeric',
          validation: ['', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_PREFER_MAC_ALG.LABEL',
          formControlName: 'preferredMacAlgorithm',
          inputType: 'pcm-select',
          options: [
            {label: 'HMAC-MD5', value: 'HMAC-MD5'},
            {label: 'HMAC-SHA1', value: 'HMAC-SHA1'},
            {label: 'HMAC-SHA2-256', value: 'HMAC-SHA2-256'}
          ],
          validation: ['']
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_REMT_USR.LABEL',
          formControlName: 'userName',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_SSH_PWD.LABEL',
          formControlName: 'password',
          inputType: 'pcm-password',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_CHAR_ENCOD.LABEL',
          formControlName: 'characterEncoding',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_IN_DIR_REQ.LABEL',
          formControlName: 'inDirectory',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_OUT_DIR.LABEL',
          formControlName: 'outDirectory',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_LCL_PRT.LABEL',
          formControlName: 'localPortRange',
          inputType: 'pcm-numeric',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_COMP.LABEL',
          formControlName: 'compression',
          inputType: 'pcm-select',
          options: [
            {label: 'NONE', value: 'NONE'},
            {label: 'ZLIB', value: 'ZLIB'}
          ],
          validation: ['']
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_RTRY_DLY.LABEL',
          formControlName: 'retryDelay',
          inputType: 'pcm-numeric',
          validation: ['']
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_CON_RTRY_CNT.LABEL',
          formControlName: 'connectionRetryCount',
          inputType: 'pcm-numeric',
          validation: ['']
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_PRFRD_CPHR.LABEL',
          formControlName: 'preferredCipher',
          inputType: 'pcm-select',
          options: [
            {label: '3DES-CBC', value: '3DES-CBC'},
            {label: 'AES128-CBC', value: 'AES128-CBC'},
            {label: 'AES128-CTR', value: 'AES128-CTR'},
            {label: 'AES256-CTR', value: 'AES256-CTR'},
            {label: 'AES192-CBC', value: 'AES192-CBC'},
            {label: 'AES192-CTR', value: 'AES192-CTR'},
            {label: 'AES256-CBC', value: 'AES256-CBC'},
            {label: 'BLOWFISH-CBC', value: 'BLOWFISH-CBC'},
            {label: 'CAST128-CBC', value: 'CAST128-CBC'},
            {label: 'TWOFISH128-CBC', value: 'TWOFISH128-CBC'},
            {label: 'TWOFISH192-CBC', value: 'TWOFISH192-CBC'},
            {label: 'TWOFISH256-CBC', value: 'TWOFISH256-CBC'}
          ],
          validation: ['']
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_AUTH_TYPE.LABEL',
          formControlName: 'preferredAuthenticationType',
          inputType: 'pcm-select',
          options: [
            {label: 'Password', value: 'PASSWORD'},
            {label: 'Public Key', value: 'PUBLIC_KEY'}
          ],
          validation: ['', [Validators.required]],
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_RESP_TME_OUT.LABEL',
          formControlName: 'responseTimeOut',
          inputType: 'pcm-numeric',
          validation: ['']
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_REMT_FL_PTRN.LABEL',
          formControlName: 'fileType',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          formControlName: '',
          inputType: 'pcm-space'
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_DEL_COL.LABEL',
          formControlName: 'deleteAfterCollection',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          inputType: 'pcm-card',
          cards: [
            {
              title: 'CERTIFICATE.CERT_TITL',
              selected: 'HubConnToPartner',
              subFields: [
                {
                  selected: 'HubConnToPartner',
                  placeholder: 'PROTOCOLS.SFGSFTP.IN_USR_IDNTY_KY.LABEL',
                  formControlName: 'userIdentityKey',
                  inputType: 'pcm-select',
                  validation: [''],
                  options: [{label: 'Select', value: ''}],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_CERT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      yes: environment.UPLOAD_UID_KEY,
                      no: environment.UPLOAD_UID_KEY
                    },
                    fields: [
                      {
                        inputType: 'pcm-file',
                        placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                        formControlName: 'file',
                        validation: ['']
                      },
                      {
                        inputType: 'pcm-text',
                        placeholder: 'CERTIFICATE.IN_USR_IDNTY_KY_NME.LABEL',
                        formControlName: 'keyName',
                        validation: ['', [removeSpaces]]
                      }

                    ]
                  }
                },
                {
                  selected: 'HubConnToPartner',
                  placeholder: 'PROTOCOLS.SFGSFTP.IN_KNWN_HST_KY.LABEL',
                  formControlName: 'knownHostKeyNames',
                  inputType: 'pcm-select',
                  validation: [''],
                  options: [{label: 'Select', value: ''}],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_CERT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      yes: environment.UPLOAD_KHOST_KEY,
                      no: environment.UPLOAD_KHOST_KEY
                    },
                    fields: [
                      {
                        inputType: 'pcm-file',
                        placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                        formControlName: 'file',
                        validation: ['']
                      },
                      {
                        inputType: 'pcm-text',
                        placeholder: 'CERTIFICATE.IN_USR_IDNTY_KY_NME.LABEL',
                        formControlName: 'keyName',
                        validation: ['', [removeSpaces]]
                      }
                    ]
                  }
                }
              ]
            }
          ]
        },
      ]
    },
    {
      selected: 'PartnerConnToHub',
      subFields: [
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_USR_NME.LABEL',
          formControlName: 'userName',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_PWD.LABEL',
          formControlName: 'password',
          inputType: 'pcm-password',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_MRG_USR.LABEL',
          formControlName: 'mergeUser',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_IN_DIR_REQ.LABEL',
          formControlName: 'inDirectory',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_OUT_DIR.LABEL',
          formControlName: 'outDirectory',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_CRTE_DIR_SI.LABEL',
          formControlName: 'createDirectoryInSI',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_TRNSFR_TYPE.LABEL',
          formControlName: 'transferType',
          inputType: 'pcm-select',
          validation: ['', [Validators.required]],
          options: [
            {label: 'Binary', value: 'Binary'},
            {label: 'ASCII', value: 'ASCII'}
          ]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_FL_TYPE.LABEL',
          formControlName: 'fileType',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_DEL_COL.LABEL',
          formControlName: 'deleteAfterCollection',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_AUTH_TYPE.LABEL',
          formControlName: 'preferredAuthenticationType',
          inputType: 'pcm-select',
          options: [
            {label: 'Password', value: 'PASSWORD'},
            {label: 'Public Key', value: 'PUBLIC_KEY'}
          ],
          validation: ['', [Validators.required]]
        },
        {
          inputType: 'pcm-card',
          cards: [
            {
              title: 'CERTIFICATE.CERT_TITL',
              selected: 'PartnerConnToHub',
              subFields: [
                {
                  selected: 'PartnerConnToHub',
                  placeholder: 'PROTOCOLS.SFGSFTP.IN_SSH_AUTH_USR_KEYS.LABEL',
                  formControlName: 'authorizedUserKeys',
                  inputType: 'pcm-select',
                  validation: [''],
                  options: [],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_CERT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      yes: environment.UPLOAD_SSH_AUTHORIZED_KEY,
                      no: environment.UPLOAD_SSH_AUTHORIZED_KEY
                    },
                    fields: [
                      {
                        inputType: 'pcm-file',
                        placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                        formControlName: 'file',
                        validation: ['']
                      },
                      {
                        inputType: 'pcm-text',
                        placeholder: 'CERTIFICATE.IN_SSH_AUTH_USR_KEY.LABEL',
                        formControlName: 'keyName',
                        validation: ['', [removeSpaces]]
                      }
                    ]
                  }
                }
              ]
            }
          ]
        },
      ]
    }
  ],
  OracleEBS: [
    {
      placeholder: 'PROTOCOLS.OracleEBS.IN_NME.LABEL',
      formControlName: 'name',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.OracleEBS.IN_PARTNR_NME.LABEL',
      formControlName: 'profileName',
      inputType: 'pcm-text', validation: ['', [Validators.required]]
    },

    {
      placeholder: 'PROTOCOLS.OracleEBS.IN_TIME_OUT.LABEL',
      units: 'Seconds', formControlName: 'responseTimeout',
      inputType: 'pcm-numeric', validation: ['', [Validators.required]]
    },
  ],
  AWS_S3: [
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_BCKT_NME.LABEL',
      formControlName: 'bucketName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_QNAME.LABEL',
      formControlName: 'queueName',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_CN_TYPE.LABEL',
      formControlName: 'inboundConnectionType',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_ACS_KEY.LABEL',
      formControlName: 'accessKey',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_SCRT_KEY.LABEL',
      formControlName: 'secretKey',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.OUT_CN_TYPE.LABEL',
      formControlName: 'outboundConnectionType',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_MAIL_BOX.LABEL',
      formControlName: 'inMailbox',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_FL_NME.LABEL',
      formControlName: 'fileName',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_FOLDER_NAME.LABEL',
      formControlName: 'folderName',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_REGN.LABEL',
      formControlName: 'region',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_END_PT.LABEL',
      formControlName: 'endPointUrl',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_DEL_COL.LABEL',
      formControlName: 'deleteAfterCollection',
      inputType: 'pcm-checkbox',
      validation: [false]
    }
  ],
  CUSTOM_PROTOCOL: [
    {
      placeholder: 'PROTOCOLS.CUSTOM_PROTOC0L.PRT_NME.LABEL',
      formControlName: 'customProtocolName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.CUSTOM_PROTOC0L.PRT_EXT.LABEL',
      formControlName: 'customProtocolExtensions',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.CUSTOM_PROTOC0L.USR_NME.LABEL',
      formControlName: 'username',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.CUSTOM_PROTOC0L.PWD.LABEL',
      formControlName: 'password',
      inputType: 'pcm-password',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.CUSTOM_PROTOC0L.AUTH_TYPE.LABEL',
      formControlName: 'authenticationType',
      inputType: 'pcm-select',
      validation: ['', [Validators.required]],
      options: [{label: "External", value: "External"}, {label: "Local", value: "Local"}]
    }
  ],
  ManagePartnerFields: [
    {
      placeholder: 'PROTOCOLS.ManagePartnerFields.IN_PARTNR_NME.LABEL',
      formControlName: 'profileName',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.ManagePartnerFields.IN_PARTNR_ID.LABEL',
      formControlName: 'profileId',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.ManagePartnerFields.IN_PROTOCOL.LABEL',
      formControlName: 'protocol',
      inputType: 'pcm-select',
      validation: [''],
      options: []
    },
    {
      placeholder: 'PROTOCOLS.ManagePartnerFields.IN_STATUS.LABEL',
      formControlName: 'status',
      inputType: 'pcm-select',
      validation: [''],
      options: [
        {label: 'Inactive', value: false},
        {label: 'Active', value: true}
      ]
    }
  ]
};

export const adapterFields = {
  SFG_CONNECT_DIRECT: [
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_ADPTR_NME.LABEL',
      formControlName: 'adapterName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  CONNECT_DIRECT: [
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_ADPTR_NME.LABEL',
      formControlName: 'adapterName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  FileSystem: [
    {
      placeholder: 'PROTOCOLS.FileSystem.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.FileSystem.IN_ADPTR_NME.LABEL',
      formControlName: 'adapterName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  FTP: [
    {
      placeholder: 'PROTOCOLS.FTP.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_ADPTR_NME.LABEL',
      formControlName: 'adapterName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  FTPS: [
    {
      placeholder: 'PROTOCOLS.FTPS.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_ADPTR_NME.LABEL',
      formControlName: 'adapterName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  SFTP: [
    {
      placeholder: 'PROTOCOLS.SFTP.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_ADPTR_NME.LABEL',
      formControlName: 'adapterName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  HTTP: [
    {
      placeholder: 'PROTOCOLS.HTTP.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.HTTP.IN_ADPTR_NME.LABEL',
      formControlName: 'adapterName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  HTTPS: [
    {
      placeholder: 'PROTOCOLS.HTTPS.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.HTTPS.IN_ADPTR_NME.LABEL',
      formControlName: 'adapterName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  Mailbox: [
    {
      placeholder: 'PROTOCOLS.Mailbox.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    }
  ],
  MQ: [
    {
      placeholder: 'PROTOCOLS.MQ.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.MQ.IN_ADPTR_NME.LABEL',
      formControlName: 'adapterName',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    }
  ],
  SMTP: [
    {
      placeholder: 'PROTOCOLS.SMTP.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    }
  ],
  SFGFTP: [
    {
      placeholder: 'PROTOCOLS.SFGFTP.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.SFGFTP.IN_ADPTR_NME.LABEL',
      formControlName: 'adapterName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  SFGFTPS: [
    {
      placeholder: 'PROTOCOLS.SFGFTPS.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.SFGFTPS.IN_ADPTR_NME.LABEL',
      formControlName: 'adapterName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  SFGSFTP: [
    {
      placeholder: 'PROTOCOLS.SFGSFTP.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.SFGSFTP.IN_ADPTR_NME.LABEL',
      formControlName: 'adapterName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  Webservice: [
    {
      placeholder: 'PROTOCOLS.Webservice.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.Webservice.IN_ADPTR_NME.LABEL',
      formControlName: 'adapterName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  OracleEBS: [
    {
      placeholder: 'PROTOCOLS.OracleEBS.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    }
  ],
  AWS_S3: [
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_ADPTR_NME.LABEL',
      formControlName: 'adapterName',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    }
  ],
  GOOGLE_DRIVE: [
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_POLNG_INTRVL.LABEL',
      formControlName: 'poolingInterval',
      inputType: 'pcm-select',
      options: [],
      validation: ['', [Validators.required]]
    }
  ]
};

export const applicationProtocols = {
  ApplicationFields: [
    {
      placeholder: 'APPLICATION.FIELDS.APP_NME_REQ.LABEL',
      formControlName: 'profileName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'APPLICATION.FIELDS.APP_ID_REQ.LABEL',
      formControlName: 'profileId',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'APPLICATION.FIELDS.EMAIL_REQ.LABEL',
      formControlName: 'emailId',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'APPLICATION.FIELDS.PHONE_REQ.LABEL',
      formControlName: 'phone',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, phoneNumValidation, removeSpaces]]
    },
    {
      placeholder: "PARTNERS.FIELDS.IN_PGP_INFO.LABEL",
      formControlName: 'pgpInfo',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'APPLICATION.FIELDS.PROTOCOL_REQ.LABEL',
      formControlName: 'protocol',
      inputType: 'pcm-select',
      validation: ['', [Validators.required]],
      options: []
    },
    {
      placeholder: 'APPLICATION.FIELDS.STATUS.LABEL',
      formControlName: 'status',
      inputType: 'pcm-select',
      required: false,
      validation: [false],
      options: [
        {label: 'Inactive', value: false},
        {label: 'Active', value: true}
      ]
    }
  ],
  ManageApplicationFields: [
    {
      placeholder: 'APPLICATION.FIELDS.APP_NME.LABEL',
      formControlName: 'profileName',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'APPLICATION.FIELDS.APP_ID.LABEL',
      formControlName: 'profileId',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'APPLICATION.FIELDS.PROTOCOL.LABEL',
      formControlName: 'protocol',
      inputType: 'pcm-select',
      validation: [''],
      options: []
    },
    {
      placeholder: 'APPLICATION.FIELDS.STATUS.LABEL',
      formControlName: 'status',
      inputType: 'pcm-select',
      validation: [''],
      options: [
        {label: 'Inactive', value: false},
        {label: 'Active', value: true}]
    },
  ],
  AS2: [
    {
      selected: 'Yes',
      subFields: [
        {
          placeholder: 'PROTOCOLS.AS2.IN_PRFL_NME.LABEL',
          formControlName: 'profileName',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]], readonly: true
        },
        {
          placeholder: 'PROTOCOLS.AS2.IN_AS2_IDNFR.LABEL',
          formControlName: 'as2Identifier',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        // {
        //   placeholder: 'PROTOCOLS.AS2.IN_EML_ADDR.LABEL',
        //   formControlName: 'emailAddress',
        //   inputType: 'pcm-text',
        //   validation: ['', [removeSpaces]]
        // },
        {
          formControlName: '',
          inputType: 'pcm-space'
        },
        {
          inputType: 'pcm-card',
          cards: [
            {
              title: 'CERTIFICATE.CERT_TITL',
              selected: 'Yes',
              subFields: [
                {
                  selected: 'Yes',
                  placeholder: 'CERTIFICATE.XCHGE_CRT.LABEL',
                  formControlName: 'exchangeCertificate',
                  inputType: 'pcm-select',
                  validation: ['', [Validators.required]],
                  options: [{label: 'Select', value: ''}],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_EXCH_CERT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      yes: environment.UPLOAD_SYSTEM_CERT,
                      no: environment.UPLOAD_TRUSTED_CERT
                    },
                    fields: {
                      Yes: [
                        {
                          inputType: 'pcm-file',
                          placeholder: 'CERTIFICATE.IN_XCHNGE_CERT_UPLD.LABEL',
                          formControlName: 'file',
                          validation: ['']
                        },
                        {
                          inputType: 'pcm-text',
                          placeholder: 'CERTIFICATE.IN_CERT_NME.LABEL',
                          formControlName: 'certName',
                          validation: ['', [removeSpaces]]
                        },
                        {
                          inputType: 'pcm-select',
                          placeholder: 'CERTIFICATE.IN_KEY_CERT.LABEL',
                          formControlName: 'certType',
                          validation: ['keyCert', [Validators.required]],
                          options: [
                            {label: 'Key Certificate', value: 'keyCert'},
                            {label: 'PKCS12 Certificate', value: 'pkcs12'}
                          ]
                        },
                        {
                          inputType: 'pcm-password',
                          placeholder: 'CERTIFICATE.IN_PVT_KEY_CERT.LABEL',
                          formControlName: 'privateKeyPassword',
                          validation: ['', [Validators.required, removeSpaces]]
                        }
                      ]
                    }
                  }
                },
                {
                  selected: 'Yes',
                  placeholder: 'CERTIFICATE.SIGN_CERT.LABEL',
                  formControlName: 'signingCertification',
                  inputType: 'pcm-select',
                  options: [{label: 'Select', value: ''}],
                  url: environment.UPLOAD_SYSTEM_CERT,
                  validation: ['', [Validators.required]],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_SYS_CERT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      yes: environment.UPLOAD_SYSTEM_CERT,
                      no: environment.UPLOAD_TRUSTED_CERT
                    },
                    fields: {
                      Yes: [
                        {
                          inputType: 'pcm-file',
                          placeholder: 'CERTIFICATE.IN_SING_CRT_UPLD.LABEL',
                          formControlName: 'file',
                          validation: ['']
                        },
                        {
                          inputType: 'pcm-text',
                          placeholder: 'CERTIFICATE.IN_CERT_NME.LABEL',
                          formControlName: 'certName',
                          validation: ['', [removeSpaces]]
                        },
                        {
                          inputType: 'pcm-select',
                          placeholder: 'CERTIFICATE.IN_KEY_CERT.LABEL',
                          formControlName: 'certType',
                          validation: ['keyCert', [Validators.required]],
                          options: [
                            {label: 'Key Certificate', value: 'keyCert'},
                            {label: 'PKCS12 Certificate', value: 'pkcs12'}
                          ]
                        },
                        {
                          inputType: 'pcm-password',
                          placeholder: 'CERTIFICATE.IN_PVT_KEY_CERT.LABEL',
                          formControlName: 'privateKeyPassword',
                          validation: ['', [Validators.required, removeSpaces]]
                        }
                      ]
                    }
                  }
                }
              ]
            }
          ]
        },
      ]
    },
    {
      selected: 'No',
      subFields: [
        {
          placeholder: 'PROTOCOLS.AS2.IN_PRFL_NME.LABEL',
          formControlName: 'profileName',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]], readonly: true
        },
        {
          placeholder: 'PROTOCOLS.AS2.IN_AS2_IDNFR.LABEL',
          formControlName: 'as2Identifier',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.SENDER_ID.LABEL',
          formControlName: 'senderId',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.SENDER_QLFR.LABEL',
          formControlName: 'senderQualifier',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.END_PT.LABEL',
          formControlName: 'endPoint',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.RESP_TIMEOUT.LABEL',
          units: 'Seconds',
          formControlName: 'responseTimeout',
          inputType: 'pcm-numeric',
          validation: ['', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.HTTP_CLNT_ADPTR.LABEL',
          formControlName: 'httpclientAdapter',
          inputType: 'pcm-text',
          validation: ['HTTPClientAdapter', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.CMPRESS_DATA.LABEL',
          formControlName: 'compressData',
          inputType: 'pcm-select',
          options: [
            {label: 'Default', value: 'default'},
            {label: 'High', value: 'high'},
            {label: 'Low', value: 'low'},
            {label: 'Medium', value: 'medium'},
            {label: 'None', value: 'none'}],
          validation: ['default', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.PAYLOAD_TYPE.LABEL',
          formControlName: 'payloadType',
          inputType: 'pcm-select',
          options: [
            {label: 'Plain Text', value: 'Plain Text'},
            {label: 'Signed Detached', value: 'Signed Detached'},
            {label: 'Encrypted', value: 'Encrypted'},
            {label: 'Signed Detached Encrypted', value: 'Signed Detached Encrypted'}],
          validation: ['Plain Text', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.MIME_TYPE.LABEL',
          formControlName: 'mimeType',
          inputType: 'pcm-select',
          options: [
            {label: 'Application', value: 'Application'},
            {label: 'Text', value: 'Text'},
            {label: 'Message', value: 'Message'},
            {label: 'Image', value: 'Image'},
            {label: 'Video', value: 'Video'},
            {label: 'Audio', value: 'Audio'}
          ],
          validation: ['Application', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.MIME_SUB_TYPE.LABEL',
          formControlName: 'mimeSubType',
          inputType: 'pcm-select',
          options: [
            {label: 'Plain', value: 'Plain'},
            {label: 'EDI-X12', value: 'EDI-X12'},
            {label: 'EDIFACT', value: 'EDIFACT'},
            {label: 'EDI-Consent', value: 'EDI-Consent'},
            {label: 'Octet-Stream', value: 'Octet-Stream'},
            {label: 'XML', value: 'XML'}
          ],
          validation: ['Plain', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.AS2.CIPHER_STRNGTH.LABEL',
          formControlName: 'cipherStrength',
          inputType: 'pcm-select',
          options: [
            {label: 'All', value: 'ALL'},
            {label: 'Strong', value: 'STRONG'},
            {label: 'Weak', value: 'WEAK'}
          ],
          validation: ['ALL', [Validators.required]]
        },
        // {
        //   placeholder: 'Retry Interval',
        //   formControlName: 'retryInterval',
        //   inputType: 'pcm-numeric',
        //   required: true,
        //   validation: [0, [Validators.required]]
        // },
        // {
        //   placeholder: 'Number Of Retries',
        //   formControlName: 'numberOfRetries',
        //   inputType: 'pcm-numeric',
        //   required: true,
        //   validation: [0, [Validators.required]]
        // },
        {
          inputType: 'pcm-card',
          cards: [
            {
              title: 'CERTIFICATE.CERT_TITL',
              selected: 'No',
              subFields: [
                {
                  selected: 'No',
                  placeholder: 'CERTIFICATE.IN_XCHANGE_CERT.LABEL',
                  formControlName: 'exchangeCertificate',
                  inputType: 'pcm-select',
                  validation: [''],
                  options: [{label: 'Select', value: ''}],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_EXCH_CERT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      yes: environment.UPLOAD_SYSTEM_CERT,
                      no: environment.UPLOAD_TRUSTED_CERT
                    },
                    fields: {
                      No: [
                        {
                          inputType: 'pcm-file',
                          placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                          formControlName: 'file',
                          validation: ['']
                        },
                        {
                          inputType: 'pcm-text',
                          placeholder: 'CERTIFICATE.IN_EXCERT_NME.LABEL',
                          formControlName: 'certName',
                          validation: ['', [removeSpaces]]
                        }
                      ]
                    }
                  }
                },
                {
                  selected: 'No',
                  placeholder: 'CERTIFICATE.SIGN_CERT.LABEL',
                  formControlName: 'signingCertification',
                  inputType: 'pcm-select',
                  options: [{label: 'Select', value: ''}],
                  url: environment.UPLOAD_SYSTEM_CERT,
                  validation: ['', [Validators.required]],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_SYS_CERT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      yes: environment.UPLOAD_SYSTEM_CERT,
                      no: environment.UPLOAD_TRUSTED_CERT
                    },
                    fields: {
                      No: [
                        {
                          inputType: 'pcm-file',
                          placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                          formControlName: 'file',
                          validation: ['']
                        },
                        {
                          inputType: 'pcm-text',
                          placeholder: 'CERTIFICATE.IN_SIGN_CERT_NME.LABEL',
                          formControlName: 'certName',
                          validation: ['', [removeSpaces]]
                        }
                      ]
                    }
                  }
                },
                {
                  selected: 'No',
                  placeholder: 'CERTIFICATE.IN_CERT_NME.LABEL',
                  formControlName: 'caCertificate',
                  inputType: 'pcm-select',
                  options: [{label: 'Select', value: ''}],
                  validation: [''],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_CA_CRT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      no: environment.UPLOAD_CA_CERT
                    },
                    fields: {
                      No: [
                        {
                          inputType: 'pcm-file',
                          placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                          formControlName: 'file',
                          validation: ['']
                        },
                        {
                          inputType: 'pcm-text',
                          placeholder: 'CERTIFICATE.IN_CA_CERT_NME.LABEL',
                          formControlName: 'certName',
                          validation: ['', [removeSpaces]]
                        }
                      ]
                    }
                  }
                }
              ]
            },
            {
              title: 'Encryption',
              selected: 'No',
              subFields: [
                {
                  placeholder: 'CERTIFICATE.IN_KEY_CERT_PASS.LABEL',
                  formControlName: 'keyCertificatePassphrase',
                  inputType: 'pcm-text',
                  validation: ['', [removeSpaces]],
                  options: []
                },
                {
                  placeholder: 'CERTIFICATE.IN_SSL_TYPE.LABEL',
                  formControlName: 'sslType',
                  inputType: 'pcm-select',
                  options: [
                    {label: 'None', value: 'SSL_NONE'},
                    {label: 'Optional', value: 'SSL_OPTIONAL'},
                    {label: 'Must', value: 'SSL_MUST'}
                  ],
                  validation: ['SSL_MUST', [Validators.required]]
                },
                {
                  placeholder: 'CERTIFICATE.IN_PAYLOAD_SEC.LABEL',
                  formControlName: 'payloadSecurity',
                  inputType: 'pcm-select',
                  options: [
                    {label: 'Plain Text', value: 'Plain Text'},
                    {label: 'Signed', value: 'Signed'},
                    {label: 'Encrypted', value: 'Encrypted'},
                    {label: 'Signed and Encrypted', value: 'Signed and Encrypted'}
                  ],
                  validation: ['Plain Text', [Validators.required]]
                },
                {
                  placeholder: 'CERTIFICATE.IN_ENCRYPT_ALG.LABEL',
                  formControlName: 'encryptionAlgorithm',
                  inputType: 'pcm-select',
                  options: [
                    {
                      label: 'Triple DES 168 CBC with PKCS5 padding',
                      value: 'Triple DES 168 CBC with PKCS5 padding'
                    },
                    {
                      label: '56-bit DES CBC with PKCS5 padding',
                      value: '56-bit DES CBC with PKCS5 padding'
                    },
                    {
                      label: '128-bit RC2 CBC with PKCS5 padding',
                      value: '128-bit RC2 CBC with PKCS5 padding'
                    },
                    {
                      label: '40-bit RC2 CBC with PKCS5 padding',
                      value: '40-bit RC2 CBC with PKCS5 padding'
                    }
                  ],
                  validation: [
                    'Triple DES 168 CBC with PKCS5 padding',
                    [Validators.required]
                  ]
                },
                {
                  placeholder: 'CERTIFICATE.IN_SIGN_ALG.LABEL',
                  formControlName: 'signatureAlgorithm',
                  inputType: 'pcm-select',
                  options: [
                    {label: 'MD5', value: 'MD5'},
                    {label: 'SHA1', value: 'SHA1'},
                    {label: 'SHA256', value: 'SHA256'},
                    {label: 'SHA384', value: 'SHA384'},
                    {label: 'SHA512', value: 'SHA512'},
                    {label: 'SHA-1', value: 'SHA-1'},
                    {label: 'SHA-256', value: 'SHA-256'},
                    {label: 'SHA-384', value: 'SHA-384'},
                    {label: 'SHA-512', value: 'SHA-512'}
                  ],
                  validation: ['MD5', [Validators.required]]
                }
              ]
            },
            {
              title: 'MDN',
              selected: 'No',
              subFields: [
                {
                  placeholder: 'CERTIFICATE.IN_MDN_REQ.LABEL',
                  formControlName: 'mdn',
                  inputType: 'pcm-radio',
                  options: [
                    {label: 'Yes', value: true},
                    {label: 'No', value: false}
                  ],
                  validation: [true, [Validators.required]]
                },
                {
                  placeholder: 'CERTIFICATE.IN_MDN_TYPE.LABEL',
                  formControlName: 'mdnType',
                  inputType: 'pcm-select',
                  options: [
                    {label: 'Synchronous', value: 'Synchronous'},
                    {label: 'Asynchronous HTTP', value: 'Asynchronous HTTP'},
                    {label: 'Asynchronous HTTPS', value: 'Asynchronous HTTPS'},
                    {label: 'Asynchronous SMTP', value: 'Asynchronous SMTP'}
                  ],
                  validation: ['', [Validators.required]]
                },
                {
                  placeholder: 'CERTIFICATE.IN_MDN_ENCRYPT.LABEL',
                  formControlName: 'mdnEncryption',
                  inputType: 'pcm-select',
                  options: [
                    {label: 'NONE', value: 'NONE'},
                    {label: 'MD5', value: 'MD5'},
                    {label: 'SHA1', value: 'SHA1'},
                    {label: 'SHA256', value: 'SHA256'},
                    {label: 'SHA384', value: 'SHA384'},
                    {label: 'SHA512', value: 'SHA512'},
                    {label: 'SHA-1', value: 'SHA-1'},
                    {label: 'SHA-256', value: 'SHA-256'},
                    {label: 'SHA-384', value: 'SHA-384'},
                    {label: 'SHA-512', value: 'SHA-512'}
                  ],
                  validation: ['MD5', [Validators.required]]
                },
                {
                  placeholder: 'CERTIFICATE.IN_RECPT_ADDR.LABEL',
                  formControlName: 'receiptToAddress',
                  inputType: 'pcm-textarea',
                  validation: ['', [removeSpaces]]
                }
              ]
            }
          ]
        }]
    }
  ],
  GOOGLE_DRIVE:[
    {
      placeholder: 'Project Id',
      formControlName: 'projectId',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]],
      readonly: 'true'
    },
    {
      placeholder: 'Client Id',
      formControlName: 'clientId',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]],
      readonly: 'true'
    },
    {
      placeholder: 'Client Email',
      formControlName: 'clientEmail',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]],
      readonly: 'true'
    },
    {
      placeholder: 'In Directory',
      formControlName: 'inDirectory',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'Out Directory',
      formControlName: 'outDirectory',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'File Type',
      formControlName: 'fileType',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'Delete After Collection',
      formControlName: 'deleteAfterCollection',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
  ],
  CONNECT_DIRECT: [
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_LCL_ND_NME.LABEL',
      formControlName: 'localNodeName',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_RMTE_ND_NME.LABEL',
      formControlName: 'remoteNodeName',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_RMTE_HST.LABEL',
      formControlName: 'remoteHost',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_RMTE_PRT.LABEL',
      formControlName: 'remotePort',
      inputType: 'pcm-numeric',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_RMTE_USR.LABEL',
      formControlName: 'remoteUser',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_RMOTE_PWD.LABEL',
      formControlName: 'remotePassword',
      inputType: 'pcm-password',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_DCB_PARAMS.LABEL',
      formControlName: 'dcb',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_LCL_X.LABEL',
      formControlName: 'localXLate',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_SYS_OPTS.LABEL',
      formControlName: 'sysOpts',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_TRNSFR_NME.LABEL',
      formControlName: 'transferType',
      inputType: 'pcm-select',
      options: [
        {label: 'Binary', value: 'Binary'},
        {label: 'ASCII', value: 'ASCII'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_CD_PG_FRM.LABEL',
      formControlName: 'codePageFrom',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_CD_PG_TO.LABEL',
      formControlName: 'codePageTo',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_SEC_PRTCL.LABEL',
      formControlName: 'securityProtocol',
      inputType: 'pcm-select',
      validation: [''],
      options: [
        {label: 'TLS 1.0', value: 'TLS 1.0'},
        {label: 'TLS 1.1', value: 'TLS 1.1'},
        {label: 'TLS 1.2', value: 'TLS 1.2'}
      ],
    },
    {
      placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_SEC.LABEL',
      formControlName: 'securePlus',
      inputType: 'pcm-select',
      validation: [''],
      options: [
        {label: 'Enable', value: true},
        {label: 'Disable', value: false}
      ]
    },
    {
      formControlName: '',
      inputType: 'pcm-space',
      validation: ['']
    },
    {
      inputType: 'pcm-card',
      cards: [
        {
          title: 'CERTIFICATE.CERT_TITL',
          subFields: [
            {
              placeholder: 'PROTOCOLS.CONNECT_DIRECT.IN_CA_CERT.LABEL',
              formControlName: 'caCertificate',
              inputType: 'pcm-select',
              options: [],
              validation: [''],
              buttons: [
                {
                  title: 'CERTIFICATE.BUTTONS.UPLOAD_CA_CRT',
                  className: 'btn btn-info'
                }
              ],
              modal: {
                url: {
                  no: environment.UPLOAD_CA_CERT
                },
                fields: [
                  {
                    inputType: 'pcm-file',
                    placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                    formControlName: 'file',
                    validation: ['']
                  },
                  {
                    inputType: 'pcm-text',
                    placeholder: 'CERTIFICATE.IN_CA_CERT_NME.LABEL',
                    formControlName: 'certName',
                    validation: ['', [removeSpaces]]
                  }
                ]
              }
            },
            {
              placeholder: 'CERTIFICATE.IN_CIPHER_SUITS.LABEL',
              formControlName: 'cipherSuits',
              inputType: 'pcm-select',
              options: [],
              validation: ['', []]
            }
          ]
        }
      ]
    },
  ],
  FTP: [
    {
      placeholder: 'PROTOCOLS.FTP.IN_HST_NME.LABEL',
      formControlName: 'hostName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_PRT_NUM.LABEL',
      formControlName: 'portNumber',
      inputType: 'pcm-numeric',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTP.TRBSFR_TYPE.LABEL',
      formControlName: 'transferType',
      inputType: 'pcm-select',
      options: [
        {label: 'Binary', value: 'Binary'},
        {label: 'ASCII', value: 'ASCII'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_USR_ID.LABEL',
      formControlName: 'userName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_PWD.LABEL',
      formControlName: 'password',
      inputType: 'pcm-password',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_CWD_UP.LABEL',
      formControlName: 'cwdUp',
      inputType: 'pcm-select',
      options: [
        {label: '0', value: '0'},
        {label: '1', value: '1'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_QUOTE.LABEL',
      formControlName: 'quote',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_SITE.LABEL',
      formControlName: 'site',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_CONCTN_TYPE.LABEL',
      formControlName: 'connectionType',
      inputType: 'pcm-select',
      options: [
        {label: 'Active', value: 'ACTIVE'},
        {label: 'Passive', value: 'PASSIVE'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_DESTN_MAIL.LABEL',
      formControlName: 'mbDestination',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_DESTN_MAIL_LKUP.LABEL',
      formControlName: 'mbDestinationLookup',
      inputType: 'pcm-select',
      options: [
        {label: '0', value: '0'},
        {label: '1', value: '1'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_CRTE_USR_SI.LABEL',
      formControlName: 'createUserInSI',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_IN_DIR.LABEL',
      formControlName: 'inDirectory',
      inputType: 'pcm-text',
      validation: ['', []]
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_OUT_DIR_REQ.LABEL',
      formControlName: 'outDirectory',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_CRTE_DIR_SI.LABEL',
      formControlName: 'createDirectoryInSI',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_FL_TYPE.LABEL',
      formControlName: 'fileType',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      formControlName: '',
      inputType: 'pcm-space',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTP.IN_DEL_COL.LABEL',
      formControlName: 'deleteAfterCollection',
      inputType: 'pcm-checkbox',
      validation: [false]
    }
  ],
  FTPS: [
    {
      placeholder: 'PROTOCOLS.FTPS.IN_HST_NME.LABEL',
      formControlName: 'hostName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_PRT_NUM.LABEL',
      formControlName: 'portNumber',
      inputType: 'pcm-numeric',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTPS.TRBSFR_TYPE.LABEL',
      formControlName: 'transferType',
      inputType: 'pcm-select',
      options: [
        {label: 'Binary', value: 'Binary'},
        {label: 'ASCII', value: 'ASCII'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_USR_ID.LABEL',
      formControlName: 'userName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_PWD.LABEL',
      formControlName: 'password',
      inputType: 'pcm-password',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_CWD_UP.LABEL',
      formControlName: 'cwdUp',
      inputType: 'pcm-select',
      options: [
        {label: '0', value: '0'},
        {label: '1', value: '1'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_QUOTE.LABEL',
      formControlName: 'quote',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_SITE.LABEL',
      formControlName: 'site',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_CONCTN_TYPE.LABEL',
      formControlName: 'connectionType',
      inputType: 'pcm-select',
      options: [
        {label: 'Active', value: 'ACTIVE'},
        {label: 'Passive', value: 'PASSIVE'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_DESTN_MAIL.LABEL',
      formControlName: 'mbDestination',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_DESTN_MAIL_LKUP.LABEL',
      formControlName: 'mbDestinationLookup',
      inputType: 'pcm-select',
      options: [
        {label: '0', value: '0'},
        {label: '1', value: '1'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_SSL_SOC.LABEL',
      formControlName: 'sslSocket',
      inputType: 'pcm-select',
      options: [
        {label: 'SSL_IMPLICIT', value: 'SSL_IMPLICIT'},
        {label: 'SSL_EXPLICIT', value: 'SSL_EXPLICIT'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_IN_DIR.LABEL',
      formControlName: 'inDirectory',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_OUT_DIR_REQ.LABEL',
      formControlName: 'outDirectory',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_CRTE_USR_SI.LABEL',
      formControlName: 'createUserInSI',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_FL_TYPE.LABEL',
      formControlName: 'fileType',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_SSL_CIPHER.LABEL',
      formControlName: 'sslCipher',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_CRTE_DIR_SI.LABEL',
      formControlName: 'createDirectoryInSI',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      formControlName: '',
      inputType: 'pcm-space'
    },
    {
      formControlName: '',
      inputType: 'pcm-space'
    },
    {
      placeholder: 'PROTOCOLS.FTPS.IN_DEL_COL.LABEL',
      formControlName: 'deleteAfterCollection',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      inputType: 'pcm-card',
      cards: [
        {
          title: 'CERTIFICATE.CERT_TITL',
          subFields: [
            {
              placeholder: 'PROTOCOLS.FTPS.IN_CRT_ID.LABEL',
              formControlName: 'certificateId',
              inputType: 'pcm-select',
              validation: ['', [Validators.required]],
              options: [{label: 'Select', value: ''}],
              buttons: [
                {
                  title: 'CERTIFICATE.BUTTONS.UPLOAD_CERT',
                  className: 'btn btn-info'
                }
              ],
              modal: {
                url: {
                  yes: environment.UPLOAD_CA_CERT,
                  no: environment.UPLOAD_CA_CERT
                },
                fields: [
                  {
                    inputType: 'pcm-file',
                    placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                    formControlName: 'file',
                    validation: ['']
                  },
                  {
                    inputType: 'pcm-text',
                    placeholder: 'CERTIFICATE.IN_CERT_NME.LABEL',
                    formControlName: 'certName',
                    validation: ['', [removeSpaces]]
                  }
                ]
              }
            }
          ]
        }
      ]
    },
  ],
  SFTP: [
    {
      placeholder: 'PROTOCOLS.SFTP.IN_HST_NME.LABEL',
      formControlName: 'hostName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_PRT_NUM.LABEL',
      formControlName: 'portNumber',
      inputType: 'pcm-numeric',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.TRBSFR_TYPE.LABEL',
      formControlName: 'transferType',
      inputType: 'pcm-select',
      options: [
        {label: 'Binary', value: 'Binary'},
        {label: 'ASCII', value: 'ASCII'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_USR_ID.LABEL',
      formControlName: 'userName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_PWD.LABEL',
      formControlName: 'password',
      inputType: 'pcm-password',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_SSH_CIPHER.LABEL',
      formControlName: 'sshCipher',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_SSH_COMP.LABEL',
      formControlName: 'sshCompression',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_SSH_MAC.LABEL',
      formControlName: 'sshMac',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_CWD_UP.LABEL',
      formControlName: 'cwdUp',
      inputType: 'pcm-select',
      options: [
        {label: '0', value: '0'},
        {label: '1', value: '1'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_QUOTE.LABEL',
      formControlName: 'quote',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_SITE.LABEL',
      formControlName: 'site',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_CONCTN_TYPE.LABEL',
      formControlName: 'connectionType',
      inputType: 'pcm-select',
      options: [
        {label: 'Active', value: 'ACTIVE'},
        {label: 'Passive', value: 'PASSIVE'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_DESTN_MAIL.LABEL',
      formControlName: 'mbDestination',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_DESTN_MAIL_LKUP.LABEL',
      formControlName: 'mbDestinationLookup',
      inputType: 'pcm-select',
      options: [
        {label: '0', value: '0'},
        {label: '1', value: '1'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_CRTE_USR_SI.LABEL',
      formControlName: 'createUserInSI',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_IN_DIR.LABEL',
      formControlName: 'inDirectory',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_OUT_DIR_REQ.LABEL',
      formControlName: 'outDirectory',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_CRTE_DIR_SI.LABEL',
      formControlName: 'createDirectoryInSI',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_FL_TYPE.LABEL',
      formControlName: 'fileType',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_SSH_AUTH.LABEL',
      formControlName: 'sshAuthentication',
      inputType: 'pcm-select',
      options: [
        {label: 'Password', value: 'PASSWORD'},
        {label: 'Public Key', value: 'PUBLIC KEY'}
      ],
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFTP.IN_DEL_COL.LABEL',
      formControlName: 'deleteAfterCollection',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      inputType: 'pcm-card',
      cards: [
        {
          title: 'CERTIFICATE.CERT_TITL',
          subFields: [
            {
              placeholder: 'PROTOCOLS.SFTP.IN_KNWN_HST_KY.LABEL',
              formControlName: 'knownHostKey',
              inputType: 'pcm-select',
              validation: [''],
              options: [],
              buttons: [
                {
                  title: 'CERTIFICATE.BUTTONS.UPLOAD_CERT',
                  className: 'btn btn-info'
                }
              ],
              modal: {
                url: {
                  yes: environment.UPLOAD_KHOST_KEY,
                  no: environment.UPLOAD_KHOST_KEY
                },
                fields: [
                  {
                    inputType: 'pcm-file',
                    placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                    formControlName: 'file',
                    validation: ['']
                  },
                  {
                    inputType: 'pcm-text',
                    placeholder: 'CERTIFICATE.IN_CERT_NME.LABEL',
                    formControlName: 'keyName',
                    validation: ['', [removeSpaces]]
                  }
                ]
              }
            },
            {
              placeholder: 'PROTOCOLS.SFTP.IN_SSH_IDNTY_KY.LABEL',
              formControlName: 'sshIdentityKeyName',
              inputType: 'pcm-select',
              validation: [''],
              options: [{label: 'Select', value: ''}],
              buttons: [
                {
                  title: 'CERTIFICATE.BUTTONS.UPLOAD_CERT',
                  className: 'btn btn-info'
                }
              ],
              modal: {
                url: {
                  yes: environment.UPLOAD_UID_KEY,
                  no: environment.UPLOAD_UID_KEY
                },
                fields: [
                  {
                    inputType: 'pcm-file',
                    placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                    formControlName: 'file',
                    validation: ['']
                  },
                  {
                    inputType: 'pcm-text',
                    placeholder: 'CERTIFICATE.IN_USR_IDNT_KY_NME.LABEL',
                    formControlName: 'keyName',
                    validation: ['', [removeSpaces]]
                  }

                ]
              }
            }
          ]
        }
      ]
    },
  ],
  HTTP: [
    {
      placeholder: 'PROTOCOLS.HTTP.IN_IN_MIL_BX.LABEL',
      formControlName: 'inMailBox',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.HTTP.IN_OUTBND_URL_REQ.LABEL',
      formControlName: 'outBoundUrl',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    }
  ],
  HTTPS: [
    {
      placeholder: 'PROTOCOLS.HTTPS.IN_IN_MIL_BX.LABEL',
      formControlName: 'inMailBox',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.HTTPS.IN_OUTBND_URL_REQ.LABEL',
      formControlName: 'outBoundUrl',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      inputType: 'pcm-card',
      cards: [
        {
          title: 'CERTIFICATE.CERT_TITL',
          subFields: [
            {
              placeholder: 'PROTOCOLS.HTTPS.IN_CERT_ID.LABEL',
              formControlName: 'certificate',
              inputType: 'pcm-select',
              validation: ['', [Validators.required]],
              options: [{label: 'Select', value: ''}],
              buttons: [
                {
                  title: 'CERTIFICATE.BUTTONS.UPLOAD_CERT',
                  className: 'btn btn-info'
                }
              ],
              modal: {
                url: {
                  yes: environment.UPLOAD_CA_CERT,
                  no: environment.UPLOAD_CA_CERT
                },
                fields: [
                  {
                    inputType: 'pcm-file',
                    placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                    formControlName: 'file',
                    validation: ['']
                  },
                  {
                    inputType: 'pcm-text',
                    placeholder: 'CERTIFICATE.IN_CERT_NME.LABEL',
                    formControlName: 'certName',
                    validation: ['', [removeSpaces]]
                  }
                ]
              }
            }
          ]
        }
      ]
    },
  ],
  SFG_CONNECT_DIRECT: [
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_LCL_ND_NME.LABEL',
      formControlName: 'localNodeName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_ND_NME.LABEL',
      formControlName: 'nodeName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_RMT_FL_NME.LABEL',
      formControlName: 'remoteFileName',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_SNDID.LABEL',
      formControlName: 'sNodeId',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_SNDPW.LABEL',
      formControlName: 'sNodeIdPassword',
      inputType: 'pcm-password',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_HSTNME_IP.LABEL',
      formControlName: 'hostName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_PRT.LABEL',
      formControlName: 'port',
      inputType: 'pcm-numeric',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_DIR_FLDR.LABEL',
      formControlName: 'directory',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_CN_TYPE.LABEL',
      formControlName: 'inboundConnectionType',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_CHK_PT.LABEL',
      formControlName: 'checkPoint',
      inputType: 'pcm-select',
      validation: [''],
      options: [
        {label: '0', value: '0'},
        {label: '100 MB', value: '100 MB'},
        {label: '1 GB', value: '1 GB'}
      ],
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.OUT_DIR_FLDR.LABEL',
      formControlName: 'outDirectory',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.OUT_CN_TYPE.LABEL',
      formControlName: 'outboundConnectionType',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_OPR_SYS.LABEL',
      formControlName: 'operatingSystem',
      inputType: 'pcm-select',
      validation: ['', [Validators.required]],
      options: [
        {label: 'Windows', value: 'Windows'},
        {label: 'Unix', value: 'Unix'},
        {label: 'Mainframe(Z/OS)', value: 'Mainframe(Z/OS)'}
      ],
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_DCB_PAR.LABEL',
      formControlName: 'dcbParam',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_DSN_NME.LABEL',
      formControlName: 'dnsName',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_UNT.LABEL',
      formControlName: 'unit',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_STRG_CLS.LABEL',
      formControlName: 'storageClass',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_SPC.LABEL',
      formControlName: 'space',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_CPY_SYS.LABEL',
      formControlName: 'copySisOpts',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_CHK_PT.LABEL',
      formControlName: 'checkPoint',
      inputType: 'pcm-select',
      validation: [''],
      options: [
        {label: '0', value: '0'},
        {label: '100 MB', value: '100 MB'},
        {label: '1 GB', value: '1 GB'}
      ],
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_COMP_LVL.LABEL',
      formControlName: 'compressionLevel',
      inputType: 'pcm-select',
      validation: [''],
      options: [
        {label: '0', value: '0'},
        {label: '1', value: '1'},
        {label: '2', value: '2'},
        {label: '3', value: '3'},
        {label: '4', value: '4'},
        {label: '5', value: '5'},
        {label: '6', value: '6'},
        {label: '7', value: '7'},
        {label: '8', value: '8'},
        {label: '9', value: '9'}
      ],
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_DISP.LABEL',
      formControlName: 'disposition',
      inputType: 'pcm-select',
      validation: [''],
      options: [
        {label: 'New', value: 'New'},
        {label: 'Mod', value: 'Mod'},
        {label: 'Rpl', value: 'Rpl'}
      ],
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_SUB.LABEL',
      formControlName: 'submit',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_RN_JB.LABEL',
      formControlName: 'runJob',
      inputType: 'pcm-textarea',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_RN_TSK.LABEL',
      formControlName: 'runTask',
      inputType: 'pcm-textarea',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_SRTY_PROT.LABEL',
      formControlName: 'securityProtocol',
      inputType: 'pcm-select',
      validation: [''],
      options: [
        {label: 'TLS 1.0', value: 'TLS 1.0'},
        {label: 'TLS 1.1', value: 'TLS 1.1'},
        {label: 'TLS 1.2', value: 'TLS 1.2'}
      ],
    },
    {
      placeholder: 'PROTOCOLS.SFG_CONNECT_DIRECT.IN_SEC.LABEL',
      formControlName: 'secure',
      inputType: 'pcm-select',
      validation: ['', [Validators.required]],
      options: [
        {label: 'Enable', value: true},
        {label: 'Disable', value: false}
      ],
    },
    {
      inputType: 'pcm-card',
      cards: [
        {
          title: 'CERTIFICATE.CERT_TITL',
          subFields: [
            {
              placeholder: 'CERTIFICATE.IN_CA_CERT.LABEL',
              formControlName: 'caCertificate',
              inputType: 'pcm-select',
              options: [],
              validation: ['', []],
              buttons: [
                {
                  title: 'CERTIFICATE.BUTTONS.UPLOAD_CA_CRT',
                  className: 'btn btn-info'
                }
              ],
              modal: {
                url: {
                  no: environment.UPLOAD_CA_CERT
                },
                fields: [
                  {
                    inputType: 'pcm-file',
                    placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                    formControlName: 'file',
                    validation: ['']
                  },
                  {
                    inputType: 'pcm-text',
                    placeholder: 'CERTIFICATE.IN_CA_CERT_NME.LABEL',
                    formControlName: 'certName',
                    validation: ['', [removeSpaces]]
                  }
                ]
              }
            },
            {
              placeholder: 'CERTIFICATE.IN_CIPHER_SUITS.LABEL',
              formControlName: 'cipherSuits',
              inputType: 'pcm-select',
              options: [],
              validation: ['', []]
            }
          ]
        }
      ]
    },
  ],
  FileSystem: [
    {
      placeholder: 'PROTOCOLS.FileSystem.IN_USR_NME.LABEL',
      formControlName: 'userName',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FileSystem.IN_PW.LABEL',
      formControlName: 'password',
      inputType: 'pcm-password',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FileSystem.IN_FL_TYP.LABEL',
      formControlName: 'fileType',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.FileSystem.IN_IN_DIR.LABEL',
      formControlName: 'inDirectory',
      inputType: 'pcm-text',
      validation: ['', []]
    },
    {
      placeholder: 'PROTOCOLS.FileSystem.IN_OUT_DIR_REQ.LABEL',
      formControlName: 'outDirectory',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.FileSystem.IN_DEL_AFTR_COLL.LABEL',
      formControlName: 'deleteAfterCollection',
      inputType: 'pcm-checkbox',
      validation: [false]
    }
  ],
  Webservice: [
    {
      placeholder: 'Name *',
      formControlName: 'name',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      formControlName: '',
      inputType: 'pcm-space',
      validation: [false]
    },
    {
      formControlName: '',
      inputType: 'pcm-space',
      validation: [false]
    },
    {
      placeholder: 'Outbound URL',
      formControlName: 'outBoundUrl',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      formControlName: '',
      inputType: 'pcm-space',
      validation: [false]
    },
    {
      formControlName: '',
      inputType: 'pcm-space',
      validation: [false]
    },
    {
      placeholder: 'In Mailbox *',
      formControlName: 'inMailBox',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'Certificate Id *',
      formControlName: 'certificateId',
      inputType: 'pcm-select',
      validation: ['', [Validators.required]],
      options: [{label: 'Select', value: ''}]
    }
  ],
  Mailbox: [
    {
      placeholder: 'PROTOCOLS.Mailbox.IN_IN_MLBX.LABEL',
      formControlName: 'inMailBox',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.Mailbox.IN_OUT_MLBX_REQ.LABEL',
      formControlName: 'outMailBox',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.Mailbox.IN_CRTE_MLBX_IN_SI.LABEL',
      formControlName: 'createMailBoxInSI',
      inputType: 'pcm-checkbox',
      validation: [false]
    },
    {
      placeholder: 'PROTOCOLS.Mailbox.IN_FLTR.LABEL',
      formControlName: 'filter',
      inputType: 'pcm-text',
      validation: ['']
      // validation: ['', [Validators.required, removeSpaces]], required: true
    }
  ],
  MQ: [
    {
      placeholder: 'PROTOCOLS.MQ.IN_HST_NME.LABEL',
      formControlName: 'hostName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.MQ.IN_FL_TYP.LABEL',
      formControlName: 'fileType',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.MQ.IN_PORT.LABEL',
      formControlName: 'port',
      inputType: 'pcm-numeric',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.MQ.CH_NME.LABEL',
      formControlName: 'channelName',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.MQ.IN_USR_NME.LABEL',
      formControlName: 'userId',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.MQ.IN_PWD.LABEL',
      formControlName: 'password',
      inputType: 'pcm-password',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.MQ.IN_QUE_MNGR.LABEL',
      formControlName: 'queueManager',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.MQ.IN_QUE_NME.LABEL',
      formControlName: 'queueName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  SAP: [
    {
      placeholder: 'PROTOCOLS.SAP.IN_ADPTR_NME.LABEL',
      formControlName: 'adapterName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SAP.IN_SAP_RTE.LABEL',
      formControlName: 'sapRoute',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  SMTP: [
    {
      placeholder: 'PROTOCOLS.SMTP.IN_NME.LABEL',
      formControlName: 'name',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_ACS_PRTCL.LABEL',
      formControlName: 'accessProtocol',
      inputType: 'pcm-select',
      options: [
        {label: 'POP3', value: 'POP3'},
        {label: 'IMAP', value: 'IMAP'}
      ],
      validation: ['', []]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_CON_RETRS.LABEL',
      formControlName: 'connectionRetries',
      inputType: 'pcm-numeric',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_USR_NME.LABEL',
      formControlName: 'userName',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_PWD.LABEL',
      formControlName: 'password',
      inputType: 'pcm-password',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_RTRY_INTRVL.LABEL',
      formControlName: 'retryInterval',
      inputType: 'pcm-numeric',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_MAIL_SVR.LABEL',
      formControlName: 'mailServer',
      inputType: 'pcm-text',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_MAIL_SVR_PRT.LABEL',
      formControlName: 'mailServerPort',
      inputType: 'pcm-numeric',
      validation: ['', [removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_MX_MSG.LABEL',
      formControlName: 'userName',
      inputType: 'pcm-numeric',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_RMV_MAIL.LABEL',
      formControlName: 'removeMails',
      inputType: 'pcm-select',
      options: [{label: 'Yes', value: true}, {label: 'No', value: false}],
      validation: ['', []]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_SSL.LABEL',
      formControlName: 'ssl',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_KY_CRT_PS.LABEL',
      formControlName: 'keyCertPassPhrase',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_CPHR_STRGT.LABEL',
      formControlName: 'cipherStrength',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_KEY_CRT.LABEL',
      formControlName: 'keyCertificate',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_CA_CRT.LABEL',
      formControlName: 'caCertificates',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    },
    {
      placeholder: 'PROTOCOLS.SMTP.IN_URI_NME.LABEL',
      formControlName: 'uriname',
      inputType: 'pcm-text',
      validation: ['', [Validators.required, removeSpaces]]
    }
  ],
  SFGFTP: [
    {
      selected: 'HubConnToApplication',
      subFields: [
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_CON_TYPE.LABEL',
          formControlName: 'connectionType',
          inputType: 'pcm-select',
          options: [
            {label: 'Active', value: 'active'},
            {label: 'Passive', value: 'passive'}
          ],
          validation: ['', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_REMT_HST.LABEL',
          formControlName: 'remoteHost',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_REMT_PRT.LABEL',
          formControlName: 'remotePort',
          inputType: 'pcm-numeric',
          validation: ['', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_USR_NME.LABEL',
          formControlName: 'userName',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_PTH_PWD.LABEL',
          formControlName: 'password',
          inputType: 'pcm-password',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          formControlName: '',
          inputType: 'pcm-space'
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_IN_DIR.LABEL',
          formControlName: 'inDirectory',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_OUT_DIR_REQ.LABEL',
          formControlName: 'outDirectory',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          formControlName: '',
          inputType: 'pcm-space'
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_NUM_RTRY.LABEL',
          formControlName: 'noOfRetries',
          inputType: 'pcm-numeric',
          validation: [0, [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_RTRY_INVT.LABEL',
          formControlName: 'retryInterval',
          inputType: 'pcm-numeric',
          validation: [0, [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_DEL_COL.LABEL',
          formControlName: 'deleteAfterCollection',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_REMT_FL_PTRN.LABEL',
          formControlName: 'fileType',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        }
      ]
    },
    {
      selected: 'ApplicationConnToHub',
      subFields: [
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_USR_ID.LABEL',
          formControlName: 'userName',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_PWD.LABEL',
          formControlName: 'password',
          inputType: 'pcm-password',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_MRG_USR.LABEL',
          formControlName: 'mergeUser',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_IN_DIR.LABEL',
          formControlName: 'inDirectory',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_OUT_DIR_REQ.LABEL',
          formControlName: 'outDirectory',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_CRTE_MLBX_SI.LABEL',
          formControlName: 'createDirectoryInSI',
          inputType: 'pcm-checkbox',
          validation: [false, [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_TRNSFR_TYPE.LABEL',
          formControlName: 'transferType',
          inputType: 'pcm-select',
          validation: ['', [Validators.required]],
          options: [
            {label: 'Binary', value: 'Binary'},
            {label: 'ASCII', value: 'ASCII'}
          ]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_FLE_TYPE.LABEL',
          formControlName: 'fileType',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_DL_COL.LABEL',
          formControlName: 'deleteAfterCollection',
          inputType: 'pcm-checkbox',
          validation: [false]
        }
      ]
    }
  ],
  SFGFTPS: [
    {
      selected: 'HubConnToApplication',
      subFields: [
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_CON_TYPE.LABEL',
          formControlName: 'connectionType',
          inputType: 'pcm-select',
          options: [
            {label: 'Active', value: 'active'},
            {label: 'Passive', value: 'passive'}
          ],
          validation: ['', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_REMT_HST.LABEL',
          formControlName: 'remoteHost',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_REMT_PRT.LABEL',
          formControlName: 'remotePort',
          inputType: 'pcm-numeric',
          validation: ['', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_ENCRYPT_STRNGTH.LABEL',
          formControlName: 'encryptionStrength',
          inputType: 'pcm-select',
          options: [
            {label: 'All', value: 'ALL'},
            {label: 'Strong', value: 'STRONG'},
            {label: 'Weak', value: 'WEAK'}
          ],
          validation: ['', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_USR_NME.LABEL',
          formControlName: 'userName',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_PTH_PWD.LABEL',
          formControlName: 'password',
          inputType: 'pcm-password',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_IN_DIR.LABEL',
          formControlName: 'inDirectory',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_OUT_DIR_REQ.LABEL',
          formControlName: 'outDirectory',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_US_CCC.LABEL',
          formControlName: 'useCCC',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_RTRY_INTVL.LABEL',
          formControlName: 'retryInterval',
          inputType: 'pcm-numeric',
          validation: [0, [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_NUM_RTRY.LABEL',
          formControlName: 'noOfRetries',
          inputType: 'pcm-numeric',
          validation: [0, [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_USE_IMPLCT_SSL.LABEL',
          formControlName: 'useImplicitSSL',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_FL_TYP.LABEL',
          formControlName: 'fileType',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          formControlName: '',
          inputType: 'pcm-space'
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_DEL_COL.LABEL',
          formControlName: 'deleteAfterCollection',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          inputType: 'pcm-card',
          cards: [
            {
              title: 'CERTIFICATE.CERT_TITL',
              selected: 'HubConnToApplication',
              subFields: [
                {
                  placeholder: 'CERTIFICATE.IN_CERT_ID.LABEL',
                  formControlName: 'caCertificateNames',
                  inputType: 'pcm-select',
                  validation: ['', [Validators.required]],
                  options: [{label: 'Select', value: ''}],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_CERT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      yes: environment.UPLOAD_CA_CERT,
                      no: environment.UPLOAD_CA_CERT
                    },
                    fields: [
                      {
                        inputType: 'pcm-file',
                        placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                        formControlName: 'file',
                        validation: ['']
                      },
                      {
                        inputType: 'pcm-text',
                        placeholder: 'CERTIFICATE.IN_CERT_NME.LABEL',
                        formControlName: 'certName',
                        validation: ['', [removeSpaces]]
                      }
                    ]
                  }
                }
              ]
            }
          ]
        }
      ]
    },
    {
      selected: 'ApplicationConnToHub',
      subFields: [
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_USR_ID.LABEL',
          formControlName: 'userName',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_PWD.LABEL',
          formControlName: 'password',
          inputType: 'pcm-password',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_MRG_USR.LABEL',
          formControlName: 'mergeUser',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_IN_DIR.LABEL',
          formControlName: 'inDirectory',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_OUT_DIR_REQ.LABEL',
          formControlName: 'outDirectory',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_CRTE_MLBX_IN_SI.LABEL',
          formControlName: 'createDirectoryInSI',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_TRNSFR_TYP.LABEL',
          formControlName: 'transferType',
          inputType: 'pcm-select',
          validation: ['', [Validators.required]],
          options: [
            {label: 'Binary', value: 'Binary'},
            {label: 'ASCII', value: 'ASCII'}
          ]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_FL_TYP.LABEL',
          formControlName: 'fileType',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTPS.IN_DEL_COL.LABEL',
          formControlName: 'deleteAfterCollection',
          inputType: 'pcm-checkbox',
          validation: [false]
        }
      ]
    }
  ],
  SFGSFTP: [
    {
      selected: 'HubConnToApplication',
      subFields: [
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_REMT_HST.LABEL',
          formControlName: 'remoteHost',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_REMT_PRT.LABEL',
          formControlName: 'remotePort',
          inputType: 'pcm-numeric',
          validation: ['', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_PREFER_MAC_ALG.LABEL',
          formControlName: 'preferredMacAlgorithm',
          inputType: 'pcm-select',
          options: [
            {label: 'HMAC-MD5', value: 'HMAC-MD5'},
            {label: 'HMAC-SHA1', value: 'HMAC-SHA1'},
            {label: 'HMAC-SHA2-256', value: 'HMAC-SHA2-256'}
          ],
          validation: ['']
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_REMT_USR.LABEL',
          formControlName: 'userName',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_SSH_PWD.LABEL',
          formControlName: 'password',
          inputType: 'pcm-password',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_CHAR_ENCOD.LABEL',
          formControlName: 'characterEncoding',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_IN_DIR.LABEL',
          formControlName: 'inDirectory',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_OUT_DIR_REQ.LABEL',
          formControlName: 'outDirectory',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_LCL_PRT.LABEL',
          formControlName: 'localPortRange',
          inputType: 'pcm-numeric',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_COMP.LABEL',
          formControlName: 'compression',
          inputType: 'pcm-select',
          options: [
            {label: 'NONE', value: 'NONE'},
            {label: 'ZLIB', value: 'ZLIB'}
          ],
          validation: ['']
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_RTRY_DLY.LABEL',
          formControlName: 'retryDelay',
          inputType: 'pcm-numeric',
          validation: ['']
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_CON_RTRY_CNT.LABEL',
          formControlName: 'connectionRetryCount',
          inputType: 'pcm-numeric',
          validation: ['']
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_PRFRD_CPHR.LABEL',
          formControlName: 'preferredCipher',
          inputType: 'pcm-select',
          options: [
            {label: '3DES-CBC', value: '3DES-CBC'},
            {label: 'AES128-CBC', value: 'AES128-CBC'},
            {label: 'AES128-CTR', value: 'AES128-CTR'},
            {label: 'AES256-CTR', value: 'AES256-CTR'},
            {label: 'AES192-CBC', value: 'AES192-CBC'},
            {label: 'AES192-CTR', value: 'AES192-CTR'},
            {label: 'AES256-CBC', value: 'AES256-CBC'},
            {label: 'BLOWFISH-CBC', value: 'BLOWFISH-CBC'},
            {label: 'CAST128-CBC', value: 'CAST128-CBC'},
            {label: 'TWOFISH128-CBC', value: 'TWOFISH128-CBC'},
            {label: 'TWOFISH192-CBC', value: 'TWOFISH192-CBC'},
            {label: 'TWOFISH256-CBC', value: 'TWOFISH256-CBC'}
          ],
          validation: ['']
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_AUTH_TYPE.LABEL',
          formControlName: 'preferredAuthenticationType',
          inputType: 'pcm-select',
          options: [
            {label: 'Password', value: 'PASSWORD'},
            {label: 'Public Key', value: 'PUBLIC_KEY'}
          ],
          validation: ['', [Validators.required]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_RESP_TME_OUT.LABEL',
          formControlName: 'responseTimeOut',
          inputType: 'pcm-numeric',
          validation: ['']
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_REMT_FL_PTRN.LABEL',
          formControlName: 'fileType',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          formControlName: '',
          inputType: 'pcm-home'
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_DEL_COL.LABEL',
          formControlName: 'deleteAfterCollection',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          inputType: 'pcm-card',
          cards: [
            {
              title: 'CERTIFICATE.CERT_TITL',
              selected: 'HubConnToApplication',
              subFields: [
                {
                  selected: 'HubConnToApplication',
                  placeholder: 'PROTOCOLS.SFGSFTP.IN_USR_IDNTY_KY.LABEL',
                  formControlName: 'userIdentityKey',
                  inputType: 'pcm-select',
                  validation: [''],
                  options: [{label: 'Select', value: ''}],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_CERT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      yes: environment.UPLOAD_UID_KEY,
                      no: environment.UPLOAD_UID_KEY
                    },
                    fields: [
                      {
                        inputType: 'pcm-file',
                        placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                        formControlName: 'file',
                        validation: ['']
                      },
                      {
                        inputType: 'pcm-text',
                        placeholder: 'CERTIFICATE.IN_USR_IDNTY_KY_NME.LABEL',
                        formControlName: 'keyName',
                        validation: ['', [removeSpaces]]
                      }

                    ]
                  }
                },
                {
                  selected: 'HubConnToApplication',
                  placeholder: 'PROTOCOLS.SFGSFTP.IN_KNWN_HST_KY.LABEL',
                  formControlName: 'knownHostKeyNames',
                  inputType: 'pcm-select',
                  validation: [''],
                  options: [{label: 'Select', value: ''}],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_CERT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      yes: environment.UPLOAD_KHOST_KEY,
                      no: environment.UPLOAD_KHOST_KEY
                    },
                    fields: [
                      {
                        inputType: 'pcm-file',
                        placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                        formControlName: 'file',
                        validation: ['']
                      },
                      {
                        inputType: 'pcm-text',
                        placeholder: 'CERTIFICATE.IN_USR_IDNTY_KY_NME.LABEL',
                        formControlName: 'keyName',
                        validation: ['', [removeSpaces]]
                      }
                    ]
                  }
                }
              ]
            }
          ]
        },
      ]
    },
    {
      selected: 'ApplicationConnToHub',
      subFields: [
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_USR_NME.LABEL',
          formControlName: 'userName',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_PWD.LABEL',
          formControlName: 'password',
          inputType: 'pcm-password',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGFTP.IN_MRG_USR.LABEL',
          formControlName: 'mergeUser',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_IN_DIR.LABEL',
          formControlName: 'inDirectory',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_OUT_DIR_REQ.LABEL',
          formControlName: 'outDirectory',
          inputType: 'pcm-text',
          validation: ['', [Validators.required, removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_CRTE_DIR_SI.LABEL',
          formControlName: 'createDirectoryInSI',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_TRNSFR_TYPE.LABEL',
          formControlName: 'transferType',
          inputType: 'pcm-select',
          validation: ['', [Validators.required]],
          options: [
            {label: 'Binary', value: 'Binary'},
            {label: 'ASCII', value: 'ASCII'}
          ]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_FL_TYPE.LABEL',
          formControlName: 'fileType',
          inputType: 'pcm-text',
          validation: ['', [removeSpaces]]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_DEL_COL.LABEL',
          formControlName: 'deleteAfterCollection',
          inputType: 'pcm-checkbox',
          validation: [false]
        },
        {
          placeholder: 'PROTOCOLS.SFGSFTP.IN_AUTH_TYPE.LABEL',
          formControlName: 'preferredAuthenticationType',
          inputType: 'pcm-select',
          options: [
            {label: 'Password', value: 'PASSWORD'},
            {label: 'Public Key', value: 'PUBLIC_KEY'}
          ],
          validation: ['', [Validators.required]]
        },
        {
          inputType: 'pcm-card',
          cards: [
            {
              title: 'CERTIFICATE.CERT_TITL',
              selected: 'ApplicationConnToHub',
              subFields: [
                {
                  selected: 'ApplicationConnToHub',
                  placeholder: 'PROTOCOLS.SFGSFTP.IN_SSH_AUTH_USR_KEYS.LABEL',
                  formControlName: 'authorizedUserKeys',
                  inputType: 'pcm-select',
                  validation: [''],
                  options: [],
                  buttons: [
                    {
                      title: 'CERTIFICATE.BUTTONS.UPLOAD_CERT',
                      className: 'btn btn-info'
                    }
                  ],
                  modal: {
                    url: {
                      yes: environment.UPLOAD_SSH_AUTHORIZED_KEY,
                      no: environment.UPLOAD_SSH_AUTHORIZED_KEY
                    },
                    fields: [
                      {
                        inputType: 'pcm-file',
                        placeholder: 'CERTIFICATE.IN_UPLD_CRT.LABEL',
                        formControlName: 'file',
                        validation: ['']
                      },
                      {
                        inputType: 'pcm-text',
                        placeholder: 'CERTIFICATE.IN_SSH_AUTH_USR_KEY.LABEL',
                        formControlName: 'keyName',
                        validation: ['', [removeSpaces]]
                      }
                    ]
                  }
                }
              ]
            }
          ]
        },
      ]
    }
  ],
  AWS_S3: [
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_SRC_PATH.LABEL',
      formControlName: 'sourcePath',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_BCKT_NME.LABEL',
      formControlName: 'bucketName',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_QNAME.LABEL',
      formControlName: 'queueName',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_ACS_KEY.LABEL',
      formControlName: 'accessKey',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_SCRT_KEY.LABEL',
      formControlName: 'secretKey',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_MAIL_BOX.LABEL',
      formControlName: 'inMailbox',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_FL_NME.LABEL',
      formControlName: 'fileName',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_FOLDER_NAME.LABEL',
      formControlName: 'folderName',
      inputType: 'pcm-text',
      validation: ['']
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_REGN.LABEL',
      formControlName: 'region',
      inputType: 'pcm-text',
      validation: ['', [Validators.required]]
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_END_PT.LABEL',
      formControlName: 'endPointUrl',
      inputType: 'pcm-text',
      validation: ['']
    }, {
      formControlName: '',
      inputType: 'pcm-space'
    },
    {
      placeholder: 'PROTOCOLS.AWS_S3.IN_DEL_COL.LABEL',
      formControlName: 'deleteAfterCollection',
      inputType: 'pcm-checkbox',
      validation: [false]
    }
  ]
};
