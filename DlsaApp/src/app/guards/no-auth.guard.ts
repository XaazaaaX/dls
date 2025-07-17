/**
 * NoAuthGuard – Verhindert den Zugriff auf bestimmte Routen für bereits authentifizierte Benutzer.
 * Beispiel: Verhindert den Zugriff auf Login- oder Registrierungsseiten, wenn der Nutzer schon eingeloggt ist.
 * 
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root' // Macht den Guard in der gesamten App verfügbar
})
export class NoAuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  /**
   * Verhindert den Zugriff auf die Route, wenn der Benutzer bereits angemeldet ist.
   * 
   * @returns true, wenn der Benutzer **nicht** authentifiziert ist (also Zugriff erlaubt);
   *          false, wenn der Benutzer eingeloggt ist (und zur Dashboard-Seite umgeleitet wird).
   */
  canActivate(): boolean {
    if (this.authService.isAuthenticated()) {
      // Benutzer ist bereits angemeldet → Umleitung zum Dashboard
      this.router.navigate(['/dashboard']);
      return false;
    }

    // Benutzer ist **nicht** angemeldet → Zugriff auf die Route erlauben
    return true;
  }
}
