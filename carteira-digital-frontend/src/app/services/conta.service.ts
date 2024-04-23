import { ContaEdicaoDTO } from './../model/ContaEdicaoDTO';
import { IConta } from './../model/IConta';
import { IDataHora } from './../model/IDataHora';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { IContaViewDTO } from '../model/IContaViewDTO';

@Injectable({
  providedIn: 'root'
})
export class ContaService {
  endpoint = "contas/";
  api = environment.api;

  constructor(private http: HttpClient) { }

  preencherSaldo() {
    return this.http.get<IContaViewDTO>(`${this.api}/${this.endpoint}consultar-valores`)
  }

  //Buscar Saldo no Formato LocalDateTime.
  preencherSaldoPorMes(data:IDataHora) {
    debugger;
    let dataInicial = encodeURIComponent( JSON.stringify(data.dataInicial));
    let dataFinal = encodeURIComponent( JSON.stringify(data.dataFinal));
    return this.http.get<IContaViewDTO>(`${this.api}/${this.endpoint}consultar-valores-data?dataInicial=${dataInicial}&dataFinal=${dataFinal}`);
  }

  //Buscar Saldo no Formato Mês e Ano.
  preencherSaldoPorMesAno(numeroMes:number, numeroAno:number){
    return this.http.get<IContaViewDTO>(`${this.api}/${this.endpoint}consultar-valores-data/${numeroMes}/${numeroAno}`)
  }

  buscarContaPorId(id:number) {
    return this.http.get<IConta>(`${this.api}/${this.endpoint}${id}`);
  }

  buscarContasCliente(){
    return this.http.get<IConta[]>(`${this.api}/${this.endpoint}`)
  }

  preencherContasCliente(){
    return this.http.get<IConta[]>(`${this.api}/${this.endpoint}`);
  }

  editarConta(conta: ContaEdicaoDTO, id:number){
    return this.http.put(`${this.api}/${this.endpoint}${id}`, conta);
  }
  
  incluirConta(conta: IConta){
    return this.http.post(`${this.api}/${this.endpoint}`, conta);
  }

}
