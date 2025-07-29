/**
 * GroupComponent – Verwaltung von Gruppen mit Anzeige, Erstellung und Bearbeitung.
 *
 * Funktionen:
 * - Anzeigen aller Gruppen in einer Tabelle
 * - Neue Gruppen erstellen und bestehende bearbeiten
 * - Integration von PrimeNG-Komponenten für UI/UX
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
import { Group, GroupService } from '../../services/group.service';


@Component({
    selector: 'app-group',
    standalone: true,
    imports: [
        // Angular & PrimeNG Module
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
    templateUrl: './group.component.html',
    providers: [MessageService, ConfirmationService]
})
export class GroupComponent {

    isEdit: boolean = false;           // Ob aktuell im Bearbeitungsmodus
    groupDialog: boolean = false;      // Sichtbarkeit des Dialogs
    submitted: boolean = false;        // Ob ein Speicherversuch unternommen wurde

    groups = signal<Group[]>([]);      // Reaktive Liste der Gruppen
    group!: Group;                     // Aktuelle Gruppe im Formular

    @ViewChild('dt') dt!: Table;       // Referenz auf die Tabelle (z. B. für Filter)

    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private groupService: GroupService
    ) { }

    ngOnInit() {
        this.loadGroups();
    }

    /**
     * Lädt alle Gruppen von der API und speichert sie im lokalen State.
     */
    loadGroups() {
        this.groupService.getAllGroups().subscribe({
            next: (data) => {
                this.groups.set(data);
                console.log(data); // Debug-Ausgabe (optional)
            },
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
     * Wendet einen globalen Filter auf die Tabelle an.
     */
    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    /**
     * Öffnet den Dialog zum Erstellen einer neuen Gruppe.
     */
    openNew() {
        this.group = { liberated: false }; // Standardwert für neue Gruppe
        this.submitted = false;
        this.groupDialog = true;
        this.isEdit = false;
    }

    /**
     * Öffnet den Dialog zum Bearbeiten einer bestehenden Gruppe.
     */
    editGroup(group: Group) {
        this.group = { ...group }; // Kopie der Gruppe zum Bearbeiten
        this.isEdit = true;
        this.groupDialog = true;
    }

    /**
     * Schließt den Dialog ohne Speichern.
     */
    hideDialog() {
        this.groupDialog = false;
        this.submitted = false;
    }

    /**
     * Speichert eine neue oder bearbeitete Gruppe.
     */
    saveGroup() {
        this.submitted = true;

        if (!this.group.groupName) return; // Name muss vorhanden sein

        if (this.isEdit) {
            // Update bestehender Gruppe
            this.groupService.updateGroup(this.group).subscribe({
                next: (data) => {
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Info',
                        detail: 'Die Änderungen wurden erfolgreich gespeichert!'
                    });

                    const updatedGroups = this.groups().map(group =>
                        group.id === data.id ? { ...group, ...data } : group
                    );
                    this.groups.set(updatedGroups);

                    this.groupDialog = false;
                    this.group = {};
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
            // Neue Gruppe erstellen
            this.groupService.createGroup(this.group).subscribe({
                next: (data) => {
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Info',
                        detail: 'Die Gruppe wurde erfolgreich angelegt!'
                    });

                    this.groups.set([data, ...this.groups()]);
                    this.groupDialog = false;
                    this.group = {};
                },
                error: (err) => {
                    console.log("err", err); // Debug-Ausgabe
                    this.messageService.add({
                        severity: 'warn',
                        summary: err.error.title,
                        detail: err.error.description
                    });
                }
            });
        }
    }

    /**
        * Öffnet eine Bestätigungsabfrage und löscht die Gruppe bei Bestätigung.
        */
    deleteGroup(group: Group) {
        this.confirmationService.confirm({
            message: `Soll die Gruppe "${group.groupName}" wirklich gelöscht werden? HINWEIS: Nur löschen, wenn die Gruppe keinem Mitglied zugewiesen ist oder war!`,
            header: 'Bestätigen',
            icon: 'pi pi-exclamation-triangle',
            rejectButtonProps: {
                icon: 'pi pi-times',
                label: 'Nein',
                outlined: true
            },
            acceptButtonProps: {
                icon: 'pi pi-check',
                label: 'Ja'
            },
            accept: () => {
                this.groupService.deleteGroup(group.id).subscribe({
                    next: () => {
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Info',
                            detail: 'Die Gruppe wurde erfolgreich gelöscht!'
                        });

                        this.groups.set(this.groups().filter(u => u.id !== group.id));
                        this.group = {};
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
        });
    }
}