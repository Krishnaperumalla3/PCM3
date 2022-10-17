import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FileProcessedComponent } from './file-processed.component';

describe('FileProcessedComponent', () => {
  let component: FileProcessedComponent;
  let fixture: ComponentFixture<FileProcessedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FileProcessedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FileProcessedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
