import { Component, OnInit } from '@angular/core';
import { OperacoesService } from 'src/app/services/operacoes.service';

@Component({
  selector: 'app-relatorios-por-data',
  templateUrl: './relatorios-por-data.component.html',
  styleUrls: ['./relatorios-por-data.component.css']
})
export class RelatoriosPorDataComponent implements OnInit {

  basicData: any;

  basicOptions: any;

  dataReceitas:any = [];
  dataDespesas:any = [];

  constructor(private operacoesService:OperacoesService) { }

  ngOnInit(): void {
    this.preencherGraficoValoresPorAno()
  }

  preencherGraficoValoresPorAno(){
    this.operacoesService.consultarValoresAno().subscribe(res=>{
      this.dataReceitas = Object.values(res)[0];
      this.dataDespesas = Object.values(res)[1];

      this.basicData = {
        labels: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
        datasets: [
            {
                label: 'Receitas',
                backgroundColor: '#33cc33',
                data: this.dataReceitas
            },
            {
                label: 'Despesas',
                backgroundColor: '#ff3300',
                data: this.dataDespesas
            }
        ]
    };
    })
  }

}
