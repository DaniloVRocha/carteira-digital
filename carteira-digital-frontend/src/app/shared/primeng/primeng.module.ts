import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { SidebarModule } from 'primeng/sidebar';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { PanelMenuModule } from 'primeng/panelmenu';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelModule } from 'primeng/panel';
import { TableModule } from 'primeng/table';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ChartModule } from 'primeng/chart';
import { DropdownModule } from 'primeng/dropdown';
import { PaginatorModule } from 'primeng/paginator';
import { TooltipModule } from 'primeng/tooltip';



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
    ToolbarModule,
    PanelModule,
    TableModule,
    ConfirmPopupModule,
    ChartModule,
    DropdownModule,
    PaginatorModule,
    TooltipModule
  ]
})
export class PrimengModule { }
