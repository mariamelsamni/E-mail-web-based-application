import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { observable, Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable( {providedIn:"root"})
export class MessagesService {
  constructor(private http: HttpClient) { }


  //url = 'http://localhost:8080/mail';
  getMailSummary(id:string,folder:string){
    return this.http.get<mailSummary[]>('http://localhost:8080/mail/requestMailsFolder',{params:{idCode:id,folder:folder},responseType:'json'});
  }

  getSortOptions(id:string){
    return this.http.get<string[]>('http://localhost:8080/mail/requestSort',{params:{idCode:id},responseType:'json'});
  }

  sort(id:string,sortOption:string){
    return this.http.get<mailSummary[]>('http://localhost:8080/mail/sort',{params:{idCode:id,sortType:sortOption},responseType:'json'})
  }

  search(id:string,key:string){
    return this.http.get<mailSummary[]>('http://localhost:8080/mail/search',{params:{idCode:id,searchWord:key},responseType:'json'})
  }

  delete(object:obj){
    return this.http.post<boolean>('http://localhost:8080/mail/delete',object,{responseType:'json'})
  }
  getSubjectFilter(id:string){
    return this.http.get<string[]>('http://localhost:8080/mail/subjects',{params:{idCode:id},responseType:'json'})
  }

  getContactFilter(id:string){
    return this.http.get<string[]>('http://localhost:8080/mail/filter/senders',{params:{idCode:id},responseType:'json'})
  }

  makeFilter(id:string,details:filterDetails){
    return this.http.post<mailSummary[]>('http://localhost:8080/mail/filter',details,{params:{idCode:id},responseType:'json'})
  }
  move(obj:moveObj){
    return this.http.post<boolean>('http://localhost:8080/mail/move',obj,{responseType:'json'})
  }
  getMoveTo(id:string){
    return this.http.get<string[]>('http://localhost:8080/mail/moveToFolders',{params:{idCode:id},responseType:'json'})
  }
  markRead(object:obj){
    return this.http.post<boolean>('http://localhost:8080/mail/setMailsRead',object,{responseType:'json'})
  }
 }
 export interface mailSummary{
    name:string[];
    bodySummary: string;
    subject: string;
    read: boolean;
 }
 export interface messages{
    from:string;
    body:string;
    read:boolean;
    subject:string;
 }
 export interface obj{
    idCode: string;
    indices: number[];
    read:boolean;
 }
 export interface filterDetails{
    categories: string[],
    subjects: string[],
    senders: string[]
 }
 export interface moveObj{
    idCode:string,
    toFolder: string,
    indices:number[]
 }