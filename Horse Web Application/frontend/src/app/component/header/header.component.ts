import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Horse } from 'src/app/dto/horse';
import { HorseService } from 'src/app/service/horse.service';
import { SportService } from 'src/app/service/sport.service';
import { HorseComponent } from '../horse/horse.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  showHorses = false;
  showSports = false;
  

  constructor(private horseService: HorseService,
    private SportService: SportService) {
  }

  ngOnInit() {
    
  }

  public showingSports(){
    this.showSports = !this.showSports;
  }
  public showingHorses(){
    this.showHorses = !this.showHorses;
  }

  onOpenModal(horse: Horse, mode: String): void{
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    
  }

  onAddHorse(addForm: NgForm): void{
    this.horseService.createHorse(addForm.value).subscribe(
      (response: Horse) => {
        console.log(response);
      },
      error => {
        alert(error.message);
      }
    )
  }

 
}
