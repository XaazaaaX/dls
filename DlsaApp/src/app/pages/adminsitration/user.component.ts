/**
 * UserComponent – Verwaltung von Benutzern mit Anzeige, Erstellung, Bearbeitung und Löschung.
 *
 * Funktionen:
 * - Benutzer anzeigen und filtern (Tabelle)
 * - Benutzer anlegen, bearbeiten und löschen
 * - Rollen zuweisen (z. B. Administrator, Benutzer, Gast)
 * - UI über PrimeNG-Komponenten
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
import { Role, User, UserService } from '../../services/user.service';
import { ToggleSwitchModule } from 'primeng/toggleswitch';

@Component({
    selector: 'app-user',
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
        ToggleSwitchModule
    ],
    templateUrl: './user.component.html',
    providers: [MessageService, ConfirmationService]
})
export class UserComponent implements OnInit {

    checked: boolean = false;              // Für ToggleSwitch (z. B. Aktivstatus)
    isEdit: boolean = false;              // Bearbeitungsmodus
    userDialog: boolean = false;          // Sichtbarkeit des Benutzer-Dialogs
    submitted: boolean = false;           // Flag für Validierung nach Speichern

    users = signal<User[]>([]);           // Reaktive Liste aller Benutzer
    user!: User;                          // Aktuell ausgewählter/zu bearbeitender Benutzer

    roles!: any[];                        // Dropdown-Optionen für Rollen

    @ViewChild('dt') dt!: Table;          // Referenz zur Tabelle (für Filter)

    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private userService: UserService
    ) { }

    ngOnInit() {
        this.loadUserData();
    }

    /**
     * Lädt alle Benutzer und initialisiert die verfügbaren Rollen.
     */
    loadUserData() {
        this.userService.getAllUsers().subscribe({
            next: (data) => this.users.set(data),
            error: (err) => {
                this.messageService.add({
                    severity: 'warn',
                    summary: err.error?.title || "Fehler",
                    detail: err.error?.description || "Es gab einen Fehler bei der API-Anfrage."
                });
            }
        });

        // Statische Rollenauswahl
        this.roles = [
            { label: 'Administrator', value: 'Administrator' },
            { label: 'Benutzer', value: 'Benutzer' },
            { label: 'Gast', value: 'Gast' }
        ];
    }

    /**
     * Wendet einen globalen Filter auf die Tabelle an.
     */
    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    /**
     * Öffnet das Formular zum Anlegen eines neuen Benutzers.
     */
    openNew() {
        this.user = {
            active: true,
            role: {}
        };
        this.isEdit = false;
        this.submitted = false;
        this.userDialog = true;
    }

    /**
     * Öffnet das Formular zum Bearbeiten eines bestehenden Benutzers.
     */
    editUser(user: User) {
        this.user = { ...user };
        this.isEdit = true;
        this.userDialog = true;
    }

    /**
     * Schließt den Dialog ohne zu speichern.
     */
    hideDialog() {
        this.userDialog = false;
        this.submitted = false;
    }

    /**
     * Öffnet eine Bestätigungsabfrage und löscht den Benutzer bei Bestätigung.
     */
    deleteUser(user: User) {
        this.confirmationService.confirm({
            message: `Soll der Benutzer "${user.username}" wirklich gelöscht werden?`,
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
                this.userService.deleteUser(user.id).subscribe({
                    next: () => {
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Info',
                            detail: 'Der Benutzer wurde erfolgreich gelöscht!'
                        });

                        this.users.set(this.users().filter(u => u.id !== user.id));
                        this.user = { role: {} };
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

    /**
     * Speichert einen neuen oder bearbeiteten Benutzer.
     */
    saveUser() {

        this.submitted = true;

        if (this.isEdit) {

            if (this.user.username && this.user.role.rolename) {
                this.userService.updateUser(this.user).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Änderungen wurden erfolgreich gespeichert!" });

                        const currentUsers = this.users();
                        const _users = currentUsers.map(user =>
                            user.id === data.id ? { ...user, ...data } : user
                        );

                        this.users.set(_users);

                        this.userDialog = false;
                        this.user = {
                            role: {}
                        };
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }

        } else {

            if (this.user.username && this.user.password && this.user.role.rolename) {
                this.userService.createUser(this.user).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Der Benutzer wurde erfolgreich angelegt!" });

                        this.users.set([ data, ...this.users()]);
                        this.userDialog = false;
                        this.user = {
                            role: {}
                        };
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }
        }
    }
}
