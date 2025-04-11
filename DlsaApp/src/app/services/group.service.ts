import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable, of } from 'rxjs';

export interface GroupDto {
  sectorname?: string;
  groupIds?: number[];
}

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

  getAllGroups(): Observable<Group[]> {

    const dummyGroups: Group[] = [
        { id: 52, groupName: 'Gruppe A', liberated: true },
        { id: 2, groupName: 'Gruppe B', liberated: false },
        { id: 3, groupName: 'Gruppe C', liberated: true },
        { id: 4, groupName: 'Gruppe D', liberated: false }
      ];
    return of(dummyGroups);
}

  /*
  getAllSectors(): Observable<Sector[]> {
      return this.http.get<Sector[]>(this.apiUrl + "/sectors", this.getHttpHeader());
  }
      */

  /*
  createActions(categories: Category[]): Observable<Category[]> {
    return this.http.post<Category[]>(this.apiUrl + "/sectors", categories, this.getHttpHeader());
  }

  updateAction(category: Category): Observable<Category> {
    return this.http.put<Category>(this.apiUrl + "/sectors/" + category.id, category,this.getHttpHeader());
  }
    */
}