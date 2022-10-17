import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FileSizeProcessedComponent } from './file-size-processed.component';

describe('FileSizeProcessedComponent', () => {
  let component: FileSizeProcessedComponent;
  let fixture: ComponentFixture<FileSizeProcessedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FileSizeProcessedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FileSizeProcessedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
