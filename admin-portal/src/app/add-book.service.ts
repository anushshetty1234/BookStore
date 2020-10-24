import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Book } from './book';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

@Injectable()
export class AddBookService {

  constructor(private http:Http) { }

  addBook(book: Book) {
    let url = "http://localhost:8181/book/add";

    let header = new Headers({
      'Content-Type':  "application/json",
      'X-Auth-Token': localStorage.getItem('xAuthToken')
    });

    return this.http.post(url,JSON.stringify(book),{headers:header});

  }

}
