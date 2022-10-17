import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {AuthGuard} from "../guards/auth.guard";
import {Role} from "../model/roles";
import {MaterialModule} from "../material/material.module";
import {ReactiveFormsModule} from "@angular/forms";
import {SharedModule} from "../shared/shared.module";
import {FileDownloadComponent} from './file-download/file-download.component';
import {FileDropComponent} from "./file-drop/file-drop.component";

const routes: Routes = [
  {
    path: 'file-upload',
    component: FileDropComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.fileOperator]}
  },
  {
    path: 'file-download',
    component: FileDownloadComponent,
    canActivate: [AuthGuard],
    data: {roles: [Role.fileOperator]}
  }
]

@NgModule({
  declarations: [FileDropComponent, FileDownloadComponent],
  imports: [
    CommonModule,
    MaterialModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule,
    SharedModule
  ]
})
export class PcmMftModule {
}
