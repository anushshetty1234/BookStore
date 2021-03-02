import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BookService } from '../book.service';
import { LoginService } from '../login.service';
import { Book } from '../models/book';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  private loggedIn=false;
  private keyword:string="";
  private bookList:Book[] = new Array();

  constructor(private loginService:LoginService,private bookService:BookService,private route:Router) { 
    this.loginService.checkSessionInNavBar.subscribe(
      ()=>{
        this.loginService.checkSession().subscribe(
          res=>{
            this.loggedIn=true;
          },
          error=>{
            console.log(error);
          }
        );
      }
    );
  }


  logout(){
    this.loginService.logout().subscribe(
      res => {
        location.reload();
      },
      error =>{
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
      }
    );
  }


  onSearchByTitle(){
    console.log(this.keyword.trim().length>0);
    if(this.keyword.trim().length >0){
    this.bookService.searchWithKeyword(this.keyword.trim()).subscribe(
      res=>{
        this.bookList = res.json();
        if(this.bookList.length!=null && this.bookList.length >0 ){
          if(this.bookList.length == 1){
              this.route.navigate(['/viewBook',this.bookList[0].id]);
          }
          else{
              this.route.navigate(['/bookList'],{queryParams:{searchFilter:this.keyword.trim()}});
          }
        }
      },
      error=>{
        console.log(error);
      }
    );
    }
  }



}
