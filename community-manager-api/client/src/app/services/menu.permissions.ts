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

import {environment} from '../../environments/environment';
import {Role} from '../model/roles';
import {profile} from '../model/profile.constants';

export const getUser = () => JSON.parse(sessionStorage.getItem('PCM_USER') || '{}');

export const getUserType = () => localStorage.getItem('activeProfile') === 'saml' ?
  getUser().authorities[0]['authority'] : getUser().userRole;

export const profileType = () => localStorage.getItem('activeProfile');

export const superAdmin = [
  {
    menu: 'COMMON.MENU.FL_TRNSFR.LABEL',
    iconCls: 'file_copy',
    menuId: 'fileTransfer',
    roles: 'admin|user',
    subMenu: [
      {
        label: 'COMMON.MENU.FL_TRNSFR.SUB_MENU.SRCH',
        iconCls: 'search',
        route: 'pcm/home'
      }
    ]
  },
  {
    menu: 'COMMON.MENU.REPORTS.LABEL',
    iconCls: 'bar_chart',
    menuId: 'reports',
    roles: 'admin',
    subMenu: [
      /*{
        label: 'Dashboard',
        route: 'pcm/reports/dashboard'
      },*/
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.DT_FLW',
        route: 'pcm/reports/data-flow-reports'
      },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.997_OVR_DUE',
        route: 'pcm/reports/overdue'
      },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.TRNSCTN_VOL_RPT',
        route: 'pcm/reports/volume-reports'
      },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.TRNSCTN_VOL_DOC',
        route: 'pcm/reports/doctype-reports'
      },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.VOL_BY_PRTNR',
        route: 'pcm/reports/volume-partners'
      }/*,
      {
        label: 'File Processed',
        route: 'pcm/reports/file-processed'
      },
      {
        label: 'File Size',
        route: 'pcm/reports/file-size'
      },
      {
        label: 'Inactive partners',
        route: 'pcm/reports/partner'
      }*/
    ]
  },
  // {
  //   menu: 'COMMON.MENU.ENVELOP.LABEL',
  //   iconCls: 'mail',
  //   menuId: 'envelope',
  //   roles: 'admin',
  //   subMenu: [
  //     {
  //       label: 'COMMON.MENU.ENVELOP.SUB_MENU.CRTE_X12',
  //       route: 'pcm/envelope/create-x12'
  //     },
  //     {
  //       label: 'COMMON.MENU.ENVELOP.SUB_MENU.MNGE_X12',
  //       route: 'pcm/envelope/manage-x12'
  //     }
  //   ]
  // },
  {
    menu: 'COMMON.MENU.PARTNER.LABEL',
    iconCls: 'public',
    menuId: 'partner',
    roles: 'admin',
    subMenu: [
      {
        label: 'COMMON.MENU.PARTNER.SUB_MENU.CRTE_PRTNR',
        route: 'pcm/partner/create'
      },
      // {
      //   label: 'COMMON.MENU.PARTNER.SUB_MENU.CRTE_AS2_REL',
      //   route: 'pcm/partner/create/as2'
      // },
      {
        label: 'COMMON.MENU.PARTNER.SUB_MENU.MNGE_PRTNR',
        route: 'pcm/partner/manage'
      }
    ]
  },
  {
    menu: 'COMMON.MENU.APPLICATION.LABEL',
    iconCls: 'extension',
    menuId: 'application',
    roles: 'admin',
    subMenu: [
      {
        label: 'COMMON.MENU.APPLICATION.SUB_MENU.CRTE_APP',
        route: 'pcm/application/create'
      },
      {
        label: 'COMMON.MENU.APPLICATION.SUB_MENU.MNGE_APP',
        route: 'pcm/application/manage'
      }
    ]
  },
  {
    menu: 'COMMON.MENU.DATA_FLW.LABEL',
    iconCls: 'swap_calls',
    menuId: 'dataFlow',
    roles: 'admin',

    subMenu: [
      {
        label: 'COMMON.MENU.DATA_FLW.SUB_MENU.BLD_DT_FLW',
        route: 'pcm/data-flow/build'
      },
      {
        label: 'COMMON.MENU.DATA_FLW.SUB_MENU.CRTE_RULE',
        route: 'pcm/data-flow/create/rule'
      },
      {
        label: 'COMMON.MENU.DATA_FLW.SUB_MENU.MNGE_RULE',
        route: 'pcm/data-flow/manage'
      }
    ]
  },
  {
    menu: 'COMMON.MENU.ACCESS_MNGNT.LABEL',
    iconCls: 'people',
    menuId: 'accessMgt',
    roles: 'admin',
    subMenu: [
      {
        label: 'COMMON.MENU.ACCESS_MNGNT.SUB_MENU.CRTE_USR',
        route: 'pcm/accessManagement/create/user'
      },
      {
        label: 'COMMON.MENU.ACCESS_MNGNT.SUB_MENU.MNGE_USR',
        route: 'pcm/accessManagement/manage/user'
      },
      {
        label: 'COMMON.MENU.ACCESS_MNGNT.SUB_MENU.CRTE_GRP',
        route: 'pcm/accessManagement/create/group'
      },
      {
        label: 'COMMON.MENU.ACCESS_MNGNT.SUB_MENU.MNGE_GRP',
        route: 'pcm/accessManagement/manage/group'
      },
      {
        label: 'COMMON.MENU.ACCESS_MNGNT.SUB_MENU.MNGE_CHB',
        route: 'pcm/accessManagement/manage/charge-back'
      }
    ]
  },
  {
    menu: 'COMMON.MENU.SETTINGS.LABEL',
    iconCls: 'settings',
    menuId: 'settings',
    roles: 'admin',
    subMenu: [
      {
        label: 'COMMON.MENU.SETTINGS.SUB_MENU.CORLTN_NME',
        route: 'pcm/settings/correlation'
      },
      {
        label: 'COMMON.MENU.SETTINGS.SUB_MENU.PIL_INTRVL',
        route: 'pcm/settings/pooling-interval'
      },
      {
        label: 'COMMON.MENU.SETTINGS.SUB_MENU.FILE_SCH',
        route: 'pcm/settings/scheduler'
      },
      // {
      //   label: 'Transactions',
      //   route: 'pcm/settings/transaction'
      // }
    ]
  }
].map(itm => {
  if (itm.menuId === 'reports' && getUser().sfgPcDReports === true) {
    const reports = [
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.SFG_PCD',
        route: 'pcm/reports/SFG-PCD'
      },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.CHARGE_BACKS',
        route: 'pcm/reports/SFG'
      },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.PCD',
        route: 'pcm/reports/PCD'
      }];
    itm.subMenu = [...itm.subMenu, ...reports];
  }
  return itm;
});

