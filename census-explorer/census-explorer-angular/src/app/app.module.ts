import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material-module';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CensusLearnListComponent } from './census-learn-list/census-learn-list.component';
import { CensusLearnService } from './service/census-learn.service';

@NgModule({
  declarations: [
    AppComponent,
    CensusLearnListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule
  ],
  providers: [CensusLearnService],
  bootstrap: [AppComponent]
})
export class AppModule { }
