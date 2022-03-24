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
import { DialogModule } from 'primeng/dialog';
import { CalendarModule } from 'primeng/calendar';
import { InputSwitchModule } from 'primeng/inputswitch';
import { RadioButtonModule } from 'primeng/radiobutton';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessagesModule } from 'primeng/messages';
import { ToastModule } from 'primeng/toast';
import { CarouselModule } from 'primeng/carousel';
import {KeyFilterModule} from 'primeng/keyfilter';
import {MessageModule} from 'primeng/message';



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
    TooltipModule,
    DialogModule,
    CalendarModule,
    InputSwitchModule,
    RadioButtonModule,
    ConfirmDialogModule,
    MessagesModule,
    ToastModule,
    CarouselModule,
    KeyFilterModule,
    MessageModule
  ]
})
export class PrimengModule { }
