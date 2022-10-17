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

import {Component, EventEmitter, forwardRef, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, NG_VALUE_ACCESSOR} from '@angular/forms';
import {IItemsMovedEvent, IListBoxItem} from 'src/app/model';

declare var require: any;
const intersectionwith = require('lodash.intersectionwith');
const differenceWith = require('lodash.differencewith');

@Component({
  selector: 'pcm-pcm-list-box',
  templateUrl: './pcm-list-box.component.html',
  styleUrls: ['./pcm-list-box.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => PcmListBoxComponent),
      multi: true
    }
  ]
})
export class PcmListBoxComponent implements OnInit {

  @Input() valueField = 'key';

  // field to use for displaying option text
  @Input() set textField(fields: any) {
    if (Array.isArray(fields)) {
      this._textField = (fields.length > 0) ? fields : ['key'];
    } else {
      this._textField = (fields) ? [fields] : ['key'];
    }
  };

  @Input() separator = ' - ';

  // array of items to display in left box
  @Input() set data(items: Array<{}>) {
    this.availableItems = [...(items || []).map((item: {}, index: number) => {
      let fields = this._textField.map((field) => {
        return item[field];
      }).filter((data) => data != undefined);
      return ({
        value: item[this.valueField],
        text: fields.join(this.separator)
      });
    }).filter((data) => data != undefined)];
  };

  // array of items to display in right box
  @Input() set selected(items: Array<{}>) {
    if (this.selectedItems.length === 0) {
      this.selectedItems = [...(items || []).map((item: {}, index: number) => {
        let position;
        var found = this.availableItems.find((element, index) => {
          position = index;
          if (item.hasOwnProperty(this.valueField)) {
            return element.value === item[this.valueField];
          }
          return element.value === item;
        });
        if (found) {
          this.availableItems.splice(position, 1);
          return found;
        }
        let fields = this._textField.map((field) => {
          return item[field];
        }).filter((data) => data != undefined);
        return ({
          value: item[this.valueField].toString(),
          text: fields.join(this.separator)
        });
      })];
    } else {
    }

  };

  // input to set search term for available list box from the outside
  @Input() set availableSearch(searchTerm: string) {
    this.searchTermAvailable = searchTerm;
    this.availableSearchInputControl.setValue(searchTerm);
  };

  // input to set search term for selected list box from the outside
  @Input() set selectedSearch(searchTerm: string) {
    this.searchTermSelected = searchTerm;
    this.selectedSearchInputControl.setValue(searchTerm);
  };

  // text to display as title above component
  @Input() title: string;
  // time to debounce search output in ms
  @Input() debounceTime = 500;
  // show/hide button to move all items between boxes
  @Input() moveAllButton = true;
  // text displayed over the available items list box
  @Input() availableText = 'Available items';
  // text displayed over the selected items list box
  @Input() selectedText = 'Selected items';
  // set placeholder text in available items list box
  @Input() availableFilterPlaceholder = 'Filter...';
  // set placeholder text in selected items list box
  @Input() selectedFilterPlaceholder = 'Filter...';

  // event called when item or items from available items(left box) is selected
  @Output() onAvailableItemSelected: EventEmitter<{} | Array<{}>> = new EventEmitter<{} | Array<{}>>();
  // event called when item or items from selected items(right box) is selected
  @Output() onSelectedItemsSelected: EventEmitter<{} | Array<{}>> = new EventEmitter<{} | Array<{}>>();
  // event called when items are moved between boxes, returns state of both boxes and item moved

  @Output() onPartnerItemsMoved: EventEmitter<IItemsMovedEvent> = new EventEmitter<IItemsMovedEvent>();
  @Output() onGroupItemsMoved: EventEmitter<IItemsMovedEvent> = new EventEmitter<IItemsMovedEvent>();
  @Output() onTransItemsMoved: EventEmitter<IItemsMovedEvent> = new EventEmitter<IItemsMovedEvent>();

