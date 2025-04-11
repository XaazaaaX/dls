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
    memberId?: number;
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

  /*
  createActions(categories: Category[]): Observable<Category[]> {
    return this.http.post<Category[]>(this.apiUrl + "/sectors", categories, this.getHttpHeader());
  }

  updateAction(category: Category): Observable<Category> {
    return this.http.put<Category>(this.apiUrl + "/sectors/" + category.id, category,this.getHttpHeader());
  }
    */
}