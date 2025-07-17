/**
 * SettingsService – Service zur Verwaltung systemweiter Einstellungen
 *
 * Funktionen:
 * - Lädt aktuelle Konfigurationen aus dem Backend
 * - Speichert aktualisierte Einstellungen (z. B. Altersgrenzen, Stichtag, DLS-Kosten)
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';

// Modell für Systemeinstellungen
export interface Settings {
  id?: number;
  ageFrom?: number;           // Mindestalter für DLS-Pflicht
  ageTo?: number;             // Höchstalter für DLS-Pflicht
  bookingMethod?: string;     // z. B. „Volles Jahr“ oder „Anteilig“
  clearing?: boolean;         // Ob automatische Ausgleichsbuchung aktiviert ist
  costDls?: number;           // Kosten pro DLS-Stunde
  countDls?: number;          // Anzahl DLS-Stunden pro Jahr
  dueDate?: Date;             // Stichtag zur Berechnung
  granularity?: string;       // Granularität der DLS (Ganze, Halbe, etc.)
}

@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  constructor(private httpService: HttpService) {}

  /**
   * Holt aktuelle Einstellungen vom Backend
   */
  getSettings(): Observable<Settings> {
    return this.httpService.get<Settings>("settings");
  }

  /**
   * Speichert geänderte Einstellungen
   * @param settings Einstellungsobjekt mit gültiger ID
   */
  updateSettings(settings: Settings): Observable<Settings> {
    return this.httpService.put<Settings>("settings/" + settings.id, settings);
  }
}