export const adminMenu = [
  {
    menu: 'COMMON.MENU.ACCESS_MNGNT.LABEL',
    iconCls: 'people',
    menuId: 'accessMgt',
    roles: 'admin',
    subMenu: [
      {
        label: 'COMMON.MENU.ACCESS_MNGNT.SUB_MENU.MNGE_USR',
        route: 'pcm/accessManagement/manage/user'
      },
      {
        label: 'COMMON.MENU.ACCESS_MNGNT.SUB_MENU.CRTE_USR',
        route: 'pcm/accessManagement/create/user'
      },
      {
        label: 'COMMON.MENU.ACCESS_MNGNT.SUB_MENU.CRTE_GRP',
        route: 'pcm/accessManagement/create/group'
      },
      {
        label: 'COMMON.MENU.ACCESS_MNGNT.SUB_MENU.MNGE_GRP',
        route: 'pcm/accessManagement/manage/group'
      }
    ]
  },
  {
    menu: 'COMMON.MENU.SETTINGS.LABEL',
    iconCls: 'settings',
    menuId: 'settings',
    roles: 'admin',
    subMenu: [
      {
        label: 'COMMON.MENU.SETTINGS.SUB_MENU.CORLTN_NME',
        route: 'pcm/settings/correlation'
      }, {
        label: 'COMMON.MENU.SETTINGS.SUB_MENU.PIL_INTRVL',
        route: 'pcm/settings/pooling-interval'
      },
      // {
      //   label: 'Transactions',
      //   route: 'pcm/settings/transaction'
      // }
    ]
  }
];

