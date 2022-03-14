import { Component, OnInit } from '@angular/core';
import { CredenciaisDTO } from 'src/app/model/CredenciaisDTO';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css']
})
export class CreateAccountComponent implements OnInit {

  creds : CredenciaisDTO = {
    email:'',
    senha:''
  };

  constructor() { }

  ngOnInit(): void {
  }

}
