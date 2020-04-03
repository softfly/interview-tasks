import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CensusLearnListComponent } from './census-learn-list/census-learn-list.component';


const routes: Routes = [
  { path: '', component: CensusLearnListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
