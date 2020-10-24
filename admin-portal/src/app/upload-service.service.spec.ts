import { TestBed, inject } from '@angular/core/testing';

import { UploadServiceService } from './upload-service.service';

describe('UploadServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UploadServiceService]
    });
  });

  it('should ...', inject([UploadServiceService], (service: UploadServiceService) => {
    expect(service).toBeTruthy();
  }));
});
