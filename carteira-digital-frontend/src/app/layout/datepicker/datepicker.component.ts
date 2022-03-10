import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-datepicker',
  templateUrl: './datepicker.component.html',
  styleUrls: ['./datepicker.component.css']
})
export class DatepickerComponent implements OnInit {

  @Input()
  date: Date = new Date();

  constructor() {
  }

  ngOnInit(): void {
  }
}
