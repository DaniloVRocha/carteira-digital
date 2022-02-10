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

  async onSubmit(){
    try{
      await this.accountService.login(this.creds).subscribe(resp => {
        this.accountService.successLogin(resp.headers.get('Authorization'));
        this.router.navigate([''])
      })

      this.router.navigate(['']);
    }catch(error){
      console.log(error)
    }
  }

}
