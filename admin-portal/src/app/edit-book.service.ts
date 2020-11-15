import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import { Book } from './book';

@Injectable()
export class EditBookService {

  constructor(private http:Http ) { }

  updateBook(book: Book){

    let url = "http://localhost:8181/book/update";

    let header= new Headers({
      'Content-Type':  "application/json",
      'X-Auth-Token': localStorage.getItem('xAuthToken')
    });

    return this.http.post(url,JSON.stringify(book),{headers:header});
  }

}
