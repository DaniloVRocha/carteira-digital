import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from '../shared/account.service';
import { CredenciaisDTO } from '../../model/CredenciaisDTO';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  creds : CredenciaisDTO = {
    email:'',
    senha:''
  };

  constructor(private accountService: AccountService,
    private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(){
      this.accountService.authenticate(this.creds).subscribe(response =>  {
        this.accountService.successLogin(response.headers.get('Authorization'));
      },
      error =>  {});
      this.router.navigate(['']);

  }

}
