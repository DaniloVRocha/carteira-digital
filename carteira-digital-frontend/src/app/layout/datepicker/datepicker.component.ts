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
    this.JSClock();
  }

  JSClock() {
    var data = new Date();
    let dia = data.getDay();
    let mes = data.getMonth() + 1;
    let ano = data.getFullYear();
    var hora = data.getHours();
    var minuto = data.getMinutes();
    var segundo = data.getSeconds();
    var dataForm = "" + ((dia < 10) ? `0${dia}`: dia);
    dataForm += ((mes < 10) ? "-0" : "-") + mes;
    dataForm += `-${ano}`;
    console.log(dataForm);
    var horaForm = "" + hora;
    horaForm += ((minuto < 10) ? ":0" : ":") + minuto;
    horaForm += ((segundo < 10) ? ":0" : ":") + segundo;
    console.log(horaForm)

    console.log(`${dataForm} ${horaForm}`)
  }
}
