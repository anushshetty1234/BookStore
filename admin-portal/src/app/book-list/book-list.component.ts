import { Component, OnInit } from '@angular/core';
import { MdDialog, MdDialogRef } from '@angular/material';
import { Router } from '@angular/router';
import { Book } from '../book';
import { GetBookListService } from '../get-book-list.service';
import { RemoveBookService } from '../remove-book.service';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {


  private bookList:Book[];
  private checked:boolean;
  private checkedAll:boolean;
  private selectedBook:Book;
  private removeBookList:Book[]=new Array();

  constructor(private getBookListService:GetBookListService,private router:Router,
              private dialog:MdDialog,private removeBookService:RemoveBookService) { }

  onSelect(book:Book){
      this.selectedBook=book;
      this.router.navigate(['/viewBook',this.selectedBook.id]);

  }

  ngOnInit() {
    this.getBookList();
  }

  getBookList(){
    this.getBookListService.getBookList().subscribe(
      res=>{
        console.log(res.json());
        this.bookList=res.json();
      },
      error=>{
        console.log(error);
      }
    );
  }

  openDialog(book: Book) {
    let dialogref = this.dialog.open(DialogComponent);
    dialogref.afterClosed().subscribe(
      result => {
        console.log(result);
        if (result == "yes") {
          this.removeBookService.removeBook(book.id).subscribe(
            res => {
              console.log(res);
              this.getBookList();
            },
            error => {
              console.log(error);
            }
          );
        }
      },
      error => {
        console.log(error);
      }
    );
  }


  updateRemoveBookList(checked:boolean,book:Book){
    
    if(checked){
        this.removeBookList.push(book);
    }
    else{
        this.removeBookList.splice(this.removeBookList.indexOf(book),1);
    }

  }

  updateSelected(checked_All:boolean){
    if(checked_All){
      this.checkedAll=true;
      this.removeBookList = this.bookList.slice(); //never assign reference to another refernce,so use slice
    }
    else{
      this.checkedAll=false;
      this.removeBookList = [];
    }
  }

  removeSelectedBooks(){
    let dialogref = this.dialog.open(DialogComponent);
    dialogref.afterClosed().subscribe(
      result => {
        console.log(result);
        if (result == "yes") {
          for(let book of this.removeBookList){
          this.removeBookService.removeBook(book.id).subscribe(
            res => {
              console.log(res);
              this.getBookList();
            },
            error => {
              this.getBookList();
              console.log(error);
            }
          );
        }
       
        }
      },
      error => {
        console.log(error);
      }
    );
  }



}





@Component({
  selector: 'app-dialog',
  templateUrl: './app-dialog.html'
})
export class DialogComponent {

  constructor(private dialogRef:MdDialogRef<DialogComponent>) { }
}