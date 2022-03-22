import { Router } from '@angular/router';
import { ClienteService } from './../../services/cliente.service';
import { CreateCreds } from './../../model/CreateCreds';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Message, MessageService } from 'primeng/api';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css'],
  providers:[MessageService]
})
export class CreateAccountComponent implements OnInit {

  formValueCadastro: FormGroup = new FormGroup({});

  senhaIgual:boolean = false;

  confirmaSenha:string = "";
  focoCampo:boolean = false;

  msgs: Message[] = [];

  displayPosition: boolean = false;

  position: string = "";

  constructor(private clienteService:ClienteService,
              private messageService:MessageService,
              private router:Router) { }

  ngOnInit(): void {
    this.formValueCadastro = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      senha: new FormControl('', Validators.compose([Validators.required, Validators.minLength(5)])),
      nome: new FormControl('', Validators.compose([Validators.required, Validators.minLength(5), Validators.maxLength(80),
        Validators.pattern(/^[a-z A-Z]*$/)])),
      cpf: new FormControl('', Validators.compose([Validators.required, Validators.minLength(11), Validators.maxLength(11)])),
    });
  }

  showPositionDialog(position: string) {
    this.position = position;
    this.displayPosition = true;
  }

  validaSenha(){
    if(this.formValueCadastro.value.senha === this.confirmaSenha){
      this.senhaIgual = true;
    }else{
      this.focoCampo = true;
      this.senhaIgual = false;
    }
  }

  createAccount(){
    const creds: CreateCreds = this.formValueCadastro.value;
    this.clienteService.createAccount(creds).subscribe(res=>{
      this.router.navigate([""])
    }, error => {
      this.messageService.add({ severity: 'error', summary: `A operação não foi salva`, detail: `${error}` });
    })
  }

}
