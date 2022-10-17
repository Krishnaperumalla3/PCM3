import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BuildFlowComponent } from './build-flow.component';

describe('BuildFlowComponent', () => {
  let component: BuildFlowComponent;
  let fixture: ComponentFixture<BuildFlowComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BuildFlowComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BuildFlowComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
