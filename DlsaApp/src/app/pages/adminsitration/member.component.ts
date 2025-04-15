import { Component, OnInit, signal, ViewChild } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Table, TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';
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

import { Sector, SectorDto, SectorService } from '../../services/sector.service';
import { Group, GroupService } from '../../services/group.service';
import { Member, MemberDto, MemberService } from '../../services/member.service';
import { Category, CategoryService } from '../../services/category.service';


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
        DatePickerModule
    ],
    templateUrl: `./member.component.html`,
    providers: [MessageService, ConfirmationService]
})
export class MemberComponent{

    isEdit: boolean = false;
    memberDialog: boolean = false;
    submitted: boolean = false;

    members = signal<Member[]>([]);
    member!: Member;
    memberDto!: MemberDto;

    groups: Group[] = [];
    categories: Category[] = [];

    selectedGroups!: number[];
    selectedCategories!: number[];

    @ViewChild('dt') dt!: Table;


    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private memberService: MemberService,
        private categoryService: CategoryService,
        private groupService: GroupService
    ) {}

    ngOnInit() {
        this.loadMembers();
        this.loadGroups();
        this.loadCategories();
    }

    loadMembers() {
        this.memberService.getAllMembers().subscribe({
            next: (data) => {
                this.members.set(data);
                console.log(data);
            },
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
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
        this.memberDto = {};
        this.submitted = false;
        this.isEdit = false;
        this.memberDialog = true;
    }

    editMember(member: Member) {
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

        this.memberDto = {};

        this.isEdit = true;
        this.memberDialog = true;
    }

    hideDialog() {
        this.memberDialog = false;
        this.submitted = false;
        this.isEdit = false;
    }
        


    saveMember() {
        
        this.submitted = true;

        if (this.isEdit) {

            
            if(this.member.memberId && this.member.forename && this.member.surname && this.member.birthdate && this.member.entryDate){

                this.memberDto = {
                    memberId: this.member.memberId,
                    surname: this.member.surname,
                    forename: this.member.forename,
                    birthdate: this.member.birthdate,
                    entryDate: new Date(Date.UTC(this.member.entryDate.getUTCFullYear(), this.member.entryDate.getUTCMonth(), this.member.entryDate.getUTCDate())),
                    leavingDate: this.member.leavingDate,
                    active: this.member.active,
                    groupIds: this.selectedGroups,
                    categorieIds: this.selectedCategories
                }

                this.memberService.updateMember(this.memberDto, this.member.id!).subscribe({
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
            

            this.isEdit = false;
        } else {

            if(this.member.memberId && this.member.forename && this.member.surname && this.member.birthdate && this.member.entryDate){

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
