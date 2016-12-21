import { Component } from '@angular/core';
import { SafeResourceUrl} from '@angular/platform-browser';
import {DomSanitizer} from "@angular/platform-browser";
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app works!';
  hystrixUrl = 'moep.de';

url: SafeResourceUrl;
  constructor(sanitizer: DomSanitizer) {
    this.url = sanitizer.bypassSecurityTrustResourceUrl("https://test.de");
  }

  setHystrixToService() {
    console.log("TEST");
    return "https://test.de";
  }
}