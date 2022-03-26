import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PrimengModule } from './shared/primeng/primeng.module';
import { LoginComponent } from './account/login/login.component';
import { CreateAccountComponent } from './account/create-account/create-account.component';
import { HomeComponent } from './layout/home/home.component';
import { AuthenticationComponent } from './layout/authentication/authentication.component';
import { PrincipalComponent } from './view/principal/principal.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http'
import { StorageService } from './account/shared/storage.service';
import { HttpInterceptorProviders } from './http-interceptors';
import { NavbarComponent } from './layout/navbar/navbar.component';
import { MovimentacoesComponent } from './view/movimentacoes/movimentacoes.component';
import { DashboardUsuarioComponent } from './layout/dashboard-usuario/dashboard-usuario.component';
import { DatepickerComponent } from './layout/datepicker/datepicker.component';
import { PaginatorComponent } from './layout/paginator/paginator.component';
import { VisualizarContasComponent } from './view/visualizar-contas/visualizar-contas.component';
import { RelatoriosPorDataComponent } from './view/relatorios/relatorios-por-data/relatorios-por-data.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    CreateAccountComponent,
    HomeComponent,
    AuthenticationComponent,
    PrincipalComponent,
    NavbarComponent,
    MovimentacoesComponent,
    DashboardUsuarioComponent,
    DatepickerComponent,
    PaginatorComponent,
    VisualizarContasComponent,
    RelatoriosPorDataComponent
  ],
  imports: [
    AppRoutingModule,
    PrimengModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
  ],
  providers: [StorageService,HttpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
