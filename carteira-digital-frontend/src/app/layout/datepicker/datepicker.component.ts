import { IMes } from './../../model/Mes';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-datepicker',
  templateUrl: './datepicker.component.html',
  styleUrls: ['./datepicker.component.css']
})
export class DatepickerComponent implements OnInit {

  meses: IMes[];

  mesSelecionado:IMes = {nome: 'Todos', dataInicioFormatada: new Date(), dataFimFormatada: new Date()}

  dataAtual:Date = new Date();



  constructor() {
    this.meses = [
      {nome: 'Janeiro', dataInicioFormatada: new Date("2021-01-01T00:00:00"), dataFimFormatada: new Date(this.dataAtual.getFullYear +"-01-31T23:59:59")},
      {nome: 'Fevereiro', dataInicioFormatada: new Date(this.dataAtual.getFullYear(),1,1,0,0,0), dataFimFormatada: new Date(this.dataAtual.getFullYear(),1,31,23,59,59)},
      {nome: 'Março', dataInicioFormatada: new Date(this.dataAtual.getFullYear(),1,1,0,0,0), dataFimFormatada: new Date(this.dataAtual.getFullYear(),1,31,23,59,59)}
  ];
  }

  ngOnInit(): void {
    console.log(this.meses);
  }

}
