import { Component, OnInit} from '@angular/core';
import {DomSanitizer} from "@angular/platform-browser";
import { SafeResourceUrl} from '@angular/platform-browser';
import { HystrixEndpointsServiceService } from '../hystrix-endpoints-service.service';

@Component({
  selector: 'app-hystrix',
  templateUrl: './hystrix.component.html',
  styleUrls: ['./hystrix.component.css']
})
export class HystrixComponent implements OnInit {

  url: SafeResourceUrl;
  name: string;
  key: string;
  services = [];

  constructor(private sanitizer: DomSanitizer, private hystrixEndpoints: HystrixEndpointsServiceService) {
    this.url = sanitizer.bypassSecurityTrustResourceUrl("http://localhost:8095/hystrix");
  }

  ngOnInit() {
    let keys = Object.keys(this.hystrixEndpoints.getAll());

    keys.forEach((value, index) => { this.services.push(this.hystrixEndpoints.getAll()[value]) });
    console.log(this.services);
  }

  hystrix(url) {
    this.url = this.sanitizer.bypassSecurityTrustResourceUrl("http://localhost:8095/hystrix/monitor?stream=" + url);
  }


}
