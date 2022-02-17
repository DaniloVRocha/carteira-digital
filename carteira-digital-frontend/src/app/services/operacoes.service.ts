import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
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
    console.log(this.http.put(`${this.api}/${this.endpoint}${id}/${codOperacao}`,id))
    return this.http.put(`${this.api}/${this.endpoint}${id}/${codOperacao}`,id)
  }
}
