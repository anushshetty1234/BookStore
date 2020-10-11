import { Injectable } from '@angular/core';
import {Http,Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';


@Injectable()
export class LoginService {

  constructor(private http:Http) { }

  sendCredentials(username:String,password:String){
      let url="http://localhost:8181/token";
      let encodedCred=btoa(username+":"+password);
      let basicHeader="Basic "+encodedCred;
      let header=new Headers({
        "Content-Type":  "application/json",
        "Authorization": basicHeader
      });
   return this.http.get(url,{headers:header});
  }

  checkSession(){
    let url="http://localhost:8181/checkSession";
    console.log("pp=="+localStorage.getItem('xAuthToken'));
    let header=new Headers({
      'X-Auth-Token':  localStorage.getItem('xAuthToken')
    });
 return this.http.get(url,{headers:header});
  }


}
