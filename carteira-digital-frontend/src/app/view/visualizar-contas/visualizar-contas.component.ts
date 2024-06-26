import { ContaEdicaoDTO } from './../../model/ContaEdicaoDTO';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TransferenciaDTO } from 'src/app/model/TransferenciaDTO';
import { OperacoesService } from 'src/app/services/operacoes.service';
import { IConta } from './../../model/IConta';
import { ContaService } from './../../services/conta.service';

@Component({
  selector: 'app-visualizar-contas',
  templateUrl: './visualizar-contas.component.html',
  styleUrls: ['./visualizar-contas.component.css'],
  providers:[ConfirmationService, MessageService]
})
export class VisualizarContasComponent implements OnInit {

  contas: IConta[] = [];
  conta:IConta={};
  responsiveOptions:any;
  display:boolean = false;
  displayInformacao:boolean = false;
  displayTransferencia:boolean = false;
  displayIncluir:boolean = false;
  saldo:any = 0;
  disabled: boolean = true;
  contaOrigemSelecionada:any = 1;
  contaDestinoSelecionada:any = 1;
  transferencia:TransferenciaDTO = {
    valor:0,
    idOrigem:0,
    idDestino:0
  }

  formValueConta: FormGroup = new FormGroup({
    instituicao: new FormControl(''),
    mostrarTelaInicial: new FormControl('')
  });
  
  formIncluir: FormGroup = new FormGroup({
    instituicao: new FormControl('', Validators.required),
    saldo: new FormControl('', Validators.required)
  });

  formTransferencia: FormGroup = new FormGroup({
    valor: new FormControl("", Validators.required),
    contaOrigem: new FormControl(""),
    contaDestino: new FormControl(""),
  });

  quantidadeView = {
    quantidadeDespesas: 0,
    quantidadeReceitas: 0
  }

  constructor(private contaService: ContaService,
              private operacoesService: OperacoesService,
              private confirmationService: ConfirmationService,
              private messageService:MessageService) {
    this.responsiveOptions = [
      {
          breakpoint: '1024px',
          numVisible: 3,
          numScroll: 3
      },
      {
          breakpoint: '768px',
          numVisible: 2,
          numScroll: 2
      },
      {
          breakpoint: '560px',
          numVisible: 1,
          numScroll: 1
      }
  ];
  }

  ngOnInit(): void {
    this.preencherDataContas();
  }

  preencherDataContas(){
      this.contaService.buscarContasCliente().subscribe(contas => this.contas = contas)
  }

  showDialogEditar(id:number){
    this.display = true;
    this.preencherFormPorIdConta(id);
  }

  showDialogInformacoes(id:number){
    this.preencherCardDetalhes(id);
    this.displayInformacao = true
  }

  showDialogTransferencia(){
    this.preencherDataContas();
    this.displayTransferencia = true;
  }

  preencherForm(conta:IConta){
    this.saldo = conta.saldo;
    this.formValueConta = new FormGroup({
      instituicao: new FormControl(conta.instituicao),
      mostrarTelaInicial: new FormControl(conta.mostrarTelaInicial),
    });
  }

  preencherFormPorIdConta(id:number){
    this.contaService.buscarContaPorId(id).subscribe(conta=>{
      this.preencherForm(conta);
      this.conta = conta;
    })
  }

  preencherCardDetalhes(id:number){
    this.contaService.buscarContaPorId(id).subscribe(conta=>{
      this.conta = conta;
      this.operacoesService.buscarQntOperacoes(this.conta.id).subscribe(res=>{
        this.quantidadeView.quantidadeReceitas = parseInt(Object.values(res)[0]);
        this.quantidadeView.quantidadeDespesas = parseInt(Object.values(res)[1]);
      })
    })
  }
  transferenciaEntreContas(){
    this.transferencia = {
      valor: this.formTransferencia.value.valor,
      idOrigem: this.contaOrigemSelecionada,
      idDestino: this.contaDestinoSelecionada
    }
    if(this.contaOrigemSelecionada === this.contaDestinoSelecionada){
      this.messageService.add({severity:'error', summary:'Erro', detail:'A Conta Origem não pode ser igual a Conta Destino.'});
    }else if(this.formTransferencia.value.valor <= 0){
      this.messageService.add({severity:'error', summary:'Erro', detail:'O Valor da Transferencia não pode ser 0.'});
    }else{
      this.confirmationService.confirm({
        message: 'Deseja transferir saldo entre as contas ?',
        header: 'Transferencia entre contas',
        icon: 'pi pi-exclamation-triangle',
        rejectLabel:'Cancelar',
        acceptLabel:'Transferir',
        accept: () => {
          this.operacoesService.transferenciaEntreContas(this.transferencia).subscribe(res=>{
  
          });
          this.messageService.add({severity:'success', summary:'Feito', detail:'Transferencia realizada com sucesso.'});
          this.displayTransferencia = false;
        },
        reject: () => {
          this.messageService.add({severity:'error', summary:'Cancelado', detail:'A Transferencia não foi feita, tente novamente'});
        }
      });
    }
  }

  editarConta(id:any){
    const conta:ContaEdicaoDTO = this.formValueConta.value;
    this.contaService.editarConta(conta, id).subscribe(res=>{
    })
    this.messageService.add({severity:'success', summary:'Feito', detail:'Conta Editada com sucesso'});
    this.display = false;
  }

  incluirConta(){
    const conta:IConta = this.formIncluir.value;

    const contaFormatada : any = {
        "instituicao": this.formIncluir.value.instituicao,
        "saldo": this.formIncluir.value.saldo,
        "mostrarTelaInicial": true
    }
    this.contaService.incluirConta(contaFormatada).subscribe(res=>{
    })
    this.messageService.add({severity:'success', summary:'Feito', detail:'Conta Criada com sucesso'});
    this.displayIncluir = false;
  }
}
