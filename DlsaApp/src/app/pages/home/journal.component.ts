import { Component, OnInit, signal, ViewChild } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Table, TableModule } from 'primeng/table';
import { CommonModule, DatePipe } from '@angular/common';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
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
import { Category, CategoryService } from '../../services/category.service';
import { Booking, BookingDto, JournalService } from '../../services/journal.service';
import { Member, MemberService } from '../../services/member.service';
import { Action, ActionService } from '../../services/action.service';


@Component({
    selector: 'app-journal',
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
        DatePickerModule
    ],
    templateUrl: `./journal.component.html`,
    providers: [MessageService, ConfirmationService, DatePipe]
})
export class JournalComponent {

    isEdit: boolean = false;
    bookingDialog: boolean = false;
    submitted: boolean = false;

    bookings = signal<Booking[]>([]);
    booking!: Booking;
    bookingDto!: BookingDto;

    actionId?: number;
    memberId?: number;

    members: Member[] = [];
    actions: Action[] = [];

    selectedGroups!: number[];
    selectedCategories!: number[];

    @ViewChild('dt') dt!: Table;


    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private journalService: JournalService,
        private memberService: MemberService,
        private actionService: ActionService,
        private datePipe: DatePipe
    ) { }

    ngOnInit() {
        this.loadbookings();
        this.loadMembers();
        this.loadActions();
    }

    get fullNameMemberOptions() {
        return this.members.map(member => ({
            id: member.id,
            fullname: member.surname + ", " + member.forename
        }));
    }

    loadbookings() {
        this.journalService.getAllBookings().subscribe({
            next: (data) => {
                this.bookings.set(data);
                console.log(data);
            },
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });
    }

    loadMembers() {
        this.memberService.getAllMembers().subscribe({
            next: (data) => {
                this.members = data;
            },
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });
    }

    loadActions() {
        this.actionService.getAllActions().subscribe({
            next: (data) => {
                this.actions = data;
            },
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });
    }

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    openNew() {
        this.booking = {};
        this.bookingDto = {};
        this.submitted = false;
        this.isEdit = false;
        this.bookingDialog = true;
    }


    cancelBooking(booking: Booking) {
        /*this.booking = { ...booking };

        this.selectedGroups = this.booking.groups!
        .map(group => group.id)
        .filter(id => id !== undefined) as number[];

        this.selectedCategories = this.booking.categories!
        .map(category => category.id)
        .filter(id => id !== undefined) as number[];

        this.booking.birthdate ? this.booking.birthdate = new Date(this.booking.birthdate!) : null;
        this.booking.entryDate ? this.booking.entryDate = new Date(this.booking.entryDate!) : null;
        this.booking.leavingDate ? this.booking.leavingDate = new Date(this.booking.leavingDate!) : null;

        this.bookingDto = {};

        this.isEdit = true;
        this.bookingDialog = true;
        */
        this.confirmationService.confirm({
            message: 'Soll die folgende Buchung wirklich gelöscht werden? <br/><br/>' +
                'Ableistungsdatum: ' + this.formatDate(booking.doneDate!) + '<br/>' +
                'Mitgliednummer: ' + booking.member?.memberId + '<br/>' +
                'Aktion: ' + booking.action?.description + '<br/>' +
                'Anzahl Dls: ' + booking.countDls + '<br/>' +
                'Bemerkung: ' + booking.comment + '<br/>',
            header: 'Bestätigen',
            icon: 'pi pi-exclamation-triangle',
            rejectButtonProps: {
                icon: 'pi pi-times',
                label: 'Nein',
                outlined: true,
            },
            acceptButtonProps: {
                icon: 'pi pi-check',
                label: 'Ja',
            },
            accept: () => {

                this.journalService.cancelBooking(booking.id!).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Buchung wurden erfolgreich stroniert!" });

                        const currentBookings = this.bookings();
                        const _bookings = currentBookings.map(booking =>
                            booking.id === data.id ? { ...booking, ...data } : booking
                        );

                        this.bookings.set(_bookings);
                        this.booking = {};
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }
        });
    }

    hideDialog() {
        this.bookingDialog = false;
        this.submitted = false;
    }



    saveBooking() {
        this.submitted = true;

        if (this.booking.countDls && this.booking.comment && this.booking.doneDate && this.memberId && this.actionId) {

            this.bookingDto = {
                countDls: this.booking.countDls,
                comment: this.booking.comment,
                doneDate: new Date(Date.UTC(this.booking.doneDate.getFullYear(), this.booking.doneDate.getMonth(), this.booking.doneDate.getDate())),
                memberId: this.memberId,
                actionId: this.actionId
            }

            this.journalService.createBooking(this.bookingDto).subscribe({
                next: (data) => {
                    this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Buchung wurde erfolgreich angelegt!" });


                    // Aktuelle Buchungen holen + neue einfügen
                    const updatedBookings = [...this.bookings(), data];

                    // Sortieren nach doneDate (null kommt ans Ende)
                    updatedBookings.sort((a, b) => {
                        const dateA = a.doneDate ? new Date(a.doneDate).getTime() : Infinity;
                        const dateB = b.doneDate ? new Date(b.doneDate).getTime() : Infinity;
                        return dateB - dateA;
                    });

                    // Setzen mit sortierter Liste
                    this.bookings.set(updatedBookings);

                    //this.bookings.set([...this.bookings(), data]);
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

    formatDate(date: Date): string | null {
        return this.datePipe.transform(date, 'dd.MM.yyyy');
    }

    rowStyle(booking: Booking) {
        if (booking.canceled) {
            return { backgroundColor: 'var(--p-content-hover-background)' };
        }
        return;
    }
}
