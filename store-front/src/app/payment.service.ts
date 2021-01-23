import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { AppConst } from './constants/app-const';
import { UserPayment } from './models/user-payment';

@Injectable()
export class PaymentService {

  private serverPath = AppConst.serverPath;

  constructor(private http:Http) { }

  createNewPayment(userPayment:UserPayment){
      let url = this.serverPath+'/payment/add';

      let header=new Headers({
        "Content-Type":  "application/json",
        "X-Auth-Token":  localStorage.getItem("xAuthToken")
      });

      return this.http.post(url,userPayment,{headers:header});
  }

  getAllPaymentList(){
    let url = this.serverPath+'/payment/getPaymentList';

    let header = new Headers({
      "Content-Type":  "application/json",
      "X-Auth-Token":  localStorage.getItem("xAuthToken")
    });

    return this.http.get(url,{headers:header});
  }

  deletePaymentInfo(id:number){
      let url = this.serverPath + '/payment/remove';

      let header = new Headers({
        "Content-Type":  "application/json",
        "X-Auth-Token":  localStorage.getItem("xAuthToken")
      });

      return this.http.post(url,id,{headers:header});
  
    }
  setDefaultPayment(id:number){
    let url = this.serverPath + '/payment/setDefault';

    let header = new Headers({
      "Content-Type":  "application/json",
      "X-Auth-Token":  localStorage.getItem("xAuthToken")
    });

    return this.http.post(url,id,{headers:header});
}

}
