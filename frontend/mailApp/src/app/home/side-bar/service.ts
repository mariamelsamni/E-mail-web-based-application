import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { observable, Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';

@Injectable( {providedIn:"root"})
export class SideBarService {
  constructor(private http: HttpClient) { }
//   url = 'http://localhost:8080/mail/requestContactsFolder';

addFolder(idCode:number,folderName:string){
  return this.http.get<boolean>('http://localhost:8080/mail/newFolder',{params:{idCode:String(idCode),folder:folderName}})
}
getContacts(id:string){
  return this.http.get<string[]>('http://localhost:8080/mail/requestContactsFolder',{params:{idCode:String(id)},responseType:'json'});
}

addContact(contact:contact){
  console.log("contact\n",contact);
  return this.http.post<boolean>('http://localhost:8080/mail/addContact',contact)
}

getEmails(index:number,id:string){
  return this.http.get<string[]>('http://localhost:8080/mail/requestContact',{params:{idCode:String(id),index:index},responseType:'json'})
}

setDefault(id:string,index:number,defaultEmail:string){
  return this.http.get<boolean>('http://localhost:8080/mail/defaultEmail',{params:{idCode:String(id),index:index,defaultEmail:defaultEmail}})
}
getFolders(id:string){
  return this.http.get<string[]>('http://localhost:8080/mail/requestFolders',{params:{idCode:String(id)},responseType:'json'});
}
deleteContacts(index:number,id:string){
  return this.http.get<boolean>('http://localhost:8080/mail/deleteContact',{params:{idCode:String(id),index:index}});
}

logOut(id:string){
  return this.http.get<boolean>('http://localhost:8080/mail/logout',{params:{idCode:String(id)},responseType:'json'});
}
}
 
 export interface contact{
    nickName: string;
    defaultEmail: string;
    emails: string[];
    idCode: string
}