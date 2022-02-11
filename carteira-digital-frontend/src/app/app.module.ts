import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PrimengModule } from './shared/primeng/primeng.module';
import { LoginComponent } from './account/login/login.component';
import { CreateAccountComponent } from './account/create-account/create-account.component';
import { HomeComponent } from './layout/home/home.component';
import { AuthenticationComponent } from './layout/authentication/authentication.component';
import { PrincipalComponent } from './view/principal/principal.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http'
import { StorageService } from './account/shared/storage.service';
import { HttpInterceptorProviders } from './http-interceptors';
import { NavbarComponent } from './layout/navbar/navbar.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    CreateAccountComponent,
    HomeComponent,
    AuthenticationComponent,
    PrincipalComponent,
    NavbarComponent,
  ],
  imports: [
    AppRoutingModule,
    PrimengModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [StorageService,HttpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
