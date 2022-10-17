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

import {NgModule, CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA} from '@angular/core';
import { CommonModule } from '@angular/common';
import { PcmTableComponent } from './pcm-table/pcm-table.component';
import { MaterialModule } from '../material/material.module';
import { LinkWidgetComponent } from './pcm-table/pcm-col-widgets/link-widget/link-widget.component';
import { ViewDetailModalComponent } from './view-detail-modal/view-detail-modal.component';
import { ColumnConfigureComponent } from './pcm-table/column-configure/column-configure.component';
import { DualListBoxComponent } from './dual-list-box/dual-list-box.component';
import { PcmListComponent } from './pcm-list/pcm-list.component';
import { ListBoxComponent } from './list-box/list-box.component';
import { FileHandlingComponent } from './file-handling/file-handling.component';
import { ReactiveFormsModule } from '@angular/forms';
import { UploadModalComponent } from './upload-modal/upload-modal.component';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { Ng2SmartTableModule } from 'ng2-smart-table';

import { PcmListBoxComponent } from './pcm-list-box/pcm-list-box.component';
import { ArraySortPipe, ArrayFilterPipe } from './pcm-list-box/array.pipes';
import { CopyWorkflowComponent } from './copy-workflow/copy-workflow.component';
import { RichEditorComponent } from './rich-editor/rich-editor.component';
import { WrappedTableComponent } from './wrapped-table/wrapped-table.component';
import { PcmPercentageFieldComponent } from './pcm-percentage-field/pcm-percentage-field.component';
import { PcmTblActionComponent } from '../ng2table/pcm-tbl-action/pcm-tbl-action.component';
import { TooltipDirective } from './list-box/tooltip.directive';
import { TranslateModule } from '@ngx-translate/core';
import { JsonInputComponent } from './json-input/json-input.component';
// @ts-ignore
import {QuillModule} from "ngx-quill";
import {BytePipe} from "./file-drag-drop/byte.pipe";
import {FileDragDropComponent} from "./file-drag-drop/file-drag-drop.component";
import {DndDirective} from "./directives/dnd.directive";
declare var require: any;

@NgModule({
  declarations: [
    PcmTableComponent,
    LinkWidgetComponent,
    ViewDetailModalComponent,
    ColumnConfigureComponent,
    DualListBoxComponent,
    PcmListComponent,
    ListBoxComponent,
    FileHandlingComponent,
    PcmListBoxComponent,
    UploadModalComponent,
    ArraySortPipe,
    ArrayFilterPipe,
    BytePipe,
    CopyWorkflowComponent,
    RichEditorComponent,
    WrappedTableComponent,
    PcmPercentageFieldComponent,
    PcmTblActionComponent,
    TooltipDirective,
    JsonInputComponent,
    FileDragDropComponent,
    DndDirective
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
    SweetAlert2Module,
    Ng2SmartTableModule,
    TranslateModule,
    QuillModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA, NO_ERRORS_SCHEMA],
  entryComponents: [
    ViewDetailModalComponent,
    ColumnConfigureComponent,
    ListBoxComponent,
    FileHandlingComponent,
    PcmListBoxComponent,
    UploadModalComponent,
    CopyWorkflowComponent,
    RichEditorComponent,
    PcmTblActionComponent,
    JsonInputComponent
  ],
  exports: [
    PcmTableComponent,
    DualListBoxComponent,
    PcmListComponent,
    ListBoxComponent,
    FileHandlingComponent,
    PcmListBoxComponent,
    UploadModalComponent,
    CopyWorkflowComponent,
    RichEditorComponent,
    PcmPercentageFieldComponent,
    WrappedTableComponent,
    TranslateModule,
    JsonInputComponent,
    FileDragDropComponent,
    DndDirective
  ]
})
export class SharedModule {}
