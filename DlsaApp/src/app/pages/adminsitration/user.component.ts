import { Component, OnInit, signal, ViewChild } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Table, TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
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
    selector: 'app-crud',
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
        ToggleSwitchModule
    ],
    template: `
        <p-toolbar styleClass="mb-6">
            <ng-template #start>
                <p-button label="Hinzufügen" icon="pi pi-plus" severity="secondary" class="mr-2" (onClick)="openNew()" />
            </ng-template>
        </p-toolbar>

        <p-table
            #dt
            [value]="users()"
            [rows]="10"
            [paginator]="true"
            [globalFilterFields]="['username', 'role.rolename', 'active']"
            [tableStyle]="{ 'min-width': '75rem' }"
            [(selection)]="selectedProducts"
            [rowHover]="true"
            dataKey="id"
            currentPageReportTemplate="Showing {first} to {last} of {totalRecords} users"
            [showCurrentPageReport]="true"
            [rowsPerPageOptions]="[10, 20, 30]"
        >
            <ng-template #caption>
                <div class="flex items-center justify-between">
                    <h5 class="m-0">Benutzerverwaltung</h5>
                    <p-iconfield>
                        <p-inputicon styleClass="pi pi-search" />
                        <input pInputText type="text" (input)="onGlobalFilter(dt, $event)" placeholder="Suche..." />
                    </p-iconfield>
                </div>
            </ng-template>


            <ng-template #header>
                <tr>
                    <th pSortableColumn="username" style="min-width: 12rem">
                        Benutzername
                        <p-sortIcon field="username" />
                    </th>
                    <th pSortableColumn="role.rolename" style="min-width: 12rem">
                        Rolle
                        <p-sortIcon field="role.rolename" />
                    </th>
                    <th pSortableColumn="active" style="min-width: 12rem">
                        Aktiviert
                        <p-sortIcon field="active" />
                    </th>
                    
                    <th style="min-width: 12rem">Optionen</th>
                </tr>
            </ng-template>


            <ng-template #body let-user>
                <tr>
                    <td style="min-width: 12rem">{{ user.username }}</td>
                    <td>{{ user.role.rolename }}</td>

                    <td>
                    @if(user.active) {
                        <i class="pi pi-check"></i>
                    } @else {
                        <i class="pi pi-times"></i>
                    }
                    </td>

                    <td style="width: auto">
                        <p-button icon="pi pi-pencil" class="mr-2" [rounded]="true" [outlined]="true" (click)="editUser(user)" />
                        <p-button icon="pi pi-trash" severity="danger" [rounded]="true" [outlined]="true" (click)="deleteUser(user)" />
                    </td>
                </tr>
            </ng-template>
        </p-table>

        <p-dialog [(visible)]="userDialog" [style]="{ width: '450px' }" header="Benutzerdetails" [modal]="true">
            <ng-template #content>
                <div class="flex flex-col gap-6">

                    <div>
                        <label for="username" class="block font-bold mb-3">Benutzername</label>
                        <input type="text" pInputText id="username" [(ngModel)]="user.username" required autofocus fluid />
                        <small class="text-red-500" *ngIf="submitted && !user.username">Benutzername ist erforderlich!</small>
                    </div>

                    <div>
                        <label for="password" class="block font-bold mb-3">Passwort</label>
                        <input type="text" pInputText id="password" [(ngModel)]="user.password" required autofocus fluid />
                        <small class="text-red-500" *ngIf="submitted && !user.password">Passwort ist erforderlich!</small>
                    </div>

                    <div>
                        <label for="role" class="block font-bold mb-3">Rolle</label>
                        <p-select [(ngModel)]="user.role.rolename" appendTo="body" inputId="role" [options]="roles" optionLabel="label" optionValue="value" placeholder="Auswählen" required fluid />
                        <small class="text-red-500" *ngIf="submitted && !user.role.rolename">Rolle ist erforderlich!</small>
                    </div>

                    <div>
                        <label for="isActive" class="block font-bold mb-3">Aktiver Account?</label>
                        <p-toggleswitch id="isActive" [(ngModel)]="user.active" >
                            <ng-template #handle let-checked="checked">
                                <i [ngClass]="['!text-xs', 'pi', checked ? 'pi-check' : 'pi-times']"></i>
                            </ng-template>
                        </p-toggleswitch>
                    </div>

                </div>
            </ng-template>

            <ng-template #footer>
                <p-button label="Abbrechen" icon="pi pi-times" text (click)="hideDialog()" />
                <p-button label="Speichern" icon="pi pi-check" (click)="saveUser()" />
            </ng-template>
        </p-dialog>

        <p-confirmdialog [style]="{ width: '450px' }" />

        <p-toast />
    `,
    providers: [MessageService, ProductService, ConfirmationService]
})
export class UserComponent implements OnInit {

    checked: boolean = false;

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

        this.submitted = false;
        this.userDialog = true;
    }

    editUser(user: User) {
        this.user = { ...user };
        this.userDialog = true;
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
                        /*this.users.set([...this.users(), ...data]);
                        this.userDialog = false;
                        this.user = {
                            role: {}
                        };
                        */

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

        this.userService.createUsers([this.user]).subscribe({
            next: (data) => {
                this.messageService.add({ severity: 'success', summary: "Info", detail: "Der Benutzer wurde erfolgreich angelegt!" });
                this.users.set([...this.users(), ...data]);
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
