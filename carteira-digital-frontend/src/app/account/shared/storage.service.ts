import { Injectable } from '@angular/core';
import { STORAGE_KEYS } from 'src/app/config/storage_keys.config';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  getLocalUser(){
    let user = localStorage.getItem(STORAGE_KEYS.localUser);
    if(user == null){
      return null;
    }else{
      return JSON.parse(user);
    }
  }

  setLocalUser(obj:any){
    if(obj == null){
      localStorage.removeItem(STORAGE_KEYS.localUser);
    }else{
      localStorage.setItem(STORAGE_KEYS.localUser, JSON.stringify(obj));
    }
  }
}
