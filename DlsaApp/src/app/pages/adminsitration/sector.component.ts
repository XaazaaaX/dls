/**
 * SectorComponent – Verwaltung von Bereichen (Sektoren) inklusive Erstellung, Bearbeitung und Zuweisung zu Gruppen.
 *
 * Funktionen:
 * - Anzeige und Filterung von Sektoren
 * - Erstellen und Bearbeiten von Sektoren mit Gruppenzuweisung
 * - Verwendung von PrimeNG für UI-Komponenten
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Component, signal, ViewChild } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Table, TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';
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
import { Sector, SectorDto, SectorService } from '../../services/sector.service';
import { Group, GroupService } from '../../services/group.service';


@Component({
    selector: 'app-sector',
    standalone: true,
    imports: [
        // Angular / PrimeNG Module
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
        MultiSelectModule
    ],
    templateUrl: './sector.component.html',
    providers: [MessageService, ConfirmationService]
})
export class SectorComponent {

    isEdit: boolean = false;              // Flag für Bearbeitungsmodus
    sectorDialog: boolean = false;       // Sichtbarkeit des Dialogfensters
    submitted: boolean = false;          // Validierungsstatus

    sectors = signal<Sector[]>([]);      // Reaktive Liste der Sektoren

    groups: Group[] = [];                // Alle verfügbaren Gruppen

    sector!: Sector;                     // Aktueller Sektor
    selectedGroups!: number[];          // Zugewiesene Gruppen-IDs
    sectorDto!: SectorDto;              // DTO für Erstellen/Bearbeiten

    @ViewChild('dt') dt!: Table;         // Referenz auf die Tabelle für Filter

    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private sectorService: SectorService,
        private groupService: GroupService
    ) { }

    ngOnInit() {
        this.loadSectors();
        this.loadGroups();
    }

    /**
     * Lädt alle Sektoren von der API.
     */
    loadSectors() {
        this.sectorService.getAllSectors().subscribe({
            next: (data) => this.sectors.set(data),
            error: (err) => {
                this.messageService.add({
                    severity: 'warn',
                    summary: err.error?.title || 'Fehler',
                    detail: err.error?.description || 'Es gab einen Fehler bei der API-Anfrage.'
                });
            }
        });
    }

    /**
     * Lädt alle Gruppen für die Zuordnung im Sektorformular.
     */
    loadGroups() {
        this.groupService.getAllGroups().subscribe({
            next: (data) => this.groups = data,
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
     * Filtert die Tabelle global (nach Suchbegriff).
     */
    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    /**
     * Öffnet Dialog zum Erstellen eines neuen Sektors.
     */
    openNew() {
        this.sector = {};
        this.selectedGroups = [];
        this.sectorDto = {};
        this.isEdit = false;
        this.submitted = false;
        this.sectorDialog = true;
    }

    /**
     * Öffnet Dialog zum Bearbeiten eines bestehenden Sektors.
     */
    editSector(sector: Sector) {
        this.sector = { ...sector };

        // Gruppen extrahieren und in die Auswahl übertragen
        this.selectedGroups = this.sector.groups
            ?.map(group => group.id)
            .filter(id => id !== undefined) as number[] || [];

        this.sectorDto = {};
        this.isEdit = true;
        this.sectorDialog = true;
    }

    /**
     * Schließt den Dialog ohne zu speichern.
     */
    hideDialog() {
        this.sectorDialog = false;
        this.submitted = false;
    }

    /**
     * Speichert einen neuen oder bearbeiteten Sektor.
     */
    saveSector() {
        this.submitted = true;

        if (!this.sector.sectorname) return;

        this.sectorDto = {
            sectorname: this.sector.sectorname,
            groupIds: this.selectedGroups
        };

        if (this.isEdit) {
            // Bearbeitung
            this.sectorService.updateSector(this.sectorDto, this.sector.id!).subscribe({
                next: (data) => {
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Info',
                        detail: 'Die Änderungen wurden erfolgreich gespeichert!'
                    });

                    const updated = this.sectors().map(s =>
                        s.id === data.id ? { ...s, ...data } : s
                    );
                    this.sectors.set(updated);

                    this.sectorDialog = false;
                    this.sector = {};
                    this.sectorDto = {};
                },
                error: (err) => {
                    this.messageService.add({
                        severity: 'warn',
                        summary: err.error.title,
                        detail: err.error.description
                    });
                }
            });

        } else {
            // Neuanlage
            this.sectorService.createSectors(this.sectorDto).subscribe({
                next: (data) => {
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Info',
                        detail: 'Der Bereich wurde erfolgreich angelegt!'
                    });

                    this.sectors.set([...this.sectors(), data]);
                    this.sectorDialog = false;
                    this.sector = {};
                    this.sectorDto = {};
                },
                error: (err) => {
                    this.messageService.add({
                        severity: 'warn',
                        summary: err.error.title,
                        detail: err.error.description
                    });
                }
            });
        }
    }
}