import {
  Component,
  forwardRef,
  HostBinding,
  HostListener,
  ViewChild,
  Input,
  ElementRef,
  Output,
  EventEmitter } from '@angular/core';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { coerceBooleanProperty } from '@angular/cdk/coercion';
import { BytePipe } from './byte.pipe';

@Component({
  selector: 'pcm-file-drag-drop',
  templateUrl: './file-drag-drop.component.html',
  styleUrls: ['./file-drag-drop.component.scss'],
  providers: [{
    provide: NG_VALUE_ACCESSOR, useExisting: forwardRef(() => FileDragDropComponent),
    multi: true
  }]
})

export class FileDragDropComponent implements ControlValueAccessor {

  constructor() { }
  @HostBinding('class.disabled')
  @Input()
  get
  disabled() {
    return this._disabled;
  }
  set
  disabled(val: boolean) {
    this._disabled = coerceBooleanProperty(val);
  }
  @Input()
  set multiple(value: boolean) {
    this._multiple = coerceBooleanProperty(value);
  }
  get multiple() {
    return this._multiple;
  }

  @Input()
  set
  displayFileSize(value: boolean) {
    this._displayFileSize = coerceBooleanProperty(value);
  }
  get
  displayFileSize() {
    return this._displayFileSize;
  }

  @Input('activeBorderColor')
  @HostBinding('style.border-color')
  set borderColor(color: string) {
    this._activeBorderColor = color;
  }
  get borderColor() {
    return this.isDragover ? this._activeBorderColor : '#ccc';
  }
  get
  files() {
    return this._files;
  }

  @HostBinding('class.empty-input')
  get isEmpty() {
    return !this.files.length;
  }


  // @HostBinding('class.drag-over')
  get isDragover() {
    return this._isDragOver;
  }
  set isDragover(value: boolean) {
    if (!this.disabled) {
      this._isDragOver = value;
    }
  }

  @Output()
  private valueChanged = new EventEmitter<File[]>();


  @ViewChild('fileInputEl')
  private fileInputEl: ElementRef;


  // does no validation, just sets the hidden file input
  @Input() accept = '*';

  private _disabled = false;

  _multiple = false;

  @Input() emptyPlaceholder = `Drop file${this.multiple ? 's' : ''} or click to select`;

  private _displayFileSize = false;

  private _activeBorderColor = 'purple';

  private _files: File[] = [];
  private _isDragOver = false;
  private _onChange = (val: File[]) => { };
  private _onTouched = () => { };

  writeValue(files: File[]): void {
    const fileArray = this.convertToArray(files);
    if (fileArray.length < 2 || this.multiple) {
      this._files = fileArray;
      this.emitChanges(this._files);
    }
    else { throw Error('Multiple files not allowed'); }

  }
  registerOnChange(fn: any): void {
    this._onChange = fn;
  }
  registerOnTouched(fn: any): void {
    this._onTouched = fn;
  }
  setDisabledState?(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  private emitChanges(files: File[]) {
    this.valueChanged.emit(files);
    this._onChange(files);
  }

  addFiles(files: File[] | FileList | File) {
    // this._onTouched();
    const fileArray = this.convertToArray(files);
    if (this.multiple) {
      // this.errorOnEqualFilenames(fileArray);
      const merged = this.files.concat(fileArray);
      this.writeValue(merged);
    }
    else {
      this.writeValue(fileArray);
    }
  }

  removeFile(file: File) {
    const fileIndex = this.files.indexOf(file);
    if (fileIndex >= 0) {
      const currentFiles = this.files.slice();
      currentFiles.splice(fileIndex, 1);
      this.writeValue(currentFiles);
    }
  }

  clear() {
    this.writeValue([]);
  }

  @HostListener('change', ['$event'])
  change(event: Event) {
    event.stopPropagation();
    this._onTouched();
    const fileList: FileList = (event.target as HTMLInputElement).files;
    if (fileList.length) {
      this.addFiles(fileList);
    }
    // clear it so change is triggered if same file is selected again
    (event.target as HTMLInputElement).value = '';
  }
  @HostListener('dragenter', ['$event'])
  @HostListener('dragover', ['$event'])
  activate(e) {
    e.preventDefault();
    this.isDragover = true;
  }
  @HostListener('dragleave', ['$event'])
  deactivate(e) {
    e.preventDefault();
    this.isDragover = false;
  }
  @HostListener('drop', ['$event'])
  handleDrop(e) {
    this.deactivate(e);
    if (!this.disabled) {
      const fileList = e.dataTransfer.files;
      this.removeDirectories(fileList).then((files: File[]) => {
        if (files.length) {
          this.addFiles(files);
        }
        this._onTouched();
      });
    }
  }

  @HostListener('click')
  open() {
    if (!this.disabled) {
      this.fileInputEl.nativeElement.click();
    }
  }

  private removeDirectories(files: FileList) {

    return new Promise((resolve, reject) => {

      const fileArray = this.convertToArray(files);

      const dirnames = [];

      const readerList = [];

      for (let i = 0; i < fileArray.length; i++) {

        const reader = new FileReader();

        reader.onerror = () => {
          dirnames.push(fileArray[i].name);
        };

        reader.onloadend = () => addToReaderList(i);

        reader.readAsArrayBuffer(fileArray[i]);
      }

      function addToReaderList(val: number) {
        readerList.push(val);
        if (readerList.length === fileArray.length) {
          resolve(fileArray.filter((file: File) => !dirnames.includes(file.name)));
        }
      }
    });
  }

  private convertToArray(files: FileList | File[] | File | null | undefined): File[] {
    if (files) {
      if (files instanceof File) {
        return [files];
      } else if (Array.isArray(files)) {
        return files;
      } else {
        return Array.prototype.slice.call(files);
      }
    }
    return [];
  }

  getFileName(file: File): string {
    if (!this._displayFileSize) { return file.name; }
    const size = new BytePipe().transform(file.size);
    return `${file.name} (${size})`;
  }

}
