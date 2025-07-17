/**
 * AnnualEvaluationComponent – Verwaltung und Ausführung von Jahresauswertungen (Evaluationsläufen).
 *
 * Funktionen:
 * - Lädt abgeschlossene Evaluationsläufe
 * - Startet neue Auswertungen (Jahreslauf) mit Ergebnis als CSV-Datei
 * - Ermöglicht das Herunterladen bestehender CSV-Dateien
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Component, signal, ViewChild } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Table, TableModule } from 'primeng/table';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { RatingModule } from 'primeng/rating';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { SelectModule } from 'primeng/select';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputNumberModule } from 'primeng/inputnumber';
import { DialogModule } from 'primeng/dialog';
import { TagModule } from 'primeng/tag';
import { InputIconModule } from 'primeng/inputicon';
import { IconFieldModule } from 'primeng/iconfield';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToggleSwitchModule } from 'primeng/toggleswitch';
import { PickListModule } from 'primeng/picklist';
import { MultiSelectModule } from 'primeng/multiselect';
import { DatePickerModule } from 'primeng/datepicker';
import { CourseOfYear, EvaluationService, Year } from '../../services/evaluation.service';


@Component({
    selector: 'app-evaluation',
    standalone: true,
    imports: [
        CommonModule,
        TableModule,
        FormsModule,
        ReactiveFormsModule,
        ButtonModule,
        RippleModule,
        ToastModule,
        ToolbarModule,
        RatingModule,
        InputTextModule,
        TextareaModule,
        SelectModule,
        RadioButtonModule,
        InputNumberModule,
        DialogModule,
        TagModule,
        InputIconModule,
        IconFieldModule,
        ConfirmDialogModule,
        ToggleSwitchModule,
        PickListModule,
        MultiSelectModule,
        DatePickerModule
    ],
    templateUrl: './annualevaluation.component.html',
    providers: [MessageService, ConfirmationService, DatePipe]
})
export class AnnualEvaluationComponent {

    evaluationDialog: boolean = false;       // Sichtbarkeit des Dialogfensters für neue Auswertung
    submitted: boolean = false;              // Validierungsstatus bei Ausführung

    coys = signal<CourseOfYear[]>([]);       // Reaktive Liste aller Auswertungen
    coy!: CourseOfYear;                      // Aktuell selektierte Auswertung

    year?: number;                           // Gewähltes Jahr für die neue Auswertung
    preEvaluation: boolean = true;           // true = Vorprüfung, false = Hauptlauf
    years: Year[] = [];                      // Liste verfügbarer Jahre

    @ViewChild('dt') dt!: Table;             // Referenz zur Tabelle (für Filter)

    constructor(
        private messageService: MessageService,
        private evaluationService: EvaluationService,
        private datePipe: DatePipe
    ) { }

    ngOnInit() {
        this.loadYears();
        this.loadCourseOfYears();
    }

    /**
     * Lädt alle verfügbaren Jahre aus der API.
     */
    loadYears() {
        this.evaluationService.getAllYears().subscribe({
            next: (data) => this.years = data,
            error: (err) => {
                this.messageService.add({
                    severity: 'warn',
                    summary: err.error.title,
                    detail: err.error.description
                });
            }
        });
    }

    /**
     * Lädt alle durchgeführten Auswertungen (COY) aus der API.
     */
    loadCourseOfYears() {
        this.evaluationService.getAllEvaluations().subscribe({
            next: (data) => this.coys.set(data),
            error: (err) => {
                this.messageService.add({
                    severity: 'warn',
                    summary: err.error?.title || "Verbindungsfehler!",
                    detail: err.error?.description || "Es gab einen Fehler bei der API-Anfrage."
                });
            }
        });
    }

    /**
     * Filtert die Tabelle global über ein Suchfeld.
     */
    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    /**
     * Öffnet das Dialogfenster zur Auswahl und Start eines neuen Evaluationslaufs.
     */
    openNew() {
        this.submitted = false;
        this.evaluationDialog = true;
        this.year = undefined;
        this.preEvaluation = true;
    }

    /**
     * Schließt den Auswertungsdialog.
     */
    hideDialog() {
        this.evaluationDialog = false;
        this.submitted = false;
    }

    /**
     * Startet den Jahresauswertungsprozess und lädt eine CSV-Datei als Ergebnis herunter.
     */
    executeEvaluation() {
        this.submitted = true;

        if (this.year) {
            this.evaluationService.doEvaluation(this.year, !this.preEvaluation).subscribe({
                next: response => {
                    const contentDisposition = response.headers.get('Content-Disposition');
                    const filename = this.getFilenameFromHeader(contentDisposition) || 'Jahreslauf.csv';

                    const blob = response.body!;
                    const url = window.URL.createObjectURL(blob);

                    const a = document.createElement('a');
                    a.href = url;
                    a.download = filename;
                    a.click();
                    window.URL.revokeObjectURL(url);

                    this.hideDialog();
                    this.loadCourseOfYears();
                },
                error: () => {
                    this.messageService.add({
                        severity: 'warn',
                        summary: 'Hinweis!',
                        detail: 'Der Jahreslauf kann erst gestartet werden, wenn der Zeitraum beendet ist! Bitte vorhandene Ergebnisse prüfen.'
                    });
                }
            });
        }
    }

    /**
     * Extrahiert den Dateinamen aus dem Content-Disposition Header.
     */
    private getFilenameFromHeader(contentDisposition: string | null): string | null {
        if (!contentDisposition) return null;
        const matches = /filename="([^"]+)"/.exec(contentDisposition);
        return matches?.[1] ?? null;
    }

    /**
     * Wandelt Base64-Daten in ein ArrayBuffer (z. B. für Datei-Downloads).
     */
    base64ToArrayBuffer(base64: string): ArrayBuffer {
        const binaryString = atob(base64);
        const len = binaryString.length;
        const bytes = new Uint8Array(len);
        for (let i = 0; i < len; i++) {
            bytes[i] = binaryString.charCodeAt(i);
        }
        return bytes.buffer;
    }

    /**
     * Löst den CSV-Download einer bestehenden Auswertung aus.
     */
    downloadCsv(coy: CourseOfYear) {
        const blob = new Blob([this.base64ToArrayBuffer(coy.file!)], { type: 'text/csv' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = coy.filename!;
        a.click();
        window.URL.revokeObjectURL(url);
    }

    /**
     * Formatiert ein Datumsobjekt in deutsches Format (dd.MM.yyyy).
     */
    formatDate(date: Date): string | null {
        return this.datePipe.transform(date, 'dd.MM.yyyy');
    }
}