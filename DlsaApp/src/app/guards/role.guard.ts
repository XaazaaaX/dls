/**
 * RoleGuard – Zugriffsschutz für Routen basierend auf Benutzerrollen.
 * 
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root' // Der Guard wird global zur Verfügung gestellt
})
export class RoleGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) { }

  /**
   * Prüft, ob der Benutzer die notwendige Rolle hat, um die Route zu aktivieren.
   * 
   * @param route - Die aktuell angeforderte Route
   * @param state - Der aktuelle Router-Status
   * @returns true, wenn Zugriff erlaubt ist; andernfalls wird zur Login-Seite umgeleitet
   */
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const allowedRoles = route.data['roles'] as string[]; // Erlaubte Rollen aus Route-Daten
    const userRole = this.authService.getUserRole();       // Aktuelle Benutzerrolle abrufen

    if (userRole && allowedRoles.includes(userRole)) {
      // Zugriff erlaubt
      return true;
    }

    // Zugriff verweigert – zur Login-Seite umleiten
    this.router.navigate(['/login']);
    return false;
  }
}
