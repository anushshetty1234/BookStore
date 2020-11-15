import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Book } from '../book';
import { GetBookService } from '../get-book.service';

@Component({
  selector: 'app-view-book',
  templateUrl: './view-book.component.html',
  styleUrls: ['./view-book.component.css']
})
export class ViewBookComponent implements OnInit {

  private book:Book=new Book();
  private bookId:number;

  constructor(private http:Http,private routeActivated:ActivatedRoute,private router:Router,private getBook:GetBookService) { }



  ngOnInit() {
    this.routeActivated.params.forEach((params:Params)=>{
      this.bookId=Number.parseInt(params['id']);
    });

    this.getBook.getBookById(this.bookId).subscribe(
      res=>{
        this.book=res.json();
      },
      error=>{
        console.log(error);
      }
    );
  }

  onSelect(editBook :Book){

    this.router.navigate(['/editBook',editBook.id]);

  }



}
