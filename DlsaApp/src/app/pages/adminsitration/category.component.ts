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
import { Category, CategoryService } from '../../services/category.service';
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
    templateUrl: `./category.component.html`,
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

    saveCategory() {

        
        this.submitted = true;

        if (this.isEdit) {
            
            if(this.category.categoryName){
                this.categoryService.updateCategory(this.category).subscribe({
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
                this.categoryService.createCategory(this.category).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Sparte wurde erfolgreich angelegt!" });
        
                        this.categories.set([...this.categories(), data]);
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
