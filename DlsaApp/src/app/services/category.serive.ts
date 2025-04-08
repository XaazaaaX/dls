import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

export interface Category {
  id?: number;
  categoryName?: string;
}

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

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

  getAllCategories(): Observable<Category[]> {
      return this.http.get<Category[]>(this.apiUrl + "/categories", this.getHttpHeader());
  }

  createSettings(categories: Category[]): Observable<Category[]> {
    return this.http.post<Category[]>(this.apiUrl + "/categories", categories, this.getHttpHeader());
  }

  updateSettings(category: Category): Observable<Category> {
    return this.http.put<Category>(this.apiUrl + "/categories/" + category.id, category,this.getHttpHeader());
  }

  /*
  deleteCategory(categoryId?: number): Observable<any> {
    return this.http.delete<any>(this.apiUrl + "/categories/" + categoryId, this.getHttpHeader());
  }
    */
}
