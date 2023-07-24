import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { MessagesService,mailSummary,messages, obj, filterDetails,moveObj } from './service';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.css']
})
export class MessagesComponent implements OnInit{
  constructor(private router: Router , private route: ActivatedRoute,private service: MessagesService){ }
  messages: messages[] = [];
  folders :string[] = []
  selected : number[] = []
  key:string = "";
  id:any;
  folder: any
  flag: boolean = false
  // category = "Category";
  subjects:string[] = []
  contacts:string[] = []
  sortOptions:string[] = [];
  categoryFilter: string[] = [];
  subjectsFilter :string[] = [];
  contactsFilter :string[] = [];
  filterFlag: boolean = true;


  //done
  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      if(params.get('id') != null){
        this.id = params.get('id');
      }
      if(params.get('folder') != null){
        this.folder = params.get('folder');
        (document.getElementById('searchBox') as HTMLInputElement).value = "";
        this.flag = false;
        //7ot mkan al console al function aly bt3ml request ll mail summary
        this.service.getMailSummary(this.id,this.folder).subscribe(res => {
          
          
          this.messages.splice(0,this.messages.length);
          console.log("get mails summary",res);
          for (let message of res){
            // let m:messages;
            console.log(message)
            console.log(message.bodySummary,message.read,message.name)
            let m:messages = {
              body : message.bodySummary,
              from : String(message.name.join(",")),
              read : message.read,
              subject : message.subject
            }
            this.messages.push(m);
          }
          
          
        })
      }
    })
  }

  //done
  displayMessage(message:any){
    // eb3at al id w al index l class al display message
    // w hwa yeb3at request ll back 34an ygib al message b al id aw al index'
    this.flag = false;
    if(message.read==false){
      message.read = true;
    }
    if(this.folder=="drafts"){
      this.router.navigate(['/home/draft',{id: this.id, index: this.messages.indexOf(message)}]);
    }
    else{
      this.router.navigate(['/home/displayMessage',{id: this.id, index: this.messages.indexOf(message)}]);
    }  
  }

  //done
  search(){
    this.key = (document.getElementById("searchBox") as HTMLInputElement).value;
    if(this.key!=""){
      // eb3at ll back al key w al category homa hy3mlo search w yerg3olk al messages
      this.service.search(this.id,this.key).subscribe(res => {
        console.log("search for the word "+this.key);
        console.log(res);
        this.messages.splice(0,this.messages.length);
        for(let mess of res){
          let message:messages = {
            body:mess.bodySummary,
            read:mess.read,
            from: String(mess.name.join(",")),
            subject: mess.subject
          }
          this.messages.push(message);
        }
        console.log(this.key);
      })  
    }
  }

  //done
  refresh(){
    //make refresh function that send to the back (inbox,sent,search) and the back send us the new messages
    this.router.navigate(['/home']);
    this.key = "";
    this.flag = false;
    this.filterFlag = true;
    // this.category = "Category";
    (document.getElementById('searchBox') as HTMLInputElement).value = "";
    this.service.getMailSummary(this.id,this.folder).subscribe(res => {
      this.messages.splice(0,this.messages.length);
      for (let message of res){
        let m:messages = {
          body : message.bodySummary,
          from : String(message.name.join(",")),
          read : message.read,
          subject: message.subject
        }
        this.messages.push(m);
      }
      
      console.log("get mails summary",res);
    })
  }


  //toggle to select items or not
  //run correctly
  //done
  toggle(){
    if(this.flag){
      this.flag = false;
    }
    else{
      this.flag = true
      let itr = document.getElementsByClassName("checkboxes")
      for (let i = 0; i < itr.length; i++) {
        (itr[i] as HTMLInputElement).checked = false;
      }
      this.filterFlag = true;
      this.selected.splice(0,this.selected.length);
    }
  }

  //push items in selected[] list and pop them
  //run correctly
  //done
  checkBox(message:any){
    let index = this.selected.indexOf(message)
    if(index == -1){
      this.selected.push(message);
      console.log("set",index);
    }
    else{
      this.selected.splice(index,1);
      console.log("delete",index)
    }
    console.log(this.selected);
  }


  //done
  showSort() {
    this.service.getSortOptions(this.id).subscribe(res => {
      console.log("get sort options",res);
      this.sortOptions = res;
      document.getElementById("myDropdown0")?.classList.toggle("show");
    })
  }


  //done
  showCategory() {
    document.getElementById("myDropdown1")?.classList.toggle("show");
  }

  

  //done
  showSubjectFilter() {

    this.service.getSubjectFilter(this.id).subscribe(res => {
      console.log("get the filter subjects ",res)
      this.subjects = res;
      document.getElementById("myDropdown3")?.classList.toggle("show");

    })   
  }


