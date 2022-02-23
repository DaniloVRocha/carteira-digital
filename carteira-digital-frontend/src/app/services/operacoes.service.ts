import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { DataHora } from '../model/IDataHora';
import { IOperacao } from '../model/IOperacao';

@Injectable({
  providedIn: 'root'
})
export class OperacoesService {

  endpoint = "operacoes/";
  api = environment.api;

  constructor(private http:HttpClient) { }

  preencherTabelaVencidos(){
    return this.http.get<IOperacao[]>(`${this.api}/${this.endpoint}consultar-operacao-vencimento`)
  }

  pagarOperacaoVencida(id:number, codOperacao:number){
    return this.http.put(`${this.api}/${this.endpoint}${id}/${codOperacao}`,id)
  }

  gastoPorMes(data:DataHora){
    return this.http.get(`${this.api}/${this.endpoint}gastoPorMes`+ '/?dataInicial='+ encodeURIComponent( JSON.stringify(data.dataInicial)) + '&dataFinal='+ encodeURIComponent( JSON.stringify(data.dataFinal)));
  }
}
