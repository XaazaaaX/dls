/**
 * GroupService – Verwaltung von Funktionsgruppen (z. B. Rollen, Tätigkeitsbereiche)
 *
 * Funktionen:
 * - Abrufen aller Gruppen
 * - Erstellen und Aktualisieren einzelner Gruppen
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';

// Modell einer Gruppe
export interface Group {
  id?: number;
  groupName?: string;   // Name der Gruppe (z. B. "Sanitätsdienst")
  liberated?: boolean;  // Optional: Befreit von DLS-Pflicht o. Ä.
}

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  constructor(private httpService: HttpService) {}

  /**
   * Ruft alle verfügbaren Gruppen vom Backend ab
   */
  getAllGroups(): Observable<Group[]> {
    return this.httpService.get<Group[]>("groups");
  }

  /**
   * Erstellt eine neue Gruppe
   * @param group - Neue Gruppendaten
   */
  createGroup(group: Group): Observable<Group> {
    return this.httpService.post<Group>("group", group);
  }

  /**
   * Aktualisiert eine bestehende Gruppe
   * @param group - Aktualisierte Daten (inkl. ID)
   */
  updateGroup(group: Group): Observable<Group> {
    return this.httpService.put<Group>("groups/" + group.id, group);
  }

  /**
   * Löscht einen Gruppe anhand seiner ID
   * @param groupId ID der Gruppe
   */
  deleteGroup(groupId?: number): Observable<any> {
    return this.httpService.delete<any>("groups/" + groupId);
  }
}