export const onBorderMenu = [
  {
    menu: 'COMMON.MENU.PARTNER.LABEL',
    iconCls: 'public',
    menuId: 'partner',
    roles: 'admin',
    subMenu: [
      {
        label: 'COMMON.MENU.PARTNER.SUB_MENU.CRTE_PRTNR',
        route: 'pcm/partner/create'
      },
      {
        label: 'COMMON.MENU.PARTNER.SUB_MENU.MNGE_PRTNR',
        route: 'pcm/partner/manage'
      },
      // {
      //   label: 'COMMON.MENU.PARTNER.SUB_MENU.CRTE_AS2_REL',
      //   route: 'pcm/partner/create/as2'
      // }
    ]
  },
  // {
  //   menu: 'COMMON.MENU.ENVELOP.LABEL',
  //   iconCls: 'mail',
  //   menuId: 'envelope',
  //   roles: 'admin',
  //   subMenu: [
  //     {
  //       label: 'COMMON.MENU.ENVELOP.SUB_MENU.CRTE_X12',
  //       route: 'pcm/envelope/create-x12'
  //     },
  //     {
  //       label: 'COMMON.MENU.ENVELOP.SUB_MENU.MNGE_X12',
  //       route: 'pcm/envelope/manage-x12'
  //     }
  //   ]
  // },
  {
    menu: 'COMMON.MENU.APPLICATION.LABEL',
    iconCls: 'extension',
    menuId: 'application',
    roles: 'admin',
    subMenu: [
      {
        label: 'COMMON.MENU.APPLICATION.SUB_MENU.CRTE_APP',
        route: 'pcm/application/create'
      },
      {
        label: 'COMMON.MENU.APPLICATION.SUB_MENU.MNGE_APP',
        route: 'pcm/application/manage'
      }
    ]
  },
  {
    menu: 'COMMON.MENU.DATA_FLW.LABEL',
    iconCls: 'swap_calls',
    menuId: 'dataFlow',
    roles: 'admin',

    subMenu: [
      {
        label: 'COMMON.MENU.DATA_FLW.SUB_MENU.BLD_DT_FLW',
        route: 'pcm/data-flow/build'
      },
      {
        label: 'COMMON.MENU.DATA_FLW.SUB_MENU.CRTE_RULE',
        route: 'pcm/data-flow/create/rule'
      },
      {
        label: 'COMMON.MENU.DATA_FLW.SUB_MENU.MNGE_RULE',
        route: 'pcm/data-flow/manage'
      }
    ]
  }
];

export const businessAdminMenu = [
  {
    menu: 'COMMON.MENU.FL_TRNSFR.LABEL',
    iconCls: 'file_copy',
    menuId: 'fileTransfer',
    roles: 'admin|user',
    subMenu: [
      {
        label: 'COMMON.MENU.FL_TRNSFR.SUB_MENU.SRCH',
        iconCls: 'search',
        route: 'pcm/home'
      }
    ]
  },
  {
    menu: 'COMMON.MENU.REPORTS.LABEL',
    iconCls: 'bar_chart',
    menuId: 'reports',
    roles: 'admin',
    subMenu: [
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.DT_FLW',
        route: 'pcm/reports/data-flow-reports'
      },
      // {
      //   label: 'COMMON.MENU.REPORTS.SUB_MENU.997_OVR_DUE',
      //   route: 'pcm/reports/overdue'
      // },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.TRNSCTN_VOL_RPT',
        route: 'pcm/reports/volume-reports'
      },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.TRNSCTN_VOL_DOC',
        route: 'pcm/reports/doctype-reports'
      },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.VOL_BY_PRTNR',
        route: 'pcm/reports/volume-partners'
      }
    ]
  },
  ...onBorderMenu
];

