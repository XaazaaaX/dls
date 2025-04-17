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
import { Product, ProductService } from '../../services/product.service';
import { Role, User, UserService } from '../../services/user.service';
import { ToggleSwitchModule } from 'primeng/toggleswitch';
//import { Product, ProductService } from '../service/product.service';

interface Column {
    field: string;
    header: string;
    customExportHeader?: string;
}

interface ExportColumn {
    title: string;
    dataKey: string;
}

@Component({
    selector: 'app-user',
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
        ReactiveFormsModule

    ],
    templateUrl: `./user.component.html`,
    providers: [MessageService, ProductService, ConfirmationService]
})
export class UserComponent implements OnInit {

    checked: boolean = false;
    isEdit: boolean = false;

    userDialog: boolean = false;

    products = signal<Product[]>([]);
    users = signal<User[]>([]);

    product!: Product;
    user!: User;

    selectedProducts!: Product[] | null;
    selectedUsers!: User[] | null;

    submitted: boolean = false;

    roles!: any[];

    @ViewChild('dt') dt!: Table;

    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private userService: UserService
    ) {}

    ngOnInit() {
        this.loadUserData();
    }

    loadUserData() {
        this.userService.getAllUsers().subscribe({
            next: (data) => {
                this.users.set(data);
                console.log(this.users);
            },
            error: (err) => {
                this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
            }
        });

        this.roles = [
            { label: 'Administrator', value: 'Administrator' },
            { label: 'Benutzer', value: 'Benutzer' },
            { label: 'Gast', value: 'Gast' }
        ];
    }

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    openNew() {
        this.product = {};

        this.user = {
            active: true,
            role: {}
        };
        this.isEdit = false;
        this.submitted = false;
        this.userDialog = true;
    }

    editUser(user: User) {
        this.user = { ...user };
        this.userDialog = true;
        this.isEdit = true;
    }

    hideDialog() {
        this.userDialog = false;
        this.submitted = false;
    }

    deleteUser(user: User) {

        this.confirmationService.confirm({
            message: 'Soll der Benutzer "' + user.username + '" wirklich gelöscht werden?',
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

                this.userService.deleteUser(user.id).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Der Benutzer wurde erfolgreich gelöscht!" });
                        
                        this.users.set(this.users().filter((val) => val.id !== user.id));
                        this.user = {
                            role: {}
                        };
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }
        });
    }

    saveUser() {

        this.submitted = true;

        if (this.isEdit) {

            if(this.user.username && this.user.role.rolename){
                this.userService.updateUser(this.user).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Änderungen wurden erfolgreich gespeichert!" });

                        const currentUsers = this.users();
                        const _users = currentUsers.map(user => 
                        user.id === data.id ? { ...user, ...data } : user
                        );
                        
                        // Aktualisiere das signal mit den neuen Benutzerdaten
                        this.users.set(_users);
        
                        //this.users.set([...this.users(), ...data]);
                        this.userDialog = false;
                        this.user = {
                            role: {}
                        };
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }

        } else {

            if(this.user.username && this.user.password && this.user.role.rolename){
                this.userService.createUser(this.user).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Der Benutzer wurde erfolgreich angelegt!" });
        
                        this.users.set([...this.users(), data]);
                        this.userDialog = false;
                        this.user = {
                            role: {}
                        };
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }
        }
    }
}
