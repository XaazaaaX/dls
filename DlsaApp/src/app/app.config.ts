/**
 * appConfig – Zentrale Konfiguration der Angular-Anwendung
 *
 * Funktionen:
 * - Setzt globale Provider für Routing, HTTP, PrimeNG, Animationen, etc.
 * - Definiert Routerverhalten und Ladeverhalten bei gleicher URL
 * - Registriert deutsches Locale
 * - Integriert benutzerdefiniertes PrimeNG-Theme (z. B. Noir)
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { ApplicationConfig, provideZoneChangeDetection, importProvidersFrom } from '@angular/core';
import { provideRouter, withRouterConfig } from '@angular/router';
import { routes } from './app.routes';
import { provideHttpClient } from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { providePrimeNG } from 'primeng/config';
import { registerLocaleData } from '@angular/common';
import de from '@angular/common/locales/de';
import { FormsModule } from '@angular/forms';
import Noir from './../theme'; // Benutzerdefiniertes PrimeNG-Theme (z. B. angepasstes Design)

registerLocaleData(de); // Aktiviert deutsches Zahlen-, Datums-, Währungsformat

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(), // HTTPClient für REST-Kommunikation

    // Optimiert Change Detection durch Event-Zusammenfassung
    provideZoneChangeDetection({ eventCoalescing: true }),

    // Router konfigurieren mit Neuladung bei identischer URL
    provideRouter(routes, withRouterConfig({ onSameUrlNavigation: 'reload' })),

    // Asynchrone Animationsbereitstellung (verbessert Ladezeit)
    provideAnimationsAsync(),

    // PrimeNG-Konfiguration mit benutzerdefiniertem Theme
    providePrimeNG({
      theme: {
        preset: Noir, // z. B. importiertes Theme-Objekt aus theme.ts
        options: {
          darkModeSelector: false // Deaktiviert systembasiertes Dark Mode Switching
        }
      }
    }),

    // FormsModule als globaler Import (Template-driven Forms)
    importProvidersFrom(FormsModule),

    // Doppelt: optionaler Cleanup
    // provideAnimationsAsync(), // <- bereits oben vorhanden (kann entfernt werden)
  ]
};