//done
  showContactFilter() {

    this.service.getContactFilter(this.id).subscribe(res => {
      console.log("get the filter contacts ",res);
      this.contacts = res;
      document.getElementById("myDropdown4")?.classList.toggle("show");
    })  
  }

  //done
  sort(sortOption: string){
    //eb3at ll back al s
      this.service.sort(this.id,sortOption).subscribe(res => {
        console.log("make the sort with the option "+sortOption);
        console.log(res);
        this.messages.splice(0,this.messages.length);
        for(let summary of res){
          let message:messages = {
            body : summary.bodySummary,
            from : String(summary.name.join(",")),
            read : summary.read ,
            subject : summary.subject
          }
          this.messages.push(message);
        } 
      })
    console.log(sortOption);
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }

  //done
  categoryFunction(s: string){
    if(this.categoryFilter.includes(s)){
      this.categoryFilter.splice(this.categoryFilter.indexOf(s),1);
    }
    else{
      this.categoryFilter.push(s);
    }
    console.log(this.categoryFilter);


    // this.category = s;
    // var dropdowns = document.getElementsByClassName("dropdown-content");
    // var i;
    // for (i = 0; i < dropdowns.length; i++) {
    //   var openDropdown = dropdowns[i];
    //   if (openDropdown.classList.contains('show')) {
    //     openDropdown.classList.remove('show');
    //   }
    // }
  }

  

  //done
  subjectFunction(subject:string){
    console.log(subject);
    if(this.subjectsFilter.includes(subject)){
      this.subjectsFilter.splice(this.subjectsFilter.indexOf(subject),1);
    }
    else{
        this.subjectsFilter.push(subject);
    }
    console.log(this.subjectsFilter);
    // var dropdowns = document.getElementsByClassName("dropdown-content");
    //   var i;
    //   for (i = 0; i < dropdowns.length; i++) {
    //     var openDropdown = dropdowns[i];
    //     if (openDropdown.classList.contains('show')) {
    //       openDropdown.classList.remove('show');
    //     }
    //   }
  }

  //done
  contactFunction(contact:string){
    console.log(contact);
    if(this.contactsFilter.includes(contact)){
      this.contactsFilter.splice(this.contactsFilter.indexOf(contact),1);
    }
    else{
      this.contactsFilter.push(contact)
    }
    console.log(this.contactsFilter);

    
      // var dropdowns = document.getElementsByClassName("dropdown-content");
      // var i;
      // for (i = 0; i < dropdowns.length; i++) {
      //   var openDropdown = dropdowns[i];
      //   if (openDropdown.classList.contains('show')) {
      //     openDropdown.classList.remove('show');
      //   }
      // }
  }


  //done
  filterToggle(){
    if(this.filterFlag){
      this.filterFlag = false;
    }
    else{
      this.filterFlag = true;
    }
  }

  //done
  submitFilter(){
    let details:filterDetails = {
      senders :this.contactsFilter,
      categories : this.categoryFilter,
      subjects : this.subjectsFilter
    }
    this.service.makeFilter(this.id,details).subscribe(res => {
      this.messages.splice(0,this.messages.length)
      if(res!=null){
        for(let summ of res){
          let message:messages = {
            from: (String)(summ.name.join(",")),
            body: summ.bodySummary,
            read: summ.read,
            subject: summ.subject
          }
          this.messages.push(message);
        }
      }
    })
  }




  //done
  delete(){
    //m4 3arf kda al sort tmam 2la l2a 
    this.selected = this.selected.sort((a,b) => a-b);
    console.log("sorted array ",this.selected);
    let object:obj = {
      idCode: this.id,
      indices: this.selected,
      read:false
    }
    this.service.delete(object).subscribe(res => {
      console.log("delete indices ",this.selected);
      console.log("response ",res);
      
      //emsa7 mn dl front
      // for(let i of this.selected){
      //   console.log("masa77", i);
      //   this.selected = this.selected.splice(i,1);
      // }
      this.refresh();
      this.toggle();
    })
    
  }



//lsaaaaaaaaa
  read(){
    let object:obj = {
      idCode: this.id,
      indices: this.selected,
      read:true
    }
    this.service.markRead(object).subscribe(res=>{
      console.log(res);
    })
    this.refresh();
    this.toggle();
    console.log("read");
  }

//lsaaaaaaaaaa
  unread(){
    let object:obj = {
      idCode: this.id,
      indices: this.selected,
      read:false
    }
    this.service.markRead(object).subscribe(res=>{
      console.log(res);
    })
    this.refresh();
    this.toggle();
    console.log("read");
  }
  //done
  move(folder:string){
    console.log(folder);
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
    //send folder and selected to the back-end
    let obj:moveObj = {
        toFolder:folder,
        indices:this.selected,
        idCode: this.id
    }
    this.service.move(obj).subscribe(res => {
      console.log("move to folder ",res);
      this.toggle();
      this.refresh();
    })
}
//lsaaaaaaaaaaa
//get the folders name
showMoveTo() {
  this.service.getMoveTo(this.id).subscribe(res=>{
    this.folders=res;
  })
  document.getElementById("myDropdown2")?.classList.toggle("show");
} 
}
