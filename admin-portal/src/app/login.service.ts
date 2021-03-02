import { Injectable } from '@angular/core';
import {Http,Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';


@Injectable()
export class LoginService {

  checkSessionInNavBar = new Subject();

  constructor(private http:Http) { }

  sendCredentials(username:String,password:String){
      let url="http://localhost:8181/admin/token";
      let encodedCred=btoa(username+":"+password);
      let basicHeader="Basic "+encodedCred;
      let header=new Headers({
        "Content-Type":  "application/json",
        "Authorization": basicHeader
      });
   return this.http.get(url,{headers:header});
  }

  checkSession(){
    let url="http://localhost:8181/admin/checkSession";
    let header=new Headers({
      'X-Auth-Token':  localStorage.getItem('xAuthToken')
    });
 return this.http.get(url,{headers:header});
  }


  logout(){
    let url="http://localhost:8181/user/logout";
    let header=new Headers({
      'X-Auth-Token':  localStorage.getItem('xAuthToken')
    });
 return this.http.post(url,{},{headers:header});
  }



}
