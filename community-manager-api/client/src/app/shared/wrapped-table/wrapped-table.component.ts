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

import {PcmTblActionComponent} from 'src/app/ng2table/pcm-tbl-action/pcm-tbl-action.component';
import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';
import {Router} from '@angular/router';
import {filsearchResp} from '../../../sample/filesearch';
import {MatDialog, MatSort} from '@angular/material';
import {FileSearchService} from 'src/app/services/file-search/file-search.service';
import {PartnerService} from 'src/app/services/partner.service';
import {ViewDetailModalComponent} from '../view-detail-modal/view-detail-modal.component';
import {displayFields} from 'src/app/model/displayfields.map';
import {RuleService} from '../../services/rule.service';
import {correlationMap} from '../../model/correlation.constant';
import Swal from 'sweetalert2';
import {UserService} from '../../services/user.service';
import {SelectionModel} from '@angular/cdk/collections';
import {EnvelopeService} from 'src/app/pcm-envelope/envelope.service';
import Utility from '../../../utility/Utility';
import {ColumnConfigureComponent} from '../pcm-table/column-configure/column-configure.component';
import { TranslateService } from '@ngx-translate/core';

export const VIEW_PARTNER_KEY = 'VIEW_PARTNER_KEY';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'pcm-wrapped-table',
  templateUrl: './wrapped-table.component.html',
  styleUrls: ['./wrapped-table.component.scss']
})
export class WrappedTableComponent implements OnInit {
  constructor(
    private service: FileSearchService,
    private partnerservice: PartnerService,
    private userservice: UserService,
    public dialog: MatDialog,
    public cdRef: ChangeDetectorRef,
    private ruleService: RuleService,
    private router: Router,
    private envelopeService: EnvelopeService,
    private translate: TranslateService
  ) {
    this.source = [];
  }

  settings = {};
  @Input() columnsConfig = {};
  @Input() data;
  @Input() req;
  @Input() qryParams;
  @Input() page;
  @Input() actionMenu = [];
  source = [];

  length = 0;
  pageIndex = 0;
  pageSize = 0;
  previousPageIndex = 0;
  @Input() dataSource: any = [];

  @Input() currentPage = 0;
  @Input() totalElements: Number;
  @Input() totalPages: Number;
  @Input() sortBy: string;
  @Input() sortDir: string;
  @Input() size: any;

  @Output() sortChange: EventEmitter<Object> = new EventEmitter();
  @Output() pagination: EventEmitter<Object> = new EventEmitter();


  selection = new SelectionModel(true, []);

  @Output() resStatus: EventEmitter<any> = new EventEmitter();
  @Output() checkBoxSelected: EventEmitter<any> = new EventEmitter();
  @Output() columnsChanged: EventEmitter<any> = new EventEmitter();


  fileResults = [];
  actionMenuButtons = [];

  @Input() headerColumns: string[];
  @Input() displayedColumns: string[];

  @Input() searchTblConfig = [];
  @Input() serachTblConfigRef = [];

  isDowloading = false;

  ELEMENT_DATA: any[] = filsearchResp.content;
  colChanged = true;

