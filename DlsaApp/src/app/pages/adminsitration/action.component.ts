import { Component, OnInit, signal, ViewChild } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Table, TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
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
        InputMaskModule

    ],
    templateUrl: `./action.component.html`,
    providers: [MessageService, ConfirmationService]
})
export class ActionComponent {

    isEdit: boolean = false;
    actionDialog: boolean = false;
    submitted: boolean = false;

    members: Member[] = [];

    actions = signal<Action[]>([]);
    action!: Action;
    actionDto!: ActionDto;

    contactId!: string;

    @ViewChild('dt') dt!: Table;


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

    get fullNameMemberOptions() {
        return this.members.map(member => ({
            id: member.id,
            fullname: member.surname + ", " + member.forename
        }));
    }

    loadActions() {
        this.actionService.getAllActions().subscribe({
            next: (data) => {
                this.actions.set(data);
            },
            error: (err) => {
                console.log(err);
                if (err.error.description) {
                    this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                } else {
                    this.messageService.add({ severity: 'warn', summary: "Verbindungsfehler!", detail: "Es gab einen Fehler bei der API-Anfrage." });
                }
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


    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    openNew() {
        this.action = {};
        this.actionDto = {};
        this.submitted = false;
        this.actionDialog = true;
        this.isEdit = false;
        this.contactId = "";
    }

    editAction(action: Action) {
        this.action = { ...action };
        this.isEdit = true;

        this.contactId = this.action.contact?.id!;

        this.actionDialog = true;
        this.actionDto = {};
    }

    hideDialog() {
        this.actionDialog = false;
        this.submitted = false;
    }



    saveAction() {

        this.submitted = true;

        if (this.isEdit) {


            if (this.action.year && this.action.description && this.contactId) {

                this.actionDto = {
                    year: this.action.year,
                    description: this.action.description,
                    contactId: this.contactId
                }


                this.actionService.updateAction(this.actionDto, this.action.id!).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Änderungen wurden erfolgreich gespeichert!" });

                        const currentAction = this.actions();
                        const _actions = currentAction.map(action =>
                            action.id === data.id ? { ...action, ...data } : action
                        );

                        this.actions.set(_actions);

                        this.actionDialog = false;
                        this.action = {};
                        this.actionDto = {};
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }

        } else {

            if (this.action.year && this.action.description && this.contactId) {

                this.actionDto = {
                    year: this.action.year,
                    description: this.action.description,
                    contactId: this.contactId
                }


                this.actionService.createActions(this.actionDto).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Aktion wurde erfolgreich angelegt!" });

                        this.actions.set([...this.actions(), data]);
                        this.actionDialog = false;
                        this.action = {};
                        this.actionDto = {};
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }
        }
    }




}
