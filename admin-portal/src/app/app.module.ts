import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';


import {MaterialModule} from '@angular/material'; //Angular material modules
import 'hammerjs'; //hammer js for animated icons

import { AppComponent } from './app.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { LoginComponent } from './login/login.component';


import { LoginService } from './login.service';
import { AppRoutingModule } from './app-routing.module';
import { AddNewBookComponent } from './add-new-book/add-new-book.component';
import { AddBookService } from './add-book.service';
import { UploadServiceService } from './upload-service.service';
import { BookListComponent, DialogComponent } from './book-list/book-list.component';
import { GetBookListService } from './get-book-list.service';
import { ViewBookComponent } from './view-book/view-book.component';
import { GetBookService } from './get-book.service';
import { EditBookComponent } from './edit-book/edit-book.component';
import { EditBookService } from './edit-book.service';
import { RemoveBookService } from './remove-book.service';


@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    LoginComponent,
    AddNewBookComponent,
    BookListComponent,
    ViewBookComponent,
    EditBookComponent,
    DialogComponent
  ],
  entryComponents: [DialogComponent],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    MaterialModule,
    AppRoutingModule
  ],
  providers: [LoginService,AddBookService,UploadServiceService
              ,GetBookListService,GetBookService,EditBookService
              ,RemoveBookService],
  bootstrap: [AppComponent]
})
export class AppModule { }
