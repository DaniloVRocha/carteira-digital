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
    return this.http.get<IContaViewDTO>(`${this.api}/${this.endpoint}atualizarValores`)
  }
  preencherSaldoPorMes(data:IDataHora) {
    let dataInicial = encodeURIComponent( JSON.stringify(data.dataInicial));
    let dataFinal = encodeURIComponent( JSON.stringify(data.dataFinal));
    return this.http.get<IContaViewDTO>(`${this.api}/${this.endpoint}atualizarValores-data?dataInicial=${dataInicial}&dataFinal=${dataFinal}`);
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
}
