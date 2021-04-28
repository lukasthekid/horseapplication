import { Component, OnInit } from '@angular/core';
import { HorseComponent } from '../horse/horse.component';

@Component({
  selector: 'app-detailed-view',
  templateUrl: './detailed-view.component.html',
  styleUrls: ['./detailed-view.component.scss']
})
export class DetailedViewComponent implements OnInit {

  constructor(private horseComponent: HorseComponent) { }

  ngOnInit(): void {
    
  }

}
