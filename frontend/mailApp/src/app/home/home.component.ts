import { Component, OnInit } from '@angular/core';
import { Router,ParamMap, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router , private route: ActivatedRoute){ }

  id: any

  
  ngOnInit(){
    this.route.paramMap.subscribe((params: ParamMap) => {
      this.id = params.get('id');
    })
  }

}
