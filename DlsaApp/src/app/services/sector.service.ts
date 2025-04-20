import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { Group } from './group.service';
import { HttpService } from './http.service';


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

  constructor(private httpService: HttpService) {}

  getAllSectors(): Observable<Sector[]> {
      return this.httpService.get<Sector[]>("sectors");
  }

  createSectors(sector: SectorDto): Observable<Sector> {
    return this.httpService.post<Sector>("sector", sector);
  }

  updateSector(sector: SectorDto, id: number): Observable<Sector> {
    return this.httpService.put<Sector>("sectors/" + id, sector);
  }
}