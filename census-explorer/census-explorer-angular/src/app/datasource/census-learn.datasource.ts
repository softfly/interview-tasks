import { CollectionViewer, DataSource } from "@angular/cdk/collections";

import { CensusLearn } from '../model/census-learn';
import { CensusLearnService } from '../service/census-learn.service';
import { Observable, BehaviorSubject, of } from "rxjs";
import { catchError, finalize } from "rxjs/operators";
import { MatPaginator } from '@angular/material/paginator';

export class CensusLearnDataSource implements DataSource<CensusLearn> {

    private learnsSubject = new BehaviorSubject<CensusLearn[]>([]);

    private loadingSubject = new BehaviorSubject<boolean>(true);

    public loading$ = this.loadingSubject.asObservable();

    constructor(private censusLearnService: CensusLearnService, private paginator: MatPaginator) { }

    loadLearns(groupBy, pageNo, pageSize, sortBy, sortDirection) {
        this.loadingSubject.next(true);

        this.censusLearnService.findAll(groupBy, pageNo, pageSize, sortBy, sortDirection).pipe(
            catchError(() => of([])),
            finalize(() => this.loadingSubject.next(false))
        )
            .subscribe(learns => {
                this.learnsSubject.next(learns['content']);
                this.paginator.length = learns['totalElements'];
            });
    }

    connect(collectionViewer: CollectionViewer): Observable<CensusLearn[]> {
        return this.learnsSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.learnsSubject.complete();
        this.loadingSubject.complete();
    }
}
