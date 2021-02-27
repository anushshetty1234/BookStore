import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { AppConst } from './constants/app-const';

@Injectable()
export class OrderService {

  constructor(private http:Http) { }

  private serverPath = AppConst.serverPath;
  
  getOrderList(){
 
    let url = this.serverPath +'/order/getOrderList';

    let header=new Headers({
      "Content-Type":  "application/json",
      "X-Auth-Token":  localStorage.getItem("xAuthToken")
    });

    return this.http.get(url,{headers:header});
}


}
