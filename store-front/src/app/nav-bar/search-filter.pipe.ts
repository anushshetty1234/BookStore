import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'searchFilter'
})
export class SearchFilterPipe implements PipeTransform {

  transform(items: any[], keyword:string): any[] {
    if (!items) {
      return [];
    }
    if (!keyword) {
      return items;
    }
    keyword = keyword.toLocaleLowerCase();

    return items.filter(it => {
      return it.toLocaleLowerCase().includes(keyword);
    });
  }

  
  }
