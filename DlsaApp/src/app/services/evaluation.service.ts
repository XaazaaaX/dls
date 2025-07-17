/**
 * EvaluationService – Dienst zur Jahresauswertung und DLS-Export
 *
 * Funktionen:
 * - Liefert verfügbare Jahre mit Auswertungsdaten
 * - Listet gespeicherte Jahresauswertungen auf
 * - Führt Auswertung (optional finalisierend) aus und liefert PDF/Binary als Download
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';
import { HttpResponse } from '@angular/common/http';

// Repräsentiert ein gespeichertes Auswertungsdokument für ein Jahr
export interface CourseOfYear {
  file?: string;         // Base64 oder Pfad
  timestamp?: Date;      // Erstellzeitpunkt
  displayName?: string;  // Anzeigename
  filename?: string;     // Tatsächlicher Dateiname
  dueDate?: Date;        // Stichtag zur Auswertung
}

// Liste der Jahre mit vorhandener Auswertung
export interface Year {
  year?: string;         // z. B. "2024"
}

@Injectable({
  providedIn: 'root'
})
export class EvaluationService {

  constructor(private httpService: HttpService) {}

  /**
   * Gibt alle Jahre zurück, für die eine Auswertung verfügbar ist
   */
  getAllYears(): Observable<Year[]> {
    return this.httpService.get<Year[]>("evaluations/years");
  }

  /**
   * Gibt alle gespeicherten Auswertungen (DLS-Jahresverläufe) zurück
   */
  getAllEvaluations(): Observable<CourseOfYear[]> {
    return this.httpService.get<CourseOfYear[]>("evaluations");
  }

  /**
   * Führt eine neue Auswertung durch und lädt das Ergebnis als PDF/Blob herunter
   * @param year - Jahr für die Auswertung (z. B. 2024)
   * @param finalize - true = endgültige Berechnung, false = Vorschau
   */
  doEvaluation(year: number, finalize: boolean): Observable<HttpResponse<Blob>> {
    const url = `evaluation/export?year=${year}&finalize=${finalize}`;
    return this.httpService.getBlob(url);
  }

  /*
  Beispiel für Dateidownload im Aufrufer:

  this.evaluationService.doEvaluation(2024, false).subscribe(response => {
    const blob = response.body;
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'auswertung_2024.pdf';
    a.click();
    window.URL.revokeObjectURL(url);
  });
  */
}
