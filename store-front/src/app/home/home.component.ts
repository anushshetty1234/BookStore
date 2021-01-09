import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private loginService:LoginService) { }

  ngOnInit() {
    // this.loginService.checkSession().subscribe(
    //   res => {
    //     this.loggedIn = true;
    //   },
    //   error =>{
    //     this.loggedIn = false;
    //   }
    // );
  }

}
