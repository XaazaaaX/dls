import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';

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

  constructor(private httpService: HttpService) {}

  getSettings(): Observable<Settings> {
      return this.httpService.get<Settings>("settings");
  }

  updateSettings(settings: any): Observable<Settings> {
    return this.httpService.put<Settings>("settings/" + settings.id, settings);
  }
  
}
