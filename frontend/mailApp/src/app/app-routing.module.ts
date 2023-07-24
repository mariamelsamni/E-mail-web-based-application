import { HttpFeatureKind } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DisplayMessageComponent } from './home/display-message/display-message.component';
import { DraftComponent } from './home/draft/draft.component';
import { HomeComponent } from './home/home.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { SignUpComponent } from './sign-up/sign-up.component';

const routes: Routes = [
  {path: '', redirectTo: '/sign-in' , pathMatch: 'full'},
  {
    path:'home' , 
    component: HomeComponent,
    children: [
      {path: 'draft', component: DraftComponent},
      {path: 'displayMessage', component: DisplayMessageComponent}
    ]
  },
  {path: 'sign-in', component: SignInComponent},
  {path: 'sign-up', component: SignUpComponent},
  {path: '**' , component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [HomeComponent,SignInComponent,SignUpComponent,PageNotFoundComponent]
 