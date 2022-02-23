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
    private operacaoService: OperacoesService,
    private route: Router) {

  }

  ngOnInit(): void {
    this.atualizarSaldo();
    this.preencherTabelaVencidos();
    this.gastoPorCategoria();
  }

  atualizarSaldo() {
    this.contaService.preencherSaldo().subscribe(res => {
      this.saldo = res.saldo;
      this.despesas = res.despesas;
      this.receitas = res.receitas;
    })
  }

  preencherTabelaVencidos() {
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
            this.preencherTabelaVencidos()
          })
          this.route.navigate([""]);
        }
    }, error =>{
      Swal.fire(
        'Erro',
        'Um Erro Inesperado aconteceu' + error,
        'error'
      )
    })
  }

  gastoPorCategoria(){
      this.operacaoService.gastoPorCategoria(this.dataHora).subscribe(res=>{
        if(Object.keys(res).length !== 0){
          this.preencherGrafico(res);
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
                  "#689F38"
              ],
              hoverBackgroundColor: [
                  "#FF6384",
                  "#36A2EB",
                  "#FFCE56",
                  "#689F38"
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
