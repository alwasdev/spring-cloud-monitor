import { Component } from '@angular/core';
import { BaseChartDirective } from 'ng2-charts/ng2-charts';

declare let SockJS;
declare let Stomp;
declare let Map;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app works!';
  stompClient: any;
  messages: any = [];
  snapshots: any = [];
  constructor() {
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