export const businessUserMenu = [
  {
    menu: 'COMMON.MENU.FL_TRNSFR.LABEL',
    iconCls: 'file_copy',
    menuId: 'fileTransfer',
    roles: 'admin|user',
    subMenu: [
      {
        label: 'COMMON.MENU.FL_TRNSFR.SUB_MENU.SRCH',
        iconCls: 'search',
        route: 'pcm/home'
      }
    ]
  },
  {
    menu: 'COMMON.MENU.REPORTS.LABEL',
    iconCls: 'bar_chart',
    menuId: 'reports',
    roles: 'admin',
    subMenu: [
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.DT_FLW',
        route: 'pcm/reports/data-flow-reports'
      },
      // {
      //   label: 'COMMON.MENU.REPORTS.SUB_MENU.997_OVR_DUE',
      //   route: 'pcm/reports/overdue'
      // },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.TRNSCTN_VOL_RPT',
        route: 'pcm/reports/volume-reports'
      },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.TRNSCTN_VOL_DOC',
        route: 'pcm/reports/doctype-reports'
      },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.VOL_BY_PRTNR',
        route: 'pcm/reports/volume-partners'
      }
    ]
  },
  {
    menu: 'COMMON.MENU.PARTNER.LABEL',
    iconCls: 'public',
    menuId: 'partner',
    roles: 'admin',
    subMenu: [
      {
        label: 'View Partner',
        route: 'pcm/partner/manage'
      }
    ]
  },
  // {
  //   menu: 'COMMON.MENU.ENVELOP.LABEL',
  //   iconCls: 'mail',
  //   menuId: 'envelope',
  //   roles: 'admin',
  //   subMenu: [
  //     {
  //       label: 'COMMON.MENU.ENVELOP.SUB_MENU.MNGE_X12',
  //       route: 'pcm/envelope/manage-x12'
  //     }
  //   ]
  // },
  {
    menu: 'COMMON.MENU.APPLICATION.LABEL',
    iconCls: 'extension',
    menuId: 'application',
    roles: 'admin',
    subMenu: [
      {
        label: 'View Application',
        route: 'pcm/application/manage'
      }
    ]
  },
  {
    menu: 'COMMON.MENU.DATA_FLW.LABEL',
    iconCls: 'swap_calls',
    menuId: 'dataFlow',
    roles: 'admin',

    subMenu: [
      {
        label: 'View Data Flow',
        route: 'pcm/data-flow/view'
      },
      {
        label: 'View Rule',
        route: 'pcm/data-flow/manage'
      }
    ]
  }
];

export const dataProcessorMenu = [
  {
    menu: 'COMMON.MENU.FL_TRNSFR.LABEL',
    iconCls: 'file_copy',
    menuId: 'fileTransfer',
    roles: 'admin|user',
    subMenu: [
      {
        label: 'COMMON.MENU.FL_TRNSFR.SUB_MENU.SRCH',
        iconCls: 'search',
        route: 'pcm/home'
      }
    ]
  },
  {
    menu: 'COMMON.MENU.REPORTS.LABEL',
    iconCls: 'bar_chart',
    menuId: 'reports',
    roles: 'admin',
    subMenu: [
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.DT_FLW',
        route: 'pcm/reports/data-flow-reports'
      },
      // {
      //   label: 'COMMON.MENU.REPORTS.SUB_MENU.997_OVR_DUE',
      //   route: 'pcm/reports/overdue'
      // },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.TRNSCTN_VOL_RPT',
        route: 'pcm/reports/volume-reports'
      },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.TRNSCTN_VOL_DOC',
        route: 'pcm/reports/doctype-reports'
      },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.VOL_BY_PRTNR',
        route: 'pcm/reports/volume-partners'
      }
    ]
  }
];

