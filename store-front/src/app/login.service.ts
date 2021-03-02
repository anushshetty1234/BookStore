import { Injectable } from '@angular/core';
import { Http,Headers } from '@angular/http';
import { Router } from '@angular/router';
import { AppConst } from './constants/app-const';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs';

@Injectable()
export class LoginService {


  checkSessionInNavBar = new Subject();

  private serverPath = AppConst.serverPath;

  constructor(private http:Http,private route:Router) { }

  sendCredentials(username:string , password:string){
    
    let url = this.serverPath + '/token' ;
    let encodedCreds = btoa(username+":"+password);
    let basicHeader = "Basic "+encodedCreds;
    let header = new Headers({
      "Content-Type":  "application/json",
      "Authorization": basicHeader
    });

   return this.http.get(url,{headers:header});

  }

  checkSession(){
    let url = this.serverPath + '/checkSession' ;
    let header = new Headers({
      "X-Auth-Token":  localStorage.getItem("xAuthToken")
    });

   return this.http.get(url,{headers:header});
  }

  logout(){
    let url = this.serverPath + '/user/logout' ;
    let header = new Headers({
      "X-Auth-Token":  localStorage.getItem("xAuthToken")
    });

   return this.http.post(url,'',{headers:header});
  }

}
