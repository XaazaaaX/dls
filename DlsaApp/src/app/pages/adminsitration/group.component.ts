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

import { Sector, SectorDto, SectorService } from '../../services/sector.service';
import { Group, GroupService } from '../../services/group.service';


@Component({
    selector: 'app-group',
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
        MultiSelectModule
    ],
    templateUrl: `./group.component.html`,
    providers: [MessageService, ConfirmationService]
})
export class GroupComponent{

    isEdit: boolean = false;
    groupDialog: boolean = false;
    submitted: boolean = false;

    groups = signal<Group[]>([]);

    group!: Group;

    @ViewChild('dt') dt!: Table;


    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private sectorService: SectorService,
        private groupService: GroupService
    ) {}

    ngOnInit() {
        this.loadGroups();
    }

    loadGroups() {
        this.groupService.getAllGroups().subscribe({
            next: (data) => {
                this.groups.set(data);
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
        this.group = {liberated:false};
        this.submitted = false;
        this.groupDialog = true;
        this.isEdit = false;
    }

    editGroup(group: Group) {
        this.group = { ...group };
        this.isEdit = true;
        this.groupDialog = true;
    }

    hideDialog() {
        this.groupDialog = false;
        this.submitted = false;
    }
        
    saveGroup() {
        this.submitted = true;

        if (this.isEdit) {

            
            if(this.group.groupName){

                this.groupService.updateGroup(this.group).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Änderungen wurden erfolgreich gespeichert!" });

                        const currentGroup = this.groups();
                        const _groups = currentGroup.map(group => 
                            group.id === data.id ? { ...group, ...data } : group
                        );
                        
                        this.groups.set(_groups);
        
                        this.groupDialog = false;
                        this.group = {};
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }
        } else {

            if(this.group.groupName){

                this.groupService.createGroup(this.group).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Gruppe wurde erfolgreich angelegt!" });
        
                        this.groups.set([...this.groups(), data]);
                        this.groupDialog = false;
                        this.group = {};
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }
        }
    }
}
