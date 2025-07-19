/**
 * CategoryComponent – Verwaltung von Kategorien (Sparten) mit Anzeige, Erstellung und Bearbeitung.
 *
 * Funktionen:
 * - Kategorien anzeigen (PrimeNG-Tabelle)
 * - Neue Kategorien anlegen und bestehende bearbeiten
 * - Verwendung von PrimeNG-UI-Komponenten
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
import { Category, CategoryService } from '../../services/category.service';

@Component({
    selector: 'app-category',
    standalone: true,
    imports: [
        // Angular/PrimeNG Module
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
    templateUrl: './category.component.html',
    providers: [MessageService, ConfirmationService]
})
export class CategoryComponent {

    isEdit: boolean = false;           // Bearbeitungsmodus
    categoryDialog: boolean = false;   // Sichtbarkeit des Dialogs
    submitted: boolean = false;        // Validierungsstatus

    categories = signal<Category[]>([]); // Reaktive Liste aller Kategorien
    category!: Category;                 // Aktuell ausgewählte/zu bearbeitende Kategorie

    @ViewChild('dt') dt!: Table;        // Referenz auf die Tabelle (für Filter etc.)

    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private categoryService: CategoryService
    ) { }

    ngOnInit() {
        this.loadCategories();
    }

    /**
     * Ruft alle Kategorien über die API ab und speichert sie im lokalen Zustand.
     */
    loadCategories() {
        this.categoryService.getAllCategories().subscribe({
            next: (data) => this.categories.set(data),
            error: (err) => {
                this.messageService.add({
                    severity: 'warn',
                    summary: err.error?.title || "Fehler",
                    detail: err.error?.description || "Es gab einen Fehler bei der API-Anfrage."
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
     * Öffnet das Dialogformular für eine neue Kategorie.
     */
    openNew() {
        this.category = {};
        this.isEdit = false;
        this.submitted = false;
        this.categoryDialog = true;
    }

    /**
     * Öffnet das Dialogformular zum Bearbeiten einer bestehenden Kategorie.
     */
    editCategory(category: Category) {
        this.category = { ...category };
        this.isEdit = true;
        this.categoryDialog = true;
    }

    /**
     * Schließt den Dialog ohne Speichern.
     */
    hideDialog() {
        this.categoryDialog = false;
        this.submitted = false;
    }

    /**
     * Speichert eine neue oder bearbeitete Kategorie.
     * Erfolgt über die entsprechenden API-Methoden.
     */
    saveCategory() {
        this.submitted = true;

        if (!this.category.categoryName) return;

        if (this.isEdit) {
            // Bearbeitungsmodus: bestehende Kategorie aktualisieren
            this.categoryService.updateCategory(this.category).subscribe({
                next: (data) => {
                    this.messageService.add({
                        severity: 'success',
                        summary: "Info",
                        detail: "Die Änderungen wurden erfolgreich gespeichert!"
                    });

                    // Lokale Liste aktualisieren
                    const updatedList = this.categories().map(category =>
                        category.id === data.id ? { ...category, ...data } : category
                    );
                    this.categories.set(updatedList);

                    this.categoryDialog = false;
                    this.category = {};
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
            // Neue Kategorie anlegen
            this.categoryService.createCategory(this.category).subscribe({
                next: (data) => {
                    this.messageService.add({
                        severity: 'success',
                        summary: "Info",
                        detail: "Die Sparte wurde erfolgreich angelegt!"
                    });

                    this.categories.set([data, ...this.categories()]);
                    this.categoryDialog = false;
                    this.category = {};
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

    /**
    * Öffnet eine Bestätigungsabfrage und löscht die Sparte bei Bestätigung.
    */
    deleteCategory(category: Category) {
        this.confirmationService.confirm({
            message: `Soll die Sparte "${category.categoryName}" wirklich gelöscht werden?`,
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
                this.categoryService.deleteCategory(category.id).subscribe({
                    next: () => {
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Info',
                            detail: 'Die Sparte wurde erfolgreich gelöscht!'
                        });

                        this.categories.set(this.categories().filter(u => u.id !== category.id));
                        this.category = {};
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