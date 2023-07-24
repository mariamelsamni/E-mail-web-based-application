import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { observable, Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable( {providedIn:"root"})
export class SignInService {
  constructor(private http: HttpClient) { }
  url = 'http://localhost:8080/mail/login';
  signIngetId(form:obj){
    return this.http.post<string[]>(`${this.url}`,form,{responseType:'json'});
  }
 }
 export interface obj{
    username: string;
    email: string;
    password: string;
    id: string;
 }