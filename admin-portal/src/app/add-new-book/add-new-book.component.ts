import { Component, OnInit } from '@angular/core';
import { AddBookService } from '../add-book.service';
import { Book } from '../book';
import { UploadServiceService } from '../upload-service.service';

@Component({
  selector: 'app-add-new-book',
  templateUrl: './add-new-book.component.html',
  styleUrls: ['./add-new-book.component.css']
})
export class AddNewBookComponent implements OnInit {

  private newBook:Book=new Book();
  private bookAdded:boolean;

  constructor(private addBookServcice:AddBookService,private uploadServiceService:UploadServiceService) { }

  onSubmit(){

    this.addBookServcice.addBook(this.newBook).subscribe(
      res=>{
        this.uploadServiceService.upload(JSON.parse(JSON.parse(JSON.stringify(res))._body).id);
        this.bookAdded=true;
        this.newBook=new Book();
        this.newBook.active=true;
        this.newBook.format="paperback";
        this.newBook.language="english";
        this.newBook.category="Management";
      },
      error=>{
        console.log(error);
      }
    );

  }

  ngOnInit() {
    this.bookAdded=false;
    this.newBook.active=true;
    this.newBook.format="paperback";
    this.newBook.language="english";
    this.newBook.category="Management";

  }

}
