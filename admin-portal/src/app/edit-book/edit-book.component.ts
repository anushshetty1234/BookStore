import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Route, Router } from '@angular/router';
import { Book } from '../book';
import { EditBookService } from '../edit-book.service';
import { GetBookService } from '../get-book.service';
import { UploadServiceService } from '../upload-service.service';

@Component({
  selector: 'app-edit-book',
  templateUrl: './edit-book.component.html',
  styleUrls: ['./edit-book.component.css']
})
export class EditBookComponent implements OnInit {

   private book=new Book();
   private bookId:number;  
   private bookUpdated:boolean;
  
  constructor(private activatedRoute:ActivatedRoute,private route :Router,
               private getBookService:GetBookService,private editBookService:EditBookService,
               private uploadImageSerice:UploadServiceService) { }


  ngOnInit() {
    this.activatedRoute.params.forEach((param:Params) => {
        this.bookId = Number.parseInt(param['id']);
    });

    this.getBookService.getBookById(this.bookId).subscribe(
      response=>{
          this.book=response.json();
          console.log(response.json());
      },
      error=>{
        console.log(error);
      }
    );
  }

  onSubmit(){
      this.editBookService.updateBook(this.book).subscribe(
        response => {
          this.uploadImageSerice.modifyImage(this.bookId);
          this.bookUpdated=true;
        },
        error=>{
          console.log(error);
        }
      );
  }

  


}
