import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

export interface Settings {
  id?: number;
  ageFrom?: number;
  ageTo?: number;
  bookingMethod?: String;
  clearing?: boolean;
  costDls?: number;
  countDls?: number;
  dueDate?: Date;
  granularity?: String;
}

@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  private apiUrl = 'http://127.0.0.1:5005'; // Ersetze mit deiner API-URL

  constructor(private http: HttpClient, private authService:AuthService) {
  }

  private getHttpHeader() {
    let authToken = this.authService.getToken();
    return {
      headers: new HttpHeaders({
        "Content-type": "application/json",
        Authorization: `Bearer ${authToken}`
      })
    };
  }

  getSettings(): Observable<Settings> {
      return this.http.get<Settings>(this.apiUrl + "/settings", this.getHttpHeader());
  }

  updateSettings(settings: any): Observable<Settings> {
    return this.http.put<Settings>(this.apiUrl + "/settings/" + settings.id, settings,this.getHttpHeader());
  }
  
}
