import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppConst } from '../constants/app-const';
import { Book } from '../models/book';
import { CartItem } from '../models/cart-item';
import { ShoppingCart } from '../models/shopping-cart';
import { ShoppingCartService } from '../shopping-cart.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {

	private serverPath = AppConst.serverPath;
	private selectedBook: Book;
	private cartItemList: CartItem[] = [];
	private cartItemNumber: number;
	private shoppingCart: ShoppingCart = new ShoppingCart();
	private cartItemUpdated: boolean;
	private emptyCart: boolean;
	private notEnoughStock: boolean;

  constructor(private shoppingCartService:ShoppingCartService,private router:Router) { }

  ngOnInit() {
    this.cartItemUpdated = false;
    this.notEnoughStock = false;
    this.getCartItems();
    this.getShoppingCart();
    
  }

  onSelect(book:Book){
	  this.selectedBook=book;
    this.router.navigate(['/viewBook',book.id]);
  }

  onRemoveCartItem(cartItem:CartItem){
    this.shoppingCartService.removeCartItem(cartItem.id).subscribe(
      res=>{
        this.getCartItems();
        this.getShoppingCart();
      },
      error=>{
        console.log(error);
      }
    );
  }

  onUpdateCartItem(cartItem:CartItem){
    this.shoppingCartService.updateCartItem(cartItem.id,cartItem.qty).subscribe(
      res=>{
        this.getCartItems();
        this.getShoppingCart();
        this.cartItemUpdated = true;
      },
      error=>{
        this.cartItemUpdated = false;
        console.log(error);
      }
    );
  }

  getCartItems(){
    this.shoppingCartService.getCartItems().subscribe(
      res=>{
          this.cartItemList = res.json();
          this.cartItemNumber = this.cartItemList.length;
          if(this.cartItemNumber == 0){
            this.emptyCart = true;
          }
          for(let i=0;i<this.cartItemList.length;i++){
            if((this.cartItemList[i].book.inStockNumber <= 0) || (this.cartItemList[i].book.inStockNumber < this.cartItemList[i].qty)){
              this.notEnoughStock = true;
            }
            else{
              this.notEnoughStock = false;
            }
          }
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



}
