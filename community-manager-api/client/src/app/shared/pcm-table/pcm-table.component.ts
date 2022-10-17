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

import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  ViewChild
} from '@angular/core';
import {Router} from '@angular/router';
import {MatDialog, MatSort} from '@angular/material';
import {FileSearchService} from 'src/app/services/file-search/file-search.service';
import {PartnerService} from 'src/app/services/partner.service';
import {ViewDetailModalComponent} from '../view-detail-modal/view-detail-modal.component';
import {ColumnConfigureComponent} from './column-configure/column-configure.component';
import {displayFields} from 'src/app/model/displayfields.map';
import {RuleService} from '../../services/rule.service';
import {correlationMap} from '../../model/correlation.constant';
import Swal from 'sweetalert2';
import {UserService} from '../../services/user.service';
import {SelectionModel} from '@angular/cdk/collections';
import {EnvelopeService} from 'src/app/pcm-envelope/envelope.service';
import Utility from '../../../utility/Utility';
import jsPDF from 'jspdf';
import 'jspdf-autotable';
import {DatePipe} from '@angular/common';
import moment from 'moment';
import {TranslateService} from '@ngx-translate/core';
import {environment} from "../../../environments/environment";
import {getUser, getUserType} from "../../services/menu.permissions";
import {Role} from "../../model/roles";
import {AppComponent} from "../../app.component";

@Component({
  selector: 'app-pcm-table',
  templateUrl: './pcm-table.component.html',
  styleUrls: ['./pcm-table.component.scss']
})
export class PcmTableComponent implements OnInit, AfterViewInit, OnChanges {
  @ViewChild('TABLE') table: ElementRef;
  selection = new SelectionModel(true, []);
  @ViewChild(MatSort) sort: MatSort;
  userRole;
  length = 0;
  pageIndex = 0;
  pageSize = 0;
  totalRecords;
  @Input() dataSource: any = [];
  @Input() isB2bActive: any;
  @Input() fromDate = moment.utc();
  @Input() toDate = moment.utc();
  @Input() currentPage = 0;
  @Input() page: string;
  @Input() actionMenu: any = [];
  @Input() totalElements: Number;
  @Input() totalPages: Number;
  @Input() sortBy: string;
  @Input() sortDir: string;
  @Input() size: any;
  @Input() headerColumns: string[];
  @Input() displayedColumns: string[];
  @Input() searchTblConfig = [];
  @Input() serachTblConfigRef = [];
  @Input() exportHeaders: any = [];
  @Input() protocolMap: any = [];

  @Output() sortChange: EventEmitter<Object> = new EventEmitter();
  @Output() pagination: EventEmitter<Object> = new EventEmitter();
  @Output() columnsChanged: EventEmitter<any> = new EventEmitter();
  @Output() resStatus: EventEmitter<any> = new EventEmitter();
  @Output() checkBoxSelected: EventEmitter<any> = new EventEmitter();
  @Output() statusChange: EventEmitter<any> = new EventEmitter()

  fileResults = [];
  actionMenuButtons = [];

  colChanged = true;
  isDowloading = false;

  constructor(
    private service: FileSearchService,
    private partnerService: PartnerService,
    private userService: UserService,
    public dialog: MatDialog,
    public cdRef: ChangeDetectorRef,
    private ruleService: RuleService,
    private router: Router,
    private envelopeService: EnvelopeService,
    private datePipe: DatePipe,
    private translate: TranslateService,
    private appComponent: AppComponent,
  ) {
  }

  navigateRule(row) {
    console.log(row);
    this.appComponent.drawer.toggle(true).then();
    if(getUserType() === Role.superAdmin || getUserType() === Role.onBoarder || getUserType() === Role.businessAdmin) {
      this.router.navigate(['/pcm/data-flow/edit-build', row.partnerProfile, row.applicationProfile, row.flow, row.seqType]).then();
    } else if(getUserType() === Role.businessUser) {
      this.router.navigate(['/pcm/data-flow/view-build', row.partnerProfile, row.applicationProfile, row.flow, row.seqType]).then();
    }
  }

  ngOnInit() {
    console.log()
    this.userRole = this.userService.role;
    this.totalRecords = this.totalElements;
    this.isB2bActive = sessionStorage.getItem('isB2Bactive') || false;
    this.fileResults = this.dataSource.slice();
    this.actionMenuButtons = this.actionMenu;
    this.displayedColumns = (this.displayedColumns || []).filter(item => item !== 'select' && item !== 'check_select');
    if (this.page !== 'data_flow reports' && this.page !== 'over_due reports' && this.page !== 'file search') {
      this.displayedColumns = ['select'].concat(this.displayedColumns);
    } else if (this.page === 'file search') {
      if (!!this.isB2bActive && this.userRole !== 'data_processor_restricted') {
        this.displayedColumns = ['check_select'].concat(this.displayedColumns);
        this.displayedColumns = ['select'].concat(this.displayedColumns);
      } else {
        this.displayedColumns = ['select'].concat(this.displayedColumns);
      }
    }
  }

  getHeader(val) {
    if(val === '#') {
      return val;
    }
    return val.split('.').reduce((o, i) => o[i], this.translate.translations.en);
  }

