import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PcdComponent } from './pcd.component';

describe('PcdComponent', () => {
  let component: PcdComponent;
  let fixture: ComponentFixture<PcdComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PcdComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PcdComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
