import { IConta } from './../../model/IConta';
import { ContaService } from './../../services/conta.service';
import { Categoria } from './../../model/Categoria';
import { OperacoesService } from 'src/app/services/operacoes.service';
import { IOperacao } from 'src/app/model/IOperacao';
import { Component, EventEmitter, OnInit } from '@angular/core';
import { IDataHora } from 'src/app/model/IDataHora';
import { IPage } from 'src/app/model/IPage';
import { IContaViewDTO } from 'src/app/model/IContaViewDTO';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ConfirmationService, Message, MessageService, PrimeNGConfig } from 'primeng/api';

@Component({
  selector: 'app-movimentacoes',
  templateUrl: './movimentacoes.component.html',
  styleUrls: ['./movimentacoes.component.css'],
  providers: [ConfirmationService, MessageService]
})

export class MovimentacoesComponent implements OnInit {

  dashboardView: IContaViewDTO = {
    saldo: 0.0,
    despesas: 0.0,
    receitas: 0.0
  }

  labelDashboard: any = {
    labelSaldo: "Saldo Previsto Mês",
    labelDespesas: "Despesas Previstas Mês",
    labelReceitas: "Receitas Previstas Mês"
  }

  date: Date = new Date(2022, 2, 1);
  dataAtual = new Date();

  formValueOperacao: FormGroup = new FormGroup({
    id: new FormControl(''),
    nome: new FormControl(''),
    valor: new FormControl('', Validators.required),
    vencimento: new FormControl(new Date()),
    tpOperacao: new FormControl(""),
    estadoPagamento: new FormControl(true)
  });
  operacoes: IOperacao[] = [];

  categorias: Categoria[] = [
    { nome: 'Geral', id: 0 },
    { nome: 'Alimentacao', id: 1 },
    { nome: 'Saude', id: 2 },
    { nome: 'Lazer', id: 3 },
    { nome: 'Fixo', id: 4 },
    { nome: 'Educacao', id: 5 },
    { nome: 'Investimento', id: 6 },
    { nome: 'Pagamento', id: 7 },
    { nome: 'Internet', id: 8 },
    { nome: 'Ajuste', id: 9 }
  ];

  contas: IConta[] = [];

  tpOperacoes = [
    { operador: "D", label: "Despesa" },
    { operador: "R", label: "Receita" }
  ]

  operacaoSelecionada = { operador: "", label: "Selecione um Tipo de Operação" }
  contaSelecionada: any = 0;
  categoriaSelecionada: number = -1;

  msgs: Message[] = [];

  displayIncluir: boolean = false;
  displayEditar: boolean = false;
  first = 0;
  page?: IPage;
  rows = 13;
  direction = "ASC"
  dataHora: IDataHora = { dataInicial: "2024-04-01", dataFinal: "2024-04-31" };

  constructor(private contaService: ContaService,
    private operacoesService: OperacoesService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService) {
  }

  ngOnInit() {
    this.operacoesPaginadasPorMesAno();
    this.atualizarSaldoPorData();
    this.preencherContasCliente();
  }

  showDialogIncluir() {
    this.displayIncluir = true;
    this.formValueOperacao = new FormGroup({
      nome: new FormControl(''),
      conta: new FormControl('', Validators.required),
      valor: new FormControl('', Validators.required),
      vencimento: new FormControl(new Date()),
      tpOperacao: new FormControl('D'),
      estadoPagamento: new FormControl(true)
    });
    this.categoriaSelecionada = 0;
  }
  showDialogEditar(id: number) {
    this.buscarPorId(id);
    this.displayEditar = true;
  }

  preencherForm(operacao: IOperacao) {
    let estadoPagamentovar;
    if (operacao.estadoPagamento === "QUITADO" ? estadoPagamentovar = true : estadoPagamentovar = false)
      var i = 0;
    for (i = 0; i < this.categorias.length; i++) {
      if (this.categorias[i].nome.toLocaleUpperCase() === operacao.categoria.toString()) {
        this.categoriaSelecionada = this.categorias[i].id;
        break;
      }
    }
    this.contaSelecionada = operacao.conta.id
    this.formValueOperacao = new FormGroup({
      id: new FormControl(operacao.id),
      nome: new FormControl(operacao.nome),
      valor: new FormControl(operacao.valor),
      vencimento: new FormControl(operacao.vencimento),
      tpOperacao: new FormControl(operacao.tpOperacao),
      estadoPagamento: new FormControl(estadoPagamentovar),
      categoria: new FormControl(this.categoriaSelecionada)
    });
  }

  buscarPorId(id: number) {
    this.operacoesService.buscarPorId(id).subscribe(res => {
      this.preencherForm(res);
    })
  }

  operacoesPaginadasPorData() {
    this.operacoesService.operacoesPaginadasPorData(this.dataHora, 0, this.rows, "vencimento", this.direction).subscribe(res => {
      this.page = res;
      this.operacoes = res.content;
    })
  }

  preencherContasCliente() {
    this.contaService.preencherContasCliente().subscribe(res => { this.contas = res; })
  }

  atualizarSaldoPorData() {
    let mesAtual = this.dataAtual.getMonth() + 1;
    let anoAtual = this.dataAtual.getFullYear();
    this.contaService.preencherSaldoPorMesAno(mesAtual, anoAtual).subscribe(res => { this.dashboardView = res; })
  }

