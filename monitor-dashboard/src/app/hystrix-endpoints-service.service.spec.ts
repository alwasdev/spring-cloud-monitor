/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { HystrixEndpointsServiceService } from './hystrix-endpoints-service.service';

describe('Service: HystrixEndpointsServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HystrixEndpointsServiceService]
    });
  });

  it('should ...', inject([HystrixEndpointsServiceService], (service: HystrixEndpointsServiceService) => {
    expect(service).toBeTruthy();
  }));
});
