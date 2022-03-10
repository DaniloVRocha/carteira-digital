import { IConta } from './../../model/IConta';
import { ContaService } from './../../services/conta.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-visualizar-contas',
  templateUrl: './visualizar-contas.component.html',
  styleUrls: ['./visualizar-contas.component.css']
})
export class VisualizarContasComponent implements OnInit {

  contas: IConta[] = [];
  responsiveOptions:any;
  display:boolean = false;
  saldo:any = 0;
  disabled: boolean = true;

  formValueConta: FormGroup = new FormGroup({
    instituicao: new FormControl(''),
    inicial: new FormControl('')
  });

  constructor(private contaService: ContaService) {
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
    this.buscarPorIdConta(id);
  }

  preencherForm(conta:IConta){
    this.saldo = conta.saldo;
    this.formValueConta = new FormGroup({
      instituicao: new FormControl(conta.instituicao),
      inicial: new FormControl(true),
    });
  }

  buscarPorIdConta(id:number){
    this.contaService.buscarContaPorId(id).subscribe(conta=>{
      this.preencherForm(conta);
    })
  }
}
