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
  Component,
  EventEmitter,
  IterableDiffers,
  Input,
  Output
} from '@angular/core';
import { DualListComponent } from 'angular-dual-listbox';

@Component({
  selector: 'pcm-dual-list-box',
  templateUrl: './dual-list-box.component.html',
  styleUrls: ['./dual-list-box.component.scss']
})
export class DualListBoxComponent extends DualListComponent {
  @Input() sourceName = '';
  @Input() targetName = '';
  @Input() key = 'id';

  @Output() selectChange: any = new EventEmitter();

  constructor(differs: IterableDiffers) {
    super(differs);
    console.log(this.available);
  }

  moveAll() {
    this.selectAll(this.available);
    this.moveItem(this.available, this.confirmed);
  }

  removeAll() {
    this.selectAll(this.confirmed);
    this.moveItem(this.confirmed, this.available);
  }

  moveItem(available, confirmed) {
    let i = 0;
    const len = available.pick.length;
    const _loop_2 = function() {
      let mv = [];
      mv = available.list.filter(function(src) {
        return src._id === available.pick[i]._id;
      });

      if (mv.length === 1) {
        // Add if not already in target.
        if (
          confirmed.list.filter(function(trg) {
            return trg._id === mv[0]._id;
          }).length === 0
        ) {
          confirmed.list.push(mv[0]);
        }
      }
    };
    for (; i < len; i += 1) {
      _loop_2();
    }
    if (this.compare !== undefined) {
      confirmed.list.sort(this.compare);
    }
    available.pick.length = 0;
    confirmed.sift = confirmed.list;
    this.selectChange.emit(confirmed.list);
  }

  // Override function in DualListComponent to add custom selectChange event.
  selectItem(list: Array<any>, item: any) {
    const pk = list.filter((e: any) => {
      return Object.is(e, item);
    });
    if (pk.length > 0) {
      // Already in list, so deselect.
      for (let i = 0, len = pk.length; i < len; i += 1) {
        const idx = list.indexOf(pk[i]);
        if (idx !== -1) {
          list.splice(idx, 1);
          this.selectChange.emit({ key: item._id, selected: false });
        }
      }
    } else {
      list.push(item);
      this.selectChange.emit({ key: item._id, selected: true });
    }
  }
}
