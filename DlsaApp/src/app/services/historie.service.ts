/**
 * HistorieService – Abfrage historischer Änderungen an Gruppen und Mitgliedern
 *
 * Funktionen:
 * - Holt Änderungsprotokolle von Gruppenmitgliedschaften
 * - Holt Änderungsprotokolle an Mitgliedsdaten (Spaltenbasiert)
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';

// Struktur einer Gruppenänderung
export interface GroupChanges {
  id?: number;
  refDate?: Date;         // Stichtag oder Bezugsdatum
  oldValue?: boolean;     // Vorheriger Zustand (z. B. war Mitglied in Gruppe)
  newValue?: boolean;     // Neuer Zustand
  groupName?: string;     // Gruppenname
}

// Struktur einer Mitgliedsänderung
export interface MemberChanges {
  id?: number;
  refDate?: Date;         // Referenzdatum (z. B. für Vergleich)
  timestamp?: Date;       // Zeitpunkt der Änderung
  column?: string;        // Spalte, die geändert wurde (z. B. ACTIVE)
  oldValue?: boolean;     // Alter Wert
  newValue?: boolean;     // Neuer Wert
  memberName?: string;    // Anzeigename des Mitglieds
}

@Injectable({
  providedIn: 'root'
})
export class HistorieService {

  constructor(private httpService: HttpService) {}

  /**
   * Holt alle Gruppenänderungen (z. B. Zu- oder Austritte)
   */
  getAllGroupChanges(): Observable<GroupChanges[]> {
    return this.httpService.get<GroupChanges[]>("historie/groups");
  }

  /**
   * Holt alle Änderungsprotokolle zu Mitgliedsdaten (Spaltenänderungen)
   */
  getAllMemberChanges(): Observable<MemberChanges[]> {
    return this.httpService.get<MemberChanges[]>("historie/members");
  }
}
