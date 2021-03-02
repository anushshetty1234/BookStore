import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AppConst } from '../constants/app-const';
import { CartItem } from '../models/cart-item';
import { Order } from '../models/order';

@Component({
  selector: 'app-order-summary',
  templateUrl: './order-summary.component.html',
  styleUrls: ['./order-summary.component.css']
})
export class OrderSummaryComponent implements OnInit {

  private order:Order = new Order();
  private orderResult:boolean;
  private cartItemList:CartItem[] = [];
  private estimatedDeliveryDate:string;
  private serverPath = AppConst.serverPath;


  constructor(private activatedRoute:ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe(
      queryParams =>{
        this.order = JSON.parse(queryParams['order']);
        if(this.order.orderStatus == "placed"){
          this.orderResult = true;
        }
        this.cartItemList = this.order.cartItemList;
        this.estimatedDeliveryDate = this.order.shippingDate;
      }
    );
  }

}
