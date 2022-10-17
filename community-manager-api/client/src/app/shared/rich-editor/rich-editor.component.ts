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
  Input,
  OnInit,
  OnChanges,
  ElementRef,
  ViewChild,
  Output,
  EventEmitter,
  forwardRef, Injector, DoCheck, HostBinding
} from '@angular/core';
import Quill from 'quill'
import {ControlValueAccessor, NG_VALUE_ACCESSOR, NgControl} from "@angular/forms";
import {MatFormFieldControl} from "@angular/material";
import {Subject} from "rxjs";
import {FocusMonitor} from "@angular/cdk/a11y";
import {coerceBooleanProperty} from "@angular/cdk/coercion";

@Component({
  selector: 'pcm-rich-editor',
  templateUrl: './rich-editor.component.html',
  styleUrls: ['./rich-editor.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => RichEditorComponent),
      multi: true
    },
    {
      provide: MatFormFieldControl,
      useExisting: RichEditorComponent
    }
  ],
  host: {
    '[id]': 'id',
    '[attr.aria-describedby]': 'describedBy'
  }
})
export class RichEditorComponent implements OnInit, OnChanges, ControlValueAccessor, DoCheck, MatFormFieldControl<any> {

  @ViewChild('container', { read: ElementRef }) container: ElementRef;
  @Output() changed: EventEmitter<any> = new EventEmitter();
//this one is important, otherwise 'Quill' is undefined
  quill : any = Quill;
  editor: any;
  touched = false;
  ngControl: NgControl;
  controlType = 'richeditor';
  errorState = false;
  stateChanges = new Subject<void>();

  static nextId = 0;
  @HostBinding() id = `rich-editor-input-${RichEditorComponent.nextId++}`;

  focused = false;

  constructor(
    public elRef: ElementRef,
    public injector: Injector,
    public fm: FocusMonitor
  ) {
    fm.monitor(elRef.nativeElement, true).subscribe(origin => {
      this.focused = !!origin;
      this.stateChanges.next();
    });
  }
  @Input() isEdit: boolean;
  @Input()
  _value: any;

  @Input()
  get value(): any {
    return this._value;
  }
  set value(value) {
    this._value = value;
    if(this.editor) {
      this.editor.setContents(this._value);
    }
    this.onChange(value);
    this.stateChanges.next();
  }

  @Input()
  get placeholder() {
    return this._placeholder;
  }
  set placeholder(plh) {
    this._placeholder = plh;
    this.stateChanges.next();
  }
  public _placeholder: string;

  @Input()
  get required() {
    return this._required;
  }
  set required(req) {
    this._required = coerceBooleanProperty(req);
    this.stateChanges.next();
  }
  public _required = false;

  @Input()
  get disabled() {
    return this._disabled;
  }
  set disabled(dis) {
    if(!dis) {
      this.editor.disable();
    }
    this._disabled = coerceBooleanProperty(dis);
    this.stateChanges.next();
  }
  public _disabled = false;
  get empty() {
    const commentText = this.editor.getText().trim();
    return !commentText;
  }
  @HostBinding('class.floating')
  get shouldLabelFloat() {
    return this.focused || !this.empty;
  }
  @HostBinding('attr.aria-describedby') describedBy = '';
  setDescribedByIds(ids: string[]) {
    this.describedBy = ids.join(' ');
  }
  ngOnInit(): void {
    this.ngControl = this.injector.get(NgControl);
    if (this.ngControl != null) { this.ngControl.valueAccessor = this; }

    let editor = this.container.nativeElement.
    querySelector('#editor')
    this.editor = new Quill(editor, {theme: 'snow'});
    this.editor.on('text-change', () => {
      this.onChange(this.editor.getContents());
    });
    this.editor.setContents(this.value);
    this.editor.disable();
  }
  onChange = (delta: any) => {};
  onTouched = () => {
    this.touched = true;
  };
  writeValue(delta: any): void {
    this.editor.setContents(delta);
    this._value = delta;
  }
  registerOnChange(fn: (v: any) => void): void {
    this.onChange = fn;
  }
  registerOnTouched(fn: () => void): void {
    this.onTouched = fn;
  }
  ngOnChanges() {
    if (this.editor) {
      this.editor.setContents(this.value);
    }
  }
  ngDoCheck(): void {
    if(this.ngControl) {
      this.errorState = this.ngControl.invalid && this.ngControl.touched;
      this.stateChanges.next();
    }
  }
  onContainerClick(event: MouseEvent): void {
    if ((event.target as Element).tagName.toLowerCase() != 'div') {
      this.container.nativeElement.querySelector('div').focus();
    }
  }
  edit() {
    if(this._disabled) {
      this._disabled = false;
      this.editor.disable();
    } else {
      this._disabled = true;
      this.editor.enable();
    }
  }
  upLoad() {
    const req = this.editor.getContents()['ops'][0]['insert'];
    this.changed.emit(req)
  }
}
