import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

@Injectable()
export class RemoveBookService {

  constructor(private http:Http) { }

  removeBook(bookid:number){

    let url="http://localhost:8181/book/remove";

    let header=new Headers({
      'Content-Type':  "application/json",
      'X-Auth-Token': localStorage.getItem('xAuthToken')
    });

    return this.http.post(url,bookid,{headers:header});

  }

}
