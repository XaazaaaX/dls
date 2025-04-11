import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';


export interface SectorDto {
  sectorname?: string;
  groupIds?: number[];
}

export interface Sector {
  id?: number;
  sectorname?: string;
  groups?: Group[];
}

export interface Group {
    id?: number;
    groupName?: string;
    liberated?: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class SectorService {

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

  getAllSectors(): Observable<Sector[]> {
      return this.http.get<Sector[]>(this.apiUrl + "/sectors", this.getHttpHeader());
  }

  /*
  createActions(categories: Category[]): Observable<Category[]> {
    return this.http.post<Category[]>(this.apiUrl + "/sectors", categories, this.getHttpHeader());
  }

  updateAction(category: Category): Observable<Category> {
    return this.http.put<Category>(this.apiUrl + "/sectors/" + category.id, category,this.getHttpHeader());
  }
    */
}