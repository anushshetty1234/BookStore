import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookDetailComponent } from './book-detail/book-detail.component';
import { BookListComponent } from './book-list/book-list.component';
import { HomeComponent } from './home/home.component';
import { MyAccountComponent } from './my-account/my-account.component';
import { MyProfileComponent } from './my-profile/my-profile.component';


const routes: Routes = [
  { path: '' , redirectTo: '/home' , pathMatch: 'full'  },
  { path: 'home', component: HomeComponent },
  { path : 'myAccount' , component: MyAccountComponent},
  { path : 'myProfile' , component: MyProfileComponent},
  { path : 'bookList' , component: BookListComponent},
  { path : 'viewBook/:id' , component: BookDetailComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }