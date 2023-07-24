import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { observable, Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable( {providedIn:"root"})
export class DisplayMessageService {
  constructor(private http: HttpClient) { }

  url = 'http://localhost:8080/mail/login';

  displayMessage(id:string,index:number){
    return this.http.get<mail>('http://localhost:8080/mail/selectMail',{params:{idCode:id,messageID:index},responseType:'json'}); 
  }

  openAttach(path:string){
    return this.http.get<boolean>('http://localhost:8080/mail/openAttach',{params:{path:path},responseType:'json'}); 
  }
 }
 export interface mail{
  subject:string
  importance:number
  idCode:string
  receivers :string[]
  body:string
  attachment:string[]
  read : boolean
  index:number
  type:string
  sender:string
  senderUsername:string
  receiversUsername:string[]
  deletionDate:string
  date:string

}