  pagarContaVencida(id: number) {
    this.confirmationService.confirm({
      message: 'Deseja informar pagamento?',
      header: 'Informar Pagamento',
      icon: 'pi pi-exclamation-triangle',
      rejectLabel: 'Cancelar',
      acceptLabel: 'Pagar',
      accept: () => {
        this.operacoesService.pagarOperacaoVencida(id).subscribe(res => {
          this.operacoesPaginadasPorMesAno();
          this.atualizarSaldoPorData();
        })
        this.messageService.add({ severity: 'success', summary: 'Feito', detail: 'Estado de Pagamento Alterado para QUITADO' });
      },
      reject: () => {
        this.messageService.add({ severity: 'error', summary: 'Cancelado', detail: 'Cancelado pelo usuário' });
      }
    });
  }

  excluirOperacao(id: number) {
    this.confirmationService.confirm({
      message: 'Deseja excluir operação? a ação não pode ser desfeita.',
      header: 'Excluir Operação',
      icon: 'pi pi-exclamation-triangle',
      rejectLabel: 'Cancelar',
      acceptLabel: 'Excluir',
      accept: () => {
        this.operacoesService.excluirOperacao(id).subscribe(res => {
          this.operacoesPaginadasPorMesAno();
          this.atualizarSaldoPorData();
        })
        this.messageService.add({ severity: 'success', summary: 'Feito', detail: 'Operação Excluida com sucesso.' });
      },
      reject: () => {
        this.messageService.add({ severity: 'error', summary: 'Cancelado', detail: 'A Operação não foi excluida' });
      }
    });
  }

  editarOperacao() {
    let data;
    if (typeof this.formValueOperacao.value.vencimento === 'string') {
      const partes = this.formValueOperacao.value.vencimento.split('/');
      const dataFormatada = `${partes[2]}-${partes[1]}-${partes[0]}`;
      data = new Date(dataFormatada);
    } else if (this.formValueOperacao.value.vencimento instanceof Date) {
      data = this.formValueOperacao.value.vencimento
    }


    const operacao: IOperacao = this.formValueOperacao.value;
    let codCategoria = this.formValueOperacao.value.estadoPagamento;
    operacao.vencimento = this.formatarDataBackend(data);
    operacao.conta = { id: this.contaSelecionada };
    operacao.categoria = this.categoriaSelecionada;
    if (codCategoria == false ? operacao.estadoPagamento = "0" : operacao.estadoPagamento = "1")

      this.operacoesService.editarOperacao(operacao).subscribe(result => {
        this.messageService.add({ severity: 'success', summary: 'Editado', detail: 'A Operação foi editada.' });
        this.operacoesPaginadasPorMesAno();
        this.atualizarSaldoPorData();
        this.displayEditar = false;
      }, error => {
        this.messageService.add({ severity: 'error', summary: `Cancelado`, detail: `${error}` });
        this.displayEditar = false;
      })

  }

  incluirOperacao() {
    debugger;
    const operacao: IOperacao = this.formValueOperacao.value;
    let codCategoria = this.formValueOperacao.value.estadoPagamento;
    operacao.vencimento = this.formatarDataBackend(this.formValueOperacao.value.vencimento);
    operacao.conta = { id: this.contaSelecionada };
    operacao.categoria = this.categoriaSelecionada;
    if (codCategoria == false ? operacao.estadoPagamento = "0" : operacao.estadoPagamento = "1")
      operacao.dataHora = this.formatarDataBackend(new Date());
    this.operacoesService.novaOperacao(operacao).subscribe(result => {
      this.messageService.add({ severity: 'success', summary: 'Editado', detail: 'A Operação foi salva.' });
      this.operacoesPaginadasPorMesAno();
      this.atualizarSaldoPorData();
      this.displayIncluir = false;
    }, error => {
      this.messageService.add({ severity: 'error', summary: `A operação não foi salva`, detail: `${error}` });
      this.displayIncluir = false;
    })

  }

  mudouDate(event: any) {
    this.dataAtual = event.novaData;
    this.operacoesPaginadasPorMesAno();
    this.atualizarSaldoPorData();
  }

  async recarregaConteudo(event: any){
  }

  formatarDataBackend(data: Date) {
    //captura de dia, mes, ano.
    let dia = data.getDate();
    let mes = data.getMonth() + 1;
    let ano = data.getFullYear();
    var hora = data.getHours();
    var minuto = data.getMinutes();
    var segundo = data.getSeconds();
    //verifica se é necessario incluir 0 para datas de apenas 1 dígito
    var dataForm = "" + ((dia < 10) ? `0${dia}` : dia);
    dataForm += ((mes < 10) ? "-0" : "-") + mes;
    dataForm += `-${ano}`;
    //verifica se é necessario incluir 0 para horas com apenas 1 digito
    var horaForm = "" + ((hora < 10) ? `0${hora}` : hora);
    horaForm += ((minuto < 10) ? ":0" : ":") + minuto;
    horaForm += ((segundo < 10) ? ":0" : ":") + segundo;

    return `${dataForm} ${horaForm}`
  }

  operacoesPaginadasPorMesAno() {
    let mesAtual = this.dataAtual.getMonth() + 1;
    let anoAtual = this.dataAtual.getFullYear();
    this.operacoesService.operacoesPaginadasPorMesAno(mesAtual, anoAtual).subscribe(res => {
      this.operacoes = res;
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
    return this.operacoes ? this.first === (this.operacoes.length - this.rows) : true;
  }

  isFirstPage(): boolean {
    return this.operacoes ? this.first === 0 : true;
  }
}
