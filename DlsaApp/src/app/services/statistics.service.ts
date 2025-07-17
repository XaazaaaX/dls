/**
 * StatisticsService – Liefert statistische Daten zu Mitgliedern und Dienstleistungsstunden
 *
 * Funktionen:
 * - Mitgliederstatistik (gesamt, aktiv, passiv, DLS-pflichtig)
 * - Jahres- und Monatsstatistiken zu Dienstleistungsstunden
 * - Top-Mitglieder nach DLS-Leistung
 * - Sektoren mit DLS-Leistungen nach Jahr (inkl. Visualisierungsfarben)
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';

// --- Schnittstellen für API-Antworten ---

export interface MemberCount {
    allMembers?: number;
    activeMembers?: number;
    passiveMembers?: number;
    dlsRequiredMembers?: number;
}

export interface AnnualServiceHours {
    labels?: string[]; // z. B. Jahre: ["2021", "2022", ...]
    data?: number[];   // DLS-Stunden pro Jahr
}

export interface MonthlyServiceHours {
    label?: string;    // z. B. "Januar"
    data?: number[];   // Stundenwerte je Monat
}

export interface TopDlsMember {
    label?: string;    // Name des Mitglieds
    data?: number;     // Gesamtstunden
}

// Farben & Daten für Sektor-Diagramme
export interface SectorsWithDlsFromYearBody {
    label?: string;
    data?: number[];
    borderColor?: any;
    pointBackgroundColor?: any;
    pointBorderColor?: any;
    pointHoverBackgroundColor?: any;
    pointHoverBorderColor?: any;
}

export interface SectorsWithDlsFromYear {
    labels?: string[]; // z. B. Monate
    body?: SectorsWithDlsFromYearBody[];
}

@Injectable({
    providedIn: 'root'
})
export class StatisticsService {

    constructor(private httpService: HttpService) {}

    /**
     * Gesamtzahl der Mitglieder (aktiv, passiv, DLS-pflichtig)
     */
    getMemberCount(): Observable<MemberCount> {
        return this.httpService.get<MemberCount>("statistics/membercount");
    }

    /**
     * Jahresstatistik der DLS-Stunden (z. B. für Balkendiagramme)
     */
    getAnnualServiceHours(): Observable<AnnualServiceHours> {
        return this.httpService.get<AnnualServiceHours>("statistics/annualservicehours");
    }

    /**
     * Monatliche DLS-Leistungen (z. B. für Linien- oder Balkendiagramme)
     */
    getMonthlyServiceHours(): Observable<MonthlyServiceHours[]> {
        return this.httpService.get<MonthlyServiceHours[]>("statistics/monthlyservicehours");
    }

    /**
     * Mitglieder mit den meisten DLS-Stunden (z. B. Top-5-Liste)
     */
    getTopDlsMember(): Observable<TopDlsMember[]> {
        return this.httpService.get<TopDlsMember[]>("statistics/topdlsmember");
    }

    /**
     * DLS-Leistungen je Sektor für ein bestimmtes Jahr
     * @param code Jahr oder Kennung (z. B. "2024")
     */
    getSelectedWithDlsFromYear(code: string): Observable<SectorsWithDlsFromYear> {
        return this.httpService.get<SectorsWithDlsFromYear>(
            "statistics/sectorswithdlsfromyear?code=" + code
        );
    }
}