export const fileOperatorMenu = [
  {
    menu: 'COMMON.MENU.FL_TRNSFR.LABEL',
    iconCls: 'file_copy',
    menuId: 'fileTransfer',
    subMenu: [
      {
        label: 'COMMON.MENU.FL_TRNSFR.SUB_MENU.SRCH',
        iconCls: 'search',
        route: 'pcm/file-search'
      }
    ]
  },
  {
    menu: 'COMMON.MENU.REPORTS.LABEL',
    iconCls: 'bar_chart',
    menuId: 'reports',
    roles: 'admin',
    subMenu: [
      // {
      //   label: 'COMMON.MENU.REPORTS.SUB_MENU.DT_FLW',
      //   route: 'pcm/reports/data-flow-reports'
      // },
      // {
      //   label: 'COMMON.MENU.REPORTS.SUB_MENU.997_OVR_DUE',
      //   route: 'pcm/reports/overdue'
      // },
      // {
      //   label: 'COMMON.MENU.REPORTS.SUB_MENU.TRNSCTN_VOL_RPT',
      //   route: 'pcm/reports/volume-reports'
      // },
      // {
      //   label: 'COMMON.MENU.REPORTS.SUB_MENU.TRNSCTN_VOL_DOC',
      //   route: 'pcm/reports/doctype-reports'
      // },
      // {
      //   label: 'COMMON.MENU.REPORTS.SUB_MENU.VOL_BY_PRTNR',
      //   route: 'pcm/reports/volume-partners'
      // }
      {
        label: 'Dashboard',
        route: 'pcm/reports/dashboard-report'
      },
    ]
  },
  {
    menu: 'MFT',
    iconCls: 'public',
    menuId: 'mft',
    roles: 'admin',
    subMenu: [
      {
        label: 'File Upload',
        route: 'pcm/mft/file-upload'
      },
      {
        label: 'File Download',
        route: 'pcm/mft/file-download'
      },
    ]
  },
];

export const dataProcessorRestrictedMenu = [
  {
    menu: 'COMMON.MENU.FL_TRNSFR.LABEL',
    iconCls: 'file_copy',
    menuId: 'fileTransfer',
    roles: 'admin|user',
    subMenu: [
      {
        label: 'COMMON.MENU.FL_TRNSFR.SUB_MENU.SRCH',
        iconCls: 'search',
        route: 'pcm/home'
      }
    ]
  },
  {
    menu: 'COMMON.MENU.REPORTS.LABEL',
    iconCls: 'bar_chart',
    menuId: 'reports',
    roles: 'admin',
    subMenu: [
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.DT_FLW',
        route: 'pcm/reports/data-flow-reports'
      },
      // {
      //   label: 'COMMON.MENU.REPORTS.SUB_MENU.997_OVR_DUE',
      //   route: 'pcm/reports/overdue'
      // },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.TRNSCTN_VOL_RPT',
        route: 'pcm/reports/volume-reports'
      },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.TRNSCTN_VOL_DOC',
        route: 'pcm/reports/doctype-reports'
      },
      {
        label: 'COMMON.MENU.REPORTS.SUB_MENU.VOL_BY_PRTNR',
        route: 'pcm/reports/volume-partners'
      }
    ]
  }
];

export const getUserMenu = (id) => {
  if (id) {
    const menuItems = {
      super_admin: superAdmin,
      admin: adminMenu,
      on_boarder: onBorderMenu,
      business_admin: businessAdminMenu,
      business_user: businessUserMenu,
      data_processor: dataProcessorMenu,
      file_operator: fileOperatorMenu,
      data_processor_restricted: dataProcessorRestrictedMenu
    };
    return (menuItems[id] || []).map(item => {
      return item;
    });
  }
};

