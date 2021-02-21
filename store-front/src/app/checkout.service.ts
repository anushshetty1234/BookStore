import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { AppConst } from './constants/app-const';
import { ShoppingCart } from './models/shopping-cart';
import { UserBilling } from './models/user-billing';
import { UserPayment } from './models/user-payment';
import { UserShipping } from './models/user-shipping';


@Injectable()
export class CheckoutService {

  private serverPath = AppConst.serverPath;

  constructor(private http:Http) { }

  
  checkout(shippingMethod:string,userShipping:UserShipping,userPaymnet:UserPayment,userBilling:UserBilling){
 
    let url = this.serverPath+'/order/checkout';

    let orderInfo = {
      "shippingMethod":shippingMethod,
      "userShipping":userShipping,
      "userPayment":userPaymnet,
      "userBilling":userBilling
    }

    let header=new Headers({
      "Content-Type":  "application/json",
      "X-Auth-Token":  localStorage.getItem("xAuthToken")
    });

    return this.http.post(url,orderInfo,{headers:header});
}

}
