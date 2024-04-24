import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  getAuthorizationToken(){
    let token = localStorage.getItem('token');
    if(token == null){
      return null;
    }else{
      return JSON.parse(token);
    }
  }

  setAuthorizationToken(obj:any){
    if(obj == null){
      console.log("ERRO JWT")
    }else{
      localStorage.setItem('token', JSON.stringify(obj));
    }
  }
}
