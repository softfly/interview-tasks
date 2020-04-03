import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { CensusLearnWrapper } from '../model/census-learn-wrapper';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CensusLearnService {

  private censusLearnsUrl: string;

  private variablesUrl: string;

  constructor(private http: HttpClient) {
    this.censusLearnsUrl = environment.restHost + 'census/learns';
    this.variablesUrl = environment.restHost + 'census/learns/variables';
  }

  public findAll(groupBy, pageNo, pageSize, sortBy, sortDirection): Observable<CensusLearnWrapper> {
    return this.http.get<CensusLearnWrapper>(this.censusLearnsUrl, {
      params: new HttpParams()
        .set('groupBy', groupBy)
        .set('pageNo', pageNo.toString())
        .set('pageSize', pageSize.toString())
        .set('sortBy', sortBy)
        .set('sortDirection', sortDirection)
    });
  }

  public findVariables(): Observable<String[]> {
    return this.http.get<String[]>(this.variablesUrl);
  }

}
