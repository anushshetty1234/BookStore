import { Component, OnInit } from '@angular/core';
import { LoginService } from "./../login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

 private credentials={"username":"","password":""};
 private loggedIn:boolean=false;

  constructor(private loginService:LoginService) { }

  onSubmit(){
    this.loginService.sendCredentials(this.credentials.username,this.credentials.password).
    subscribe(res => {
            console.log(res);
            console.log(res.json());
            localStorage.setItem("xAuthToken", res.json().token);
            this.loggedIn=true;
            //location.reload();
          },
          error => {
            console.log(error);
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
      }
    );
  }

}
