import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';  // Beispiel für einen AuthService

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  private apiUrl = 'http://127.0.0.1:5005';  // Basis-URL für die API

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHttpHeader() {
    let authToken = this.authService.getToken();  // Authentifizierungs-Token abrufen
    return {
      headers: new HttpHeaders({
        "Content-type": "application/json",
        Authorization: `Bearer ${authToken}`
      })
    };
  }

  // GET-Anfrage
  get<T>(endpoint: string): Observable<T> {
    return this.http.get<T>(`${this.apiUrl}/${endpoint}`, this.getHttpHeader())
      .pipe(catchError(this.handleError));  // Fehlerbehandlung hinzufügen
  }

  // GET-Anfrage für Blobs (z. B. Dateien)
  getBlob(endpoint: string): Observable<HttpResponse<Blob>> {
    let authToken = this.authService.getToken();
    return this.http.get(`${this.apiUrl}/${endpoint}`, {
      headers: new HttpHeaders({
        Authorization: `Bearer ${authToken}`
      }),
      responseType: 'blob',
      observe: 'response'
    }).pipe(catchError(this.handleError));
  }

  // POST-Anfrage
  post<T>(endpoint: string, body: any): Observable<T> {
    return this.http.post<T>(`${this.apiUrl}/${endpoint}`, body, this.getHttpHeader())
      .pipe(catchError(this.handleError));
  }

  // POST-Anfrage für Multipart
  postMultipart<T>(endpoint: string, body: any): Observable<T> {
    let authToken = this.authService.getToken();
    return this.http.post<T>(`${this.apiUrl}/${endpoint}`, body, {
      headers: new HttpHeaders({
        Authorization: `Bearer ${authToken}`
      })
    }).pipe(catchError(this.handleError));
  }

  // PUT-Anfrage
  put<T>(endpoint: string, body: any): Observable<T> {
    return this.http.put<T>(`${this.apiUrl}/${endpoint}`, body, this.getHttpHeader())
      .pipe(catchError(this.handleError));
  }

  // DELETE-Anfrage
  delete<T>(endpoint: string): Observable<T> {
    return this.http.delete<T>(`${this.apiUrl}/${endpoint}`, this.getHttpHeader())
      .pipe(catchError(this.handleError));
  }

  // Fehlerbehandlung
  private handleError(error: HttpErrorResponse) {
    console.log(error);
    //return throwError(() => ({error: {title: "Verbindungsfehler", description: "Es gab einen Fehler bei der API-Anfrage."}}));
    return throwError(() => error);
  }
}