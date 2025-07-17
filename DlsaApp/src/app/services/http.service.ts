/**
 * HttpService – Zentrale Schnittstelle für HTTP-Anfragen
 *
 * Funktionen:
 * - Wrapper für GET, POST, PUT, DELETE und Datei-Upload
 * - Automatische Integration des JWT-Tokens aus AuthService
 * - Fehlerbehandlung zentral über `catchError`
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
  HttpResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  // Basis-URL der REST-API
  private apiUrl = 'http://127.0.0.1:5005';

  constructor(private http: HttpClient, private authService: AuthService) { }

  /**
   * Erzeugt Standard-Header mit Auth-Token für JSON-Anfragen
   */
  private getHttpHeader() {
    const authToken = this.authService.getToken();
    return {
      headers: new HttpHeaders({
        'Content-type': 'application/json',
        Authorization: `Bearer ${authToken}`
      })
    };
  }

  /**
   * GET-Anfrage für JSON-Daten
   * @param endpoint REST-Endpunkt ohne Basis-URL
   */
  get<T>(endpoint: string): Observable<T> {
    return this.http.get<T>(`${this.apiUrl}/${endpoint}`, this.getHttpHeader())
      .pipe(catchError(this.handleError));
  }

  /**
   * GET-Anfrage für Binärdaten (z. B. Datei-Download)
   */
  getBlob(endpoint: string): Observable<HttpResponse<Blob>> {
    const authToken = this.authService.getToken();
    return this.http.get(`${this.apiUrl}/${endpoint}`, {
      headers: new HttpHeaders({
        Authorization: `Bearer ${authToken}`
      }),
      responseType: 'blob' as 'blob',
      observe: 'response'
    }).pipe(catchError(this.handleError));
  }

  /**
   * POST-Anfrage mit JSON-Body
   */
  post<T>(endpoint: string, body: any): Observable<T> {
    return this.http.post<T>(`${this.apiUrl}/${endpoint}`, body, this.getHttpHeader())
      .pipe(catchError(this.handleError));
  }

  /**
   * POST-Anfrage für Multipart-FormData (z. B. Datei-Upload)
   */
  postMultipart<T>(endpoint: string, body: FormData): Observable<T> {
    const authToken = this.authService.getToken();
    return this.http.post<T>(`${this.apiUrl}/${endpoint}`, body, {
      headers: new HttpHeaders({
        Authorization: `Bearer ${authToken}`
        // Content-Type wird von Browser bei FormData automatisch gesetzt
      })
    }).pipe(catchError(this.handleError));
  }

  /**
   * PUT-Anfrage zur Aktualisierung von Ressourcen
   */
  put<T>(endpoint: string, body: any): Observable<T> {
    return this.http.put<T>(`${this.apiUrl}/${endpoint}`, body, this.getHttpHeader())
      .pipe(catchError(this.handleError));
  }

  /**
   * DELETE-Anfrage zum Entfernen von Ressourcen
   */
  delete<T>(endpoint: string): Observable<T> {
    return this.http.delete<T>(`${this.apiUrl}/${endpoint}`, this.getHttpHeader())
      .pipe(catchError(this.handleError));
  }

  /**
   * Zentrale Fehlerbehandlung
   */
  private handleError(error: HttpErrorResponse) {
    // Hier könnten Logs oder Transformationen folgen
    return throwError(() => error);
  }
}
