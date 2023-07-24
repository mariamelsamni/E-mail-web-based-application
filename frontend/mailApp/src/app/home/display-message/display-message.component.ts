import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { DisplayMessageService } from './service';

@Component({
  selector: 'app-display-message',
  templateUrl: './display-message.component.html',
  styleUrls: ['./display-message.component.css']
})
export class DisplayMessageComponent implements OnInit {
  constructor(private router: Router , private route: ActivatedRoute,private service: DisplayMessageService){ }
  index: any
  id: any
  from : any
  to:any
  subject :any
  message :any
  date :any
  attachs:string[] = []
  importance:any

  // index:any
  // id:any
  // from = "ayman"
  // to= "maro"
  // subject ="ay 7aga"
  // message ="how are you how are you how are you how are you how are you how are you how are you how are you how are you how are you how are you how are you how are you how are you how are youhow are you how are you how are you how are you how are you"
  // date ="17/11/1980"
  // attachs = ["helloo","world","marshelino"]
  // importance=3

  ngOnInit() {
    this.route.paramMap.subscribe((params: ParamMap) => {
      if(params.get('id') != null){
        this.id = params.get('id')
      }
      if(params.get('index') != null){
        this.index = params.get('index');
        console.log("display message",this.id,this.index);
        //hna nb3t ll back ngib al message 34an n3mlha display
        this.service.displayMessage(this.id,this.index).subscribe(res => {
          this.from = res.sender
          this.to = res.receivers.join(",");
          this.subject = res.subject
          this.message = res.body
          this.date = res.date
          this.attachs = res.attachment
          this.importance = res.importance
        })
      }
      
    })
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


  
}