export const partnerActionButtons = () => {
  if (getUserType() === Role.businessUser) {
    return [
      {
        page: 'manage partner',
        label: 'COMMON.MENU.PARTNER.ACTIONS.VIEW',
        iconCls: '',
        class: 'btn btn-success',
        link: '',
        id: 'viewPartner'
      },
    ];
  } else {
    return [
      {
        page: 'manage partner',
        label: 'COMMON.MENU.PARTNER.ACTIONS.ACTVTE',
        iconCls: '',
        class: 'btn btn-success',
        link: environment.STATUS_OF_PARTNER,
        id: 'partActivate'
      },
      {
        page: 'manage partner',
        label: 'COMMON.MENU.PARTNER.ACTIONS.DEACTVTE',
        iconCls: '',
        class: 'btn btn-pcm-danger',
        link: environment.STATUS_OF_PARTNER,
        id: 'partDeactivate'
      },
      {
        page: 'manage partner',
        label: 'COMMON.MENU.PARTNER.ACTIONS.EDIT',
        iconCls: '',
        class: 'btn btn-pcm-edit',
        link: '',
        id: 'partEdit'
      },
      {
        page: 'manage partner',
        label: 'COMMON.MENU.PARTNER.ACTIONS.DELETE',
        iconCls: '',
        class: 'btn btn-pcm-delete',
        link: environment.DELETE_PARTNER,
        id: 'partDelete'
      }
    ];
  }
};

export const appActionButtons = () => {
  if (getUserType() === Role.businessUser) {
    return [
      {
        page: 'manage application',
        label: 'COMMON.MENU.APPLICATION.ACTIONS.VIEW',
        iconCls: '',
        class: 'btn btn-pcm-view',
        link: '',
        id: 'viewApp'
      }
    ];
  } else {
    return [
      {
        page: 'manage application',
        label: 'COMMON.MENU.APPLICATION.ACTIONS.ACTVTE',
        iconCls: '',
        class: 'btn btn-success',
        link: environment.STATUS_OF_APPLICATION,
        id: 'appActivate'
      },
      {
        page: 'manage application',
        label: 'COMMON.MENU.APPLICATION.ACTIONS.DEACTVTE',
        iconCls: '',
        class: 'btn btn-pcm-danger',
        link: environment.STATUS_OF_APPLICATION,
        id: 'appDeactivate'
      },
      {
        page: 'manage application',
        label: 'COMMON.MENU.APPLICATION.ACTIONS.EDIT',
        iconCls: '',
        class: 'btn btn-pcm-edit',
        link: '',
        id: 'appEdit'
      },
      {
        page: 'manage application',
        label: 'COMMON.MENU.APPLICATION.ACTIONS.DELETE',
        iconCls: '',
        class: 'btn btn-pcm-delete',
        link: environment.CREATE_APPLICATION,
        id: 'appDelete'
      }
    ];
  }
};

export const ruleActionButtons = () => {
  if (getUserType() === Role.businessUser) {
    return [
      {
        page: 'manage rule',
        label: 'COMMON.MENU.DATA_FLW.ACTIONS.VIEW',
        iconCls: '',
        class: 'btn btn-pcm-edit',
        link: '',
        id: 'viewRule'
      }
    ];
  } else {
    return [
      {
        page: 'manage rule',
        label: 'COMMON.MENU.DATA_FLW.ACTIONS.EDIT',
        iconCls: '',
        class: 'btn btn-pcm-edit',
        link: '',
        id: 'ruleEdit'
      },
      {
        page: 'manage rule',
        label: 'COMMON.MENU.DATA_FLW.ACTIONS.DELETE',
        iconCls: '',
        class: 'btn btn-pcm-delete',
        link: environment.DELETE_RULE,
        id: 'ruleDelete'
      }
    ];
  }
};

