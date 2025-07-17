import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  private apiUrl = 'http://127.0.0.1:5005';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHttpHeader() {
    let authToken = this.authService.getToken();
    return {
      headers: new HttpHeaders({
        "Content-type": "application/json",
        Authorization: `Bearer ${authToken}`
      })
    };
  }

  get<T>(endpoint: string): Observable<T> {
    return this.http.get<T>(`${this.apiUrl}/${endpoint}`, this.getHttpHeader())
      .pipe(catchError(this.handleError));
  }

  getBlob(endpoint: string): Observable<HttpResponse<Blob>> {
    let authToken = this.authService.getToken();
    return this.http.get(`${this.apiUrl}/${endpoint}`, {
      headers: new HttpHeaders({
        Authorization: `Bearer ${authToken}`
      }),
      responseType: 'blob' as 'blob',
      observe: 'response'
    }).pipe(catchError(this.handleError));
  }

  post<T>(endpoint: string, body: any): Observable<T> {
    return this.http.post<T>(`${this.apiUrl}/${endpoint}`, body, this.getHttpHeader())
      .pipe(catchError(this.handleError));
  }

  postMultipart<T>(endpoint: string, body: any): Observable<T> {
    let authToken = this.authService.getToken();
    return this.http.post<T>(`${this.apiUrl}/${endpoint}`, body, {
      headers: new HttpHeaders({
        Authorization: `Bearer ${authToken}`
      })
    }).pipe(catchError(this.handleError));
  }

  put<T>(endpoint: string, body: any): Observable<T> {
    return this.http.put<T>(`${this.apiUrl}/${endpoint}`, body, this.getHttpHeader())
      .pipe(catchError(this.handleError));
  }

  delete<T>(endpoint: string): Observable<T> {
    return this.http.delete<T>(`${this.apiUrl}/${endpoint}`, this.getHttpHeader())
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(() => error);
  }
}