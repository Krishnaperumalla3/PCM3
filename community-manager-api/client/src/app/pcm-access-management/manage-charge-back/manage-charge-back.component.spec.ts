import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageChargeBackComponent } from './manage-charge-back.component';

describe('ManageChargeBackComponent', () => {
  let component: ManageChargeBackComponent;
  let fixture: ComponentFixture<ManageChargeBackComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ManageChargeBackComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageChargeBackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
