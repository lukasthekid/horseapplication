import {Component, OnInit} from '@angular/core';
import {SportService} from '../../service/sport.service';
import {Sport} from '../../dto/sport';
import { NgForm } from '@angular/forms';
import { HorseComponent } from '../horse/horse.component';

@Component({
  selector: 'app-sport',
  templateUrl: './sport.component.html',
  styleUrls: ['./sport.component.scss']
})
export class SportComponent implements OnInit {

  error = false;
  onAdding = false;
  errorMessage = '';
  sport: Sport;
  addSport: Sport;
  sports: Sport[];

  constructor(private sportService: SportService) {
  }

  ngOnInit() {
    this.getAllSports();
  }

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  public onOpenAdd(){
    this.onAdding = !this.onAdding;
  }
  public onAddSport(addForm:NgForm){
    this.addSport = addForm.value;
    if (addForm.value.desc === "") {
      this.addSport.desc = null;
    }

      this.sportService.createSport(this.addSport).subscribe(
        (response: Sport) => {
          console.log(response);
          this.getAllSports();
        },
        error => {
          this.defaultServiceErrorHandling(error);
        }
      );
  }

  public onDelete(sport: Sport){
    this.sportService.deleteSportById(sport.id).subscribe(
      (response: void) => {
        this.getAllSports();
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );

    location.reload();
  }

  /**
   * Loads the sport for the specified id
   *
   * @param id the id of the sport
   */
  private loadSport(id: number) {
    this.sportService.getSportById(id).subscribe(
      (sport: Sport) => {
        this.sport = sport;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  public getAllSports(): void{
    console.log(this.sports);
    this.sportService.getAllSports().subscribe(
        (response: Sport[]) => {
            this.sports = response;
            console.log(response);
            console.log(this.sports);
        },
        error => {
          this.defaultServiceErrorHandling(error);
        }
        
    )
}



  private defaultServiceErrorHandling(error: any) {
    console.log(error);
    this.error = true;
    if (error.status === 0) {
      // If status is 0, the backend is probably down
      this.errorMessage = 'The backend seems not to be reachable';
    } else if (error.error.message === 'No message available') {
      // If no detailed error message is provided, fall back to the simple error name
      this.errorMessage = error.error.error;
    } else {
      this.errorMessage = error.error.message;
    }
  }

}
