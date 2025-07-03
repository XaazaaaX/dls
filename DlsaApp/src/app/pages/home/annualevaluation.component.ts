import { Component, OnInit, signal, ViewChild } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Table, TableModule } from 'primeng/table';
import { CommonModule, DatePipe } from '@angular/common';
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
import { BookingService } from '../../services/booking.service';
import { MemberService } from '../../services/member.service';
import { ActionService } from '../../services/action.service';
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

    coys = signal<CourseOfYear[]>([]);
    coy!: CourseOfYear;

    year?: number;
    preEvaluation?: boolean = true;
    years: Year[] = [];

    @ViewChild('dt') dt!: Table;


    constructor(
        private messageService: MessageService,
        private evaluationService: EvaluationService,
        private datePipe: DatePipe
    ) { }

    ngOnInit() {
        this.loadYears();
        this.loadCourseOfYears();
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
            },
            error: (err) => {
                if (err.error.description) {
                    this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                  } else {
                    this.messageService.add({ severity: 'warn', summary: "Verbindungsfehler!", detail: "Es gab einen Fehler bei der API-Anfrage." });
                  }
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
        this.submitted = false;
        this.evaluationDialog = true;

        this.year = undefined;
        this.preEvaluation = true;
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

                    this.loadCourseOfYears();
                },
                error: (err) => {
                    this.messageService.add({ severity: 'warn', summary: "Hinweis!", detail: "Der Jahreslauf kann erst gestartet werden, wenn der Zeitraum beendet ist! Falls der angegebene Zeitraum vor oder in einem abgeschlossenem Jahreslauf liegt, bitte das Ergebnis in der Tabelle verwenden!" });
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
