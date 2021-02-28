import { Component, OnInit } from '@angular/core';
import { MdDialog, MdDialogRef } from '@angular/material';
import { Router } from '@angular/router';
import { CheckoutService } from '../checkout.service';
import { AppConst } from '../constants/app-const';
import { Book } from '../models/book';
import { CartItem } from '../models/cart-item';
import { Order } from '../models/order';
import { ShoppingCart } from '../models/shopping-cart';
import { User } from '../models/user';
import { UserBilling } from '../models/user-billing';
import { UserPayment } from '../models/user-payment';
import { UserShipping } from '../models/user-shipping';
import { ProgressSpinnerDialogComponent } from '../progress-spinner-dialog/progress-spinner-dialog.component';
import { ShoppingCartService } from '../shopping-cart.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

  private user:User;
  private serverPath = AppConst.serverPath;
  private selectedBook: Book;
  private cartItemList: CartItem[] = [];
  private cartItemNumber: number;
  private shoppingCart: ShoppingCart = new ShoppingCart();
  private cartItemUpdated:boolean;
  private payment:UserPayment = new UserPayment();
  private shippingAddress:UserShipping = new UserShipping();
  private billingAddress: UserBilling = new UserBilling();
  private userShippingList: UserShipping[] = [];
  private userPaymentList: UserPayment[] = [];
  private selectedTab: number;
  private emptyShippingList: boolean = true;
  private emptyPaymentList: boolean = true;
  private stateList: string[] = ['karnataka','maharahstra','gujrat'];
  private shippingMethod:string;
  private order:Order = new Order();


  constructor(private userService:UserService,private shoppingCartService:ShoppingCartService,private checkoutService:CheckoutService
      ,private router:Router, private dialog: MdDialog) { }

  ngOnInit() {
    this.getCurrentUser();
    this.getCartItems();
    this.getShoppingCart();
    this.shippingMethod = 'groundShipping';
  }

  selectedChange(tab : number){
    this.selectedTab = tab;
  }
  goToPayment(){
    this.selectedTab = 1;
  }
  goToReview(){
    this.selectedTab = 2;
  }


  setShippingAddress(setShipping:UserShipping){
    this.shippingAddress = setShipping;
  }

  setPaymentMethod(setPayment:UserPayment){
    this.payment = setPayment;
    this.billingAddress = setPayment.userBilling;
  }

  setBillingAsShipping(setBillingAsShippingFlag : boolean){
    if(setBillingAsShippingFlag){
      this.billingAddress.userBillingCity = this.shippingAddress.userShippingCity;
      this.billingAddress.userBillingCountry = this.shippingAddress.userShippingCountry;
      this.billingAddress.userBillingName = this.shippingAddress.userShippingName;
      this.billingAddress.userBillingState = this.shippingAddress.userShippingState;
      this.billingAddress.userBillingStreet1 = this.shippingAddress.userShippingStreet1;
      this.billingAddress.userBillingStreet2 = this.shippingAddress.userShippingStreet2;
      this.billingAddress.userBillingZipcode = this.shippingAddress.userShippingZipcode;
    }else{
      this.billingAddress = new UserBilling();
    }
  }

  getCurrentUser(){
    this.userService.getCurrentUser().subscribe(
      res=>{
          this.user = res.json();
          this.userPaymentList = this.user.userPaymentList;
          this.userShippingList = this.user.userShippingList;

          if( this.userPaymentList.length){
            this.emptyPaymentList = false;
          }
          if( this.userShippingList.length){
            this.emptyShippingList = false;
          }

          for (let index in this.userPaymentList) {
            if(this.userPaymentList[index].defaultPayment) {
              this.payment = this.userPaymentList[index];
              this.billingAddress = this.payment.userBilling;
              break;
            }
          }

          for (let index in this.userShippingList) {
            if(this.userShippingList[index].userShippingDefault) {
              this.shippingAddress = this.userShippingList[index];
              break;
            }
          }
      
      },
      error=>{
        console.log(error);
      }
    );
  }

  getCartItems(){
    this.shoppingCartService.getCartItems().subscribe(
      res=>{
          this.cartItemList = res.json();
          this.cartItemNumber = this.cartItemList.length;
      },
      error=>{
        console.log(error);
      }
    );
  }


  getShoppingCart(){
    this.shoppingCartService.getShoppingCart().subscribe(
      res=>{
          this.shoppingCart = res.json();
      },
      error=>{
        console.log(error);
      }
    );
  }

  onSubmit(){
    
    let dialogRef: MdDialogRef<ProgressSpinnerDialogComponent> = this.dialog.open(ProgressSpinnerDialogComponent, {
      disableClose: true
    });


    this.checkoutService.checkout(this.shippingMethod,this.shippingAddress,this.payment,this.billingAddress).subscribe(
      res=>{
          this.order = res.json();
          this.router.navigate(['/orderResult'],{queryParams:{order:JSON.stringify(this.order)}});
          dialogRef.close();
      },
      error=>{
          this.order.orderStatus = "failed";
          this.router.navigate(['/orderResult'],{queryParams:{order:JSON.stringify(this.order)}});
          dialogRef.close();
      }
    );

  }



}
