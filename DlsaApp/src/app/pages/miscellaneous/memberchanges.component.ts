/**
 * MemberChangesComponent – Anzeige historischer Änderungen an Mitgliederdaten
 *
 * Funktionen:
 * - Holt Mitgliedsänderungen vom Backend und zeigt sie tabellarisch an
 * - Nutzt PrimeNG-Komponenten für UI und Benutzerinteraktion
 * - Unterstützung für globale Filterung (Suche)
 * - Verwendung benutzerdefinierter Pipes zur Formatierung von Spalten
 *   (z. B. Spaltennamen übersetzen, Boolean zu „Ja/Nein“)
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
import { HistorieService, MemberChanges } from '../../services/historie.service';
import { ColumnNamePipe } from '../../pipelines/column-name.pipe';
import { BooleanToJaNeinPipe } from '../../pipelines/boolean-to-ja-nein.pipe';

@Component({
  selector: 'app-memberchanges',
  standalone: true,
  imports: [
    // Angular / PrimeNG / Custom Pipes
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
    InputMaskModule,

    // Benutzerdefinierte Pipes
    ColumnNamePipe,
    BooleanToJaNeinPipe
  ],
  templateUrl: `./memberchanges.component.html`,
  providers: [MessageService, ConfirmationService]
})
export class MemberChangesComponent {

  // Signal zur Speicherung der Änderungen (reaktiv)
  memberChanges = signal<MemberChanges[]>([]);

  // Referenz zur PrimeNG-Tabelle
  @ViewChild('dt') dt!: Table;

  constructor(
    private messageService: MessageService,
    private hsitorieService: HistorieService // ❗ Tippfehler: sollte „historieService“ heißen
  ) { }

  ngOnInit() {
    // Daten beim Initialisieren laden
    this.loadMemberChanges();
  }

  // Änderungen von Mitgliedern vom Server laden
  loadMemberChanges() {
    this.hsitorieService.getAllMemberChanges().subscribe({
      next: (data) => {
        console.log(data); // Debug-Ausgabe (kann später entfernt werden)
        this.memberChanges.set(data);
      },
      error: (err) => {
        // Fehlerbehandlung mit PrimeNG-Toast
        if (err.error.description) {
          this.messageService.add({
            severity: 'warn',
            summary: err.error.title,
            detail: err.error.description
          });
        } else {
          this.messageService.add({
            severity: 'warn',
            summary: "Verbindungsfehler!",
            detail: "Es gab einen Fehler bei der API-Anfrage."
          });
        }
      }
    });
  }

  // Globale Filterfunktion für Tabelle
  onGlobalFilter(table: Table, event: Event) {
    table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  }
}