  getProtocol(val) {
    let proto;
    this.protocolMap.forEach(item => {
      if(item.key === val) {
        proto = item.value
      }
    })
    return proto;
  }

  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.slice().length;
    return numSelected === numRows;
  }

  masterToggle() {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.slice().forEach(row => {
        if (row.corebpid !== null) {
          this.selection.select(row);
        }
      });
  }

  applyFilter(filterValue) {
    const fltrString = filterValue['target']['value'].trim().toLowerCase();
    if (fltrString !== '') {
      this.fileResults = this.dataSource.slice().filter((row) => {
        for (const key of this.displayedColumns) {
          if (row[key] && row[key] !== null && row[key] !== undefined) {
            const val = row[key].toString().toLowerCase();
            if (val.indexOf(fltrString) > -1) {
              return row;
            }
          }
        }
      });
      this.totalRecords = this.fileResults.length;
    } else {
      this.fileResults = this.dataSource.slice();
      this.totalRecords = this.totalElements;
    }
  }

  changePage(e) {
    this.pagination.emit(e);
  }

  sortData(e) {
    this.sortChange.emit(e);
  }

  ngAfterViewInit() {
    // If the user changes the sort order, reset back to the first page.
    // this.sort.sortChange.subscribe(rr => console.log('Sort', rr));
  }

  menuClick(btnDtl, row) {
    let URL = '';
    switch (btnDtl.id) {
      case 'details':
        URL = `${btnDtl.link}${row.seqid}`;
        break;
      case 'cor_details':
        URL = `${btnDtl.link}${row.seqid}`;
        break;
      case 'activity':
        URL = `${btnDtl.link}${row.seqid}`;
        break;
      case 'filePickup':
        URL = `${btnDtl.link}${row.pickbpid}`;
        break;
      case 'fileReprocess':
        URL = `${btnDtl.link}${row.corebpid}`;
        break;
      case 'fileDropAgain':
        URL = `${btnDtl.link}${row.deliverybpid}`;
        break;
      case 'appActivate':
        URL = `${btnDtl.link}${row.pkId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: this.translate.instant('SWEET_ALERT.APP.ACTIVATE.TITLE'),
          text: this.translate.instant('SWEET_ALERT.APP.ACTIVATE.BODY'),
          showCancelButton: true,
          confirmButtonText: this.translate.instant('SWEET_ALERT.APP.ACTIVATE.CNFRM_TXT'),
        }).then(result => {
          if (!!result['value']) {
            this.partnerService.statusPartner(btnDtl.link, row.pkId, row.appIsActive)
              .subscribe((res) => {
                Swal.fire({
                  title: this.translate.instant('SWEET_ALERT.APP.TITLE'),
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                }).then();
                this.resStatus.emit(res);
              }, (err) => {
                if(err.status !== 401) {
                  Swal.fire(
                    this.translate.instant('SWEET_ALERT.APP.TITLE'),
                    err['error']['errorDescription'],
                    'error'
                  ).then();
                }
              });
          }
        });
        break;
      case 'appDeactivate':
        URL = `${btnDtl.link}${row.pkId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: this.translate.instant('SWEET_ALERT.APP.DEACTIVATE.TITLE'),
          text: this.translate.instant('SWEET_ALERT.APP.DEACTIVATE.BODY'),
          showCancelButton: true,
          confirmButtonText: this.translate.instant('SWEET_ALERT.APP.DEACTIVATE.CNFRM_TXT')
        }).then((result) => {
          if (!!result.value) {
            this.partnerService.statusPartner(btnDtl.link, row.pkId, row.appIsActive)
              .subscribe((res) => {
                Swal.fire({
                  title: this.translate.instant('SWEET_ALERT.APP.TITLE'),
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                }).then();

                this.resStatus.emit(res);
              }, (err) => {
                if(err.status !== 401) {
                  Swal.fire(
                    this.translate.instant('SWEET_ALERT.APP.TITLE'),
                    err['error']['errorDescription'],
                    'error'
                  ).then();
                }
              });
          }
        });
        break;
      case 'appDelete':
        URL = `${btnDtl.link}${row.pkId}`;
        if((row.appIntegrationProtocol === 'SFGFTP' || row.appIntegrationProtocol === 'SFGFTPS' || row.appIntegrationProtocol === 'SFGSFTP') && getUser().sfgEnabled === false) {
          Swal.fire({
            title: 'Application',
            text: 'SFG Profiles , can\'t be deleted through Community Manager.',
            type: 'warning'
          }).then();
          return false;
        }

        if (row.appIntegrationProtocol === 'SFGFTP' || row.appIntegrationProtocol === 'SFGFTPS' || row.appIntegrationProtocol === 'SFGSFTP') {
          const url = '/pcm/si/application/';
          Swal.fire({
            type: 'question',
            customClass: {
              icon: 'swal2-question-mark'
            },
            title: this.translate.instant('SWEET_ALERT.APP.DELETE.TITLE'),
            text: this.translate.instant('SWEET_ALERT.APP.DELETE.BODY'),
            html: '<h3>delete user in SI <input type="checkbox" id="user"  /></h3><p/>' +
              '<h3>delete mailbox in SI <input type="checkbox" id="mailbox"  /></h3>',
            showCancelButton: true,
            confirmButtonText: this.translate.instant('SWEET_ALERT.APP.DELETE.CNFRM_TXT'),
            focusConfirm: false,
            preConfirm: () => {
              // @ts-ignore
              const user = Swal.getPopup().querySelector('#user').checked
              // @ts-ignore
              const mail = Swal.getPopup().querySelector('#mailbox').checked
              return {user: user, mailboxes: mail}
            }
          }).then((result) => {
            this.partnerService.deleteApp(url, row.appIntegrationProtocol, row.pkId, result.value.user, result.value.mailboxes)
              .subscribe(res => {
                Swal.fire({
                  title: this.translate.instant('SWEET_ALERT.APP.TITLE'),
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                }).then();
                this.resStatus.emit(res);
              }, (err) => {
                if(err.status !== 401) {
                  Swal.fire(
                    this.translate.instant('SWEET_ALERT.APP.TITLE'),
                    err['error']['errorDescription'],
                    'error'
                  ).then();
                }
              });
          });
        } else if (row.appIntegrationProtocol === 'SFG_CONNECT_DIRECT') {
          const url = environment.CREATE_APPLICATION;
          Swal.fire({
            type: 'question',
            customClass: {
              icon: 'swal2-question-mark'
            },
            title: this.translate.instant('SWEET_ALERT.APP.DELETE.TITLE'),
            text: this.translate.instant('SWEET_ALERT.APP.DELETE.BODY'),
            input: 'checkbox',
            inputPlaceholder: this.translate.instant('SWEET_ALERT.APP.DELETE.SI_TXT'),
            showCancelButton: true,
            confirmButtonText: this.translate.instant('SWEET_ALERT.APP.DELETE.CNFRM_TXT'),
          }).then((result) => {
            if (result.value === 1 || result.value === 0) {
              this.partnerService.deleteApp(url, row.appIntegrationProtocol, row.pkId, result.value === 1)
                .subscribe(res => {
                  Swal.fire({
                    title: this.translate.instant('SWEET_ALERT.APP.TITLE'),
                    text: res['statusMessage'],
                    type: 'success',
                    showConfirmButton: false,
                    timer: 2000
                  }).then();
                  this.resStatus.emit(res);
                }, (err) => {
                  if(err.status !== 401) {
                    Swal.fire(
                      this.translate.instant('SWEET_ALERT.APP.TITLE'),
                      err['error']['errorDescription'],
                      'error'
                    ).then();
                  }
                });
            }
          });
        } else {
          Swal.fire({
            type: 'question',
            customClass: {
              icon: 'swal2-question-mark'
            },
            title: this.translate.instant('SWEET_ALERT.APP.DELETE.TITLE'),
            text: this.translate.instant('SWEET_ALERT.APP.DELETE.BODY'),
            showCancelButton: true,
            confirmButtonText: this.translate.instant('SWEET_ALERT.APP.DELETE.CNFRM_TXT'),
          }).then((result) => {
            if (!!result.value) {
              this.partnerService.deleteApp(btnDtl.link, row.appIntegrationProtocol, row.pkId, 'true')
                .subscribe(res => {
                  Swal.fire({
                    title: this.translate.instant('SWEET_ALERT.APP.TITLE'),
                    text: res['statusMessage'],
                    type: 'success',
                    showConfirmButton: false,
                    timer: 2000
                  }).then();
                  this.resStatus.emit(res);
                }, (err) => {
                  if(err.status !== 401) {
                    Swal.fire(
                      this.translate.instant('SWEET_ALERT.APP.TITLE'),
                      err['error']['errorDescription'],
                      'error'
                    ).then();
                  }
                });
            }
          });
        }
        break;
      case 'appEdit':
        URL = `${btnDtl.link}${row.pkId}`;
        if((row.appIntegrationProtocol === 'SFGFTP' || row.appIntegrationProtocol === 'SFGFTPS' || row.appIntegrationProtocol === 'SFGSFTP') && getUser().sfgEnabled === false) {
          Swal.fire({
            title: 'Application',
            text: 'SFG Profiles , can\'t be edited through Community Manager.',
            type: 'warning'
          }).then();
          return false;
        }
        this.router.navigate(['/pcm/application/edit', row.appIntegrationProtocol, row.pkId]);
        break;
      case 'viewApp':
        URL = `${btnDtl.link}${row.pkId}`;
        this.router.navigate(['/pcm/application/view', row.appIntegrationProtocol, row.pkId]).then();
        break;
      case 'partActivate':
        URL = `${btnDtl.link}${row.pkId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: this.translate.instant('SWEET_ALERT.PARTNER.ACTIVATE.TITLE'),
          text: this.translate.instant('SWEET_ALERT.APP.ACTIVATE.BODY'),
          showCancelButton: true,
          confirmButtonText: this.translate.instant('SWEET_ALERT.APP.ACTIVATE.CNFRM_TXT')
        }).then(result => {
          if (!!result['value']) {
            this.partnerService.statusPartner(btnDtl.link, row.pkId, row.status).subscribe((res) => {
                Swal.fire({
                  title: this.translate.instant('SWEET_ALERT.PARTNER.TITLE'),
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                }).then();
                this.resStatus.emit(res);
              }, (err) => {
                if(err.status !== 401) {
                  Swal.fire(
                    this.translate.instant('SWEET_ALERT.PARTNER.TITLE'),
                    err['error']['errorDescription'],
                    'error'
                  ).then();
                }
              });
          }
        });
        break;
      case 'partDeactivate':
        URL = `${btnDtl.link}${row.pkId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: this.translate.instant('SWEET_ALERT.PARTNER.DEACTIVATE.TITLE'),
          text: this.translate.instant('SWEET_ALERT.PARTNER.DEACTIVATE.BODY'),
          showCancelButton: true,
          confirmButtonText: this.translate.instant('SWEET_ALERT.PARTNER.DEACTIVATE.CNFRM_TXT'),
        }).then((result) => {
          if (!!result['value']) {
            this.partnerService.statusPartner(btnDtl.link, row.pkId, row.status).subscribe((res) => {
                Swal.fire({
                  title: this.translate.instant('SWEET_ALERT.PARTNER.TITLE'),
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                }).then();
                this.resStatus.emit(res);
              }, (err) => {
                if(err.status !== 401) {
                  Swal.fire(
                    this.translate.instant('SWEET_ALERT.PARTNER.TITLE'),
                    err['error']['errorDescription'],
                    'error'
                  ).then();
                }
              });
          }
        });
        break;
      case 'partDelete':
        URL = `${btnDtl.link}${row.pkId}`;
        if((row.tpProtocol === 'SFGFTP' || row.tpProtocol === 'SFGFTPS' || row.tpProtocol === 'SFGSFTP') && getUser().sfgEnabled === false) {
          Swal.fire({
            title: 'Partner',
            text: 'SFG Profiles , can\'t be deleted through Community Manager',
            type: 'warning'
          }).then();
          return false;
        }
        if (row.tpProtocol === 'SFGFTP' || row.tpProtocol === 'SFGFTPS' || row.tpProtocol === 'SFGSFTP') {
          const url = '/pcm/si/partner/';
          Swal.fire({
            type: 'question',
            customClass: {
              icon: 'swal2-question-mark'
            },
            title: this.translate.instant('SWEET_ALERT.PARTNER.DELETE.TITLE'),
            text: this.translate.instant('SWEET_ALERT.PARTNER.DELETE.BODY'),
            html: '<h3>delete user in SI <input type="checkbox" id="user"  /></h3><p/>' +
              '<h3>delete mailbox in SI <input type="checkbox" id="mailbox"  /></h3>',
            showCancelButton: true,
            confirmButtonText: this.translate.instant('SWEET_ALERT.PARTNER.DELETE.CNFRM_TXT'),
            focusConfirm: false,
            preConfirm: () => {
              // @ts-ignore
              const user = Swal.getPopup().querySelector('#user').checked
              // @ts-ignore
              const mail = Swal.getPopup().querySelector('#mailbox').checked
              return {user: user, mailboxes: mail}
            }
          }).then(result => {
            this.partnerService.deletePartner(url, row.tpProtocol, row.pkId, result.value.user, result.value.mailboxes)
              .subscribe(res => {
                Swal.fire({
                  title: this.translate.instant('SWEET_ALERT.PARTNER.TITLE'),
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                }).then();
                this.resStatus.emit(res);
              }, (err) => {
                if(err.status !== 401) {
                  Swal.fire(
                    this.translate.instant('SWEET_ALERT.PARTNER.TITLE'),
                    err['error']['errorDescription'],
                    'error'
                  ).then();
                }
              });
          });
        } else if (row.tpProtocol === 'SFG_CONNECT_DIRECT') {
          const url = environment.DELETE_PARTNER;
          Swal.fire({
            type: 'question',
            customClass: {
              icon: 'swal2-question-mark'
            },
            title: this.translate.instant('SWEET_ALERT.PARTNER.DELETE.TITLE'),
            text: this.translate.instant('SWEET_ALERT.PARTNER.DELETE.BODY'),
            input: 'checkbox',
            inputPlaceholder: this.translate.instant('SWEET_ALERT.PARTNER.DELETE.SI_TXT'),
            showCancelButton: true,
            confirmButtonText: this.translate.instant('SWEET_ALERT.PARTNER.DELETE.CNFRM_TXT'),
          }).then((result) => {
            if (result.value === 1 || result.value === 0) {
              this.partnerService.deletePartner(url, row.tpProtocol, row.pkId, result.value === 1)
                .subscribe(res => {
                  Swal.fire({
                    title: this.translate.instant('SWEET_ALERT.PARTNER.TITLE'),
                    text: res['statusMessage'],
                    type: 'success',
                    showConfirmButton: false,
                    timer: 2000
                  }).then();
                  this.resStatus.emit(res);
                }, (err) => {
                  if(err.status !== 401) {
                    Swal.fire(
                      this.translate.instant('SWEET_ALERT.PARTNER.TITLE'),
                      err['error']['errorDescription'],
                      'error'
                    ).then();
                  }
                });
            }
          });
        } else {
          Swal.fire({
            type: 'question',
            customClass: {
              icon: 'swal2-question-mark'
            },
            title: this.translate.instant('SWEET_ALERT.PARTNER.DELETE.TITLE'),
            text: this.translate.instant('SWEET_ALERT.PARTNER.DELETE.BODY'),
            showCancelButton: true,
            confirmButtonText: this.translate.instant('SWEET_ALERT.PARTNER.DELETE.CNFRM_TXT')
          })
            .then((result) => {
              if (!!result.value) {
                this.partnerService.deletePartner(btnDtl.link, row.tpProtocol, row.pkId, false)
                  .subscribe(res => {
                    Swal.fire({
                      title: this.translate.instant('SWEET_ALERT.PARTNER.TITLE'),
                      text: res['statusMessage'],
                      type: 'success',
                      showConfirmButton: false,
                      timer: 2000
                    }).then();
                    this.resStatus.emit(res);
                  }, (err) => {
                    if(err.status !== 401) {
                      Swal.fire(
                        this.translate.instant('SWEET_ALERT.PARTNER.TITLE'),
                        err['error']['errorDescription'],
                        'error'
                      ).then();
                    }
                  });
              }
            });
        }
        break;
      case 'partEdit':
        URL = `${btnDtl.link}${row.pkId}`;
        if((row.tpProtocol === 'SFGFTP' || row.tpProtocol === 'SFGFTPS' || row.tpProtocol === 'SFGSFTP') && getUser().sfgEnabled === false) {
          Swal.fire({
            title: 'Partner',
            text: 'SFG Profiles , can\'t be edited through Community Manager.',
            type: 'warning'
          }).then();
          return false;
        }
        this.router.navigate(['/pcm/partner/edit', row.tpProtocol, row.pkId]).then();
        break;
      case 'viewPartner':
        URL = `${btnDtl.link}${row.pkId}`;
        this.router.navigate(['/pcm/partner/view', row.tpProtocol, row.pkId]).then();
        break;
      case 'ruleDelete':
        URL = `${btnDtl.link}${row.ruleId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: this.translate.instant('SWEET_ALERT.RULE.DELETE.TITLE'),
          text: this.translate.instant('SWEET_ALERT.RULE.DELETE.BODY'),
          showCancelButton: true,
          confirmButtonText: this.translate.instant('SWEET_ALERT.PARTNER.DELETE.CNFRM_TXT'),
        }).then((result) => {
          if (!!result.value) {
            this.ruleService.deleteRule(btnDtl.link, row.ruleId)
              .subscribe(res => {
                Swal.fire({
                  title: this.translate.instant('SWEET_ALERT.RULE.TITLE'),
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                }).then();
                this.resStatus.emit(res);
              }, (err) => {
                if(err.status !== 401) {
                  Swal.fire(
                    this.translate.instant('SWEET_ALERT.RULE.TITLE'),
                    err['error']['errorDescription'],
                    'error'
                  ).then();
                }
              });
          }
        });
        break;
      case 'ruleEdit':
        URL = `${btnDtl.link}${row.ruleId}`;
        this.router.navigate(['/pcm/data-flow/edit/rule', row.ruleId]).then();
        break;
      case 'viewRule':
        URL = `${btnDtl.link}${row.ruleId}`;
        this.router.navigate(['/pcm/data-flow/view/rule', row.ruleId]).then();
        break;
      case 'userActivate':
        URL = `${btnDtl.link}${row.pkId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: this.translate.instant('SWEET_ALERT.USER.ACTIVATE.TITLE'),
          text: this.translate.instant('SWEET_ALERT.USER.ACTIVATE.BODY'),
          showCancelButton: true,
          confirmButtonText: this.translate.instant('SWEET_ALERT.USER.ACTIVATE.CNFRM_TXT')
        }).then((result) => {
          if (!!result.value) {
            this.partnerService.statusUser(btnDtl.link, row.userid, row.status)
              .subscribe(res => {
                Swal.fire({
                  title: this.translate.instant('SWEET_ALERT.USER.TITLE'),
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                }).then();
                this.resStatus.emit(res);
              }, (err) => {
                if(err.status !== 401) {
                  Swal.fire(
                    this.translate.instant('SWEET_ALERT.USER.TITLE'),
                    err['error']['errorDescription'],
                    'error'
                  ).then();
                }
              });
          }
        });
        break;
      case 'userDeactivate':
        URL = `${btnDtl.link}${row.pkId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: this.translate.instant('SWEET_ALERT.USER.DEACTIVATE.TITLE'),
          text: this.translate.instant('SWEET_ALERT.USER.DEACTIVATE.BODY'),
          showCancelButton: true,
          confirmButtonText: this.translate.instant('SWEET_ALERT.USER.DEACTIVATE.CNFRM_TXT'),
        }).then(result => {
          if (!!result.value) {
            this.partnerService.statusUser(btnDtl.link, row.userid, row.status)
              .subscribe(res => {
                Swal.fire({
                  title: this.translate.instant('SWEET_ALERT.USER.TITLE'),
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                }).then();
                this.resStatus.emit(res);
              }, (err) => {
                if(err.status !== 401) {
                  Swal.fire(
                    this.translate.instant('SWEET_ALERT.USER.TITLE'),
                    err['error']['errorDescription'],
                    'error'
                  ).then();
                }
              });
          }
        });
        break;
      case 'userDelete':
        URL = `${btnDtl.link}${row.userid}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: this.translate.instant('SWEET_ALERT.USER.DELETE.TITLE'),
          text: this.translate.instant('SWEET_ALERT.USER.DELETE.BODY'),
          showCancelButton: true,
          confirmButtonText: this.translate.instant('SWEET_ALERT.USER.DELETE.CNFRM_TXT'),
        }).then((result) => {
          if (!!result.value) {
            this.userService.deleteUser(btnDtl.link, row.userid).subscribe((res) => {
                Swal.fire({
                  title: this.translate.instant('SWEET_ALERT.USER.TITLE'),
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                }).then();
                this.resStatus.emit(res);
              }, (err) => {
                if(err.status !== 401) {
                  Swal.fire(
                    this.translate.instant('SWEET_ALERT.USER.TITLE'),
                    err['error']['errorDescription'],
                    'error'
                  ).then();
                }
              });
          }
        });
        break;
      case 'userEdit':
        URL = `${btnDtl.link}${row.pkId}`;
        this.router.navigate(['/pcm/accessManagement/edit/user', row.userid]).then();
        break;
      case 'userPermissions':
        URL = `${btnDtl.link}${row.pkId}`;
        this.router.navigate(['/pcm/accessManagement/user-permissions', row.userid]).then();
        break;
      case 'userApprove':
        URL = `${environment.USER_ACTIVITY}?userId=${row.userid}`;
        this.userService.userActivity(URL).subscribe(res => {
          Swal.fire({
            type: 'info',
            customClass: {
              icon: 'swal2-info-mark'
            },
            title: 'User Activity',
            text: res['value'],
            showCancelButton: true,
            confirmButtonText: 'Approve',
          }).then((result) => {
            if(!!result.value) {
              Swal.fire({
                type: 'question',
                customClass: {
                  icon: 'swal2-question-mark'
                },
                title: 'User Approval',
                text: 'Do you want to approve the user?',
                showCancelButton: true,
                cancelButtonText: 'No',
                confirmButtonText: 'Yes'
              }).then((result) => {
                if(!!result.value) {
                  this.userService.unlockUser(`${environment.USER_UNLOCK}?userId=${row.userid}`).subscribe(res => {
                    Swal.fire({
                      title: 'User unlock',
                      text: res['statusMessage'],
                      type: 'success',
                      showConfirmButton: false,
                      timer: 2000
                    }).then();
                    this.resStatus.emit(res);
                  });
                }
              });
            }
          });
        });
        break;
      case 'groupDelete':
        URL = `${btnDtl.link}${row.pkId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: this.translate.instant('SWEET_ALERT.GROUP.DELETE.TITLE'),
          text: this.translate.instant('SWEET_ALERT.GROUP.DELETE.BODY'),
          showCancelButton: true,
          confirmButtonText: this.translate.instant('SWEET_ALERT.GROUP.DELETE.CNFRM_TXT'),
        }).then((result) => {
          if (!!result.value) {
            this.userService.deleteUser(btnDtl.link, row.pk_Id)
              .subscribe(res => {
                Swal.fire({
                  title: this.translate.instant('SWEET_ALERT.GROUP.TITLE'),
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                }).then();
                this.resStatus.emit(res);
              }, (err) => {
                if(err.status !== 401) {
                  Swal.fire(
                    this.translate.instant('SWEET_ALERT.GROUP.TITLE'),
                    err['error']['errorDescription'],
                    'error'
                  ).then();
                }
              });
          }
        });
        break;
      case 'groupEdit':
        URL = `${btnDtl.link}${row.pkId}`;
        this.router.navigate(['/pcm/accessManagement/edit/group', row.pk_Id]).then();
        break;
      case 'envelopDelete':
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: 'Are you sure?',
          text: 'you want to delete this user?',
          showCancelButton: true,
          confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
          if (!!result.value) {
            this.envelopeService.delete(btnDtl.link, row.pk_Id)
              .subscribe(res => {
                Swal.fire({
                  title: 'Envelop',
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                }).then();
                this.resStatus.emit(res);
              }, (err) => {
                if(err.status !== 401) {
                  Swal.fire(
                    'Envelop',
                    err['error']['errorDescription'],
                    'error'
                  ).then();
                }
              });
          }
        });
        break;
      case 'viewEnvelop':
        sessionStorage.setItem('VIEW_ENVELOP_KEY', JSON.stringify(row));
        this.router.navigate(['/pcm/envelope/view', row.pk_Id]).then();
        break;
      case 'envelopEdit':
        this.router.navigate(['/pcm/envelope/edit', row.pk_Id]).then();
        break;
      case 'apiEdit':
        URL = `${btnDtl.link}${row.pkId}`;
        this.router.navigate(['/pcm/api/edit', row.pkId]).then();
        break;
      case 'apiDelete':
        URL = `${btnDtl.link}${row.pkId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: 'Are you sure?',
          text: 'you want to delete this Proxy API?',
          showCancelButton: true,
          confirmButtonText: 'Yes, delete it!',
        }).then((result) => {
          if (!!result.value) {
            this.userService.deleteUser(btnDtl.link, row.pkId)
              .subscribe(res => {
                Swal.fire({
                  title: 'Proxy API',
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                }).then();
                this.resStatus.emit(res);
              }, (err) => {
                if(err.status !== 401) {
                  Swal.fire(
                    'Proxy API',
                    err['error']['errorDescription'],
                    'error'
                  ).then();
                }
              });
          }
        });
        break;
      default:
        URL = '';
        break;
    }
    if (URL === '') {
      this.service
        .fileSearchAction(URL)
        .subscribe(res => console.log(res), err => console.log(err));
    } else {
      this.openDialog(row, btnDtl);

    }
  }

  viewFile(row, key) {
    if (this.isDowloading) {
      return false;
    }
    // let path = row.srcarcfileloc ? row.srcarcfileloc : row.destarcfileloc;
    const type = key === 'srcfilename' ? 'source' : 'destination';
    this.isDowloading = true;
    this.service.viewFile(row.seqid, type).subscribe(res => {
      this.isDowloading = false;
      const dialogRef = this.dialog.open(ViewDetailModalComponent, {
        width: '60%',
        data: {
          page: 'view file',
          type: 'details',
          row: row,
          flowType: type,
          body: res
        }
      });
      dialogRef.afterClosed().subscribe();
    }, err => {
      this.isDowloading = false;
      const errorDescription = JSON.parse(err['error']);
      if(err.status !== 401) {
        Swal.fire(
          'Error',
          errorDescription['errorDescription'],
          'error'
        ).then();
      }
    });
  }

  openDialog(row, btnDtl) {
    if (btnDtl.page === 'file search') {
      if (btnDtl.id === 'details') {
        const tranactionDetails = [], otherDetails = [];

        for (const key in row) {
          if (key === 'adverrorstatus' || key === 'statusComments') {
            const obj1: any = {};
            obj1['label'] = displayFields[key] ? this.translate.instant(displayFields[key]) : this.translate.instant(key);
            obj1['value'] = row[key] ? row[key] : '-';
            otherDetails.push(obj1);
          } else if (key !== 'seqid') {
            const Obj: any = {};
            Obj['label'] = displayFields[key] ? this.translate.instant(displayFields[key]) : this.translate.instant(key);
            Obj['value'] = row[key] ? row[key] : '-';
            tranactionDetails.push(Obj);
          }
        }

        const dialogRef = this.dialog.open(ViewDetailModalComponent, {
          width: '60%',
          data: {
            page: 'file search',
            type: 'details',
            title: this.translate.instant('COMMON.MODAL.FILE_SRCH.TITLE'),
            body: {
              tranactionDetails,
              otherDetails
            }
          }
        });
        dialogRef.afterClosed().subscribe(result => {
          console.log(`Dialog result: ${result}`);
        });
      } else if (btnDtl.id === 'cor_details') {
        const correlationDetails = [];
        this.service.getCorrelationData().subscribe(res => {
          let fields = [];
          const obj = {};
          for (const val in res) {
            if (Utility.isNotEmpty(res[val])) {
              const crlFld = {};
              crlFld['label'] = correlationMap[val];
              fields.push(crlFld);
              obj['label'] = res[val];
            }
          }
          for (const key in row) {
            if (key.includes('correlationValue')) {
              fields.forEach((val) => {
                if (val['label'] === key) {
                  if (res[key] !== null) {
                    const corDtl = {};
                    corDtl['label'] = res[key.replace('correlationValue', 'correlationName')];
                    corDtl['value'] = row[key] ? row[key] : '-';
                    correlationDetails.push(corDtl);
                  }
                }
              });
            }
          }
          const dialogRef = this.dialog.open(ViewDetailModalComponent, {
            width: '60%',
            data: {
              page: 'file search',
              type: 'cor_details',
              title: this.translate.instant('COMMON.MODAL.FILE_SRCH.TITLE'),
              body: {
                correlationDetails
              }
            }
          });
          dialogRef.afterClosed().subscribe();
        });
      } else if (btnDtl.id === 'activity') {
        let activityResults;
        this.service.fileActivity(btnDtl.link, row.corebpid, row.seqid)
          .subscribe(res => {
            activityResults = res;
            const dialogRef = this.dialog.open(ViewDetailModalComponent, {
              width: '70%',
              panelClass: 'file_activity',
              data: {
                page: 'file search',
                type: 'activity',
                title: this.translate.instant('COMMON.MODAL.ACTVTY.TITLE'),
                body: {
                  activityResults
                }
              }
            });
            dialogRef.afterClosed().subscribe();
          });
      } else if (btnDtl.id === 'fileReprocess' || btnDtl.id === 'filePickup' || btnDtl.id === 'fileDropAgain') {
        let processId;
        if (btnDtl.id === 'fileReprocess') {
          processId = row.corebpid;
        } else if (btnDtl.id === 'filePickup') {
          processId = row.pickbpid;
        } else if (btnDtl.id === 'fileDropAgain') {
          processId = row.deliverybpid;
        }
        if (processId != null) {
          Swal.fire({
            type: 'question',
            customClass: {
              icon: 'swal2-question-mark'
            },
            title: btnDtl.id,
            text: 'Do you want to ' + btnDtl.id + '?',
            showCancelButton: true,
            confirmButtonText: 'YES',
          }).then(result => {
            if (!!result.value) {
              this.service.fileProcess(btnDtl.link, processId, row.seqid, btnDtl.id)
                .subscribe(res => {
                  Swal.fire(
                    'Success',
                    res['statusMessage'],
                    'success'
                  ).then();
                  this.resStatus.emit(res);
                }, (err) => {
                  if(err.status !== 401) {
                    Swal.fire(
                      'Error',
                      err['error']['errorDescription'],
                      'error'
                    ).then();
                  }
                });
            }
          });
        } else {
          Swal.fire({
            title: '<strong>Oops...</strong>',
            type: 'info',
            html: 'Sorry.! This transaction <b>' + btnDtl.id + ' [processId]</b> is null.',
            showCloseButton: true,
            focusConfirm: false,
          }).then();
        }
      }
    }
  }

  openColumnConfig(): void {
    const colKeys = this.searchTblConfig.map(val => val.key);
    const dialogRef = this.dialog.open(ColumnConfigureComponent, {
      width: '250px',
      data: {
        columns: this.serachTblConfigRef.map(col => {
          col['selected'] = colKeys.indexOf(col.key) > -1;
          return col;
        }),
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result.length > 0) {
        this.columnsChanged.emit(result);
      }
    });

    this.cdRef.detectChanges();
  }

  multipleReprocess() {
    const req = {
      content: this.selection.selected.map(function (obj) {
        return {key: obj.seqid, value: obj.corebpid};
      })
    };

    this.service.multipleProcess(req).subscribe(res => {
      Swal.fire({
        title: 'Partner',
        text: res['statusMessage'],
        type: 'success',
        showConfirmButton: false,
        timer: 2000
      }).then();
      this.resStatus.emit(res);
    }, (err) => {
      if(err.status !== 401) {
        Swal.fire(
          'File Transfer',
          err['error']['errorDescription'],
          'error'
        ).then();
      }
    });
  }

  ngOnChanges(changes): void {
    this.ngOnInit();
  }

  // exportCsv() {
  //   const options = {
  //     headers: this.headerColumns
  //   };
  //   const CsvResults = JSON.parse(JSON.stringify(this.fileResults));
  //   CsvResults.filter( row => {
  //     Object.keys(row).filter(key => {
  //       if(this.displayedColumns.indexOf(key) === -1) {
  //
  //         delete row[key];
  //       }
  //     }).map(item => item);
  //   });
  //   new Angular5Csv(CsvResults, 'Export_CSV', options);
  // }

  exportPdf() {
    let today = new Date();
    const date = moment(today).format('MM/DD/YYYY HH:mm a '); // this.datePipe.transform(new Date(today), ' MMM dd, yyyy, HH:mm:ss a ');
    const displayCols = this.displayedColumns.filter(col => {
      if (col !== 'check_select' && col !== 'select') {
        return col;
      }
    });
    let columns = [];
    this.exportHeaders.forEach((col, i) => {
      columns.push({title: col, dataKey: displayCols[i]});
    });
    const isFromDate = moment(this.fromDate).format('MM/DD/YYYY HH:mm a '); //this.datePipe.transform(new Date(this.fromDate), 'MM/dd/yyyy, HH:mm a');
    const isTodDate = moment(this.toDate).format('MM/DD/YYYY HH:mm a '); // this.datePipe.transform(new Date(this.toDate), 'MM/dd/yyyy, HH:mm a');
    const content = 'File Transfer Search - ' + isFromDate + ' to ' + isTodDate;
    let pdfResults = JSON.parse(JSON.stringify(this.fileResults));
    console.log(moment(this.fromDate).format('MM/DD/YYYY HH:mm a '));
    pdfResults.filter(row => {
      Object.keys(row).filter(key => {
        if (key === 'filearrived') {
          row[key] = moment(row[key]).format('MM/DD/YYYY HH:mm a '); // this.datePipe.transform(new Date(row[key]), ' MMM dd, yyyy, HH:mm:ss a ');
        }
      }).map(item => item);
    });
    let doc = new jsPDF('l', 'pt');
    doc.text(content, 150, 25);
    doc.autoTable(columns, pdfResults);
    doc.save('File Transfer Search -' + date + '.pdf');
    console.log(columns);
  }

  download(csv: string, name: string) {
    const filename = name + '.csv';
    const blob = new Blob([csv], {type: 'text/csv'});
    if (window.navigator && window.navigator.msSaveBlob) {
      window.navigator.msSaveBlob(blob, filename);
    } else {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.setAttribute('style', 'display:none;');
      a.href = url;
      a.download = filename;
      a.click();
    }
  }

  exportCsv() {
    const reportName = 'File Transfer';
    let CsvResults = JSON.parse(JSON.stringify(this.fileResults));
    CsvResults.filter(row => {
      Object.keys(row).filter(key => {
        if (this.displayedColumns.indexOf(key) === -1) {
          delete row[key];
        }
      }).map(item => item);
    });

    CsvResults.filter(row => {
      Object.keys(row).filter(key => {
        if (key === 'filearrived') {
          row[key] = moment(row[key]).format('MM/DD/YYYY HH:mm a '); // this.datePipe.transform(new Date(row[key]), ' MM/dd/yyyy HH:mm a ');
        }
      }).map(item => item);
    });

    if (CsvResults) {
      let filename = `${reportName}`;
      let csv = PcmTableComponent.ConvertToCSV(CsvResults);
      this.download(csv, filename);
    }
  }

  static ConvertToCSV(objArray) {
    let array = typeof objArray !== 'object' ? JSON.parse(objArray) : objArray;
    let str = '';
    let row = '';

    for (let index in objArray[0]) {
      row += index + ',';
    }
    row = row.slice(0, -1);
    str += row + '\r\n';

    for (let i = 0; i < array.length; i++) {
      let line = '';
      for (let index in array[i]) {
        if (line != '') line += ',';
        line += array[i][index];
      }
      str += line + '\r\n';
    }
    return str;
  }

  changeStatus(event, rec) {
    this.statusChange.emit({event, rec});
  }

}
