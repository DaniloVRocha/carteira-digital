import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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
  

  constructor(private contaService: ContaService,
    private operacaoService: OperacoesService,
    private router: Router) {

  }
  ngOnInit(): void {
    this.atualizarSaldo();
    this.preencherTabelaVencidos();
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
        if(tpOperacao == 'R'){
          this.operacaoService.pagarOperacaoVencida(id, 1).subscribe(res =>{
            this.preencherTabelaVencidos();
            this.atualizarSaldo();
            Swal.fire(
              'Feito',
              'O valor da receita foi adicionado ao seu saldo.',
              'success'
            )
          })
        }else if(tpOperacao == 'D'){
          this.operacaoService.pagarOperacaoVencida(id, 2).subscribe(res =>{
            Swal.fire(
              'Feito',
              'O valor da despesa foi descontado do seu saldo.',
              'success'
            )
          })
        }

      }
    }, error =>{
      Swal.fire(
        'Erro',
        'Um Erro Inesperado aconteceu' + error,
        'error'
      )
    })
  }

}
