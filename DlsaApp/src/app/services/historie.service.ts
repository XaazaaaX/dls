import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';


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

  private apiUrl = 'http://127.0.0.1:5005'; // Ersetze mit deiner API-URL

  constructor(private http: HttpClient, private authService:AuthService) {
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

  getAllGroupChanges(): Observable<GroupChanges[]> {
      return this.http.get<GroupChanges[]>(this.apiUrl + "/historie/groups", this.getHttpHeader());
  }

  getAllMemberChanges(): Observable<MemberChanges[]> {
    return this.http.get<MemberChanges[]>(this.apiUrl + "/historie/members", this.getHttpHeader());
}
  
}
