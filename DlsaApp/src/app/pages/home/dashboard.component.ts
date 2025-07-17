/**
 * DashboardComponent – Übersichtskomponente zur Visualisierung von Statistiken rund um Mitglieder und Dienstleistungen.
 *
 * Funktionen:
 * - Zeigt Mitgliederzahlen, Dienstleistungsstunden (jährlich/monatlich)
 * - Zeigt Top-Mitglieder mit den meisten DLS
 * - Interaktive Radar-Darstellung je nach Gruppierung (Bereiche, Sparten etc.)
 * - Charts werden dynamisch mit Primeng/ChartJS erstellt
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Component } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TableModule } from 'primeng/table';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
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
import { MenuModule } from 'primeng/menu';
import { AnnualServiceHours, MemberCount, MonthlyServiceHours, SectorsWithDlsFromYear, StatisticsService, TopDlsMember } from '../../services/statistics.service';
import { FluidModule } from 'primeng/fluid';
import { ChartModule } from 'primeng/chart';
import { TreeModule } from 'primeng/tree';

interface RadarSelectOption {
    name: string;
    code: string;
}

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [
        CommonModule,
        TableModule,
        FormsModule,
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
        ReactiveFormsModule,
        PickListModule,
        MultiSelectModule,
        DatePickerModule,
        MenuModule,
        ChartModule,
        FluidModule,
        TreeModule
    ],
    templateUrl: './dashboard.component.html',
    providers: [MessageService, ConfirmationService, DatePipe]
})
export class DashboardComponent {

    // Menüstruktur (kann für Kontextmenü oder Aktionen genutzt werden)
    items = [
        { label: 'Add New', icon: 'pi pi-fw pi-plus' },
        { label: 'Remove', icon: 'pi pi-fw pi-trash' }
    ];

    memberCount: MemberCount = {};                   // Gesamtzahl aktiver/ausgetretener Mitglieder
    topDlsMembers: TopDlsMember[] = [{}];            // Mitglieder mit den meisten Dienstleistungsstunden

    // Chart-Daten und Optionen
    lineData: any;
    lineOptions: any;

    barData: any;
    barOptions: any;

    radarData: any;
    radarOptions: any;

    // Radar-Auswahl (Dropdown)
    radarSelectOptions: RadarSelectOption[] | undefined;
    radarSelected: RadarSelectOption = { name: 'Bereiche', code: 'sector' };

    constructor(
        private messageService: MessageService,
        private statisticsService: StatisticsService,
    ) { }

    ngOnInit() {
        this.loadMembercount();
        this.loadAnnualServiceHours();
        this.loadMonthlyServiceHours();
        this.loadTopDlsMember();
        this.loadSectorsWithDlsFromYear(this.radarSelected.code);

        // Optionen für Radar-Auswahl (Dropdown)
        this.radarSelectOptions = [
            { name: 'Bereiche', code: 'sector' },
            { name: 'Funktionsgruppen', code: 'group' },
            { name: 'Sparten', code: 'category' },
        ];
    }

    /**
     * Neuladen des Radar-Charts basierend auf der Auswahl (Bereiche, Gruppen, Sparten).
     */
    reloadRadarChart(option: RadarSelectOption) {
        this.loadSectorsWithDlsFromYear(option.code);
    }

    /**
     * Lädt Mitgliederstatistik (gesamt / aktiv / ausgetreten).
     */
    loadMembercount() {
        this.statisticsService.getMemberCount().subscribe({
            next: (data) => this.memberCount = data,
            error: (err) => {
                this.messageService.add({
                    severity: 'warn',
                    summary: err.error?.title || 'Fehler',
                    detail: err.error?.description || 'Verbindungsfehler!'
                });
            }
        });
    }

    /**
     * Lädt Jahresdaten für Dienstleistungsstunden (als Balkendiagramm).
     */
    loadAnnualServiceHours() {
        this.statisticsService.getAnnualServiceHours().subscribe({
            next: (data) => this.setupAnnualServiceHoursChart(data),
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });
    }

    /**
     * Lädt Monatsdaten für Dienstleistungsstunden (als Liniendiagramm).
     */
    loadMonthlyServiceHours() {
        this.statisticsService.getMonthlyServiceHours().subscribe({
            next: (data) => this.setupMonthlyServiceHours(data),
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });
    }

    /**
     * Lädt Top-Mitglieder mit den meisten DLS.
     */
    loadTopDlsMember() {
        this.statisticsService.getTopDlsMember().subscribe({
            next: (data) => this.topDlsMembers = data,
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });
    }

    /**
     * Lädt gruppierte DLS-Daten für Radar-Diagramm.
     */
    loadSectorsWithDlsFromYear(code: string) {
        this.statisticsService.getSelectedWithDlsFromYear(code).subscribe({
            next: (data) => this.setupSectorsWithDlsFromYear(data),
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });
    }

    /**
     * Konfiguration des Balkendiagramms (Jahres-DLS).
     */
    setupAnnualServiceHoursChart(data: AnnualServiceHours) {
        const style = getComputedStyle(document.documentElement);

        this.barData = {
            labels: data.labels,
            datasets: [{
                label: 'DLS',
                data: data.data,
                backgroundColor: style.getPropertyValue('--p-green-400'),
                borderColor: style.getPropertyValue('--p-green-400')
            }]
        };

        this.barOptions = {
            maintainAspectRatio: false,
            aspectRatio: 0.8,
            plugins: {
                legend: {
                    labels: { color: style.getPropertyValue('--text-color') }
                }
            },
            scales: {
                x: {
                    ticks: { color: style.getPropertyValue('--text-color-secondary') },
                    grid: { display: false, drawBorder: false }
                },
                y: {
                    ticks: { color: style.getPropertyValue('--text-color-secondary') },
                    grid: { color: style.getPropertyValue('--surface-border'), drawBorder: false }
                }
            }
        };
    }

    /**
     * Konfiguration des Liniendiagramms (Monatliche DLS).
     */
    setupMonthlyServiceHours(data: MonthlyServiceHours[]) {
        const style = getComputedStyle(document.documentElement);

        this.lineData = {
            labels: ['Januar', 'Februar', 'März', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember'],
            datasets: data.map((entry, idx) => ({
                label: entry.label,
                data: entry.data,
                fill: false,
                borderColor: style.getPropertyValue(`--p-${['blue', 'orange', 'green'][idx]}-400`),
                backgroundColor: style.getPropertyValue(`--p-${['blue', 'orange', 'green'][idx]}-400`),
                tension: 0.4
            }))
        };

        this.lineOptions = {
            maintainAspectRatio: false,
            aspectRatio: 0.8,
            plugins: {
                legend: {
                    labels: { color: style.getPropertyValue('--text-color') }
                }
            },
            scales: {
                x: {
                    ticks: { color: style.getPropertyValue('--text-color-secondary') },
                    grid: { color: style.getPropertyValue('--surface-border'), drawBorder: false }
                },
                y: {
                    ticks: { color: style.getPropertyValue('--text-color-secondary') },
                    grid: { color: style.getPropertyValue('--surface-border'), drawBorder: false }
                }
            }
        };
    }

    /**
     * Konfiguration des Radar-Diagramms (nach Gruppierung).
     */
    setupSectorsWithDlsFromYear(data: SectorsWithDlsFromYear) {
        const style = getComputedStyle(document.documentElement);
        const colors = [
            '--p-indigo-400', '--p-purple-400', '--p-blue-400',
            '--p-red-400', '--p-green-400', '--p-orange-400', '--p-teal-400'
        ];

        data.body?.forEach((dataset, index) => {
            const color = style.getPropertyValue(colors[index % colors.length]);
            Object.assign(dataset, {
                borderColor: color,
                pointBackgroundColor: color,
                pointBorderColor: color,
                pointHoverBackgroundColor: style.getPropertyValue('--text-color'),
                pointHoverBorderColor: color
            });
        });

        this.radarData = {
            labels: data.labels,
            datasets: data.body
        };

        this.radarOptions = {
            plugins: {
                legend: {
                    labels: { color: style.getPropertyValue('--text-color') }
                }
            },
            scales: {
                r: {
                    pointLabels: { color: style.getPropertyValue('--text-color') },
                    grid: { color: style.getPropertyValue('--surface-border') }
                }
            }
        };
    }
}