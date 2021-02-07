import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { AppConst } from './constants/app-const';
import { CartItem } from './models/cart-item';

@Injectable()
export class ShoppingCartService {

  constructor(private http:Http) { }

  private serverPath = AppConst.serverPath;

  getCartItems(){
    let url = this.serverPath+'/cart/getCartItems';

    let header=new Headers({
      "Content-Type":  "application/json",
      "X-Auth-Token":  localStorage.getItem("xAuthToken")
    });

    return this.http.get(url,{headers:header});
  }

  getShoppingCart(){
    let url = this.serverPath+'/cart/getShoppingCart';

    let header=new Headers({
      "Content-Type":  "application/json",
      "X-Auth-Token":  localStorage.getItem("xAuthToken")
    });

    return this.http.get(url,{headers:header});
  }

  removeCartItem(cartItemId:number){
    let url = this.serverPath+'/cart/removeCartItem';

    let header=new Headers({
      "Content-Type":  "application/json",
      "X-Auth-Token":  localStorage.getItem("xAuthToken")
    });

    return this.http.post(url,cartItemId,{headers:header});
  }

  addCartItem(bookId:number,quantity:number){
    let url = this.serverPath+'/cart/addICartItem';

    let addItemInfo = {
      "bookId" : bookId,
      "quantity" : quantity
    }

    let header=new Headers({
      "Content-Type":  "application/json",
      "X-Auth-Token":  localStorage.getItem("xAuthToken")
    });

    return this.http.post(url,addItemInfo,{headers:header});
  }

  updateCartItem(cartItemId:number , quantity:number){
    let url = this.serverPath+'/cart/updateItem';

    let updateInfo = {
      "cartItemId" : cartItemId,
      "quantity" : quantity
    }

    let header=new Headers({
      "Content-Type":  "application/json",
      "X-Auth-Token":  localStorage.getItem("xAuthToken")
    });

    return this.http.post(url,updateInfo,{headers:header});
  }

}
