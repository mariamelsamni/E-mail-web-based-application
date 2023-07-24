import { BoundElementProperty } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { SideBarService,contact } from './service';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit{
  constructor(private router: Router , private route: ActivatedRoute,private service: SideBarService){ }
  id:any
  user:any
  folders:string[] = [];
  contacts:string[] = []

  emails:string[] = []

  //done
  ngOnInit() {
    console.log("sidebar")
    this.route.paramMap.subscribe((params: ParamMap) => {
      if(params.get('id') != null){
        this.id = params.get('id');
      }
      if(params.get('username') != null){
        this.user = params.get('username');
      }
    })
    console.log(this.id)
    //get all the folders names
    //m4 la2ihaaa f al backkkk
    this.service.getFolders(this.id).subscribe(body=>{
      this.folders.splice(0,this.folders.length);
      console.log("getFolders",body);
      for(let folder of body){
        this.folders.push(folder);
      }
    })
    //get all the contacts names
    this.service.getContacts(this.id).subscribe(body => {
      console.log("get contacts",body);
      for(let contact of body){
        this.contacts.push(contact);
      }
    })
  }

  //done
  navigateTo(){
    this.router.navigate(['/home/draft',{id: this.id,index: -1}])
    
  }

  //done
  folderClicked(folder: any){
    //dayman lma tdos 3la ay 7aga trg3 hna al awl
    this.router.navigate(['/home',{id: this.id,folder: folder}])
  }

  //done
  addFolder(){
    let name:any = (window.prompt("input the file name"));
    if(name!="" && this.folders.indexOf(name)==-1){
      this.folders.push(name);
      this.service.addFolder(this.id,name).subscribe(res => {
        console.log("add new folder : "+"name",res)
      })
    }

  }

  

  
  //done
  addContact(){
    let nickname = window.prompt("input Nickname");
    let emails:string[] = [];
    let defaultEmail:string;
    let input:string;
    if(!(nickname=="" || nickname==null)){
      defaultEmail = String (window.prompt("input the default email"));
      if(!(defaultEmail=="" || defaultEmail==null)){
        input = String(window.prompt("input addition email or click ok without type any thing"));
        while(!(input=="" || input==null)){
          emails.push(input);
          input = String(window.prompt("input addition email or click ok without type any thing"));
        }
        
        console.log(nickname,defaultEmail,emails);
        //send to back to create the contact
        //add contact to the backkkk
        let contact:contact = {
          nickName: nickname,
          defaultEmail: defaultEmail,
          idCode: this.id,
          emails: emails
        }
        this.service.addContact(contact)
        .subscribe(body => {
          if(body)
          {
            this.contacts.push(contact.nickName);
          }
          console.log(body);
        })    
      }
    }
  }


  



  //done
  dropDown(index:any) {
    // eb3t ll back al index of contact 34an ydilk al emails
    this.service.getEmails(index,this.id)
    .subscribe(res => {
      console.log("get emails",res);
      this.emails = res;
      document.getElementById("drop"+index)?.classList.toggle("show");
    })  
  }


  //done
  emailsFunction(defaultEmail:string,contactIndex:number){
    //eb3at ll back al indexes and they will set the default email
    this.service.setDefault(this.id,contactIndex,defaultEmail)
    .subscribe(res => {
      console.log("setDefault email",res);
    })
    console.log(defaultEmail,contactIndex);
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }


//lsaaaaaaaaaaaaaaaaaaaaaaa
deleteContact(index: any){
  //send al index ll back
  this.service.deleteContacts(index,this.id).subscribe(body=>{
    console.log(body);
  });
  this.contacts.splice(index,1);

}

logOut(){
  // this.service.logOut(this.id).subscribe(res => {
    // if(res==true){
      this.router.navigate(['/sign-in']);
    // }
  // })
}

}

