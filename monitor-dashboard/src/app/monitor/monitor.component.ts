import { Component, OnInit } from '@angular/core';
import { BaseChartDirective } from 'ng2-charts/ng2-charts';
import { HystrixEndpointsServiceService } from '../hystrix-endpoints-service.service';

declare let SockJS;
declare let Stomp;
declare let Map;

@Component({
  selector: 'app-monitor',
  templateUrl: './monitor.component.html',
  styleUrls: ['./monitor.component.css']
})
export class MonitorComponent implements OnInit {
  stompClient: any;
  messages: any = [];
  snapshots: any = [];
  constructor(private hystrixEndpoints: HystrixEndpointsServiceService) {
    this.connect();
  }

  ngOnInit() {
    this.connect();
  }

  connect() {
    let that = this;
    let socket = new SockJS('http://localhost:8095/monitor');
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);
      that.stompClient.subscribe('/topic/monitor', function (message) {
        that.snapshots = [];
        let test = buildMap(JSON.parse(message.body)).forEach(function (value, key, map) {
          that.snapshots.push(value);
          that.hystrixEndpoints.addHystrixUrl(value, key);
        });

      });
    }, function (err) {
      console.log('err', err);
    });
  }

  public doughnutChartLabels: string[] = ['Download Sales', 'In-Store Sales', 'Mail-Order Sales'];
  public doughnutChartData: number[] = [350, 450, 100];
  public doughnutChartType: string = 'doughnut';

  public snapshot: any = { application: 'moep', usedMemory: 100, freeMemory: 200 };

}
const buildMap = o => Object.keys(o).reduce((m, k) => m.set(k, o[k]), new Map());