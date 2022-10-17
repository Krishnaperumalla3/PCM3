import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionNamesComponent } from './transaction-names.component';

describe('TransactionNamesComponent', () => {
  let component: TransactionNamesComponent;
  let fixture: ComponentFixture<TransactionNamesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TransactionNamesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransactionNamesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
