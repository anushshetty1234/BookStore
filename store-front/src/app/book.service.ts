import { Injectable } from '@angular/core';
import { Headers, Http, URLSearchParams } from '@angular/http';
import { AppConst } from './constants/app-const';

@Injectable()
export class BookService {

  private serverPath = AppConst.serverPath;

  constructor(private http:Http) { }

  getBookList(){
    let url = this.serverPath+'/book/bookList';

    let header=new Headers({
      "Content-Type":  "application/json",
    });

    return this.http.get(url,{headers:header});
  }


  getBookById(id:number){
    let url = this.serverPath+'/book/'+id;

    let header=new Headers({
      "Content-Type":  "application/json",
    });

    return this.http.get(url,{headers:header});
  }

  searchWithKeyword(keyword:string){
    let url = this.serverPath+'/book/search?keyword='+keyword;

    let header=new Headers({
      "Content-Type":  "application/json",
    });

     return this.http.get(url,{headers:header});
  }

}
