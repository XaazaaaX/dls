import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { Group } from './group.service';


export interface SectorDto {
  sectorname?: string;
  groupIds?: number[];
}

export interface Sector {
  id?: number;
  sectorname?: string;
  groups?: Group[];
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

  createSectors(categories: SectorDto[]): Observable<Sector[]> {
    return this.http.post<Sector[]>(this.apiUrl + "/sectors", categories, this.getHttpHeader());
  }

  updateSector(sector: SectorDto, id: number): Observable<Sector> {
    return this.http.put<Sector>(this.apiUrl + "/sectors/" + id, sector,this.getHttpHeader());
  }
}