  // private variables to manage class
  _textField = ['name'];
  searchTermAvailable = '';
  searchTermSelected = '';
  availableItems: Array<IListBoxItem> = [];
  selectedItems: Array<IListBoxItem> = [];
  listBoxForm: FormGroup;
  availableListBoxControl: FormControl = new FormControl();
  selectedListBoxControl: FormControl = new FormControl();
  availableSearchInputControl: FormControl = new FormControl();
  selectedSearchInputControl: FormControl = new FormControl();

  // control value accessors
  _onChange = (_: any) => {
  };
  _onTouched = () => {
  };

  constructor(public fb: FormBuilder) {

    this.listBoxForm = this.fb.group({
      availableListBox: this.availableListBoxControl,
      selectedListBox: this.selectedListBoxControl,
      availableSearchInput: this.availableSearchInputControl,
      selectedSearchInput: this.selectedSearchInputControl
    });
  }

  ngOnInit(): void {

    this.availableListBoxControl
      .valueChanges
      .subscribe((items: Array<{}>) => this.onAvailableItemSelected.emit(items));
    this.selectedListBoxControl
      .valueChanges
      .subscribe((items: Array<{}>) => this.onSelectedItemsSelected.emit(items));
    this.availableSearchInputControl
      .valueChanges
      .subscribe((search: string) => this.searchTermAvailable = search);
    this.selectedSearchInputControl
      .valueChanges
      .subscribe((search: string) => this.searchTermSelected = search);
  }

