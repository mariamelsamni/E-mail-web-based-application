import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable( {providedIn:"root"})
export class SignUpService {
  constructor(private http: HttpClient) { }
  url = 'http://localhost:8080/mail/signup';
  signUpgetId(form:obj){
    return this.http.post<string[]>(`${this.url}`,form,{responseType:'json'});
  }
 }
 export interface obj{
    username: string;
    email: string;
    password: string;
 }
