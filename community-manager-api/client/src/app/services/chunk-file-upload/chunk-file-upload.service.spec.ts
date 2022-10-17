import { TestBed } from '@angular/core/testing';

import { ChunkFileUploadService } from './chunk-file-upload.service';

describe('ChunkFileUploadService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ChunkFileUploadService = TestBed.get(ChunkFileUploadService);
    expect(service).toBeTruthy();
  });
});
