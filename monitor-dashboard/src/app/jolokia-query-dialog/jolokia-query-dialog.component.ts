import { Component, OnInit, Input, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-jolokia-query-dialog',
  templateUrl: './jolokia-query-dialog.component.html',
  styleUrls: ['./jolokia-query-dialog.component.css']
})
export class JolokiaQueryDialogComponent implements OnInit {


  @Input('isShown')
  isShown: string;

  @Output('close')
  close: EventEmitter<boolean> = new EventEmitter<boolean>();

  constructor() { }

  ngOnInit() {
  }

  onClose() {
    this.close.emit(false);
  }

}
