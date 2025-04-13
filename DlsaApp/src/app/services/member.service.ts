import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { Group } from './group.service';
import { Category } from './category.service';

export interface MemberDto {
    surname?: string;
    forename?: string;
    memberId?: string;
    entryDate?: Date;
    leavingDate?: Date;
    active?: boolean;
    birthdate?: Date;
    groupIds?: number[];
    categorieIds?: number[];
}

export interface Member {
    id?: number;
    surname?: string;
    forename?: string;
    memberId?: string;
    entryDate?: Date;
    leavingDate?: Date;
    active?: boolean;
    birthdate?: Date;
    aikz?: boolean;
    groups?: Group[];
    categories?: Category[];
}

@Injectable({
    providedIn: 'root'
})
export class MemberService {

    private apiUrl = 'http://127.0.0.1:5005';

    constructor(private http: HttpClient, private authService: AuthService) {
    }

    private getHttpHeader() {
        let authToken = this.authService.getToken();
        return {
            headers: new HttpHeaders({
                "Content-type": "application/json",
                Authorization: `Bearer ${authToken}`
            })
        };
    }

    getAllMembers(): Observable<Member[]> {
        return this.http.get<Member[]>(this.apiUrl + "/members", this.getHttpHeader());
    }

    createMembers(members: MemberDto[]): Observable<Member[]> {
        return this.http.post<Member[]>(this.apiUrl + "/members", members, this.getHttpHeader());
    }

    updateMember(member: MemberDto, id: number): Observable<Member> {
        return this.http.put<Member>(this.apiUrl + "/members/" + id, member,this.getHttpHeader());
    }
}