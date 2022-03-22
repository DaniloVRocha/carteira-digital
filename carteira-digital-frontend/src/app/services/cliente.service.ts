import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { CreateCreds } from '../model/CreateCreds';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  endpoint = "clientes/";
  api = environment.api;

  constructor(private http: HttpClient) { }

  createAccount(createCreds: CreateCreds){
    return this.http.post(`${this.api}/${this.endpoint}`, createCreds)
  }
}
