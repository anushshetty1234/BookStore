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

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavBarComponent,
    MyAccountComponent,
    MyProfileComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    MaterialModule,
    AppRoutingModule
  ],
  providers: [LoginService,UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
