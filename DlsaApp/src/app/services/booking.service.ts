/**
 * BookingService – Verwaltung von Dienstleistungs-Buchungen (DLS)
 *
 * Funktionen:
 * - Abrufen aller Buchungen (inkl. Mitglied und Aktion)
 * - Anlegen neuer Buchungen
 * - Stornieren (Soft-Delete) vorhandener Buchungen
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Member } from './member.service';
import { Action } from './action.service';
import { HttpService } from './http.service';

// DTO zur Erstellung einer neuen Buchung (z. B. im Dialog)
export interface BookingDto {
  countDls?: number;     // Anzahl der gebuchten DLS-Stunden
  comment?: string;      // Freitext (optional)
  doneDate?: Date;       // Datum der Leistung
  memberId?: number;     // ID des Mitglieds
  actionId?: number;     // ID der Aktion
}

// Vollständige Buchung (z. B. bei Anzeige)
export interface Booking {
  id?: number;
  countDls?: number;
  comment?: string;
  doneDate?: Date;
  canceled?: boolean;     // true = storniert
  bookingDate?: Date;     // Datum der Buchungserfassung
  member?: Member;        // Verknüpftes Mitgliedsobjekt
  action?: Action;        // Verknüpfte Aktion
}

@Injectable({
  providedIn: 'root'
})
export class BookingService {

  constructor(private httpService: HttpService) {}

  /**
   * Holt alle Buchungen (inkl. verknüpfter Mitglieder und Aktionen)
   */
  getAllBookings(): Observable<Booking[]> {
    return this.httpService.get<Booking[]>("bookings");
  }

  /**
   * Erstellt eine neue Buchung
   * @param booking - DLS-Daten inkl. Member/Action IDs
   */
  createBooking(booking: BookingDto): Observable<Booking> {
    return this.httpService.post<Booking>("booking", booking);
  }

  /**
   * Storniert eine bestehende Buchung
   * @param id - ID der Buchung, die storniert werden soll
   */
  cancelBooking(id: number): Observable<Booking> {
    return this.httpService.delete<Booking>("bookings/" + id);
  }
}
