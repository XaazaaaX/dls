import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable, of } from 'rxjs';

export interface Group {
  id?: number;
  groupName?: string;
  liberated?: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class GroupService {

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

  getAllGroups(): Observable<Group[]> {
    return this.http.get<Group[]>(this.apiUrl + "/groups", this.getHttpHeader());
  }

  createGroups(groups: Group[]): Observable<Group[]> {
    return this.http.post<Group[]>(this.apiUrl + "/groups", groups, this.getHttpHeader());
  }

  updateGroup(group: Group): Observable<Group> {
    return this.http.put<Group>(this.apiUrl + "/groups/" + group.id, group,this.getHttpHeader());
  }
}