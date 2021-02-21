import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import {MaterialModule} from '@angular/material'; //Angular material modules
import 'hammerjs'; //hammer js for animated icons

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AppRoutingModule } from './app-routing.module';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { MyAccountComponent } from './my-account/my-account.component';
import { LoginService } from './login.service';
import { UserService } from './user.service';
import { MyProfileComponent } from './my-profile/my-profile.component';
import { PaymentService } from './payment.service';
import { ShippingService } from './shipping.service';
import { BookListComponent } from './book-list/book-list.component';
import {  DataTableModule } from 'angular2-datatable';
import { DataFilterPipe } from './book-list/data-filter.pipe';
import { BookService } from './book.service';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { ShoppingCartService } from './shopping-cart.service';
import { OrderComponent } from './order/order.component';
import { CheckoutService } from './checkout.service';
import { OrderSummaryComponent } from './order-summary/order-summary.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavBarComponent,
    MyAccountComponent,
    MyProfileComponent,
    BookListComponent,
    DataFilterPipe,
    BookDetailComponent,
    ShoppingCartComponent,
    OrderComponent,
    OrderSummaryComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    MaterialModule,
    AppRoutingModule,
    DataTableModule
  ],
  providers: [LoginService,UserService,PaymentService,ShippingService,BookService,ShoppingCartService,CheckoutService],
  bootstrap: [AppComponent]
})
export class AppModule { }
