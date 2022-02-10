import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CredenciaisDTO } from 'src/app/model/CredenciaisDTO';
import { environment } from 'src/environments/environment';
import { StorageService } from './storage.service';


@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: HttpClient, public storage:StorageService) {
  }

 

  login(creds: CredenciaisDTO) {
    return this.http.post(`${environment.api}/login`, creds,
      {
        observe:'response',
        responseType:'text'
      })
  }
  
  successLogin(token: any) {
    debugger;
    let tokenPronto = token.substring(7);
    this.storage.setAuthorizationToken(tokenPronto);
  }
}
