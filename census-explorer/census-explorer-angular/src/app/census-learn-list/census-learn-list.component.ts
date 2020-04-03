import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';

import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';

import { CensusLearnService } from '../service/census-learn.service';
import { CensusLearnDataSource } from '../datasource/census-learn.datasource';
import { tap } from 'rxjs/operators';
import { merge, fromEvent } from 'rxjs';
import { MatSelect } from '@angular/material/select';
import { FormControl, Validators } from '@angular/forms';



@Component({
  selector: 'app-census-learn-list',
  templateUrl: './census-learn-list.component.html',
  styleUrls: ['./census-learn-list.component.css']
})
export class CensusLearnListComponent implements AfterViewInit, OnInit {
  //Select
  variables: String[];

  //Datatable
  displayedColumns: string[] = ['value', 'count', 'age'];
  dataSource: CensusLearnDataSource;

  @ViewChild(MatSelect, { static: true }) variable: MatSelect;

  @ViewChild(MatSort, { static: true }) sort: MatSort;

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;

  constructor(private censusLearnService: CensusLearnService) { }

  ngOnInit() {
    this.censusLearnService.findVariables().subscribe(data => {
      this.variables = data;
      this.variable.value = data[0];
      this.loadLearnsPage();
    });
    this.dataSource = new CensusLearnDataSource(this.censusLearnService, this.paginator);
  }

  ngAfterViewInit() {
    // reset the paginator after sorting
    merge(this.sort.sortChange, this.variable.valueChange)
      .subscribe(() => this.paginator.pageIndex = 0);

    merge(this.sort.sortChange, this.paginator.page, this.variable.valueChange).pipe(
      tap(() => this.loadLearnsPage())
    ).subscribe();
  }

  loadLearnsPage() {
    this.dataSource.loadLearns(
      this.variable.value,
      this.paginator.pageIndex,
      this.paginator.pageSize,
      this.sort.active,
      this.sort.direction,
    );
  }

}
