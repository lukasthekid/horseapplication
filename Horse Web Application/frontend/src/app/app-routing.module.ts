import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DetailedViewComponent } from './component/detailed-view/detailed-view.component';
import { HorseComponent } from './component/horse/horse.component';
import {SportComponent} from './component/sport/sport.component';

const routes: Routes = [
  {path: '', redirectTo: '', pathMatch: 'full'},
  {path: 'sports', component: SportComponent},
  {path: 'horses', 
  component: HorseComponent},
  {path: 'detail', component: DetailedViewComponent}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
