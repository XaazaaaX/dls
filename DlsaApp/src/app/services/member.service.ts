import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { Group } from './group.service';
import { Category } from './category.service';
import { HttpService } from './http.service';

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

export interface MemberEditDto {
    refDate?: Date;
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
    refDate?: Date;
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

    constructor(private httpService: HttpService) { }

    getAllMembers(): Observable<Member[]> {
        return this.httpService.get<Member[]>("members");
    }

    createMember(member: MemberDto): Observable<Member> {
        return this.httpService.post<Member>("member", member);
    }

    updateMember(member: MemberDto, id: number): Observable<Member> {
        return this.httpService.put<Member>("members/" + id, member);
    }

    uploadMember(file: File): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('file', file);
        return this.httpService.postMultipart<Member>("member/upload", formData);
    }
}