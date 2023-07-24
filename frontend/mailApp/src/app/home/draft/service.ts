import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { observable, Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable( {providedIn:"root"})
export class DraftService {
  constructor(private http: HttpClient) { }

  sendNew(object:mail){
      return this.http.post<boolean>('http://localhost:8080/mail/mail',object,{responseType:'json'});
  }
  sendOld(object:mail){
    return this.http.post<boolean>('http://localhost:8080/mail/sendDraft',object,{responseType:'json'});
  }

  saveNew(object:mail){
    return this.http.post<boolean>('http://localhost:8080/mail/draftt',object,{responseType:'json'});
  }

  saveOld(object:mail){
    return this.http.post<boolean>('http://localhost:8080/mail/editDraft',object,{responseType:'json'}); 
  }

  loadDraft(id:string,index:number){
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
