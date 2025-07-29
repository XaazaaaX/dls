/**
 * SectorService – Verwaltung von Bereichen (z. B. organisatorische Einheiten)
 *
 * Funktionen:
 * - Abruf aller Bereichen
 * - Erstellung und Aktualisierung von Bereichen
 * - Verknüpfung von Gruppen mit Bereichen über IDs
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
  sectorname?: string;     // Bezeichnung des Bereichs
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

  constructor(private httpService: HttpService) { }

  /**
   * Holt alle Bereichen inkl. Gruppen vom Backend
   */
  getAllSectors(): Observable<Sector[]> {
    return this.httpService.get<Sector[]>("sectors");
  }

  /**
   * Erstellt einen neuen Bereichs mit Gruppenreferenzen
   * @param sector - Eingabeobjekt mit Bereichname und zugeordneten Gruppen-IDs
   */
  createSectors(sector: SectorDto): Observable<Sector> {
    return this.httpService.post<Sector>("sector", sector);
  }

  /**
   * Aktualisiert einen vorhandenen Bereich
   * @param sector - Neue Bereichdaten
   * @param id - ID des zu aktualisierenden Bereichs
   */
  updateSector(sector: SectorDto, id: number): Observable<Sector> {
    return this.httpService.put<Sector>("sectors/" + id, sector);
  }

  /**
   * Löscht einen Bereich anhand seiner ID
   * @param sectorId ID der Bereich
   */
  deleteSector(sectorId?: number): Observable<any> {
    return this.httpService.delete<any>("sector/" + sectorId);
  }
}
