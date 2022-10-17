import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SfgComponent } from './sfg.component';

describe('SfgComponent', () => {
  let component: SfgComponent;
  let fixture: ComponentFixture<SfgComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SfgComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SfgComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