export const fileTransferSearchButtons = () => {
  if (getUserType() === Role.dataProcessorRestricted) {
    return [
      {
        page: 'file search',
        label: 'COMMON.MENU.FL_TRNSFR.ACTIONS.VIEW_DTL',
        iconCls: '',
        class: 'btn btn-pcm-1',
        link: '',
        id: 'details'
      },
      {
        page: 'file search',
        label: 'COMMON.MENU.FL_TRNSFR.ACTIONS.COR_DTL',
        iconCls: '',
        class: 'btn btn-pcm-8',
        link: '',
        id: 'cor_details'
      },
      {
        page: 'file search',
        label: 'COMMON.MENU.FL_TRNSFR.ACTIONS.VIEW_ACTVTY',
        iconCls: '',
        class: 'btn btn-pcm-2',
        link: environment.FILE_TRANSFER_ACTIVITY,
        id: 'activity'
      }
    ];
  } else {
    return [
      {
        page: 'file search',
        label: 'COMMON.MENU.FL_TRNSFR.ACTIONS.VIEW_DTL',
        iconCls: '',
        class: 'btn btn-pcm-1',
        link: '',
        id: 'details'
      },
      {
        page: 'file search',
        label: 'COMMON.MENU.FL_TRNSFR.ACTIONS.COR_DTL',
        iconCls: '',
        class: 'btn btn-pcm-8',
        link: '',
        id: 'cor_details'
      },
      {
        page: 'file search',
        label: 'COMMON.MENU.FL_TRNSFR.ACTIONS.VIEW_ACTVTY',
        iconCls: '',
        class: 'btn btn-pcm-2',
        link: environment.FILE_TRANSFER_ACTIVITY,
        id: 'activity'
      },
      {
        page: 'file search',
        label: 'COMMON.MENU.FL_TRNSFR.ACTIONS.PCKUP_NW',
        iconCls: '',
        class: 'btn btn-pcm-3',
        link: environment.FILE_TRANSFER_PICK_NOW,
        id: 'filePickup'
      },
      {
        page: 'file search',
        label: 'COMMON.MENU.FL_TRNSFR.ACTIONS.REPROCESS',
        iconCls: '',
        class: 'btn btn-pcm-4',
        link: environment.FILE_TRANSFER_REPROCESS,
        id: 'fileReprocess'
      },
      {
        page: 'file search',
        label: 'COMMON.MENU.FL_TRNSFR.ACTIONS.DRP_AGN',
        iconCls: '',
        class: 'btn btn-pcm-5',
        link: environment.FILE_TRANSFER_DROP_AGAIN,
        id: 'fileDropAgain'
      }
    ];
  }
};

export const superAdminOptions = [
  {label: 'Select', value: ''},
  {label: 'Super Admin', value: Role.superAdmin},
  {label: 'Admin', value: Role.admin},
  {label: 'On Boarder', value: Role.onBoarder},
  {label: 'Business Admin', value: Role.businessAdmin},
  {label: 'Business User', value: Role.businessUser},
  {label: 'File Operator', value: Role.fileOperator},
  {label: 'Data Processor', value: Role.dataProcessor},
  {label: 'Data Processor Restricted', value: Role.dataProcessorRestricted}
];

export const adminOptions = [
  {label: 'Select', value: ''},
  {label: 'Admin', value: Role.admin},
  {label: 'On Boarder', value: Role.onBoarder},
  {label: 'Business Admin', value: Role.businessAdmin},
  {label: 'Business User', value: Role.businessUser},
  {label: 'File Operator', value: Role.fileOperator},
  {label: 'Data Processor', value: Role.dataProcessor},
  {label: 'Data Processor Restricted', value: Role.dataProcessorRestricted}
];

export const userOptions = () => {
  if (getUserType() === Role.superAdmin && profileType() !== profile.SSO_SSP_SEAS) {
    return superAdminOptions;
  } else if (getUserType() === Role.admin || profileType() === profile.SSO_SSP_SEAS) {
    return adminOptions;
  }
};
