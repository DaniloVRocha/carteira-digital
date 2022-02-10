import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { SidebarModule } from 'primeng/sidebar';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import {PanelMenuModule} from 'primeng/panelmenu';
import {MegaMenuModule} from 'primeng/megamenu';
import {ToolbarModule} from 'primeng/toolbar';


@NgModule({
  declarations: [],
  exports: [
    BrowserAnimationsModule,
    BrowserModule,
    CardModule,
    InputTextModule,
    ButtonModule,
    SidebarModule,
    ProgressSpinnerModule,
    PanelMenuModule,
    MegaMenuModule,
    ToolbarModule
  ]
})
export class PrimengModule { }
