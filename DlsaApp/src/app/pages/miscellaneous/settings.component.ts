/**
 * SettingsComponent – Verwaltung systemweiter Einstellungen
 *
 * Funktionen:
 * - Anzeigen und Bearbeiten von Konfigurationen für Dienstleistungsregeln
 * - Formularfelder mit PrimeNG-Komponenten (Input, Select, Checkbox, Radiobuttons etc.)
 * - Abruf und Speicherung der Einstellungen über SettingsService
 * - Benutzerfeedback über PrimeNG Toast
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
*/

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
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
    selector: 'app-documentation',
    standalone: true,
    imports: [
        // Angular & PrimeNG Imports
        CommonModule,
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
    <!-- Einstellungen Header + Speichern-Button -->
    <div class="card">
        <div class="flex flex-wrap items-center justify-between">
            <h4 class="m-0">Einstellungen</h4>
            <p-iconfield>
                <p-button label="Speichern" icon="pi pi-save" severity="primary" class="mr-2" (onClick)="save()" />
            </p-iconfield>
        </div>
    </div>

    <!-- Berechnungsgrundlagen -->
    <div class="card flex flex-col gap-4">
        <div class="font-semibold text-xl">Berechnungsgrundlagen</div>

        <!-- Stichtag -->
        <div class="flex flex-wrap gap-2 items-center">
            <label for="dueDate">Stichtag (Enddatum eines Monats)</label>
            <input pInputText id="dueDate" type="text" [(ngModel)]="settings.dueDate" />
        </div>

        <!-- DLS-Stunden pro Jahr -->
        <div class="flex flex-wrap gap-2 items-center">
            <label for="countDls">Anzahl der Dienstleistungsstunden pro Jahr</label>
            <p-inputnumber id="countDls" [(ngModel)]="settings.countDls" mode="decimal" locale="de-DE" [maxFractionDigits]="2" />
        </div>

        <!-- Kosten pro Stunde -->
        <div class="flex flex-wrap gap-2 items-center">
            <label for="costDls">Kosten pro Dienstleistungsstunde</label>
            <p-inputnumber id="costDls" [(ngModel)]="settings.costDls" mode="currency" currency="EUR" locale="de-DE" />
        </div>

        <!-- Altersgrenzen -->
        <div class="flex flex-wrap gap-2 items-center">
            <label for="ageFrom">Mindestalter für Dienstleistungsstunden</label>
            <p-inputnumber [(ngModel)]="settings.ageFrom" min="0" max="99" />
        </div>

        <div class="flex flex-wrap gap-2 items-center">
            <label for="ageTo">Maximalalter für Dienstleistungsstunden</label>
            <p-inputnumber [(ngModel)]="settings.ageTo" min="0" max="99" />
        </div>
    </div>

    <!-- Buchungsverhalten -->
    <div class="card flex flex-col gap-4">
        <div class="font-semibold text-xl">Buchungsverhalten</div>

        <!-- Jahreslauf-Methode -->
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

        <!-- Checkbox für Ausgleichsbuchung -->
        <div class="flex flex-wrap gap-2 items-center">
            <div class="flex items-center">
                <p-checkbox id="clearing" [(ngModel)]="settings.clearing" [binary]="true"/>
                <label for="clearing" class="ml-2">Ausgleichsbuchungen beim Jahreslauf</label>
            </div>
        </div>

        <!-- Granularität -->
        <div class="flex flex-wrap gap-2 items-center">
            <label for="granularity">Granularität</label>
            <p-select [(ngModel)]="settings.granularity" [options]="granularityOptions" optionLabel="name" optionValue="name" placeholder="Auswählen" />
        </div>
    </div>

    <!-- Toast-Meldungen -->
    <p-toast />
    `,
    styles: ``
})
export class SettingsComponent {

    // Einstellungen werden im ngModel gebunden
    settings: Settings = {};

    // Auswahloptionen für Granularität
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

    // Lädt Einstellungen vom Server
    loadSettings() {
        console.log("Fetch");
        this.settingsService.getSettings().subscribe({
            next: (data) => {
                this.settings = data;
            },
            error: (err) => {
                const detail = err.error?.description || "Es gab einen Fehler bei der API-Anfrage.";
                this.messageService.add({
                    severity: 'warn',
                    summary: err.error?.title || 'Verbindungsfehler!',
                    detail
                });
            }
        });
    }

    // Speichert Einstellungen an den Server
    updateSettings() {
        this.settingsService.updateSettings(this.settings).subscribe({
            next: () => {
                this.messageService.add({
                    severity: 'success',
                    summary: "Info",
                    detail: "Die Änderungen wurden erfolgreich gespeichert!"
                });
            },
            error: (err) => {
                const detail = err.error?.description || "Es gab einen Fehler bei der API-Anfrage.";
                this.messageService.add({
                    severity: 'warn',
                    summary: err.error?.title || 'Verbindungsfehler!',
                    detail
                });
            }
        });
    }

    // Speichern-Klickhandler
    save() {
        this.updateSettings();
    }
}