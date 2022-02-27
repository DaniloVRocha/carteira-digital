import { OperacoesService } from 'src/app/services/operacoes.service';
import { IOperacao } from 'src/app/model/IOperacao';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { IDataHora } from 'src/app/model/IDataHora';
import { IPage } from 'src/app/model/IPage';
import { IContaViewDTO } from 'src/app/model/IContaViewDTO';
import { ContaService } from 'src/app/services/conta.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-movimentacoes',
  templateUrl: './movimentacoes.component.html',
  styleUrls: ['./movimentacoes.component.css']
})

export class MovimentacoesComponent implements OnInit {

  dashboardView: IContaViewDTO = {
    saldo: 0.0,
    despesas: 0.0,
    receitas: 0.0
  }

  operacoes:IOperacao[] = [];

  first = 0;

  page?:IPage;

  rows = 13;

  direction = "ASC"

  dataHora:IDataHora = {dataInicial:"2022-02-01 00:00:00", dataFinal:"2022-02-31 23:59:00"};

  constructor(private contaService: ContaService,
    private operacoesService: OperacoesService) {
  }

  ngOnInit() {
    this.operacoesPaginadasPorData();
    this.atualizarSaldoPorData();
  }

  operacoesPaginadasPorData(){
    debugger;
    this.operacoesService.operacoesPaginadasPorData(this.dataHora, 0, this.rows, "vencimento", this.direction).subscribe(res => {
      this.page = res;
      this.operacoes = res.content;
    })
  }

  atualizarSaldoPorData() {
    this.contaService.preencherSaldoPorMes(this.dataHora).subscribe(res => {
      this.dashboardView = res;
    })
  }

  pagarContaVencida(id:number){
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
          this.operacoesService.pagarOperacaoVencida(id, 1).subscribe(res =>{
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

  excluirOperacao(id:number){
    Swal.fire({
      title: 'Deseja Excluir a operação?',
      text: "A ação não poderá ser desfeita",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sim, Excluir Operação!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
          this.operacoesService.excluirOperacao(id).subscribe(res =>{
            Swal.fire(
              'Feito',
              'Operação foi excluida com sucesso.',
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

  next() {
      this.first = this.first + this.rows;
      this.operacoesPaginadasPorData();
  }

  prev() {
      this.first = this.first - this.rows;
      this.operacoesPaginadasPorData();
  }

  reset() {
      this.first = 0;
      this.operacoesPaginadasPorData();
  }

  isLastPage(): boolean {
      return this.operacoes ? this.first === (this.operacoes.length - this.rows): true;
  }

  isFirstPage(): boolean {
      return this.operacoes ? this.first === 0 : true;
  }
}
