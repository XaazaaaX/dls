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
        DatePickerModule,
        FileUploadModule
    ],
    templateUrl: `./member.component.html`,
    providers: [MessageService, ConfirmationService]
})
export class MemberComponent {

    isEdit: boolean = false;
    memberDialog: boolean = false;
    memberUploadDialog: boolean = false;
    submitted: boolean = false;

    members = signal<Member[]>([]);
    member!: Member;
    memberDto!: MemberDto;
    memberEditDto!: MemberEditDto;

    groups: Group[] = [];
    categories: Category[] = [];

    selectedGroups!: number[];
    selectedCategories!: number[];

    selectedFile: File | null = null;

    @ViewChild('dt') dt!: Table;


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

    onFileChange(event: any): void {
        const file = event.files?.[0];
        if (file) {
            this.selectedFile = file;
        }
    }

    upload(): void {
        if (this.selectedFile) {
            this.memberService.uploadMember(this.selectedFile).subscribe({
                next: (data) => {

                    let countAddedMembers = data.length
                    this.messageService.add({ severity: 'success', summary: "Info", detail: countAddedMembers + " Mitglieder wurden erfolgreich angelegt!" });

                    if (data.length > 0) {
                        this.members.set([...this.members(), ...data]);
                    }
                    this.memberUploadDialog = false;
                },
                error: (err) => {
                    this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                }
            });
        }
    }

    loadMembers() {
        this.memberService.getAllMembers().subscribe({
            next: (data) => {
                this.members.set(data);
                console.log(data);
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

    loadCategories() {
        this.categoryService.getAllCategories().subscribe({
            next: (data) => {
                this.categories = data;
                console.log(data);
            },
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });
    }

    loadGroups() {
        this.groupService.getAllGroups().subscribe({
            next: (data) => {
                this.groups = data;
                console.log(data);
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
        this.member = {};
        this.selectedGroups = [];
        this.selectedCategories = [];
        this.memberDto = {};
        this.submitted = false;
        this.isEdit = false;
        this.memberDialog = true;
    }
    openNewUpload() {
        this.selectedFile = null;
        this.memberUploadDialog = true;
    }

    editMember(member: Member) {

        this.selectedGroups = [];
        this.selectedCategories = [];

        this.member = { ...member };

        this.selectedGroups = this.member.groups!
            .map(group => group.id)
            .filter(id => id !== undefined) as number[];

        this.selectedCategories = this.member.categories!
            .map(category => category.id)
            .filter(id => id !== undefined) as number[];

        this.member.birthdate ? this.member.birthdate = new Date(this.member.birthdate!) : null;
        this.member.entryDate ? this.member.entryDate = new Date(this.member.entryDate!) : null;
        this.member.leavingDate ? this.member.leavingDate = new Date(this.member.leavingDate!) : null;

        this.member.refDate = new Date();

        this.memberDto = {};
        this.memberEditDto = {};

        this.isEdit = true;
        this.memberDialog = true;
    }

    hideDialog() {
        this.memberDialog = false;
        this.submitted = false;
    }

    hideUploadDialog() {
        this.memberUploadDialog = false;
        this.selectedFile = null;
    }

    saveMember() {

        this.submitted = true;

        if (this.isEdit) {


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
                }

                this.memberService.updateMember(this.memberEditDto, this.member.id!).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Änderungen wurden erfolgreich gespeichert!" });

                        const currentMember = this.members();
                        const _members = currentMember.map(member =>
                            member.id === data.id ? { ...member, ...data } : member
                        );

                        this.members.set(_members);

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

            if (this.member.memberId && this.member.forename && this.member.surname && this.member.birthdate && this.member.entryDate) {

                this.memberDto = {
                    memberId: this.member.memberId,
                    surname: this.member.surname,
                    forename: this.member.forename,
                    birthdate: this.member.birthdate,
                    entryDate: this.member.entryDate,
                    active: true,
                    groupIds: this.selectedGroups,
                    categorieIds: this.selectedCategories
                }

                this.memberService.createMember(this.memberDto).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Das Mitglied wurde erfolgreich angelegt!" });

                        this.members.set([...this.members(), data]);
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
}
