/**
 * MemberComponent – Verwaltung von Mitgliedsdaten, inkl. Erstellung, Bearbeitung und Massenimport.
 *
 * Funktionen:
 * - Mitglieder anzeigen, hinzufügen, bearbeiten und per Datei-Upload importieren
 * - Zuordnung zu Gruppen und Kategorien
 * - Nutzung von PrimeNG für UI-Komponenten und Upload-Funktion
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
import { DatePickerModule } from 'primeng/datepicker';
import { Group, GroupService } from '../../services/group.service';
import { Member, MemberDto, MemberEditDto, MemberService } from '../../services/member.service';
import { Category, CategoryService } from '../../services/category.service';
import { FileUploadModule } from 'primeng/fileupload';


@Component({
    selector: 'app-member',
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
        MultiSelectModule,
        DatePickerModule,
        FileUploadModule
    ],
    templateUrl: './member.component.html',
    providers: [MessageService, ConfirmationService]
})
export class MemberComponent {

    isEdit: boolean = false;                   // Bearbeitungsmodus
    memberDialog: boolean = false;            // Sichtbarkeit des Mitglieder-Dialogs
    memberUploadDialog: boolean = false;      // Sichtbarkeit des Upload-Dialogs
    submitted: boolean = false;               // Validierungsflag

    members = signal<Member[]>([]);           // Reaktive Liste der Mitglieder
    member!: Member;                          // Aktuelles Mitglied im Formular
    memberDto!: MemberDto;                    // DTO zum Erstellen
    memberEditDto!: MemberEditDto;            // DTO zum Bearbeiten

    groups: Group[] = [];                     // Alle verfügbaren Gruppen
    categories: Category[] = [];              // Alle verfügbaren Kategorien

    selectedGroups!: number[];                // Zugewiesene Gruppen (IDs)
    selectedCategories!: number[];            // Zugewiesene Kategorien (IDs)

    selectedFile: File | null = null;         // Für Datei-Upload

    @ViewChild('dt') dt!: Table;              // Tabellenreferenz (z. B. für Filter)

    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private memberService: MemberService,
        private categoryService: CategoryService,
        private groupService: GroupService
    ) { }

    ngOnInit() {
        this.loadMembers();
        this.loadGroups();
        this.loadCategories();
    }

    /**
     * Reagiert auf Dateiauswahl beim Upload.
     */
    onFileChange(event: any): void {
        const file = event.files?.[0];
        if (file) {
            this.selectedFile = file;
        }
    }

    /**
     * Führt den Upload der ausgewählten Datei aus.
     */
    upload(): void {
        if (this.selectedFile) {
            this.memberService.uploadMember(this.selectedFile).subscribe({
                next: (data) => {
                    const count = data.length;
                    this.messageService.add({
                        severity: 'success',
                        summary: 'Info',
                        detail: `${count} Mitglieder wurden erfolgreich angelegt!`
                    });
                    if (count > 0) {
                        this.members.set([...this.members(), ...data]);
                    }
                    this.memberUploadDialog = false;
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
     * Lädt alle Mitglieder von der API.
     */
    loadMembers() {
        this.memberService.getAllMembers().subscribe({
            next: (data) => {
                const formattedData = data.map((member: any) => ({
                    ...member,
                    entryDateFormatted: new Date(member.entryDate).toLocaleDateString('de-DE', {
                        day: '2-digit',
                        month: '2-digit',
                        year: 'numeric'
                    })
                }));
                this.members.set(formattedData);
            },
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
     * Lädt alle Kategorien.
     */
    loadCategories() {
        this.categoryService.getAllCategories().subscribe({
            next: (data) => this.categories = data,
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
     * Lädt alle Gruppen.
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
     * Globaler Textfilter für Tabelle.
     */
    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    /**
     * Öffnet Formular zum Anlegen eines neuen Mitglieds.
     */
    openNew() {
        this.member = {};
        this.selectedGroups = [];
        this.selectedCategories = [];
        this.memberDto = {};
        this.submitted = false;
        this.isEdit = false;
        this.memberDialog = true;
    }

    /**
     * Öffnet den Upload-Dialog.
     */
    openNewUpload() {
        this.selectedFile = null;
        this.memberUploadDialog = true;
    }

    /**
     * Öffnet das Formular zum Bearbeiten eines Mitglieds.
     */
    editMember(member: Member) {
        this.member = { ...member };

        this.selectedGroups = this.member.groups?.map(g => g.id!).filter(Boolean) || [];
        this.selectedCategories = this.member.categories?.map(c => c.id!).filter(Boolean) || [];

        // Datumsfelder konvertieren
        this.member.birthdate &&= new Date(this.member.birthdate);
        this.member.entryDate &&= new Date(this.member.entryDate);
        this.member.leavingDate &&= new Date(this.member.leavingDate);
        this.member.refDate = new Date();

        this.memberDto = {};
        this.memberEditDto = {};

        this.isEdit = true;
        this.memberDialog = true;
    }

    /**
     * Schließt das Mitgliederformular.
     */
    hideDialog() {
        this.memberDialog = false;
        this.submitted = false;
    }

    /**
     * Schließt den Upload-Dialog.
     */
    hideUploadDialog() {
        this.memberUploadDialog = false;
        this.selectedFile = null;
    }

    /**
     * Speichert ein neues oder bearbeitetes Mitglied.
     */
    saveMember() {
        this.submitted = true;

        if (this.isEdit) {
            // Bearbeitungsmodus
            if (this.member.refDate && this.member.memberId && this.member.forename && this.member.surname && this.member.birthdate && this.member.entryDate) {

                this.memberEditDto = {
                    refDate: new Date(Date.UTC(this.member.refDate.getFullYear(), this.member.refDate.getMonth(), this.member.refDate.getDate())),
                    memberId: this.member.memberId,
                    surname: this.member.surname,
                    forename: this.member.forename,
                    birthdate: new Date(Date.UTC(this.member.birthdate.getFullYear(), this.member.birthdate.getMonth(), this.member.birthdate.getDate())),
                    entryDate: new Date(Date.UTC(this.member.entryDate.getFullYear(), this.member.entryDate.getMonth(), this.member.entryDate.getDate())),
                    leavingDate: this.member.leavingDate ? new Date(Date.UTC(this.member.leavingDate.getFullYear(), this.member.leavingDate.getMonth(), this.member.leavingDate.getDate())) : undefined,
                    active: this.member.active,
                    groupIds: this.selectedGroups,
                    categorieIds: this.selectedCategories
                };

                this.memberService.updateMember(this.memberEditDto, this.member.id!).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: 'Info', detail: 'Die Änderungen wurden erfolgreich gespeichert!' });
                        const updated = this.members().map(m => m.id === data.id ? { ...m, ...data } : m);
                        this.members.set(updated);

                        this.memberDialog = false;
                        this.member = {};
                        this.memberDto = {};
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }

        } else {
            // Neuanlage
            if (this.member.memberId && this.member.forename && this.member.surname && this.member.birthdate && this.member.entryDate) {

                this.memberDto = {
                    memberId: this.member.memberId,
                    surname: this.member.surname,
                    forename: this.member.forename,
                    birthdate: new Date(Date.UTC(this.member.birthdate.getFullYear(), this.member.birthdate.getMonth(), this.member.birthdate.getDate())),
                    entryDate: new Date(Date.UTC(this.member.entryDate.getFullYear(), this.member.entryDate.getMonth(), this.member.entryDate.getDate())),
                    active: true,
                    groupIds: this.selectedGroups,
                    categorieIds: this.selectedCategories
                }

                this.memberService.createMember(this.memberDto).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Das Mitglied wurde erfolgreich angelegt!" });

                        this.members.set([data, ...this.members()]);
                        this.memberDialog = false;
                        this.member = {};
                        this.memberDto = {};
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }
        }

    }

    /**
        * Öffnet eine Bestätigungsabfrage und löscht den Bereich bei Bestätigung.
        */
        deleteMember(member: Member) {
            this.confirmationService.confirm({
                message: `Soll das Mitglied "${member.forename} ${member.surname}" wirklich gelöscht werden?`,
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
                    this.memberService.deleteMember(member.id).subscribe({
                        next: () => {
                            this.messageService.add({
                                severity: 'success',
                                summary: 'Info',
                                detail: 'Das Mitglied wurde erfolgreich gelöscht!'
                            });
    
                            this.members.set(this.members().filter(u => u.id !== member.id));
                            this.member = {};
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
