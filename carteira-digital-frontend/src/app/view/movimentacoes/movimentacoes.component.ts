import { ContaService } from './../../services/conta.service';
import { Categoria } from './../../model/Categoria';
import { OperacoesService } from 'src/app/services/operacoes.service';
import { IOperacao } from 'src/app/model/IOperacao';
import { Component, OnInit} from '@angular/core';
import { IDataHora } from 'src/app/model/IDataHora';
import { IPage } from 'src/app/model/IPage';
import { IContaViewDTO } from 'src/app/model/IContaViewDTO';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ConfirmationService, Message, MessageService, PrimeNGConfig } from 'primeng/api';

@Component({
  selector: 'app-movimentacoes',
  templateUrl: './movimentacoes.component.html',
  styleUrls: ['./movimentacoes.component.css'],
  providers:[ConfirmationService, MessageService]
})

export class MovimentacoesComponent implements OnInit {

  dashboardView: IContaViewDTO = {
    saldo: 0.0,
    despesas: 0.0,
    receitas: 0.0
  }

  formValueOperacao: FormGroup = new FormGroup({
    id: new FormControl(''),
    nome: new FormControl(''),
    valor: new FormControl('', Validators.required),
    vencimento: new FormControl(new Date()),
    tpOperacao: new FormControl(""),
    estadoPagamento: new FormControl(true)
  });
  operacoes:IOperacao[] = [];

  categorias: Categoria[] = [
    {nome: 'Selecione uma Categoria', id: -1},
    {nome: 'Geral', id: 0},
    {nome: 'Alimentacao', id: 1},
    {nome: 'Saude', id: 2},
    {nome: 'Lazer', id: 3},
    {nome: 'Fixo', id: 4},
    {nome: 'Educacao', id: 5},
    {nome: 'Investimento', id: 6},
    {nome: 'Pagamento', id: 7},
    {nome: 'Internet', id: 8},
    {nome: 'Ajuste', id: 9}
  ];;

  tpOperacoes = [
    {operador:"D", label:"Despesa"},
    {operador:"R", label:"Receita"}
  ]

  operacaoSelecionada = {operador:"", label:"Selecione um Tipo de Operação"}

  categoriaSelecionada: number = -1;

  msgs: Message[] = [];

  displayIncluir: boolean = false;
  displayEditar: boolean = false;
  first = 0;
  page?:IPage;
  rows = 13;
  direction = "ASC"
  dataHora:IDataHora = {dataInicial:"2022-03-01 00:00:00", dataFinal:"2022-03-31 23:59:00"};

  constructor(private contaService: ContaService,
    private operacoesService: OperacoesService,
    private confirmationService: ConfirmationService,
    private messageService:MessageService) {
  }

  ngOnInit() {
    this.operacoesPaginadasPorData();
    this.atualizarSaldoPorData();
  }

  preencherForm(operacao:IOperacao) {
    debugger;
    let estadoPagamentovar;
    if(operacao.estadoPagamento === "QUITADO"? estadoPagamentovar = true : estadoPagamentovar = false)
    var i = 0;
    console.log(this.categoriaSelecionada);
    for(i = 0; i < this.categorias.length;i++){
      if(this.categorias[i].nome.toLocaleUpperCase() === operacao.categoria.toString()){
        this.categoriaSelecionada = this.categorias[i].id;
        break;
      }
    }
    this.formValueOperacao = new FormGroup({
      id: new FormControl(operacao.id),
      nome: new FormControl(operacao.nome),
      valor: new FormControl(operacao.valor),
      vencimento: new FormControl(operacao.vencimento),
      tpOperacao: new FormControl(operacao.tpOperacao),
      estadoPagamento: new FormControl(estadoPagamentovar),
      categoria : new FormControl(this.categoriaSelecionada)
    });
  }

  buscarPorId(id:number){
    this.operacoesService.buscarPorId(id).subscribe(res => {
      this.preencherForm(res);
    })
  }

  showDialogIncluir(){
    this.displayIncluir = true;
    this.formValueOperacao = new FormGroup({
      nome: new FormControl(''),
      conta: new FormControl('', Validators.required),
      valor: new FormControl('', Validators.required),
      vencimento: new FormControl(new Date()),
      tpOperacao: new FormControl('D'),
      estadoPagamento: new FormControl(true)
    });
  }
  showDialogEditar(id:number){
    this.buscarPorId(id);
    this.displayEditar = true;
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
    this.confirmationService.confirm({
      message: 'Deseja informar pagamento?',
      header: 'Informar Pagamento',
      icon: 'pi pi-exclamation-triangle',
      rejectLabel:'Cancelar',
      acceptLabel:'Pagar',
      accept: () => {
          this.operacoesService.pagarOperacaoVencida(id, 1).subscribe(res =>{
            console.log(res);
          })
          this.messageService.add({severity:'success', summary:'Feito', detail:'Estado de Pagamento Alterado para QUITADO'});

      },
      reject: () => {
        this.messageService.add({severity:'error', summary:'Cancelado', detail:'Cancelado pelo usuário'});
      }
  });
  }

  excluirOperacao(id:number){
    this.confirmationService.confirm({
      message: 'Deseja excluir operação? a ação não pode ser desfeita.',
      header: 'Excluir Operação',
      icon: 'pi pi-exclamation-triangle',
      rejectLabel:'Cancelar',
      acceptLabel:'Excluir',
      accept: () => {
          this.operacoesService.excluirOperacao(id).subscribe(res =>{
          })
          this.messageService.add({severity:'success', summary:'Feito', detail:'Operação Excluida com sucesso.'});
      },
      reject: () => {
        this.messageService.add({severity:'error', summary:'Cancelado', detail:'A Operação não foi excluida'});
      }
  });
  }

  editarOperacao(){
    debugger;
    const operacao: IOperacao = this.formValueOperacao.value;
    console.log(operacao.vencimento);
    let dataForm = operacao.vencimento.replace("/", "-").replace("/", "-") + " 00:00:00";
    let codCategoria = this.formValueOperacao.value.estadoPagamento;
    operacao.vencimento = dataForm;
    operacao.conta = {id:1};
    operacao.categoria = this.categoriaSelecionada;

    if(codCategoria == false ? operacao.estadoPagamento = "0" : operacao.estadoPagamento = "1")
    this.operacoesService.editarCliente(operacao).subscribe(result => {
      console.log(result)
      this.messageService.add({ severity: 'success', summary: 'Editado', detail: 'A Operação foi editada.' });
      this.displayEditar = false;
    }, error => {
      this.messageService.add({ severity: 'error', summary: `Cancelado`, detail: `${error}` });
      this.displayEditar = false;
    })
  }

  cancelarEdicao() {
    this.displayEditar = false;
    this.displayIncluir = false;
    this.messageService.add({ severity: 'error', summary: 'Cancelado', detail: 'Edição Cancelada.' });
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
