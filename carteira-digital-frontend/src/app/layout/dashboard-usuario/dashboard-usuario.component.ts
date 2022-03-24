import { IContaViewDTO } from './../../model/IContaViewDTO';
import { ContaService } from 'src/app/services/conta.service';
import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-usuario',
  templateUrl: './dashboard-usuario.component.html',
  styleUrls: ['./dashboard-usuario.component.css']
})
export class DashboardUsuarioComponent implements OnInit {

  @Input()
  dashboardView: IContaViewDTO = {
    saldo: 0.0,
    despesas: 0.0,
    receitas: 0.0
  }

  @Input()
  labelDashboard:any = {
    labelSaldo:"",
    labelDespesas:"",
    labelReceitas:""
  }

  constructor() { }

  ngOnInit(): void {
  }
}
