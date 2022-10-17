import { TestBed } from '@angular/core/testing';

import { EndpointFlowService } from './endpoint-flow.service';

describe('EndpointFlowService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EndpointFlowService = TestBed.get(EndpointFlowService);
    expect(service).toBeTruthy();
  });
});
