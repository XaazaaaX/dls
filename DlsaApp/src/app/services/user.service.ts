import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';


interface UserRequest {
  username: string;
  password: string;
}

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

  getAllUsers(): Observable<User[]> {
      return this.http.get<User[]>(this.apiUrl + "/users", this.getHttpHeader());
  }

  createUser(data: User): Observable<User> {
    return this.http.post<User>(this.apiUrl + "/user", data, this.getHttpHeader());
  }

  deleteUser(userId?: number): Observable<any> {
    return this.http.delete<any>(this.apiUrl + "/users/" + userId, this.getHttpHeader());
  }

  updateUser(user: User): Observable<User> {
    return this.http.put<User>(this.apiUrl + "/users/" + user.id, user,this.getHttpHeader());
  }
  
}
