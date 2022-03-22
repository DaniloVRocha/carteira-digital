import { TransferenciaDTO } from './../model/TransferenciaDTO';
import { IPage } from './../model/IPage';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { IDataHora } from '../model/IDataHora';
import { IOperacao } from '../model/IOperacao';

@Injectable({
  providedIn: 'root'
})
export class OperacoesService {

  endpoint = "operacoes/";
  api = environment.api;

  constructor(private http:HttpClient) { }

  buscarPorId(id:number){
    return this.http.get<IOperacao>(`${this.api}/${this.endpoint}${id}`)
  }

  preencherTabelaVencidos(){
    return this.http.get<IOperacao[]>(`${this.api}/${this.endpoint}consultar-operacao-vencimento`)
  }

  pagarOperacaoVencida(id:number, codOperacao:number){
    return this.http.put(`${this.api}/${this.endpoint}${id}/${codOperacao}`,id)
  }

  gastoPorMes(data:IDataHora){
    return this.http.get(`${this.api}/${this.endpoint}gastoPorMes`+ '/?dataInicial='+ encodeURIComponent( JSON.stringify(data.dataInicial)) + '&dataFinal='+ encodeURIComponent( JSON.stringify(data.dataFinal)));
  }

  gastoPorCategoria(numeroMes:number, numeroAno:number){
    return this.http.get(`${this.api}/${this.endpoint}consultar-categorias-data/${numeroMes}/${numeroAno}`)
  }

  excluirOperacao(id:number){
    return this.http.delete(`${this.api}/${this.endpoint}${id}`)
  }

  editarOperacao(operacao:IOperacao){
    return this.http.put(`${this.api}/${this.endpoint}${operacao.id}`,operacao)
  }
  novaOperacao(operacao:IOperacao){
    return this.http.post(`${this.api}/${this.endpoint}`,operacao)
  }

  operacoesPaginadasPorData(data:IDataHora, page:number, qntLinhas:number, orderBy:string, direction:string){
    let dataInicial = encodeURIComponent( JSON.stringify(data.dataInicial));
    let dataFinal = encodeURIComponent( JSON.stringify(data.dataFinal));
    return this.http.get<IPage>(`${this.api}/${this.endpoint}page-date?page=${page}&linesPerPage=${qntLinhas}&orderBy=${orderBy}&direction=${direction}&dataInicial=${dataInicial}&dataFinal=${dataFinal}`);
  }

  operacoesPaginadasPorMesAno(numeroMes:number, numeroAno:number){
    return this.http.get<IOperacao[]>(`${this.api}/${this.endpoint}consultar-operacao-data/${numeroMes}/${numeroAno}`)
  }

  buscarQntOperacoes(id:any){
    return this.http.get(`${this.api}/${this.endpoint}qnt-operacoes/${id}`);
  }
  transferenciaEntreContas(transferencia:TransferenciaDTO){
    return this.http.post(`${this.api}/${this.endpoint}transferencia`, transferencia);
  }
}
