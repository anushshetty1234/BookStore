import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { AppConst } from './constants/app-const';
import { UserShipping } from './models/user-shipping';

@Injectable()
export class ShippingService {

  private serverPath = AppConst.serverPath;

  constructor(private http:Http) { }

  addNewShipping(userShipping:UserShipping){
    let url = this.serverPath+'/shipping/add';

    let header=new Headers({
      "Content-Type":  "application/json",
      "X-Auth-Token":  localStorage.getItem("xAuthToken")
    });

    return this.http.post(url,userShipping,{headers:header});
  }

  removeShipping(id:number){
    let url = this.serverPath+'/shipping/delete';

    let header=new Headers({
      "Content-Type":  "application/json",
      "X-Auth-Token":  localStorage.getItem("xAuthToken")
    });

    return this.http.post(url,id,{headers:header});
  }

  setDefault(id:number){
    let url = this.serverPath+'/shipping/setDefault';

    let header=new Headers({
      "Content-Type":  "application/json",
      "X-Auth-Token":  localStorage.getItem("xAuthToken")
    });

    return this.http.post(url,id,{headers:header});
  }

}
