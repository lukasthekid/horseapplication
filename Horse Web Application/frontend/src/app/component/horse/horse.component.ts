import {Component, OnInit, Input} from '@angular/core';
import {SportService} from '../../service/sport.service';
import {Sport} from '../../dto/sport';
import { Horse } from 'src/app/dto/horse';
import { HorseService } from 'src/app/service/horse.service';
import { NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { NgForm } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { validateLocaleAndSetLanguage } from 'typescript';
import { SportComponent } from '../sport/sport.component';


@Component({
  selector: 'app-horse',
  templateUrl: './horse.component.html',
  styleUrls: ['./horse.component.scss'],
})
export class HorseComponent implements OnInit {
  

  onEdit = false;
  onAdding = false;
  error = false;
  checked = false;
  errorMessage = '';
  favSports: Sport[];
  favSport:Sport;
  horse: Horse;
  editHorse:Horse;
  detailHorse:Horse;
  detailSport:string;
  horses: Horse[];
  updatedHorses: Horse[];
  addHorse:Horse;
  searchHorse:Horse;
  searchName:string;
  searchDesc:string;
  searchSex:string;
  searchDob:Date;
  searchSport:string;
  allHorses:Horse[];
  dadHorse:Horse;
  momHorse:Horse;

  
 
  




  constructor( private horseService: HorseService, private sportService:SportService) {
    
  }

  ngOnInit() {
    
    this.getAllSports();
    this.getAllHorses2();
    this.getAllChildren();
  }
 

  /**
   * Error flag will be deactivated, which clears the error message
   */
  vanishError() {
    this.error = false;
  }

  /**
   * Loads the sport for the specified id
   *
   * @param id the id of the sport
   */
  private loadHorse(id: number) {
    this.horseService.getHorseById(id).subscribe(
      (horse: Horse) => {
        this.horse = horse;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

  public refresh(){
    this.getAllHorses();
  }

  public getAllHorses(): void{
      this.horseService.getAllHorses().subscribe(
          (response: Horse[]) => {
              
              this.horses = response;
           
          },
          error => {
            this.defaultServiceErrorHandling(error);
          }
          
      )
  }

  public getAllHorses2(): void{
    this.horseService.getAllHorses().subscribe(
        (response: Horse[]) => {
            
            this.allHorses = response;
            this.allHorses[response.length] = null;
         
        },
        error => {
          this.defaultServiceErrorHandling(error);
        }
        
    )
}

  public getAllSports(): void{
    this.sportService.getAllSports().subscribe(
        (response: Sport[]) => {
            this.favSports = response;
            this.favSports[response.length] = null;


            
        },
        error => {
          this.defaultServiceErrorHandling(error);
        }
        
    )
}

  public getAllChildren(): void{
    this.horseService.getAllChildren().subscribe(
        (response: Horse[]) => {
            this.horses = response;

            
        },
        error => {
          this.defaultServiceErrorHandling(error);
        }
        
    )
}


public addParentsToList(childHorse:Horse){
 
    this.updatedHorses = [];
    
    let j = 0;
    const size = this.horses.length;
    for (let i = 0; i < size; i++) {
      this.updatedHorses[j] = this.horses[i];

      if (this.horses[i] == childHorse) {
        
        if (childHorse.dad!=null) {
          if (!this.horses.includes(childHorse.dad)) {
            j += 1;
          this.updatedHorses[j] = childHorse.dad;
          }
          
        }
        if (childHorse.mom!=null) {
          if (!this.horses.includes(childHorse.mom)) {
            j += 1;
          this.updatedHorses[j] = childHorse.mom;
          }
        }
      }
      j+=1;
      
    }
    this.horses = this.updatedHorses;
  
  
  
}

public onSearchingHorse(searchForm){
  this.searchHorse = searchForm.value;
    
  this.validateForm(searchForm, this.searchHorse, true, true);

    this.horseService.searchHorse(this.searchHorse).subscribe(
      (response: Horse[]) => {
        console.log(response);
        this.horses = response;
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );

    this.favSport = null;

}

/*
  public searchByName(){
    this.horses = this.horses.filter(res =>{
      return res.name.toLowerCase().match(this.searchName.toLowerCase());
    })

  }
  public searchByDesc(){
    this.horses = this.horses.filter(res =>{
      if (res.desc!=null) {
        return res.desc.toLowerCase().match(this.searchDesc.toLowerCase());
      }
    })
  }
  public searchBySex(){
    this.horses = this.horses.filter(res =>{
      return res.sex.toLowerCase()===(this.searchSex.toLowerCase());
    })
  }

  public searchByDob(){
    this.horses = this.horses.filter(res =>{
      return res.dob < this.searchDob;
    })
  }
  public searchBySport(){
    this.horses = this.horses.filter(res =>{
      if (res.favoriteSport!=null) {
        this.favSport = res.favoriteSport;
        return this.favSport.name.toLowerCase().match(this.searchSport.toLowerCase());
      }
    })
  }
  */


  public onOpenEdit(horse:Horse){
    this.getAllSports();
    this.getAllHorses2();
    this.editHorse = horse;
    this.favSport = horse.favoriteSport;
    this.dadHorse = horse.dad;
    this.momHorse = horse.mom;
}
 public onOpenAdd(){
  this.getAllSports();
  this.getAllHorses2();
   this.onAdding = !this.onAdding;
 }




  public onDelete(horse: Horse){
    this.horseService.deleteHorseById(horse.id).subscribe(
      (response: void) => {
      
        this.getAllHorses();
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );
  }

 


  public updateHorse(updateForm: NgForm, horse: Horse){

    this.validateForm(updateForm, horse, false, false);


    this.horseService.updateHorse(horse).subscribe(
      (response: Horse) => {
        
        this.getAllHorses();
      },
      error => {
        this.defaultServiceErrorHandling(error);
      }
    );

    this.favSport = null;
    this.dadHorse = null;
    this.momHorse = null;
  }

  /*public onOpenModule(horse: Horse, mode: string): void{
    const container = document.getElementById('maincontainer');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addEmployeeModal');
    }
    if (mode === 'edit') {
      this.editHorse = horse;
      button.setAttribute('data-target', '#updateEmployeeModal');
    }
    if (mode === 'delete') {
      this.deleteHorse = horse;
      button.setAttribute('data-target', '#deleteEmployeeModal');
    }
    container.appendChild(button);
    button.click();
  }
  */



  public onAddingHorse(addForm:NgForm){

    this.addHorse = addForm.value;
    
    this.validateForm(addForm, this.addHorse, true, false);

      this.horseService.createHorse(this.addHorse).subscribe(
        (horse: Horse) => {
          console.log(horse);
          this.getAllHorses();
        },
        error => {
          this.defaultServiceErrorHandling(error);
        }
      );

      this.favSport = null;
      this.dadHorse = null;
      this.momHorse = null;
  }


  /*public goToLink(horse: Horse){
    

    window.open("http://localhost:8080/horses/"+ horse.id);

}
*/


public onDetail(horse:Horse){
  if (horse == null) {
    document.getElementById("openErrorButton").click();
    return;
  }

  this.detailHorse = horse;

  if (horse.favoriteSport!=null) {
    this.detailSport = this.sportToString(horse.favoriteSport);
  }

   
}

private validateForm(form:NgForm, horse:Horse, add:boolean, search:boolean):void{

  if(!search){
  horse.name = form.value.name;
  horse.dob = form.value.dob;
  horse.sex = form.value.sex;
  horse.favoriteSport = this.favSport;
  horse.dad = this.dadHorse;
  horse.mom = this.momHorse;

  if (form.value.desc != "") {
    horse.desc = form.value.desc;
  }
  

  if (add) {
    if (form.value.desc === "") {
      horse.desc = null;
    }
  }
}

else{
  form.value.name!=""?horse.name=form.value.name:horse.name = null;
  form.value.desc!=""?horse.desc=form.value.desc:horse.desc = null;
  form.value.dob!=""?horse.dob=form.value.dob:horse.dob = null;
  form.value.sex!=""?horse.sex=form.value.sex:horse.sex = null;
  horse.favoriteSport = this.favSport;
}


}

public loadParent(idString:string, dad:boolean){
  if (idString !="") {
   const id = parseInt(idString);
   for (let index = 0; index < this.allHorses.length; index++) {
     if (id == this.allHorses[index].id) {
       if (dad) {
         this.dadHorse = this.allHorses[index];
       }
       else{
        this.momHorse = this.allHorses[index];
       }
       
     }
     
   }
  }
  else{
    if (dad) {
      this.dadHorse = null;
    }
    else this.momHorse = null;
  }
}

public loadfavoriteSport(idString:string){
  if (idString !="") {
    const id = parseInt(idString);
   for (let index = 0; index < this.favSports.length; index++) {
     if (id == this.favSports[index].id) {
       this.favSport = this.favSports[index];
       
     
     
   }
  }
  }
  else this.favSport = null;
  
}





private sportToString(sport:Sport):string{
  let sportString = "";
  sportString += sport.name;

  if (sport.desc!=null) {
    sportString += ", " + sport.desc;
  }

  return sportString;
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
