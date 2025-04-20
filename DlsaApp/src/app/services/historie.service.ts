import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';


export interface GroupChanges{
    id?: number;
    refDate?: Date;
    oldValue?: boolean;
    newValue?: boolean;
    groupName?: string;
}

export interface MemberChanges{
    id?: number;
    refDate?: Date;
    column?: string
    oldValue?: boolean;
    newValue?: boolean;
    memberName?: string;
}

@Injectable({
  providedIn: 'root'
})
export class HistorieService {

  constructor(private httpService: HttpService) {}

  getAllGroupChanges(): Observable<GroupChanges[]> {
      return this.httpService.get<GroupChanges[]>("historie/groups");
  }

  getAllMemberChanges(): Observable<MemberChanges[]> {
    return this.httpService.get<MemberChanges[]>("historie/members");
}
  
}
