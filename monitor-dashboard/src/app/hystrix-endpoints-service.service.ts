import { Injectable } from '@angular/core';

@Injectable()
export class HystrixEndpointsServiceService {

  constructor() { }


  hystrixUrls = [];

  public addHystrixUrl(object, key) {
    this.hystrixUrls[key] = object;
  }
  public getAll() {
    return this.hystrixUrls;
  }

}
