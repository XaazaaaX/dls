/**
 * UserService – Verwaltung von Benutzerdaten und Rollen
 *
 * Funktionen:
 * - Benutzer abrufen, erstellen, aktualisieren und löschen
 * - Kommuniziert über HttpService mit dem Backend
 * - Wird global bereitgestellt (Singleton)
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';

// Rollenmodell für Benutzerrechte
export interface Role {
  rolename?: string;
}

// Benutzerobjekt mit Rollenbezug
export interface User {
  id?: number;
  username?: string;
  password?: string;
  active?: boolean;
  role: {
    rolename?: string;
  };
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpService: HttpService) {}

  /**
   * Ruft alle Benutzer aus dem Backend ab
   */
  getAllUsers(): Observable<User[]> {
    return this.httpService.get<User[]>("users");
  }

  /**
   * Erstellt einen neuen Benutzer
   * @param data Benutzerdaten
   */
  createUser(data: User): Observable<User> {
    return this.httpService.post<User>("user", data);
  }

  /**
   * Löscht einen Benutzer anhand seiner ID
   * @param userId ID des Benutzers
   */
  deleteUser(userId?: number): Observable<any> {
    return this.httpService.delete<any>("users/" + userId);
  }

  /**
   * Aktualisiert einen bestehenden Benutzer
   * @param user Benutzerobjekt mit Änderungen
   */
  updateUser(user: User): Observable<User> {
    return this.httpService.put<User>("users/" + user.id, user);
  }
}