  ngOnInit(): void {
    if (this.actionMenu && this.actionMenu.length > 0) {
      this.source = this.data.map(itm => Object.assign({actionBtn: ''}, itm));
      this.columnsConfig = Object.assign({
          actionBtn: {
            title: 'Actions',
            type: 'custom',
            filter: false,
            renderComponent: PcmTblActionComponent,
            onComponentInitFunction: (instance: any) => {
              instance.page = this.page;
              instance.actionMenuButtons = this.actionMenu;
              instance.actionBtn = true;
              instance.click.subscribe(row => {
                this.menuClick(row.item, row);
              });
            }
          }
        },
        this.columnsConfig);
    } else {
      this.source = this.data;
    }
    this.settings = {
      columns: this.columnsConfig,
      actions: {
        delete: false,
        add: false,
        edit: false
      },
    };
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

  applyFilter(filterValue: string) {
    const fltrString = filterValue.trim().toLowerCase();
    if (fltrString !== '') {
      this.fileResults = this.dataSource.slice().filter(row => {
        for (const key of this.displayedColumns) {
          if (row[key] && row[key] !== null && row[key] !== undefined) {
            const val = row[key].toString().toLowerCase();
            if (val.indexOf(fltrString) > -1) {
              return row;
            }
          }
        }
      });
    } else {
      this.fileResults = this.dataSource.slice();
    }
  }

  changePage(e) {
    this.pagination.emit(e);
  }

  sortData(e) {
    this.sortChange.emit(e);
  }


  menuClick(btnDtl, row) {
    let URL = '';
    switch (btnDtl.id) {
      case 'details':
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
        // URL = `${btnDtl.link}${row.pkId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: 'Are you sure?',
          text: 'you want to activate this application?',
          showCancelButton: true,
          confirmButtonText: 'Yes, activate it!'
        }).then((result) => {
          if (!!result.value) {
            this.partnerservice.statusPartner(btnDtl.link, row.pkId, row.appIsActive)
              .subscribe(res => {
                Swal.fire({
                  title: 'Application',
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                });
                this.resStatus.emit(res);
              }, (err) => {
                console.log(err);
                Swal.fire(
                  'Application',
                  err['error']['errorDescription'],
                  'error'
                );
              });
          }
        });

        break;
      case 'appDeactivate':
        // URL = `${btnDtl.link}${row.pkId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: 'Are you sure?',
          text: 'you want to deactivate this application?',
          showCancelButton: true,
          confirmButtonText: 'Yes, deactivate it!'
        }).then((result) => {
          if (!!result.value) {
            this.partnerservice.statusPartner(btnDtl.link, row.pkId, row.appIsActive)
              .subscribe(res => {
                Swal.fire({
                  title: 'Application',
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                });

                this.resStatus.emit(res);
              }, (err) => {
                console.log(err);
                Swal.fire(
                  'Application',
                  err['error']['errorDescription'],
                  'error'
                );
              });
          }
        });


        break;
      case 'appDelete':
        // URL = `${btnDtl.link}${row.pkId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: 'Are you sure?',
          text: 'you want to delete this application?',
          showCancelButton: true,
          confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
          if (!!result.value) {
            this.partnerservice.deletePartner(btnDtl.link, row.appIntegrationProtocol, row.pkId, 'true')
              .subscribe(res => {
                Swal.fire({
                  title: 'Application',
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                });
                this.resStatus.emit(res);
              }, (err) => {
                Swal.fire(
                  'Application',
                  err['error']['errorDescription'],
                  'error'
                );
              });
          }
        });
        break;
      case 'appEdit':
        // URL = `${btnDtl.link}${row.pkId}`;
        this.router.navigate(['/pcm/application/edit', row.appIntegrationProtocol, row.pkId]);
        break;
      case 'viewApp':
        // URL = `${btnDtl.link}${row.pkId}`;
        sessionStorage.setItem(VIEW_PARTNER_KEY, JSON.stringify(row));
        this.router.navigate(['/pcm/application/view', row.pkId]);
        break;
      case 'partActivate':
        // URL = `${btnDtl.link}${row.pkId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: 'Are you sure?',
          text: 'you want to activate this partner?',
          showCancelButton: true,
          confirmButtonText: 'Yes, activate it!'
        }).then((result) => {
          if (!!result.value) {
            this.partnerservice.statusPartner(btnDtl.link, row.pkId, row.status)
              .subscribe(res => {
                Swal.fire({
                  title: 'Partner',
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                });
                this.resStatus.emit(res);
              }, (err) => {
                console.log(err);
                Swal.fire(
                  'Partner',
                  err['error']['errorDescription'],
                  'error'
                );
              });
          }
        });

        break;
      case 'partDeactivate':
        // URL = `${btnDtl.link}${row.pkId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: 'Are you sure?',
          text: 'you want to deactivate this application?',
          showCancelButton: true,
          confirmButtonText: 'Yes, deactivate it!'
        }).then((result) => {
          if (!!result.value) {
            this.partnerservice.statusPartner(btnDtl.link, row.pkId, row.status)
              .subscribe(res => {
                Swal.fire({
                  title: 'Partner',
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                });
                this.resStatus.emit(res);
              }, (err) => {
                console.log(err);
                Swal.fire(
                  'Partner',
                  err['error']['errorDescription'],
                  'error'
                );
              });
          }
        });
        break;
      case 'partDelete':
        // URL = `${btnDtl.link}${row.pkId}`;
        if (row.tpProtocol === 'SFGFTP' || row.tpProtocol === 'SFGFTPS' || row.tpProtocol === 'SFGSFTP') {
          Swal.fire({
            type: 'question',
            customClass: {
              icon: 'swal2-question-mark'
            },
            title: 'Are you sure?',
            text: 'you want to delete this partner?',
            input: 'checkbox',
            inputPlaceholder: 'delete in SI',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!'
          })
            .then((result) => {
              console.log(result);
              if (result.value === 1 || result.value === 0) {
                this.partnerservice.deletePartner(btnDtl.link, row.tpProtocol, row.pkId, result.value === 1)
                  .subscribe(res => {
                    Swal.fire({
                      title: 'Partner',
                      text: res['statusMessage'],
                      type: 'success',
                      showConfirmButton: false,
                      timer: 2000
                    });
                    this.resStatus.emit(res);
                  }, (err) => {
                    Swal.fire(
                      'Partner',
                      err['error']['errorDescription'],
                      'error'
                    );
                  });
              }
            });
        } else {
          Swal.fire({
            type: 'question',
            customClass: {
              icon: 'swal2-question-mark'
            },
            title: 'Are you sure?',
            text: 'you want to delete this partner?',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!'
          })
            .then((result) => {
              if (!!result.value) {
                this.partnerservice.deletePartner(btnDtl.link, row.tpProtocol, row.pkId, false)
                  .subscribe(res => {
                    Swal.fire({
                      title: 'Partner',
                      text: res['statusMessage'],
                      type: 'success',
                      showConfirmButton: false,
                      timer: 2000
                    });
                    this.resStatus.emit(res);
                  }, (err) => {
                    Swal.fire(
                      'Partner',
                      err['error']['errorDescription'],
                      'error'
                    );
                  });
              }
            });
        }
        break;
      case 'partEdit':
        // URL = `${btnDtl.link}${row.pkId}`;
        this.router.navigate(['/pcm/partner/edit', row.tpProtocol, row.pkId]);
        break;
      case 'viewPartner':
        sessionStorage.setItem(VIEW_PARTNER_KEY, JSON.stringify(row));
        this.router.navigate(['/pcm/partner/view', row.pkId]);
        break;
      case 'ruleDelete':
        // URL = `${btnDtl.link}${row.ruleId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: 'Are you sure?',
          text: 'you want to delete this rule?',
          showCancelButton: true,
          confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
          if (!!result.value) {
            this.ruleService.deleteRule(btnDtl.link, row.ruleId)
              .subscribe(res => {
                Swal.fire({
                  title: 'Rule',
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                });
                this.resStatus.emit(res);
              }, (err) => {
                console.log(err);
                Swal.fire(
                  'Rule',
                  err['error']['errorDescription'],
                  'error'
                );
              });
          }
        });
        break;
      case 'ruleEdit':
        // URL = `${btnDtl.link}${row.ruleId}`;
        this.router.navigate(['/pcm/data-flow/edit/rule', row.ruleId]);
        break;
      case 'userActivate':
        // URL = `${btnDtl.link}${row.pkId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: 'Are you sure?',
          text: 'you want to activate this user?',
          showCancelButton: true,
          confirmButtonText: 'Yes, activate it!'
        }).then((result) => {
          if (!!result.value) {
            this.partnerservice.statusPartner(btnDtl.link, row.userid, row.status)
              .subscribe(res => {
                Swal.fire({
                  title: 'User',
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                });
                this.resStatus.emit(res);
              }, (err) => {
                console.log(err);
                Swal.fire(
                  'User',
                  err['error']['errorDescription'],
                  'error'
                );
              });
          }
        });
        break;
      case 'userDeactivate':
        // URL = `${btnDtl.link}${row.pkId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: 'Are you sure?',
          text: 'you want to deactivate this user?',
          showCancelButton: true,
          confirmButtonText: 'Yes, deactivate it!'
        }).then((result) => {
          if (!!result.value) {
            this.partnerservice.statusPartner(btnDtl.link, row.userid, row.status)
              .subscribe(res => {
                Swal.fire({
                  title: 'User',
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                });
                this.resStatus.emit(res);
              }, (err) => {
                console.log(err);
                Swal.fire(
                  'User',
                  err['error']['errorDescription'],
                  'error'
                );
              });
          }
        });
        break;
      case 'userDelete':
        // URL = `${btnDtl.link}${row.userid}`;
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
            this.userservice.deleteUser(btnDtl.link, row.userid)
              .subscribe(res => {
                Swal.fire({
                  title: 'User',
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                });
                this.resStatus.emit(res);
              }, (err) => {
                console.log(err);
                Swal.fire(
                  'User',
                  err['error']['errorDescription'],
                  'error'
                );
              });
          }
        });
        break;
      case 'userEdit':
        // URL = `${btnDtl.link}${row.pkId}`;
        this.router.navigate(['/pcm/accessManagement/edit/user', row.userid]);
        break;
      case 'groupDelete':
        // URL = `${btnDtl.link}${row.pkId}`;
        Swal.fire({
          type: 'question',
          customClass: {
            icon: 'swal2-question-mark'
          },
          title: 'Are you sure?',
          text: 'you want to delete this group?',
          showCancelButton: true,
          confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
          if (!!result.value) {
            this.userservice.deleteUser(btnDtl.link, row.pk_Id)
              .subscribe(res => {
                Swal.fire({
                  title: 'Group',
                  text: res['statusMessage'],
                  type: 'success',
                  showConfirmButton: false,
                  timer: 2000
                });
                this.resStatus.emit(res);
              }, (err) => {
                console.log(err);
                Swal.fire(
                  'Group',
                  err['error']['errorDescription'],
                  'error'
                );
              });
          }
        });
        break;
      case 'groupEdit':
        // URL = `${btnDtl.link}${row.pkId}`;
        this.router.navigate(['/pcm/accessManagement/edit/group', row.pk_Id]);
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
                });
                this.resStatus.emit(res);
              }, (err) => {
                console.log(err);
                Swal.fire(
                  'Envelop',
                  err['error']['errorDescription'],
                  'error'
                );
              });
          }
        });
        break;
      case 'viewEnvelop':
        sessionStorage.setItem('VIEW_ENVELOP_KEY', JSON.stringify(row));
        this.router.navigate(['/pcm/envelope/view', row.pk_Id]);
        break;
      case 'envelopEdit':
        this.router.navigate(['/pcm/envelope/edit', row.pk_Id]);
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
      console.log('onActionClick', btnDtl, row);

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
      console.log(res);
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
      dialogRef.afterClosed().subscribe(result => {
        console.log(`Dialog result: ${result}`);
      });
    }, err => {
      this.isDowloading = false;
      console.log(err);
      Swal.fire(
        'Error',
        err['statusText'],
        'error'
      );
    });
  }

  openDialog(row, btnDtl) {
    console.log(row);
    if (btnDtl.page === 'file search') {
      if (btnDtl.id === 'details') {
        let correlationDetails = [], tranactionDetails = [], otherDetails = [];
        this.service.getCorrelationData().subscribe(res => {
          const fields = [];
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
              fields.forEach(val => {
                if (val.label === key) {
                  if (res[key] !== null) {
                    const corDtl = {};
                    corDtl['label'] = res[key.replace('correlationValue', 'correlationName')];
                    corDtl['value'] = row[key] ? row[key] : '-';
                    correlationDetails.push(corDtl);
                  }
                }
              });

            } else if (key === 'adverrorstatus' || key === 'statusComments') {
              const obj: any = {};
              obj['label'] = displayFields[key] ? displayFields[key] : key;
              obj['value'] = row[key] ? row[key] : '-';
              otherDetails.push(obj);
              console.log(otherDetails);
            } else if (key !== 'seqid') {
              const obj: any = {};
              obj['label'] = displayFields[key] ? displayFields[key] : key;
              obj['value'] = row[key] ? row[key] : '-';
              tranactionDetails.push(obj);
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
                correlationDetails,
                otherDetails
              }
            }
          });
          dialogRef.afterClosed().subscribe(result => {
            console.log(`Dialog result: ${result}`);
          });
        });
      } else if (btnDtl.id === 'activity') {
        let activityResults;
        this.service.fileActivity(btnDtl.link, row.corebpid, row.seqid)
          .subscribe(res => {
            activityResults = res;
            console.log(res);
            const dialogRef = this.dialog.open(ViewDetailModalComponent, {
              width: '60%',
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
            dialogRef.afterClosed().subscribe(result => {
              console.log(`Dialog result: ${result}`);
            });
          });
      } else if (btnDtl.id === 'fileReprocess' || btnDtl.id === 'filePickup' || btnDtl.id === 'fileDropAgain') {
        let processId;
        console.log(row);
        if (btnDtl.id === 'fileReprocess') {
          processId = row.corebpid;
        } else if (btnDtl.id === 'filePickup') {
          processId = row.pickbpid;
        } else if (btnDtl.id === 'fileDropAgain') {
          processId = row.deliverybpid;
        }
        if (processId != null) {
          this.service.fileProcess(btnDtl.link, processId, row.seqid, btnDtl.id)
            .subscribe(res => {
              Swal.fire(
                'Success',
                res['statusMessage'],
                'success'
              );
              this.resStatus.emit(res);
            }, (err) => {
              Swal.fire(
                'Error',
                err['error']['errorDescription'],
                'error'
              );
            });
        } else {
          Swal.fire({
            title: '<strong>Oops...</strong>',
            type: 'info',
            html: 'Sorry.! This transaction <b>' + btnDtl.id + ' [processId]</b> is null.',
            showCloseButton: true,
            showCancelButton: true,
            focusConfirm: false,
          });
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
      console.log(result);
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
    console.log(req);
    this.service.multipleProcess(req).subscribe(res => {
      console.log(res);

      Swal.fire({
        title: 'Partner',
        text: res['statusMessage'],
        type: 'success',
        showConfirmButton: false,
        timer: 2000
      });
      this.resStatus.emit(res);
    }, (err) => {
      Swal.fire(
        'File Transfer',
        err['error']['errorDescription'],
        'error'
      );
    });
  }

}
