import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../book.service';
import { AppConst } from '../constants/app-const';
import { Book } from '../models/book';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  private serverPath = AppConst.serverPath;

  private filterQuery : String = "";
  private rowsOnPage : number = 5;


  private bookList:Book[];
  private checked:boolean;
  private checkedAll:boolean;
  private selectedBook:Book;
  private removeBookList:Book[]=new Array();

  constructor(private boookService:BookService,private route:Router,private activatedRoute:ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.queryParams.subscribe(
      params=>{ 
        this.filterQuery = params['searchFilter'];
      }
    );
    this.getBookList();
  }

  onSelect(book:Book){
    this.selectedBook = book;
    this.route.navigate(['/viewBook',book.id]);
  }

  getBookList(){
    this.boookService.getBookList().subscribe(
      res=>{
        console.log(res.json());
        this.bookList = res.json();
      },
      error=>{
        console.log(error);
      }
    );
  }

}
