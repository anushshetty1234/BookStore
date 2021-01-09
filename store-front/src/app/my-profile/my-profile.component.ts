import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppConst } from '../constants/app-const';
import { LoginService } from '../login.service';
import { User } from '../models/user';
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






  
  constructor(private userService:UserService,private loginService:LoginService,private router:Router) { }

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
        
          this.dataFetched=true;
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

}
