/**
 * ActionComponent – Verwaltung und Anzeige von Aktionen (CRUD) mit PrimeNG-Tabelle und Dialog.
 *
 * Funktionen:
 * - Anzeige aller Aktionen
 * - Erstellen und Bearbeiten von Aktionen
 * - Integration mit PrimeNG-Komponenten für UI/UX
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Component, OnInit, signal, ViewChild } from '@angular/core';
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
import { InputMaskModule } from 'primeng/inputmask';
import { Action, ActionDto, ActionService } from '../../services/action.service';
import { Member, MemberService } from '../../services/member.service';

@Component({
    selector: 'app-action',
    standalone: true,
    imports: [
        // PrimeNG & Angular Module für UI und Formulare
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
        InputMaskModule
    ],
    templateUrl: './action.component.html',
    providers: [MessageService, ConfirmationService]
})
export class ActionComponent implements OnInit {

    isEdit: boolean = false; // Flag für Bearbeitungsmodus
    actionDialog: boolean = false; // Sichtbarkeit des Dialogfensters
    submitted: boolean = false; // Validierungsstatus beim Speichern

    members: Member[] = []; // Liste aller Mitglieder (für Dropdown)

    actions = signal<Action[]>([]); // Reaktive Liste der Aktionen
    action!: Action; // Aktuelle Aktion (zum Bearbeiten)
    actionDto!: ActionDto; // DTO für API-Aufrufe

    contactId!: string; // Aktuell ausgewählter Kontakt (Mitglied)

    @ViewChild('dt') dt!: Table; // Referenz auf die Tabelle

    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private memberService: MemberService,
        private actionService: ActionService
    ) { }

    ngOnInit() {
        this.loadActions();
        this.loadMembers();
    }

    /**
     * Gibt eine Liste aller Mitglieder als Optionen mit Vor- und Nachname zurück.
     */
    get fullNameMemberOptions() {
        return this.members.map(member => ({
            id: member.id,
            fullname: `${member.surname}, ${member.forename}`
        }));
    }

    /**
     * Lädt alle Aktionen von der API.
     */
    loadActions() {
        this.actionService.getAllActions().subscribe({
            next: (data) => this.actions.set(data),
            error: (err) => {
                console.log(err);
                this.messageService.add({
                    severity: 'warn',
                    summary: err.error?.title || "Fehler",
                    detail: err.error?.description || "Es gab einen Fehler bei der API-Anfrage."
                });
            }
        });
    }

    /**
     * Lädt alle Mitglieder für die Zuordnung in der Aktionsmaske.
     */
    loadMembers() {
        this.memberService.getAllMembers().subscribe({
            next: (data) => this.members = data,
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
     * Filtert die Tabelle global anhand des Suchfelds.
     */
    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    /**
     * Öffnet den Dialog für das Erstellen einer neuen Aktion.
     */
    openNew() {
        this.action = {};
        this.actionDto = {};
        this.submitted = false;
        this.actionDialog = true;
        this.isEdit = false;
        this.contactId = "";
    }

    /**
     * Öffnet den Dialog zum Bearbeiten einer bestehenden Aktion.
     */
    editAction(action: Action) {
        this.action = { ...action };
        this.isEdit = true;
        this.contactId = this.action.contact?.id!;
        this.actionDialog = true;
        this.actionDto = {};
    }

    /**
     * Schließt den Dialog ohne Speichern.
     */
    hideDialog() {
        this.actionDialog = false;
        this.submitted = false;
    }

    /**
     * Speichert eine neue oder bearbeitete Aktion über die API.
     */
    saveAction() {
        this.submitted = true;

        if (this.action.year && this.action.description && this.contactId) {
            this.actionDto = {
                year: this.action.year,
                description: this.action.description,
                contactId: this.contactId
            };

            if (this.isEdit) {
                // Aktion bearbeiten
                this.actionService.updateAction(this.actionDto, this.action.id!).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Änderungen wurden erfolgreich gespeichert!" });

                        const updatedActions = this.actions().map(action =>
                            action.id === data.id ? { ...action, ...data } : action
                        );
                        this.actions.set(updatedActions);

                        this.actionDialog = false;
                        this.action = {};
                        this.actionDto = {};
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
                // Neue Aktion erstellen
                this.actionService.createActions(this.actionDto).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Aktion wurde erfolgreich angelegt!" });

                        this.actions.set([data, ...this.actions()]);
                        this.actionDialog = false;
                        this.action = {};
                        this.actionDto = {};
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
}