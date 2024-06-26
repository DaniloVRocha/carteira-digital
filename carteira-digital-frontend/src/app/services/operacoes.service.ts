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

  novaOperacao(operacao:IOperacao){
    return this.http.post(`${this.api}/${this.endpoint}`,operacao)
  }

  excluirOperacao(id:number){
    return this.http.delete(`${this.api}/${this.endpoint}${id}`)
  }

  editarOperacao(operacao:IOperacao){
    return this.http.put(`${this.api}/${this.endpoint}${operacao.id}`,operacao)
  }

  buscarPorId(id:number){
    return this.http.get<IOperacao>(`${this.api}/${this.endpoint}${id}`)
  }

  preencherTabelaVencidos(){
    return this.http.get<IOperacao[]>(`${this.api}/${this.endpoint}consultarOperacaoVencimento`)
  }

  pagarOperacaoVencida(id:number){
    return this.http.put(`${this.api}/${this.endpoint}alterarEstadoPagamento/${id}`, id)
  }

  gastoPorMes(data:IDataHora){
    return this.http.get(`${this.api}/${this.endpoint}gastoPorMes`+ '/?dataInicial='+ encodeURIComponent( JSON.stringify(data.dataInicial)) + '&dataFinal='+ encodeURIComponent( JSON.stringify(data.dataFinal)));
  }

  gastoPorCategoria(numeroMes:number, numeroAno:number){
    return this.http.get(`${this.api}/${this.endpoint}consultarQuantidadeCategoriasPorMesAno/${numeroMes}/${numeroAno}`)
  }

  //Buscar Operações no formato LocalDateTime
  operacoesPaginadasPorData(data:IDataHora, page:number, qntLinhas:number, orderBy:string, direction:string){
    let dataInicial = encodeURIComponent( JSON.stringify(data.dataInicial));
    let dataFinal = encodeURIComponent( JSON.stringify(data.dataFinal));
    return this.http.get<IPage>(`${this.api}/${this.endpoint}page-date?page=${page}&linesPerPage=${qntLinhas}&orderBy=${orderBy}&direction=${direction}&dataInicial=${dataInicial}&dataFinal=${dataFinal}`);
  }

  //Buscar Operações no formato Número de mês e ano.
  operacoesPaginadasPorMesAno(numeroMes:number, numeroAno:number){
    return this.http.get<IOperacao[]>(`${this.api}/${this.endpoint}consultarOperacaoMesAno/${numeroMes}/${numeroAno}`)
  }

  buscarQntOperacoes(id:any){
    return this.http.get(`${this.api}/${this.endpoint}qntOperacoes/${id}`);
  }
  
  transferenciaEntreContas(transferencia:TransferenciaDTO){
    return this.http.post(`${this.api}/${this.endpoint}transferencia`, transferencia);
  }

  consultarValoresAno(){
    return this.http.get<[]>(`${this.api}/${this.endpoint}consultarValoresAno`)
  }
}
