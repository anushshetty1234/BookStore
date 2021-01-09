import { Injectable } from '@angular/core';
import { Http,Headers } from '@angular/http';
import { Router } from '@angular/router';
import { AppConst } from './constants/app-const';
import { Observable } from 'rxjs/Observable';
import { User } from './models/user';


@Injectable()
export class UserService {

  private serverPath = AppConst.serverPath;

  constructor(private http:Http) { }

  newUser(username:String,email:String ){

    let url = this.serverPath+'/user/newUser';
    let userInfo = {
      "username":username,
      "email":email
    }; 

    let tokenHeader = new Headers({
      'Content-Type':'application/json',
      'X-Auth-Token' : localStorage.getItem('xAuthToken')
    });

   return  this.http.post(url,JSON.stringify(userInfo),{headers:tokenHeader});

  }


  retrievePassword(email:String){
    let url = this.serverPath+'/user/forgotPassword';
    let userInfo = {
      "email":email
    }; 

    let tokenHeader = new Headers({
      'Content-Type':'application/json',
      'X-Auth-Token' : localStorage.getItem('xAuthToken')
    });

   return  this.http.post(url,JSON.stringify(userInfo),{headers:tokenHeader});
  }

  updateUserInfo(user:User,newPassword:String){
    let url = this.serverPath+'/user/updateUserInfo';
    let userInfo = {
      "id":user.id,
      "fristname" : user.firstName,
      "lastname":user.lastName,
      "username":user.username,
      "currentPassword":user.password,
      "email":user.email,
      "password" : newPassword
    };

    let headers = new Headers({
      'Content-Type':'application/json',
      'X-Auth-Token' : localStorage.getItem('xAuthToken')
    });

    return this.http.post(url,userInfo,{headers:headers});
  }

  getCurrentUser(){
    let url = this.serverPath+'/user/getCurrentUser';
    
    let headers = new Headers({
      'Content-Type':'application/json',
      'X-Auth-Token' : localStorage.getItem('xAuthToken')
    });

    return this.http.get(url,{headers:headers});
  }




}
