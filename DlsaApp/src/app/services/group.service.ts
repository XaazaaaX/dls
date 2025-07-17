import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpService } from './http.service';

export interface Group {
  id?: number;
  groupName?: string;
  liberated?: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  constructor(private httpService: HttpService) {}

  getAllGroups(): Observable<Group[]> {
    return this.httpService.get<Group[]>("groups");
  }

  createGroup(group: Group): Observable<Group> {
    return this.httpService.post<Group>("group", group);
  }

  updateGroup(group: Group): Observable<Group> {
    return this.httpService.put<Group>("groups/" + group.id, group);
  }
}