import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';

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
    id?: string;
    surname?: string;
    forename?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ActionService {

  constructor(private httpService: HttpService) {}

  getAllActions(): Observable<Action[]> {
      return this.httpService.get<Action[]>("actions");
  }

  createActions(action: ActionDto): Observable<Action> {
    return this.httpService.post<Action>("action", action);
  }

  updateAction(action: ActionDto, id: number): Observable<Action> {
    return this.httpService.put<Action>("actions/" + id, action);
  }
}