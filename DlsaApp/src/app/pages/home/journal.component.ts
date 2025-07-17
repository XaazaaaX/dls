/**
 * JournalComponent – Verwaltung und Erfassung von Dienstleistungs-Buchungen (DLS).
 *
 * Funktionen:
 * - Anzeigen, Erstellen und Stornieren von Buchungen (inkl. Auswahl von Mitgliedern und Aktionen)
 * - Verwendung von PrimeNG-Komponenten für UI (Dialoge, Tabellen, Toasts)
 * - Sortierte Anzeige nach Datum der Leistungserbringung (doneDate)
 * - Stornierte Buchungen werden visuell hervorgehoben
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Component, signal, ViewChild } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Table, TableModule } from 'primeng/table';
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
import { Booking, BookingDto, BookingService } from '../../services/booking.service';
import { Member, MemberService } from '../../services/member.service';
import { Action, ActionService } from '../../services/action.service';


@Component({
    selector: 'app-journal',
    standalone: true,
    imports: [
        // Alle benötigten Angular- und PrimeNG-Module
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
        DatePickerModule
    ],
    templateUrl: `./journal.component.html`,
    providers: [MessageService, ConfirmationService, DatePipe]
})
export class JournalComponent {

    // Steuert den Editiermodus und die Sichtbarkeit des Buchungsdialogs
    isEdit: boolean = false;
    bookingDialog: boolean = false;
    submitted: boolean = false;

    // Signal zur Speicherung der Buchungsliste (reaktiv)
    bookings = signal<Booking[]>([]);
    booking!: Booking; // Aktuell ausgewählte Buchung
    bookingDto!: BookingDto; // DTO für Backend-Kommunikation

    // Ausgewählte IDs zur Erstellung neuer Buchungen
    actionId?: number;
    memberId?: number;

    // Datenquellen für Auswahlfelder
    members: Member[] = [];
    actions: Action[] = [];

    selectedGroups!: number[];
    selectedCategories!: number[];

    // Referenz zur Tabelle für Filterung
    @ViewChild('dt') dt!: Table;

    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private bookingService: BookingService,
        private memberService: MemberService,
        private actionService: ActionService,
        private datePipe: DatePipe
    ) { }

    ngOnInit() {
        // Beim Initialisieren: Daten laden
        this.loadBookings();
        this.loadMembers();
        this.loadActions();
    }

    // Mapping der Mitgliederliste für Anzeige in Dropdown
    get fullNameMemberOptions() {
        return this.members.map(member => ({
            id: member.id,
            fullname: member.surname + ", " + member.forename
        }));
    }

    // Buchungen vom Backend laden
    loadBookings() {
        this.bookingService.getAllBookings().subscribe({
            next: (data) => {
                this.bookings.set(data);
                console.log(data);
            },
            error: (err) => {
                const detail = err.error?.description || "Es gab einen Fehler bei der API-Anfrage.";
                this.messageService.add({ severity: 'warn', summary: "Verbindungsfehler!", detail });
            }
        });
    }

    // Mitglieder vom Backend laden
    loadMembers() {
        this.memberService.getAllMembers().subscribe({
            next: (data) => this.members = data,
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });
    }

    // Aktionen vom Backend laden
    loadActions() {
        this.actionService.getAllActions().subscribe({
            next: (data) => this.actions = data,
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });
    }

    // Globale Tabellenfilterung
    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    // Dialog für neue Buchung öffnen
    openNew() {
        this.booking = {};
        this.bookingDto = {};
        this.submitted = false;
        this.isEdit = false;
        this.bookingDialog = true;
    }

    // Buchung stornieren mit Bestätigungsdialog
    cancelBooking(booking: Booking) {
        this.confirmationService.confirm({
            message: `Soll die folgende Buchung wirklich storniert werden?<br/><br/>
                Ableistungsdatum: ${this.formatDate(booking.doneDate!)}<br/>
                Mitgliednummer: ${booking.member?.memberId}<br/>
                Aktion: ${booking.action?.description}<br/>
                Anzahl Dls: ${booking.countDls}<br/>
                Bemerkung: ${booking.comment}<br/>`,
            header: 'Bestätigen',
            icon: 'pi pi-exclamation-triangle',
            rejectButtonProps: { icon: 'pi pi-times', label: 'Nein', outlined: true },
            acceptButtonProps: { icon: 'pi pi-check', label: 'Ja' },
            accept: () => {
                this.bookingService.cancelBooking(booking.id!).subscribe({
                    next: () => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Buchung wurden erfolgreich storniert!" });
                        this.loadBookings();
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }
        });
    }

    // Dialog schließen
    hideDialog() {
        this.bookingDialog = false;
        this.submitted = false;
    }

    // Neue Buchung speichern
    saveBooking() {
        this.submitted = true;

        if (this.booking.countDls && this.booking.doneDate && this.memberId && this.actionId) {
            this.bookingDto = {
                countDls: this.booking.countDls,
                comment: this.booking.comment,
                doneDate: new Date(Date.UTC(
                    this.booking.doneDate.getFullYear(),
                    this.booking.doneDate.getMonth(),
                    this.booking.doneDate.getDate()
                )),
                memberId: this.memberId,
                actionId: this.actionId
            };

            this.bookingService.createBooking(this.bookingDto).subscribe({
                next: (data) => {
                    this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Buchung wurde erfolgreich angelegt!" });

                    const updatedBookings = [...this.bookings(), data];

                    // Nach Datum sortieren (neueste zuerst, leere ans Ende)
                    updatedBookings.sort((a, b) => {
                        const dateA = a.doneDate ? new Date(a.doneDate).getTime() : Infinity;
                        const dateB = b.doneDate ? new Date(b.doneDate).getTime() : Infinity;
                        return dateB - dateA;
                    });

                    this.bookings.set(updatedBookings);
                    this.bookingDialog = false;
                    this.booking = {};
                    this.bookingDto = {};
                    this.memberId = undefined;
                    this.actionId = undefined;
                },
                error: (err) => {
                    this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                }
            });
        }
    }

    // Datum formatieren für Anzeige
    formatDate(date: Date): string | null {
        return this.datePipe.transform(date, 'dd.MM.yyyy');
    }

    // Zeilenhervorhebung für stornierte Buchungen
    rowStyle(booking: Booking) {
        if (booking.canceled) {
            return { backgroundColor: 'var(--p-content-hover-background)' };
        }
        return;
    }
}