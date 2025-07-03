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
import { Sector, SectorDto, SectorService } from '../../services/sector.service';
import { Group, GroupService } from '../../services/group.service';


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
    templateUrl: `./sector.component.html`,
    providers: [MessageService, ConfirmationService]
})
export class SectorComponent {

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
    ) { }

    ngOnInit() {
        this.loadSectors();
        this.loadGroups();
    }

    loadSectors() {
        this.sectorService.getAllSectors().subscribe({
            next: (data) => {
                this.sectors.set(data);
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

    loadGroups() {
        this.groupService.getAllGroups().subscribe({
            next: (data) => {
                this.groups = data;
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
        this.selectedGroups = [];
        this.sectorDto = {};
        this.isEdit = false;
        this.submitted = false;
        this.sectorDialog = true;
    }

    editSector(sector: Sector) {
        this.sector = { ...sector };

        this.selectedGroups = this.sector.groups!
            .map(group => group.id)
            .filter(id => id !== undefined) as number[];

        this.sectorDto = {};

        this.isEdit = true;
        this.sectorDialog = true;
    }

    hideDialog() {
        this.sectorDialog = false;
        this.submitted = false;
    }



    saveSector() {
        this.submitted = true;

        if (this.isEdit) {

            if (this.sector.sectorname) {

                this.sectorDto.sectorname = this.sector.sectorname;
                this.sectorDto.groupIds = this.selectedGroups;


                this.sectorService.updateSector(this.sectorDto, this.sector.id!).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Änderungen wurden erfolgreich gespeichert!" });

                        const currentSector = this.sectors();
                        const _sectors = currentSector.map(sector =>
                            sector.id === data.id ? { ...sector, ...data } : sector
                        );

                        this.sectors.set(_sectors);

                        this.sectorDialog = false;
                        this.sector = {};
                        this.sectorDto = {};
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }
        } else {

            if (this.sector.sectorname) {

                this.sectorDto.sectorname = this.sector.sectorname;
                this.sectorDto.groupIds = this.selectedGroups;

                this.sectorService.createSectors(this.sectorDto).subscribe({
                    next: (data) => {
                        this.messageService.add({ severity: 'success', summary: "Info", detail: "Der Bereich wurde erfolgreich angelegt!" });

                        this.sectors.set([...this.sectors(), data]);
                        this.sectorDialog = false;
                        this.sector = {};
                        this.sectorDto = {};
                    },
                    error: (err) => {
                        this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
                    }
                });
            }
        }
    }
}
