import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { error } from 'protractor';
import { AppConst } from '../constants/app-const';
import { LoginService } from '../login.service';
import { User } from '../models/user';
import { UserBilling } from '../models/user-billing';
import { UserPayment } from '../models/user-payment';
import { UserShipping } from '../models/user-shipping';
import { PaymentService } from '../payment.service';
import { ShippingService } from '../shipping.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-my-profile',
  templateUrl: './my-profile.component.html',
  styleUrls: ['./my-profile.component.css']
})
export class MyProfileComponent implements OnInit {

  private serverPath = AppConst.serverPath;
	private dataFetched = false;
	private loginError:boolean;
	private loggedIn:boolean;
	private credential = {'username':'', 'password':''};

	private user: User = new User();
	private updateSuccess: boolean;
	private newPassword: string;
	private incorrectPassword: boolean;
	private currentPassword: string;

  //tabs
  private selectedProfileTab: number = 0;
  private selectedBillingTab: number = 0;
  private selectedShippingTab: number = 0;

  //payment
  private userPayment: UserPayment = new UserPayment();
	private userBilling: UserBilling = new UserBilling();
  private userPaymentList: UserPayment[] =[];
  private defaultPaymentSet:boolean;
	private defaultUserPaymentId: number;
  private stateList: string[] = ['karnataka','maharahstra','gujrat'];
  
  //shipping
  private defaultShippingSet:boolean;
  private userShippingList: UserShipping[] = [];
  private defaultUserShippingId: number;
  private userShipping: UserShipping = new UserShipping();
  
  constructor(private userService:UserService,private loginService:LoginService,
              private paymentService:PaymentService,private shippingService:ShippingService,private router:Router) { }

  onUpdateUserInfo(){
      this.user.password = this.currentPassword;
      this.userService.updateUserInfo(this.user,this.newPassword).subscribe(
        res =>{
            console.log(res.text());
            this.updateSuccess = true;
        },
        error =>{
            console.log(error.text());
            let errorMessage = error.text();
            if(errorMessage === "Incorrect current password"){
              this.incorrectPassword = true;
            }
        }
      );
  }

  getCurrentUser(){
    this.userService.getCurrentUser().subscribe(
      res=>{
          this.user = res.json();
          this.userPaymentList = this.user.userPaymentList;
          this.userShippingList = this.user.userShippingList;
          
          for (let index in this.userPaymentList) {
            if(this.userPaymentList[index].defaultPayment) {
              this.defaultUserPaymentId = this.userPaymentList[index].id;
              break;
            }
          }

          for (let index in this.userShippingList) {
            if(this.userShippingList[index].userShippingDefault) {
              this.defaultUserShippingId = this.userShippingList[index].id;
              break;
            }
          }
        
          this.dataFetched = true;
      },
      error=>{
        console.log(error);
      }
    );
  }

  ngOnInit() {
    this.loginService.checkSession().subscribe(
      res => {
        this.loggedIn = true;
      },
      error =>{
        this.loggedIn = false;
        this.router.navigate(['/myAccount']);
      }
    );
    this.getCurrentUser();
  }





  //payment relataed funtions
  onNewPayment(){
    this.userPayment.userBilling=this.userBilling;
       this.paymentService.createNewPayment(this.userPayment).subscribe(
         res=>{
           this.getCurrentUser();
           this.userPayment = new UserPayment();
           this.userBilling = new UserBilling();
           this.selectedBillingTab = 0;
         },
         error=>{
           console.log();
          }
      );
  }


  selectedBillingChange(val:number){
    this.selectedBillingTab = val;
  }

  onRemovePayment(id:number){
    this.paymentService.deletePaymentInfo(id).subscribe(
      res=>{
            this.getCurrentUser();
      },
      error=>{
          console.log(error);
      }
    );
  }

  onUpdatePayment(userPaymentUpdate : UserPayment){
      this.userPayment = userPaymentUpdate;
      this.userBilling = userPaymentUpdate.userBilling;
      this.selectedBillingTab = 1;
  }

  setDefaultPayment(){
      this.defaultPaymentSet = false;
      this.paymentService.setDefaultPayment(this.defaultUserPaymentId).subscribe(
        res=>{
          this.getCurrentUser();
          this.defaultPaymentSet = true;
        },
        error=>{
          console.log(error.text());
        }
      );
  }
  

//shipping related funtions

  selectedShippingChange(tabNumber:number){ 
      this.selectedShippingTab = tabNumber;
  }

  onUpdateShipping(userShippingUpdate:UserShipping){
    this.userShipping = userShippingUpdate;
    this.selectedShippingTab = 1;
  }

  onRemoveShipping(id:number){
    this.shippingService.removeShipping(id).subscribe(
      res=>{
        this.getCurrentUser();
      },
      error=>{
        console.log(error.text());
      }
    );
  }

  onNewShipping(){
    this.shippingService.addNewShipping(this.userShipping).subscribe(
      res=>{
        this.userShipping = new UserShipping();
        this.getCurrentUser();
        this.selectedShippingTab=0;
      },
      error=>{
        console.log(error);
      }
    );
  }

  setDefaultShipping(){
    this.shippingService.setDefault(this.defaultUserShippingId).subscribe(
      res=>{
        this.getCurrentUser();
        this.defaultShippingSet = true;
      },
      error=>{
        console.log(error);
      }
    );
  }

}
