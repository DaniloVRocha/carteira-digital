import { Component, OnInit } from '@angular/core';
import { IContaViewDTO } from 'src/app/model/IContaViewDTO';
import { IDataHora } from 'src/app/model/IDataHora';
import { IOperacao } from 'src/app/model/IOperacao';
import { ContaService } from 'src/app/services/conta.service';
import { OperacoesService } from 'src/app/services/operacoes.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  styleUrls: ['./principal.component.css']
})
export class PrincipalComponent implements OnInit {

  existeOperacao:boolean = false;

  operacoes: IOperacao[] = [];
  dataHora:Date = new Date(2022, 2, 1);
  data: any;
  options: any;
  dashboardView: IContaViewDTO = {
    saldo: 0.0,
    despesas: 0.0,
    receitas: 0.0
  }

  constructor(private contaService: ContaService,
    private operacaoService: OperacoesService) {

  }

  ngOnInit(): void {
    this.preencherTabelaVencidos();
    this.gastoPorCategoria();
    this.atualizarSaldo();
  }

  preencherTabelaVencidos() {
    this.operacaoService.preencherTabelaVencidos().subscribe(res => {
      this.operacoes = res;
    })
  }

  atualizarSaldo() {
    this.contaService.preencherSaldo().subscribe(res => {
      this.dashboardView = res;
    })
  }

  pagarContaVencida(id:number, tpOperacao:string){
    Swal.fire({
      title: 'Deseja Mudar o Estado de Pagamento para quitado?',
      text: "O valor será descontado de seu saldo.",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sim, Pagar Conta!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
          this.operacaoService.pagarOperacaoVencida(id, 1).subscribe(res =>{
            Swal.fire(
              'Feito',
              'O valor da receita foi adicionado ao seu saldo.',
              'success'
            )
          })
          this.preencherTabelaVencidos()
        }
    }, error =>{
      Swal.fire(
        'Erro',
        'Um Erro Inesperado aconteceu' + error,
        'error'
      )
    })
  }

  mudouDate(event:any){
    this.dataHora = event.novaData;
    this.gastoPorCategoria();
  }

  gastoPorCategoria(){
    debugger;
      this.operacaoService.gastoPorCategoria(this.dataHora.getMonth()+1, this.dataHora.getFullYear()).subscribe(res=>{
        if(Object.keys(res).length !== 0){
          this.preencherGrafico(res);
          this.existeOperacao = true;
        }else{
          this.existeOperacao = false;
        }
      })
  }

  preencherGrafico(res:any) {

    this.data = {
      labels: [] = Object.keys(res),
      datasets: [
          {
              data: [] = Object.values(res),
              backgroundColor: [
                  "#FF6384",
                  "#36A2EB",
                  "#FFCE56",
                  "#689F38",
                  "#581845"
              ],
              hoverBackgroundColor: [
                  "#FF6384",
                  "#36A2EB",
                  "#FFCE56",
                  "#689F38",
                  "#581845"
              ]
          }
      ]
  };

    this.options = {
        plugins: {
            legend: {
                labels: {
                    color: '#495057'
                }
            }
        }
    };
  }

}
