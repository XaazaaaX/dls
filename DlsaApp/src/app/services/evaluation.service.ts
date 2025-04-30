import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Member } from './member.service';
import { Action } from './action.service';
import { HttpService } from './http.service';
import { HttpResponse } from '@angular/common/http';

export interface CourseOfYear {
    file?: string;
    timestamp?: Date;
    displayName?: string
    filename?: string;
    dueDate?: Date;
}

export interface Year {
    year?: string;
}

@Injectable({
    providedIn: 'root'
})
export class EvaluationService {

    constructor(private httpService: HttpService) { }

    getAllYears(): Observable<Year[]> {
        return this.httpService.get<Year[]>("evaluations/years");
    }

    getAllEvaluations(): Observable<CourseOfYear[]> {
        return this.httpService.get<CourseOfYear[]>("evaluations");
    }

    /*doEvaluation(): Observable<Blob> {
        return this.httpService.getBlob('evaluation/export?year=2024&finalize=false');
    }*/

    doEvaluation(year: number, finalize: boolean): Observable<HttpResponse<Blob>> {
        return this.httpService.getBlob("evaluation/export?year=" + year + "&finalize=" + finalize );
    }

    /*
    this.httpService.getBlob('download/pdf').subscribe(blob => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'datei.pdf';
        a.click();
        window.URL.revokeObjectURL(url);
    });
    */

}