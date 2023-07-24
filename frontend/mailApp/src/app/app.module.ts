import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule, routingComponents } from './app-routing.module';
import { AppComponent } from './app.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { DraftComponent } from './home/draft/draft.component';
import { DisplayMessageComponent } from './home/display-message/display-message.component';
import { SideBarComponent } from './home/side-bar/side-bar.component';
import { MessagesComponent } from './home/messages/messages.component';
import { SignInComponent } from './sign-in/sign-in.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { SignUpService } from './sign-up/service';



@NgModule({
  declarations: [
    AppComponent,
    routingComponents,
    SignInComponent,
    SignUpComponent,
    PageNotFoundComponent,
    DraftComponent,
    DisplayMessageComponent,
    SideBarComponent,
    MessagesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
