import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { DraftService,mail } from './service';

@Component({
  selector: 'app-draft',
  templateUrl: './draft.component.html',
  styleUrls: ['./draft.component.css']
})
export class DraftComponent implements OnInit{
  constructor(private router: Router , private route: ActivatedRoute,private service: DraftService){ }
  index:any
  id:any
  to:any 
  message:any
  subject:any 
  attachs:string[] = []
  importance = 1;
  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      if(params.get('id') != null){
        this.id = params.get('id');
      }
      if(params.get('index') != null){
        this.index = params.get('index');
        console.log("draftt",this.id,this.index);
        //hna nktb functionnn al display draft swa2 message gdida aw adima 
        //if index = -1 (gdida)
        //else adima hatha mn al back
        if(this.index==-1){
          this.to = ""
          this.message = ""
          this.subject = ""
          this.attachs.splice(0,this.attachs.length)
          this.importance = 1
        }
        else{
          //call the back function
          this.service.loadDraft(this.id,this.index).subscribe(res => {
            this.to = res.receivers.join(",")
            this.message = res.body
            this.subject = res.subject
            this.attachs = res.attachment
            this.importance = res.importance
          })
        }
      }
    })
    
  }


  attach(){
    let x = window.prompt("enter the file path");
    if(!(x==null || x=="")){
        this.attachs.push(String(x))
    }
  }
  del(att:string){
    if(this.attachs.includes(att)){
      this.attachs.splice(this.attachs.indexOf(att),1)
    }
  }
  setImp(num:number){
      this.importance = num;
  }
  //done
  openAttach(attach:string){
    this.service.openAttach(attach).subscribe(res => {
      console.log("open atach");
      if(res == false){
        window.alert("file path is wrong");
      }
    })
}




  //done
  save(){
    //send to back to save to attachments
    let obj : mail = {
      subject : (document.getElementById("subject") as HTMLInputElement).value,
      importance : this.importance,
      idCode: this.id,
      receivers : [(document.getElementById("to") as HTMLInputElement).value],
      body : (document.getElementById("message") as HTMLInputElement).value,
      attachment : this.attachs,
      read : false,
      index:-1,
      type:"",
      sender:"",
      senderUsername:"",
      receiversUsername:[""],
      deletionDate:"",
      date:""
    }
    if(this.index==-1){
      
      this.service.saveNew(obj).subscribe(res => {
        if(res == true){
          this.router.navigate(['/home',{id: this.id}]);
          window.alert("saved");
        }
        else{
          window.alert("Error");
        }
      })
    }
    else{
      obj.index = this.index;
      this.service.saveOld(obj).subscribe(res => {
        if(res == true){
          this.router.navigate(['/home',{id: this.id}]);
          window.alert("saved");
        }
        else{
          window.alert("Error");
        }
      });
  }
  
}


  //done
  send(){
    //send to the the other user
    let obj : mail = {
      subject : (document.getElementById("subject") as HTMLInputElement).value,
      importance : this.importance,
      idCode: this.id,
      receivers : ((document.getElementById("to") as HTMLInputElement).value).split(","),
      body : (document.getElementById("message") as HTMLInputElement).value,
      attachment : this.attachs,
      read : false,
      index:-1,
      type:"",
      sender:"",
      senderUsername:"",
      receiversUsername:[""],
      deletionDate:"",
      date:""
    }
    if(this.index==-1){
      
      this.service.sendNew(obj).subscribe(res => {
        if(res == true){
          this.router.navigate(['/home',{id: this.id}]);
          window.alert("sent");
        }
        else{
          window.alert("Error");
        }
      })
    }
    else{
        obj.index = this.index;
        this.service.sendOld(obj).subscribe(res => {
          if(res == true){
            this.router.navigate(['/home',{id: this.id}]);
            window.alert("sent");
          }
          else{
            window.alert("Error");
          }
        });
    }
  }



}
