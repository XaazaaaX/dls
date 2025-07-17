import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';

export interface Role {
  rolename?: string;
}

export interface User {
  id?: number;
  username?: string;
  password?: string;
  active?: boolean;
  role: {
    rolename?: string
  };
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpService: HttpService) {
  }

  getAllUsers(): Observable<User[]> {
      return this.httpService.get<User[]>("users");
  }

  createUser(data: User): Observable<User> {
    return this.httpService.post<User>("user", data);
  }

  deleteUser(userId?: number): Observable<any> {
    return this.httpService.delete<any>("users/" + userId);
  }

  updateUser(user: User): Observable<User> {
    return this.httpService.put<User>("users/" + user.id, user);
  }
  
}
