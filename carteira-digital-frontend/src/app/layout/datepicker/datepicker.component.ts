import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-datepicker',
  templateUrl: './datepicker.component.html',
  styleUrls: ['./datepicker.component.css']
})
export class DatepickerComponent implements OnInit {

  @Input()
  date: Date = new Date();

  @Output()
  mudouData = new EventEmitter();

  constructor() {
  }

  ngOnInit(): void {
  }

  recarregaConteudo(){
    this.mudouData.emit({novaData:this.date})
  }
}
