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
}
