/**
 * AuthService – Authentifizierungsdienst für Login, Token-Handling und Rollenlogik
 *
 * Funktionen:
 * - Login über API mit JWT
 * - Speichern des Tokens im LocalStorage
 * - Extraktion von Benutzerrolle und Benutzername
 * - Prüfung von Authentifizierung, Rollen und Token-Gültigkeit
 * - Logout
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { jwtDecode } from 'jwt-decode';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';

// Anmeldedaten (Login-Formular)
interface LoginRequest {
  username: string;
  password: string;
}

// Antwort vom Server bei erfolgreichem Login
interface LoginResponse {
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authUrl = environment.apiUrl + '/auth/login'; // API-Login-Endpunkt
  private tokenKey = 'authToken';                        // Key für LocalStorage

  constructor(private http: HttpClient, private router: Router) {}

  /**
   * Führt den Login durch und speichert das JWT im LocalStorage
   */
  login(data: LoginRequest): Observable<LoginResponse> {
    return this.http.post(this.authUrl, data).pipe(
      tap((response: any) => {
        if (response.token) {
          localStorage.setItem(this.tokenKey, response.token);
        }
      })
    );
  }

  /**
   * Gibt die Benutzerrolle aus dem JWT zurück
   */
  getUserRole(): string | null {
    const token = this.getToken();
    if (!token) return null;

    const decodedToken: any = jwtDecode(token);
    return decodedToken?.role || null;
  }

  /**
   * Gibt den Benutzernamen (subject) aus dem JWT zurück
   */
  getUserName(): string | null {
    const token = this.getToken();
    if (!token) return null;

    const decodedToken: any = jwtDecode(token);
    return decodedToken?.sub || null;
  }

  /**
   * Gibt zurück, ob der Benutzer Administrator ist
   */
  isAdmin(): boolean {
    return ['Administrator'].includes(this.getUserRole() ?? '');
  }

  /**
   * Gibt zurück, ob der Benutzer mindestens Benutzer ist
   */
  isUser(): boolean {
    return ['Benutzer', 'Administrator'].includes(this.getUserRole() ?? '');
  }

  /**
   * Gibt zurück, ob der Benutzer mindestens Gast ist
   */
  isGuest(): boolean {
    return ['Gast', 'Benutzer', 'Administrator'].includes(this.getUserRole() ?? '');
  }

  /**
   * Löscht den Token und leitet zur Login-Seite weiter
   */
  logout(): void {
    localStorage.removeItem(this.tokenKey);
    this.router.navigate(['/login']);
  }

  /**
   * Gibt den gespeicherten JWT zurück (oder null)
   */
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  /**
   * Prüft, ob ein gültiger (nicht abgelaufener) Token vorliegt
   */
  isAuthenticated(): boolean {
    const token = this.getToken();

    if (token) {
      if (!this.isTokenExpired(token)) {
        console.log('Token not expired');
        return true;
      } else {
        console.log('Token expired');
        this.logout();
        return false;
      }
    }

    return false;
  }

  /**
   * Überprüft, ob der JWT abgelaufen ist
   * @param token - JWT-Token
   */
  isTokenExpired(token: string): boolean {
    const decodedToken = JSON.parse(atob(token.split('.')[1])); // Payload extrahieren
    const expirationDate = decodedToken.exp * 1000;             // UNIX-Timestamp in ms
    return expirationDate < Date.now();
  }

  /**
   * Gibt zurück, ob überhaupt ein Token vorhanden ist
   */
  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
