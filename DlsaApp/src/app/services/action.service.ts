/**
 * ActionService – Service zur Verwaltung von Aktionen (z. B. Vereinsaktivitäten)
 *
 * Funktionen:
 * - Abrufen aller Aktionen
 * - Erstellen neuer Aktionen
 * - Aktualisieren bestehender Aktionen
 *
 * Verwendet: HttpService für API-Kommunikation (GET, POST, PUT)
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';

// DTO für Eingaben beim Erstellen/Aktualisieren
export interface ActionDto {
    year?: string;
    description?: string;
    contactId?: string; // Referenz auf Kontaktperson
}

// Vollständiges Action-Objekt (inkl. Kontaktinfo)
export interface Action {
    id?: number;
    year?: string;
    description?: string;
    contact?: Contact;
}

// Kontaktinformationen einer zugeordneten Person
export interface Contact {
    id?: string;
    surname?: string;
    forename?: string;
}

@Injectable({
    providedIn: 'root'
})
export class ActionService {

    constructor(private httpService: HttpService) {}

    /**
     * Ruft alle vorhandenen Aktionen vom Backend ab
     */
    getAllActions(): Observable<Action[]> {
        return this.httpService.get<Action[]>("actions");
    }

    /**
     * Erstellt eine neue Aktion mit den angegebenen Daten
     * @param action - Daten für neue Aktion (ohne ID)
     */
    createActions(action: ActionDto): Observable<Action> {
        return this.httpService.post<Action>("action", action);
    }

    /**
     * Aktualisiert eine bestehende Aktion
     * @param action - Neue Daten
     * @param id - ID der zu aktualisierenden Aktion
     */
    updateAction(action: ActionDto, id: number): Observable<Action> {
        return this.httpService.put<Action>("actions/" + id, action);
    }
}
