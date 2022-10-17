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

import { Columns } from './columns';
import { PcmTblActionComponent } from './pcm-tbl-action/pcm-tbl-action.component';
import { PcmRenderCellComponent } from './pcm-render-cell/pcm-render-cell.component';

export const FILE_SEARCH_COLS: Array<Columns> = [
    {
        title: 'File Arrived',
        id: 'filearrived',
        filter: false,
    },
    {
        title: 'Partner',
        id: 'partner',
        filter: false
    },
    {
        title: 'Application',
        id: 'application',
        filter: false
    },
    {
        title: 'Src. Filename',
        id: 'srcfilename',
        filter: false,
        type: 'custom',
        renderComponent: PcmRenderCellComponent,
        onComponentInitFunction: (instance: any) => {
            instance.title = 'Src. Filename';
            instance.key = 'srcfilename';
        }
    },
    {
        title: 'Dest. Filename',
        id: 'destfilename',
        filter: false,
        type: 'custom',
        renderComponent: PcmRenderCellComponent,
        onComponentInitFunction: (instance: any) => {
            instance.title = 'Dest. Filename';
            instance.key = 'destfilename';

        }
    },
    {
        title: 'Trans',
        id: 'transfile',
        filter: false
    },
    {
        title: 'Status',
        id: 'status',
        filter: false
    },
    {
        title: 'Flow Inout',
        id: 'flowinout',
        filter: false
    },
    {
        title: 'Doc. Trans',
        id: 'doctrans',
        filter: false
    },
    {
        title: 'Sender Id',
        id: 'senderid',
        filter: false
    },
    {
        title: 'Reciever Id',
        id: 'reciverid',
        filter: false
    },
];
