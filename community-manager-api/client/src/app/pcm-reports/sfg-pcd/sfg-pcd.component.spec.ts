import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SfgPcdComponent } from './sfg-pcd.component';

describe('SfgPcdComponent', () => {
  let component: SfgPcdComponent;
  let fixture: ComponentFixture<SfgPcdComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SfgPcdComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SfgPcdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
