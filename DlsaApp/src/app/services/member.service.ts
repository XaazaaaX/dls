/**
 * MemberService – Verwaltung von Mitgliedsdaten
 *
 * Funktionen:
 * - Abruf aller Mitglieder (inkl. Gruppen und Kategorien)
 * - Erstellung und Aktualisierung einzelner Mitglieder
 * - Datei-Upload für Massenimport von Mitgliedsdaten
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Group } from './group.service';
import { Category } from './category.service';
import { HttpService } from './http.service';

// Eingabe-Datenmodell zur Erstellung eines neuen Mitglieds
export interface MemberDto {
    surname?: string;
    forename?: string;
    memberId?: string;
    entryDate?: Date;
    leavingDate?: Date;
    active?: boolean;
    birthdate?: Date;
    groupIds?: number[];        // IDs der zugeordneten Gruppen
    categorieIds?: number[];    // IDs der zugeordneten Kategorien
}

// Edit-Modell für zukünftige Erweiterung (z. B. mit Stichtagsbezug)
export interface MemberEditDto {
    refDate?: Date;
    surname?: string;
    forename?: string;
    memberId?: string;
    entryDate?: Date;
    leavingDate?: Date;
    active?: boolean;
    birthdate?: Date;
    groupIds?: number[];
    categorieIds?: number[];
}

// Vollständiges Member-Objekt, wie es vom Server zurückgegeben wird
export interface Member {
    id?: number;
    refDate?: Date;
    surname?: string;
    forename?: string;
    memberId?: string;
    entryDate?: Date;
    leavingDate?: Date;
    active?: boolean;
    birthdate?: Date;
    aikz?: boolean;             // Mitglied ist AIKZ-pflichtig
    groups?: Group[];
    categories?: Category[];
}

@Injectable({
    providedIn: 'root'
})
export class MemberService {

    constructor(private httpService: HttpService) { }

    /**
     * Holt alle Mitglieder inkl. Gruppenzuordnung und Kategorien
     */
    getAllMembers(): Observable<Member[]> {
        return this.httpService.get<Member[]>("members");
    }

    /**
     * Erstellt ein neues Mitglied im System
     * @param member - Eingabedaten (z. B. Name, Gruppen-IDs, Kategorien)
     */
    createMember(member: MemberDto): Observable<Member> {
        return this.httpService.post<Member>("member", member);
    }

    /**
     * Aktualisiert ein vorhandenes Mitglied
     * @param member - Neue Werte als DTO
     * @param id - ID des Mitglieds, das aktualisiert werden soll
     */
    updateMember(member: MemberDto, id: number): Observable<Member> {
        return this.httpService.put<Member>("members/" + id, member);
    }

    /**
     * Lädt eine Datei mit Mitgliedsdaten hoch (z. B. Excel oder CSV)
     * @param file - Dateiobjekt (multipart/form-data)
     */
    uploadMember(file: File): Observable<any> {
        const formData: FormData = new FormData();
        formData.append('file', file);
        return this.httpService.postMultipart<Member>("member/upload", formData);
    }

    /**
   * Löscht ein Mitglied anhand seiner ID
   * @param memberId ID der Mitglied
   */
    deleteMember(memberId?: number): Observable<any> {
        return this.httpService.delete<any>("member/" + memberId);
    }
}
