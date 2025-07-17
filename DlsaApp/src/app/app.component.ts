/**
 * AppComponent – Root-Komponente der Angular-Anwendung
 *
 * Funktionen:
 * - Leitet alle Inhalte an die aktive Route weiter
 * - Verwendet Angulars RouterOutlet für dynamisches Laden von Komponenten
 * - Kein eigener View – dient als Platzhalter für Seitenlayouts (z. B. AppLayout)
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet], // Standalone-RouterOutlet für dynamische Routenanzeige
  template: '<router-outlet></router-outlet>' // Template-Platzhalter für geroutete Komponenten
})
export class AppComponent {
  // Kein zusätzlicher Code erforderlich – Steuerung erfolgt durch Routing
}