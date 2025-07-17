import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';

export interface MemberCount {
    allMembers?: number;
    activeMembers?: number;
    passiveMembers?: number
    dlsRequiredMembers?: number;
}

export interface AnnualServiceHours {
    labels?: string[];
    data?: number[];
}

export interface MonthlyServiceHours {
    label?: string;
    data?: number[];
}

export interface TopDlsMember {
    label?: string;
    data?: number;
}


export interface SectorsWithDlsFromYearBody{
    label?: string;
    data?: number[];

    borderColor?: any;
    pointBackgroundColor?: any;
    pointBorderColor?: any;
    pointHoverBackgroundColor?: any;
    pointHoverBorderColor?: any;
}

export interface SectorsWithDlsFromYear {
    labels?: string[];
    body?: SectorsWithDlsFromYearBody[];
}


@Injectable({
    providedIn: 'root'
})
export class StatisticsService {

    constructor(private httpService: HttpService) {}

    getMemberCount(): Observable<MemberCount> {
        return this.httpService.get<MemberCount>("statistics/membercount");
    }

    getAnnualServiceHours(): Observable<AnnualServiceHours> {
        return this.httpService.get<AnnualServiceHours>("statistics/annualservicehours");
    }

    getMonthlyServiceHours(): Observable<MonthlyServiceHours[]> {
        return this.httpService.get<MonthlyServiceHours[]>("statistics/monthlyservicehours");
    }

    getTopDlsMember(): Observable<TopDlsMember[]> {
        return this.httpService.get<TopDlsMember[]>("statistics/topdlsmember");
    }

    getSelectedWithDlsFromYear(code: string): Observable<SectorsWithDlsFromYear> {
        return this.httpService.get<SectorsWithDlsFromYear>("statistics/sectorswithdlsfromyear?code=" + code);
    }
}