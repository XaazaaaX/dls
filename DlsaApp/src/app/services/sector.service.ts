/**
 * SectorService – Verwaltung von Sektoren (z. B. organisatorische Einheiten)
 *
 * Funktionen:
 * - Abruf aller Sektoren
 * - Erstellung und Aktualisierung von Sektoren
 * - Verknüpfung von Gruppen mit Sektoren über IDs
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Group } from './group.service';
import { HttpService } from './http.service';

// Datenmodell für Eingaben (DTO)
export interface SectorDto {
  sectorname?: string;     // Bezeichnung des Sektors
  groupIds?: number[];     // Zugehörige Gruppen (IDs)
}

// Vollständiges Datenmodell inkl. verknüpfter Gruppen
export interface Sector {
  id?: number;
  sectorname?: string;
  groups?: Group[];        // Ausgegebene Gruppendaten vom Backend
}

@Injectable({
  providedIn: 'root'
})
export class SectorService {

  constructor(private httpService: HttpService) {}

  /**
   * Holt alle Sektoren inkl. Gruppen vom Backend
   */
  getAllSectors(): Observable<Sector[]> {
    return this.httpService.get<Sector[]>("sectors");
  }

  /**
   * Erstellt einen neuen Sektor mit Gruppenreferenzen
   * @param sector - Eingabeobjekt mit Sektorname und zugeordneten Gruppen-IDs
   */
  createSectors(sector: SectorDto): Observable<Sector> {
    return this.httpService.post<Sector>("sector", sector);
  }

  /**
   * Aktualisiert einen vorhandenen Sektor
   * @param sector - Neue Sektordaten
   * @param id - ID des zu aktualisierenden Sektors
   */
  updateSector(sector: SectorDto, id: number): Observable<Sector> {
    return this.httpService.put<Sector>("sectors/" + id, sector);
  }
}
