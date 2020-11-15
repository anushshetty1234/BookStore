import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

@Injectable()
export class GetBookService {

  constructor(private http:Http) { }

  getBookById(id:number){
    let url = "http://localhost:8181/book/"+id;
    let header = new Headers({
      'Content-Type':  "application/json",
      'X-Auth-Token': localStorage.getItem('xAuthToken')
    });
    return this.http.get(url,{headers:header});
  }

}
