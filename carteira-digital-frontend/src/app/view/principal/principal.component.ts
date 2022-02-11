import { Component, OnInit } from '@angular/core';
import { IContaViewDTO } from 'src/app/model/IContaViewDTO';
import { ContaService } from 'src/app/services/conta.service';

@Component({
  selector: 'app-principal',
  templateUrl: './principal.component.html',
  styleUrls: ['./principal.component.css']
})
export class PrincipalComponent implements OnInit{
 
  saldo:number = 0.0;
  despesas:number = 0.0;
  receitas:number = 0.0;
  
  constructor(private contaService: ContaService) {

  }
  ngOnInit(): void {
    this.atualizarSaldo();
  }
  
  atualizarSaldo() {
    debugger;
    this.contaService.preencherSaldo().subscribe(res => {
      this.saldo = res.saldo; 
      this.despesas = res.despesas;
      this.receitas = res.receitas;
    })
  }

}
