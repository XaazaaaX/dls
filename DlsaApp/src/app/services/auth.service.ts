import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { jwtDecode } from 'jwt-decode';
import { Router } from '@angular/router';

interface LoginRequest {
  username: string;
  password: string;
}

interface LoginResponse {
  token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authUrl = 'http://localhost:5005/auth/login'; // Ersetze mit deiner API-URL
  private tokenKey = 'authToken';

  constructor(private http: HttpClient, private router: Router) { }

  login(data: LoginRequest): Observable<LoginResponse> {
    return this.http.post(this.authUrl, data).pipe(
      tap((response: any) => {
        if (response.token) {
          localStorage.setItem(this.tokenKey, response.token);
        }
      })
    );
  }

  getUserRole(): string | null {
    const token = this.getToken();
    if (!token) return null;

    const decodedToken: any = jwtDecode(token);
    return decodedToken?.role || null;
  }

  getUserName(): string | null {
    const token = this.getToken();
    if (!token) return null;

    const decodedToken: any = jwtDecode(token);
    return decodedToken?.sub || null;
  }

  isAdmin(): boolean{
    return ["Administrator"].includes(this.getUserRole() ?? '');
  }

  isUser(): boolean{
    return ["Benutzer", "Administrator"].includes(this.getUserRole() ?? '');
  }

  isGuest(): boolean{
    return ["Gast", "Benutzer", "Administrator"].includes(this.getUserRole() ?? '');
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  isAuthenticated(): boolean {
    let token = this.getToken();

    if (token) {

      if (!this.isTokenExpired(token)) {
        console.log("Token not expired");
        return true;
      }
      else
      {
        console.log("Token expired");
        this.logout();
        return false;
      }

    }

    return false;
  }

  isTokenExpired(token: string): boolean {
    const decodedToken = JSON.parse(atob(token.split('.')[1])); // JWT dekodieren
    const expirationDate = decodedToken.exp * 1000; // Ablaufdatum in Millisekunden
    return expirationDate < Date.now(); // Überprüfen, ob der Token abgelaufen ist
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}