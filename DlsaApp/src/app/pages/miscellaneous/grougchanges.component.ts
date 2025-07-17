/**
 * GroupChangesComponent – Anzeige von Gruppenänderungen (Historie)
 *
 * Funktionen:
 * - Lädt und zeigt historische Gruppenänderungen in einer Tabelle an
 * - Verwendet PrimeNG-Komponenten (Tabelle, Toasts, Buttons)
 * - Fehlerbehandlung und Benutzerfeedback über PrimeNG Toast
 * - Globale Filterfunktion für Tabelleninhalte
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

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
import { InputMaskModule } from 'primeng/inputmask';
import { GroupChanges, HistorieService } from '../../services/historie.service';

@Component({
  selector: 'app-groupchanges',
  standalone: true,
  imports: [
    // Angular + PrimeNG-Module für UI und Formulare
    CommonModule,
    TableModule,
    FormsModule,
    ReactiveFormsModule,
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
    InputMaskModule
  ],
  templateUrl: `./groupchanges.component.html`,
  providers: [MessageService, ConfirmationService]
})
export class GroupChangesComponent {

  // Signal für reaktive Gruppenänderungsdaten
  groupChanges = signal<GroupChanges[]>([]);

  // ViewChild-Referenz zur Tabelle für Filterfunktionen
  @ViewChild('dt') dt!: Table;

  constructor(
    private messageService: MessageService,
    private hsitorieService: HistorieService // ⚠️ Tippfehler: sollte wahrscheinlich „historieService“ heißen
  ) {}

  ngOnInit() {
    // Beim Laden: Daten abrufen
    this.loadGroupChanges();
  }

  // Abrufen aller Gruppenänderungen vom Backend
  loadGroupChanges() {
    this.hsitorieService.getAllGroupChanges().subscribe({
      next: (data) => {
        console.log(data);
        this.groupChanges.set(data); // Signal aktualisieren
      },
      error: (err) => {
        // Fehlerausgabe mit PrimeNG-Toast
        if (err.error.description) {
          this.messageService.add({ severity: 'warn', summary: err.error.title, detail: err.error.description });
        } else {
          this.messageService.add({ severity: 'warn', summary: "Verbindungsfehler!", detail: "Es gab einen Fehler bei der API-Anfrage." });
        }
      }
    });
  }

  // Globale Filterung für Tabelleninhalt
  onGlobalFilter(table: Table, event: Event) {
    table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  }
}