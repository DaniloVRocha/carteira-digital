import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CredenciaisDTO } from 'src/app/model/CredenciaisDTO';
import { LocalUser } from 'src/app/model/local_user';
import { environment } from 'src/environments/environment';
import { StorageService } from './storage.service';


@Injectable({
  providedIn: 'root'
})
export class AccountService {

  headers = new Headers();
  constructor(private http: HttpClient, public storage:StorageService) {
  }

   authenticate(creds: CredenciaisDTO) {
    return this.http.post(`${environment.api}/login`, creds,
      {
        observe: 'response',
        responseType: 'text'
      })  
    }

  successLogin(token: any) {
    let tokenPronto = token.substring(7);
    let user:LocalUser = {
      token : tokenPronto
    };
    this.storage.setLocalUser(user);
  }

  logout(){
    this.storage.setLocalUser(null);
  }
}
