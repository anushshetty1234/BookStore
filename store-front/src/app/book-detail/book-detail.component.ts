import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BookService } from '../book.service';
import { AppConst } from '../constants/app-const';
import { Book } from '../models/book';
import { ShoppingCartService } from '../shopping-cart.service';

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {

  private bookId: number;
	private book: Book = new Book();
	private serverPath = AppConst.serverPath;
	private numberList: number[] = [1,2,3,4,5,6,7,8,9];
	private qty: number;

	private addBookSuccess: boolean = false;
	private notEnoughStock:boolean = false;

  constructor(private boookService:BookService,private activatedRoute:ActivatedRoute
              ,private shoppingCartService:ShoppingCartService) { }

  ngOnInit() {
    this.activatedRoute.params.subscribe(
      params=>{
        this.bookId = +params['id'];
      }
    );
    this.boookService.getBookById(this.bookId).subscribe(
      res=>{
          this.book = res.json();
          let currentStock = this.book.inStockNumber;
          if(!(currentStock >0)){
            this.notEnoughStock = true;
          }
      },
      error=>{
        console.log(error);
      }
    );
    this.qty=1;
  }


  onAddToCart(){
      this.shoppingCartService.addCartItem(this.bookId,this.qty).subscribe(
        res=>{
            this.addBookSuccess = true;
        },
        error=>{
            this.addBookSuccess = false;
            console.log(error);
        }
      );
  }

}
