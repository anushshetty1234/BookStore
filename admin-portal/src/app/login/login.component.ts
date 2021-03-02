import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { LoginService } from "./../login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

 private credentials={"username":"","password":""};
 private loggedIn:boolean=false;

  constructor(private loginService:LoginService,private router: Router) { 

  }

  onSubmit(){
    this.loginService.sendCredentials(this.credentials.username,this.credentials.password).
    subscribe(res => {
            console.log(res);
            console.log(res.json());
            localStorage.setItem("xAuthToken", res.json().token);
            this.loggedIn=true;
            this.loginService.checkSessionInNavBar.next();
            this.router.navigate(['/login']);
          },
          error => {
            console.log(error);
            this.loggedIn=false;
          }
      );
  
  
  }
  
  ngOnInit() {
    this.loginService.checkSession().subscribe(
      res=>{
        this.loggedIn=true;
      },
      error=>{
        console.log(error);
        this.loggedIn=false;
      }
    );
  }

}
