import { Component } from '@angular/core';
import { Route, Router } from '@angular/router';
import { SignInService,obj } from './service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent {
  constructor(private router: Router,private service: SignInService) { }

  public username: any
  public password: any
  public id: any
  
  signIn(){
    this.username = (document.getElementById('username') as HTMLInputElement).value;
    this.password = (document.getElementById('password') as HTMLInputElement).value;
    console.log(this.username,this.password)
    //run correctly
    // badal al if statement hab3at ll back end 
    if(this.username != null && this.password != null){
      // we can send alot of objects and attributes but we will not send the password
      // this.router.navigate(['/home',{username: this.username , password: this.password}])
      let form: obj = {
          username: this.username,
          email: this.username,
          password: this.password,
          id: this.id
      }
      this.service.signIngetId(form)
      .subscribe((body) => {
          if(body[0]=="false"){
            window.alert("this email or password are wrong")
          }
          else{
            this.id = body[0];
            this.username = body[1]
            this.router.navigate(['/home',{id: this.id,username:this.username}])
            console.log("navigateeeeeeeeeeee")
          }     
      })
    }
  }

}
