import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

export interface ActionDto {
    year?: string;
    description?: string;
    contactId?: string;
}

export interface Action {
  id?: number;
  year?: string;
  description?: string;
  contact?: Contact;
}

export interface Contact {
    memberId?: string;
    surname?: string;
    forename?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ActionService {

  private apiUrl = 'http://127.0.0.1:5005';

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

  getAllActions(): Observable<Action[]> {
      return this.http.get<Action[]>(this.apiUrl + "/actions", this.getHttpHeader());
  }

  createActions(action: ActionDto): Observable<Action> {
    return this.http.post<Action>(this.apiUrl + "/action", action, this.getHttpHeader());
  }

  updateAction(action: ActionDto, id: number): Observable<Action> {
    return this.http.put<Action>(this.apiUrl + "/actions/" + id, action,this.getHttpHeader());
  }
}