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
    this.formatarDataBackend(new Date());
  }

  formatarDataBackend(data: Date) {
    debugger;
    //captura de dia, mes, ano.
    let dia = data.getDate();
    let mes = data.getMonth() + 1;
    let ano = data.getFullYear();
    var hora = data.getHours();
    var minuto = data.getMinutes();
    var segundo = data.getSeconds();
    //verifica se é necessario incluir 0 para datas de apenas 1 dígito
    var dataForm = "" + ((dia < 10) ? `0${dia}`: dia);
    dataForm += ((mes < 10) ? "-0" : "-") + mes;
    dataForm += `-${ano}`;
    //verifica se é necessario incluir 0 para horas com apenas 1 digito
    var horaForm = "" + ((hora < 10) ? `0${hora}` : hora) ;
    horaForm += ((minuto < 10) ? ":0" : ":") + minuto;
    horaForm += ((segundo < 10) ? ":0" : ":") + segundo;
    console.log(`${dataForm} ${horaForm}`)
    return `${dataForm} ${horaForm}`
  }
}
