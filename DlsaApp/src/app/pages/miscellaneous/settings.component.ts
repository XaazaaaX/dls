import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Settings, SettingsService } from '../../services/settings.service';
import { FormsModule } from '@angular/forms';
import { CheckboxModule } from 'primeng/checkbox';
import { RadioButtonModule } from 'primeng/radiobutton';
import { SelectButtonModule } from 'primeng/selectbutton';
import { InputGroupModule } from 'primeng/inputgroup';
import { FluidModule } from 'primeng/fluid';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { FloatLabelModule } from 'primeng/floatlabel';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { InputNumberModule } from 'primeng/inputnumber';
import { SliderModule } from 'primeng/slider';
import { RatingModule } from 'primeng/rating';
import { ColorPickerModule } from 'primeng/colorpicker';
import { KnobModule } from 'primeng/knob';
import { SelectModule } from 'primeng/select';
import { DatePickerModule } from 'primeng/datepicker';
import { ToggleSwitchModule } from 'primeng/toggleswitch';
import { TreeSelectModule } from 'primeng/treeselect';
import { MultiSelectModule } from 'primeng/multiselect';
import { ListboxModule } from 'primeng/listbox';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { TextareaModule } from 'primeng/textarea';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
    selector: 'app-documentation',
    standalone: true,
    imports: [CommonModule,
        FormsModule,
        InputTextModule,
        ButtonModule,
        CheckboxModule,
        RadioButtonModule,
        SelectButtonModule,
        InputGroupModule,
        FluidModule,
        IconFieldModule,
        InputIconModule,
        FloatLabelModule,
        AutoCompleteModule,
        InputNumberModule,
        SliderModule,
        RatingModule,
        ColorPickerModule,
        KnobModule,
        SelectModule,
        DatePickerModule,
        ToggleButtonModule,
        ToggleSwitchModule,
        TreeSelectModule,
        MultiSelectModule,
        ListboxModule,
        InputGroupAddonModule,
        TextareaModule,
        ToastModule
    ],
    providers: [MessageService],
    template: `
    <div class="card">
        <div class="flex flex-wrap items-center justify-between">
                <h4 class="m-0">Einstellungen</h4>
                <p-iconfield>
                <p-button label="Speichern" icon="pi pi-save" severity="primary" class="mr-2" (onClick)="save()" />
            </p-iconfield>
        </div>
    </div>

                <div class="card flex flex-col gap-4">
                    <div class="font-semibold text-xl">Berechnungsgrundlagen</div>

                    <div class="flex flex-wrap gap-2 items-center">
                        <label for="dueDate">Stichtag (Enddatum eines Monats)</label>
                        <input pInputText id="dueDate" type="text" [(ngModel)]="settings.dueDate" />
                    </div>

                    <div class="flex flex-wrap gap-2 items-center">
                        <label for="countDls">Anzahl der Dienstleistungsstunden pro Jahr</label>
                        <p-inputnumber id="countDls" [(ngModel)]="settings.countDls" inputId="locale-german" mode="decimal" locale="de-DE" [maxFractionDigits]="2" />
                    </div>

                    <div class="flex flex-wrap gap-2 items-center">
                        <label for="costDls">Kosten pro Dienstleistungsstunde</label>
                        <p-inputnumber id="costDls" [(ngModel)]="settings.costDls" mode="currency"inputId="currency-germany"currency="EUR" locale="de-DE" />
                    </div>

                    <div class="flex flex-wrap gap-2 items-center">
                        <label for="ageFrom">Mindestalter für Dienstleistungsstunden</label>
                        <p-inputnumber [(ngModel)]="settings.ageFrom" inputId="locale-german" min="0" max="99" />
                    </div>

                    <div class="flex flex-wrap gap-2 items-center">
                        <label for="ageTo">Maximalalter für Dienstleistungsstunden</label>
                        <p-inputnumber [(ngModel)]="settings.ageTo" inputId="locale-german" min="0" max="99" />
                    </div>

                </div>




                <div class="card flex flex-col gap-4">
                    <div class="font-semibold text-xl">Buchungsverhalten</div>

                    <div class="flex flex-col md:flex-row gap-4">
                        <label for="bookingMethod">Berechnung beim Jahreslauf</label>
                        <div class="flex items-center">
                            <p-radiobutton id="bookingMethod1" name="bookingMethod1" value="Volles Jahr zum Stichtag" [(ngModel)]="settings.bookingMethod" />
                            <label for="bookingMethod1" class="leading-none ml-2">Volles Jahr zum Stichtag</label>
                        </div>
                        <div class="flex items-center">
                            <p-radiobutton id="bookingMethod2" name="bookingMethod2" value="Anteilig bis zum Stichtag" [(ngModel)]="settings.bookingMethod" />
                            <label for="bookingMethod2" class="leading-none ml-2">Anteilig bis zum Stichtag</label>
                        </div>
                    </div>

                    <div class="flex flex-wrap gap-2 items-center">
                        <div class="flex items-center">
                            <p-checkbox type="checkbox" id="clearing" name="clearing" [(ngModel)]="settings.clearing" [binary]="true"/>
                            <label for="clearing" class="ml-2">Ausgleichsbuchungen beim Jahreslauf</label>
                        </div>
                    </div>

                    <div class="flex flex-wrap gap-2 items-center">
                        <label for="granularity">Granularität</label>
                        <p-select [(ngModel)]="settings.granularity" [options]="granularityOptions" optionLabel="name" optionValue="name" placeholder="Auswählen" />
                    </div>

                </div>

                <p-toast />
`,
    styles: ``
})
export class SettingsComponent {

    settings: Settings = {};

    inputGroupValue: any;
    floatValue: any = null;
    selectedAutoValue: any;
    autoFilteredValue: any;
    filterCountry: any;
    calendarValue: any;
    inputNumberValue: any;
    radioValue: any;
    checkboxValue: any;
    date: any;



    granularityOptions = [
        { name: 'Keine' },
        { name: 'Ganze' },
        { name: 'Halbe' },
        { name: 'Viertel' }
    ];


    constructor(
        private messageService: MessageService,
        private settingsService: SettingsService
    ) { }

    ngOnInit() {
        this.loadSettings();
    }

    loadSettings() {
        console.log("Fetch");
        this.settingsService.getSettings().subscribe({
            next: (data) => {
                this.settings = data;
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

    updateSettings() {
        this.settingsService.updateSettings(this.settings).subscribe({
            next: (data) => {
                this.messageService.add({ severity: 'success', summary: "Info", detail: "Die Änderungen wurden erfolgreich gespeichert!" });
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

    save() {
        this.updateSettings();
    }

}