  /**
   * Move all items from available to selected
   */
  moveAllItemsToSelected(): void {

    if (!this.availableItems.length) {
      return;
    }
    let moved = this.availableItems.map(function (obj) {
      return obj.value;
    });
    this.selectedItems = [...this.selectedItems, ...this.availableItems];
    this.availableItems = [];
    this.onPartnerItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: moved,
      from: 'available',
      to: 'selected'
    });
    this.onGroupItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: moved,
      from: 'available',
      to: 'selected'
    });
    this.onTransItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: moved,
      from: 'available',
      to: 'selected'
    });
    this.availableListBoxControl.setValue([]);
    this.writeValue(this.getValues());
  }

  /**
   * Move all items from selected to available
   */
  moveAllItemsToAvailable(): void {

    if (!this.selectedItems.length) {
      return;
    }
    let moved = this.selectedItems.map(function (obj) {
      return obj.value;
    });
    this.availableItems = [...this.availableItems, ...this.selectedItems];
    this.selectedItems = [];
    this.onPartnerItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: moved,
      from: 'selected',
      to: 'available'
    });
    this.onGroupItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: moved,
      from: 'selected',
      to: 'available'
    });
    this.onTransItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: moved,
      from: 'selected',
      to: 'available'
    });
    this.selectedListBoxControl.setValue([]);
    this.writeValue([]);
  }

  /**
   * Move marked items from available items to selected items
   */
  moveMarkedAvailableItemsToSelected(): void {

    // first move items to selected
    this.selectedItems = [...this.selectedItems,
      ...intersectionwith(this.availableItems, this.availableListBoxControl.value,
        (item: IListBoxItem, value: string) => item.value === value)];
    // now filter available items to not include marked values
    this.availableItems = [...differenceWith(this.availableItems, this.availableListBoxControl.value,
      (item: IListBoxItem, value: string) => item.value === value)];
    // clear marked available items and emit event
    this.onPartnerItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: this.availableListBoxControl.value,
      from: 'available',
      to: 'selected'
    });
    this.onGroupItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: this.availableListBoxControl.value,
      from: 'available',
      to: 'selected'
    });
    this.onTransItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: this.availableListBoxControl.value,
      from: 'available',
      to: 'selected'
    });
    this.availableListBoxControl.setValue([]);
    this.availableSearchInputControl.setValue('');
    this.writeValue(this.getValues());
  }

  /**
   * Move marked items from selected items to available items
   */
  moveMarkedSelectedItemsToAvailable(): void {

    // first move items to available
    this.availableItems = [...this.availableItems,
      ...intersectionwith(this.selectedItems, this.selectedListBoxControl.value,
        (item: IListBoxItem, value: string) => item.value === value)];
    // now filter available items to not include marked values
    this.selectedItems = [...differenceWith(this.selectedItems, this.selectedListBoxControl.value,
      (item: IListBoxItem, value: string) => item.value === value)];
    // clear marked available items and emit event
    this.onPartnerItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: this.availableListBoxControl.value,
      from: 'available',
      to: 'selected'
    });
    this.onGroupItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: this.availableListBoxControl.value,
      from: 'available',
      to: 'selected'
    });
    this.onTransItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: this.availableListBoxControl.value,
      from: 'available',
      to: 'selected'
    });
    this.selectedListBoxControl.setValue([]);
    this.selectedSearchInputControl.setValue('');
    this.writeValue(this.getValues());
  }

  /**
   * Move single item from available to selected
   * @param item
   */
  moveAvailableItemToSelected(item: IListBoxItem): void {

    this.availableItems = this.availableItems.filter((listItem: IListBoxItem) => listItem.value !== item.value);
    this.selectedItems = [...this.selectedItems, item];
    this.onPartnerItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: this.availableListBoxControl.value,
      from: 'available',
      to: 'selected'
    });
    this.onGroupItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: this.availableListBoxControl.value,
      from: 'available',
      to: 'selected'
    });
    this.onTransItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: this.availableListBoxControl.value,
      from: 'available',
      to: 'selected'
    });
    this.availableSearchInputControl.setValue('');
    this.availableListBoxControl.setValue([]);
    this.writeValue(this.getValues());
  }

  /**
   * Move single item from selected to available
   * @param item
   */
  moveSelectedItemToAvailable(item: IListBoxItem): void {

    this.selectedItems = this.selectedItems.filter((listItem: IListBoxItem) => listItem.value !== item.value);
    this.availableItems = [...this.availableItems, item];
    this.onPartnerItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: this.availableListBoxControl.value,
      from: 'available',
      to: 'selected'
    });
    this.onGroupItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: this.availableListBoxControl.value,
      from: 'available',
      to: 'selected'
    });
    this.onTransItemsMoved.emit({
      available: this.availableItems,
      selected: this.selectedItems.map(function (obj) {
        return obj.value;
      }),
      movedItems: this.availableListBoxControl.value,
      from: 'available',
      to: 'selected'
    });
    this.selectedSearchInputControl.setValue('');
    this.selectedListBoxControl.setValue([]);
    this.writeValue(this.getValues());
  }

  /**
   * Function to pass to ngFor to improve performance, tracks items
   * by the value field
   * @param index
   * @param item
   */
  trackByValue(index: number, item: {}): string {
    return item[this.valueField];
  }

  /* Methods from ControlValueAccessor interface, required for ngModel and formControlName - begin */
  writeValue(value: any): void {
    if (this.selectedItems && value && value.length > 0) {
      this.selectedItems = [...this.selectedItems,
        ...intersectionwith(this.availableItems, value, (item: IListBoxItem, val: string) => item.value === val)];
      this.availableItems = [...differenceWith(this.availableItems, value,
        (item: IListBoxItem, val: string) => item.value === val)];
    }
    this._onChange(value);
  }

  registerOnChange(fn: (_: any) => {}): void {
    this._onChange = fn;
  }

  registerOnTouched(fn: () => {}): void {
    this._onTouched = fn;
  }

  /* Methods from ControlValueAccessor interface, required for ngModel and formControlName - end */

  /**
   * Utility method to get values from
   * selected items
   */
  private getValues(): string[] {
    return (this.selectedItems || []).map((item: IListBoxItem) => item.value);
  }
}

