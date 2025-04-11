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

import { Action, ActionDto, ActionService } from '../../services/action.service';
import { Group, Sector, SectorDto, SectorService } from '../../services/sector.service';
import { GroupService } from '../../services/group.service';


@Component({
    selector: 'app-sector',
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
    template: `

        <div class="card">
<p-table
            #dt
            [value]="sectors()"
            [rows]="10"
            [paginator]="true"
            [globalFilterFields]="['asd']"
            [rowHover]="true"
            dataKey="id"
            currentPageReportTemplate="Showing {first} to {last} of {totalRecords} actions"
            [showCurrentPageReport]="true"
            [rowsPerPageOptions]="[10, 20, 30]"
        >

        <ng-template #caption>
                <div class="flex flex-wrap items-center justify-between">
                    <h4 class="m-0">Bereichsverwaltung</h4>
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

                    <th pSortableColumn="actionDescription">
                        Name
                        <p-sortIcon field="actionDescription" />
                    </th>
                    
                    <th>Optionen</th>
                </tr>
            </ng-template>


            <ng-template #body let-sector>
                <tr>
                    <td style="width: auto; min-width: 5rem;">{{ sector.sectorname }}</td>

                    <td style="width: 10%; min-width: 6rem;">
                        <p-button icon="pi pi-pencil" class="mr-2" [rounded]="true" [outlined]="true" (click)="editSector(sector)" />
                    </td>
                </tr>
            </ng-template>
        </p-table>
        </div>




        <p-dialog [(visible)]="sectorDialog" [style]="{ width: '450px' }" header="Bereichsdetails" [modal]="true">
            <ng-template #content>
                <div class="flex flex-col gap-6">

                    <div>
                        <label for="categoryName" class="block font-bold mb-3">Spartenbezeichnung</label>
                        <input type="text" pInputText id="categoryName" [(ngModel)]="sector.sectorname"  required autofocus fluid />
                        <small class="text-red-500" *ngIf="submitted && !sector.sectorname">Benutzername ist erforderlich!</small>
                    </div>

                    <div>
                        <label for="categoryName" class="block font-bold mb-3">Funktionsgruppen</label>
                        <p-multiselect [options]="groups" appendTo="body" [(ngModel)]="selectedGroups" optionLabel="groupName" optionValue="id" placeholder="Auswählen"  [maxSelectedLabels]="0" [selectedItemsLabel]="'{0} Gruppe(n) ausgewählt!'" fluid />
                        <small class="text-red-500" *ngIf="submitted && !sector.sectorname">Benutzername ist erforderlich!</small>
                    </div>

                






                </div>
            </ng-template>

            <ng-template #footer>
                <p-button label="Abbrechen" icon="pi pi-times" text (click)="hideDialog()" />
                <p-button label="Speichern" icon="pi pi-check" (click)="saveSector()" />
            </ng-template>
        </p-dialog>





        <p-confirmdialog [style]="{ width: '450px' }" />

        <p-toast />
    `,
    providers: [MessageService, ConfirmationService]
})
export class SectorComponent{

    isEdit: boolean = false;
    sectorDialog: boolean = false;
    submitted: boolean = false;

    sectors = signal<Sector[]>([]);

    groups: Group[] = [];

    sector!: Sector;
    selectedGroups!: number[];
    sectorDto!: SectorDto;

    @ViewChild('dt') dt!: Table;


    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private sectorService: SectorService,
        private groupService: GroupService
    ) {}

    ngOnInit() {
        this.loadSectors();
        this.loadGroups();
    }

    loadSectors() {
        this.sectorService.getAllSectors().subscribe({
            next: (data) => {
                this.sectors.set(data);
                console.log("Bereiche:");
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
                console.log("Gruppen");
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
        this.sector = {};

        this.submitted = false;
        this.sectorDialog = true;
    }

    editSector(sector: Sector) {
        this.sector = { ...sector };

        this.selectedGroups = this.sector.groups!
        .map(group => group.id)
        .filter(id => id !== undefined) as number[];

        this.isEdit = true;
        this.sectorDialog = true;
    }

    hideDialog() {
        /*
        this.actionDialog = false;
        this.submitted = false;
        */
    }
        


    saveSector() {

        console.log(this.selectedGroups);
        

        /*
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
             */
    }
   


    
}
