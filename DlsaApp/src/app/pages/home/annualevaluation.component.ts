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
import { Booking, BookingDto, BookingService } from '../../services/booking.service';
import { Member, MemberService } from '../../services/member.service';
import { Action, ActionService } from '../../services/action.service';
import { CourseOfYear, EvaluationService, Year } from '../../services/evaluation.service';


@Component({
    selector: 'app-evaluation',
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
    templateUrl: `./annualevaluation.component.html`,
    providers: [MessageService, ConfirmationService, DatePipe]
})
export class AnnualEvaluationComponent {

    isEdit: boolean = false;
    evaluationDialog: boolean = false;
    submitted: boolean = false;

    bookings = signal<Booking[]>([]);
    booking!: Booking;
    bookingDto!: BookingDto;

    memberId?: number;

    members: Member[] = [];
    actions: Action[] = [];

    coys = signal<CourseOfYear[]>([]);
    coy!: CourseOfYear;

    year?: number;
    preEvaluation?: boolean = true;
    years: Year[] = [];

    selectedGroups!: number[];
    selectedCategories!: number[];

    @ViewChild('dt') dt!: Table;


    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private bookingService: BookingService,
        private memberService: MemberService,
        private actionService: ActionService,
        private evaluationService: EvaluationService,
        private datePipe: DatePipe
    ) { }

    ngOnInit() {
        this.loadYears();
        this.loadCourseOfYears();
    }

    get fullNameMemberOptions() {
        return this.members.map(member => ({
            id: member.id,
            fullname: member.surname + ", " + member.forename
        }));
    }

    loadBookings() {
        this.bookingService.getAllBookings().subscribe({
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

    loadYears() {
        this.evaluationService.getAllYears().subscribe({
            next: (data) => {
                this.years = data;
            },
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });
    }

    loadCourseOfYears() {
        this.evaluationService.getAllEvaluations().subscribe({
            next: (data) => {

                this.coys.set(data);



                //results.forEach(result => {



                    // Um CSV Export zu machen
                    /*
                    const blob = new Blob([this.base64ToArrayBuffer(result.file!)], { type: 'text/csv' });
                    const url = window.URL.createObjectURL(blob);
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = result.filename!;
                    a.click();
                    window.URL.revokeObjectURL(url);
                    */

                //});



            },
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });
    }

    base64ToArrayBuffer(base64: string): ArrayBuffer {
        const binaryString = atob(base64); // decode base64 to binary string
        const len = binaryString.length;
        const bytes = new Uint8Array(len);

        // Convert binary string to ArrayBuffer (Uint8Array)
        for (let i = 0; i < len; i++) {
            bytes[i] = binaryString.charCodeAt(i);
        }

        return bytes.buffer;
    }

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    openNew() {
        this.booking = {};
        this.bookingDto = {};
        this.submitted = false;
        this.evaluationDialog = true;

        this.year = undefined;
        this.preEvaluation = true;
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
            message: 'Soll die folgende Buchung wirklich storniert werden? <br/><br/>' +
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

                this.bookingService.cancelBooking(booking.id!).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Buchung wurden erfolgreich storniert!" });

                        /*
                        const currentBookings = this.bookings();
                        const _bookings = currentBookings.map(booking =>
                            booking.id === data.id ? { ...booking, ...data } : booking
                        );

                        this.bookings.set(_bookings);
                        this.booking = {};
                        */

                        this.loadBookings();
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }
        });
    }

    hideDialog() {
        this.evaluationDialog = false;
        this.submitted = false;
    }

    executeEvaluation() {
        this.submitted = true;

        if (this.year) {

            this.evaluationService.doEvaluation(this.year, !this.preEvaluation).subscribe({
                next: response => {

                    console.log(response);

                    const contentDisposition = response.headers.get('Content-Disposition');
                    const filename = this.getFilenameFromHeader(contentDisposition) || `Jahreslauf.csv`;

                    const blob = response.body!;
                    const url = window.URL.createObjectURL(blob);

                    const a = document.createElement('a');
                    a.href = url;
                    a.download = filename;
                    a.click();

                    window.URL.revokeObjectURL(url);


                    this.hideDialog();
                },
                error: (err) => {
                    this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                }
            });
        }
    }

    private getFilenameFromHeader(contentDisposition: string | null): string | null {
        if (!contentDisposition) return null;
        const matches = /filename="([^"]+)"/.exec(contentDisposition);
        return matches?.[1] ?? null;
    }

    downloadCsv(coy: CourseOfYear) {
        const blob = new Blob([this.base64ToArrayBuffer(coy.file!)], { type: 'text/csv' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = coy.filename!;
        a.click();
        window.URL.revokeObjectURL(url);
    }

    formatDate(date: Date): string | null {
        return this.datePipe.transform(date, 'dd.MM.yyyy');
    }
}
