import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DataHora } from 'src/app/model/IDataHora';
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

  saldo: number = 0.0;
  despesas: number = 0.0;
  receitas: number = 0.0;
  operacoes: IOperacao[] = [];
  data: any;
  options: any;
  dataHora:DataHora = new DataHora("2022-02-01 00:00:00", "2022-02-31 23:59:00");


  constructor(private contaService: ContaService,
    private operacaoService: OperacoesService) {

  }

  ngOnInit(): void {
    this.atualizarSaldo();
    this.preencherTabelaVencidos();
    this.preencherGrafico();
  }

  atualizarSaldo() {
    this.contaService.preencherSaldo().subscribe(res => {
      this.saldo = res.saldo;
      this.despesas = res.despesas;
      this.receitas = res.receitas;
    })
  }

  preencherTabelaVencidos() {
    debugger;
    this.operacaoService.preencherTabelaVencidos().subscribe(res => {
      this.operacoes = res;
    })
  }

  pagarContaVencida(id:number, tpOperacao:string){
    console.log(id)
    console.log(tpOperacao)
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
        }

    }, error =>{
      Swal.fire(
        'Erro',
        'Um Erro Inesperado aconteceu' + error,
        'error'
      )
    })
  }

  gastosPorMes(){
    var qntMeses:number = 7;
    for(var i=0; i<=qntMeses; i++){
      this.operacaoService.gastoPorMes(this.dataHora).subscribe(res=>{
        
      })
    }
  }

  preencherGrafico() {
    this.data = {
      labels: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho'],
      datasets: [
        {
          label: 'Despesas',
          backgroundColor: '#D05F5F',
          data: [65, 59, 80, 81, 56, 55, 40]
        },
        {
          label: 'Receitas',
          backgroundColor: '#86AB65',
          data: [28, 48, 40, 19, 86, 27, 90]
        }
      ]
    }
    this.options = {
      title: {
        display: true,
        text: 'My Title',
        fontSize: 16
      },
      legend: {
        position: 'bottom'
      }
    };
  }

}
