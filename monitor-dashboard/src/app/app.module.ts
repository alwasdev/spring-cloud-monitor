import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { ChartsModule } from 'ng2-charts/ng2-charts';

import { AppComponent } from './app.component';
import { MonitorComponent } from './monitor/monitor.component';
import { RouterModule, Routes }   from '@angular/router';
import { HystrixComponent } from './hystrix/hystrix.component';
import { ZipkinComponent } from './zipkin/zipkin.component';

import { HystrixEndpointsServiceService } from './hystrix-endpoints-service.service';
import { JolokiaQueryDialogComponent } from './jolokia-query-dialog/jolokia-query-dialog.component';

const appRoutes: Routes = [
  { path: '', component: MonitorComponent },
  { path: 'hystrix', component: HystrixComponent },
  {
    path: 'zipkin',
    component: ZipkinComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    MonitorComponent,
    HystrixComponent,
    ZipkinComponent,
    JolokiaQueryDialogComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    ChartsModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [HystrixEndpointsServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
