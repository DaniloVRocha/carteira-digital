import { Component, OnInit } from '@angular/core';
import { PrimeNGConfig, MenuItem } from 'primeng/api';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  visibleSidebar1:boolean = false;
  items: MenuItem[] = [];


  constructor(private primengConfig: PrimeNGConfig) { }

    ngOnInit() {
      this.primengConfig.ripple = true;
      this.items = [
        {
            label: 'Home',
            icon: 'pi pi-home',
            routerLink:'/'
        },
        {
            label: 'Movimentações',
            icon: 'pi pi-money-bill',
            routerLink:'/movimentacoes'
        },
        {
            label: 'Contas',
            icon: 'pi pi-fw pi-wallet',
            items: [
                {
                    label: 'Visualizar Contas',
                    icon: 'pi pi-pi pi-credit-card'
                },
                {
                    label: 'Transferencias entre contas',
                    icon: 'pi pi-pi pi-arrows-h'
                },
            ]
        },
        {
            label: 'Relatórios',
            icon: 'pi pi-fw pi-chart-bar',
            items: [
                {
                    label: 'Relatórios por Data',
                    icon: 'pi pi-pi pi-calendar'
                },
                {
                    label: 'Relatórios por Categoria',
                    icon: 'pi pi-pi pi-list'
                },
            ]
        },
        {
            label: 'Ações',
            icon: 'pi pi-fw pi-cog',
            items: [
                {
                    label: 'Ajuste de Saldo',
                    icon: 'pi pi-fw pi-pencil',
                },
                {
                    label: 'Editar Conta de Usuário',
                    icon: 'pi pi-fw pi-tags',
                    items: [
                        {label: 'Editar Conta', icon: 'pi pi-fw pi-user-edit'},
                        {label: 'Excluir minha conta', icon: 'pi pi-fw pi-user-minus'}
                    ]
                }
            ]
        }
    ];
    }

}
