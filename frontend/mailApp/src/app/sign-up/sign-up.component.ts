import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { SignUpService,obj } from './service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent {
  constructor(private router: Router,private service: SignUpService) { }
  
  public username: any 
  public password: any
  public email: any
  public id: any
  
  signUp(){
    this.username = (document.getElementById('username') as HTMLInputElement).value;
    this.password = (document.getElementById('password') as HTMLInputElement).value;
    this.email = (document.getElementById('email') as HTMLInputElement).value;
    console.log(this.username,this.email,this.password)
    //run correctly
    // badal al if statement hab3at ll back end
    if(this.email != null && this.password!=null && this.username!=null){
      //eb3t ll back
      let form : obj = {
        username: this.username,
        password: this.password,
        email: this.email
      }
      this.service.signUpgetId(form)
      .subscribe((data) => 
      {
        console.log("sign up",data);
        if(data[0]=="false"){
          window.alert("this username 've been used");
        }
        else{
          this.id = data[0];
          this.username = data[1]
          console.log(this.id,this.username);
          // this.router.navigateByUrl("/home");
          this.router.navigate(['/home',{id: this.id,usename: this.username}]);
          console.log("navigateeeeeeeeeeee");
        }
        
      })       
    }   
  }
}

