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
import { Category, CategoryService } from '../../services/category.serive';
//import { Product, ProductService } from '../service/product.service';

@Component({
    selector: 'app-category',
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
    template: `

        <div class="card">
<p-table
            #dt
            [value]="categories()"
            [rows]="10"
            [paginator]="true"
            [globalFilterFields]="['categoryName']"
            [rowHover]="true"
            dataKey="id"
            currentPageReportTemplate="Showing {first} to {last} of {totalRecords} categories"
            [showCurrentPageReport]="true"
            [rowsPerPageOptions]="[10, 20, 30]"
        >

        <ng-template #caption>
                <div class="flex flex-wrap items-center justify-between">
                    <h4 class="m-0">Spartenverwaltung</h4>
                    <div class="flex flex-wrap items-center justify-between">
                    <p-button label="Hinzufügen" icon="pi pi-plus" severity="primary" class="mr-2" (onClick)="openNew()" />
                    <p-iconfield>
                        <p-inputicon styleClass="pi pi-search" />
                        <input pInputText type="text" (input)="onGlobalFilter(dt, $event)" placeholder="Suche..." />
                    </p-iconfield>
                    </div>
                    
                </div>
            </ng-template>



            <ng-template #header>
                <tr>
                    <th pSortableColumn="categoryName">
                        Spartenbezeichnung
                        <p-sortIcon field="categoryName" />
                    </th>
                    
                    <th>Optionen</th>
                </tr>
            </ng-template>


            <ng-template #body let-category>
                <tr>
                    <td style="width: auto; min-width: 5rem;">{{ category.categoryName }}</td>

                    <td style="width: 10%; min-width: 6rem;">
                        <p-button icon="pi pi-pencil" class="mr-2" [rounded]="true" [outlined]="true" (click)="editCategory(category)" />
                    </td>
                </tr>
            </ng-template>
        </p-table>
        </div>




        <p-dialog [(visible)]="categoryDialog" [style]="{ width: '450px' }" header="Spartendetails" [modal]="true">
            <ng-template #content>
                <div class="flex flex-col gap-6">

                    <div>
                        <label for="categoryName" class="block font-bold mb-3">Spartenbezeichnung</label>
                        <input type="text" pInputText id="categoryName" [(ngModel)]="category.categoryName"  required autofocus fluid />
                        <small class="text-red-500" *ngIf="submitted && !category.categoryName">Benutzername ist erforderlich!</small>
                    </div>

                </div>
            </ng-template>

            <ng-template #footer>
                <p-button label="Abbrechen" icon="pi pi-times" text (click)="hideDialog()" />
                <p-button label="Speichern" icon="pi pi-check" (click)="saveCategory()" />
            </ng-template>
        </p-dialog>





        <p-confirmdialog [style]="{ width: '450px' }" />

        <p-toast />
    `,
    providers: [MessageService, ConfirmationService]
})
export class CategoryComponent{

    isEdit: boolean = false;
    categoryDialog: boolean = false;
    submitted: boolean = false;

    categories = signal<Category[]>([]);

    category!: Category;

    @ViewChild('dt') dt!: Table;


    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private categoryService: CategoryService
    ) {
        
    }

    ngOnInit() {
        this.loadCategories();
    }

    loadCategories() {
        this.categoryService.getAllCategories().subscribe({
            next: (data) => {
                this.categories.set(data);
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
        this.category = {};

        this.submitted = false;
        this.categoryDialog = true;
    }

    editCategory(category: Category) {
        this.category = { ...category };
        this.categoryDialog = true;
        this.isEdit = true;
    }

    hideDialog() {
        this.categoryDialog = false;
        this.submitted = false;
    }

    
    /*
    deleteCategory(category: Category) {

        this.confirmationService.confirm({
            message: 'Soll die Sparte "' + category.categoryName + '" wirklich gelöscht werden?',
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

                this.categoryService.deleteCategory(category.id).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Der Benutzer wurde erfolgreich gelöscht!" });
                        
                        this.categories.set(this.categories().filter((val) => val.id !== category.id));
                        this.category = {};
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }
        });
    }
    */
        


    saveCategory() {

        
        this.submitted = true;

        if (this.isEdit) {
            
            if(this.category.categoryName){
                this.categoryService.updateSettings(this.category).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Änderungen wurden erfolgreich gespeichert!" });

                        const currentCategory = this.categories();
                        const _categories = currentCategory.map(category => 
                            category.id === data.id ? { ...category, ...data } : category
                        );
                        
                        this.categories.set(_categories);
        
                        this.categoryDialog = false;
                        this.category = {};
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }

            this.isEdit = false;

        } else {

            if(this.category.categoryName){
                this.categoryService.createSettings([this.category]).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Sparte wurde erfolgreich angelegt!" });
        
                        this.categories.set([...this.categories(), ...data]);
                        this.categoryDialog = false;
                        this.category = {};
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }
        }
            
    }



    
}
