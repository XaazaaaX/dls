import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';

export interface Category {
  id?: number;
  categoryName?: string;
}

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private httpService: HttpService) {}

  getAllCategories(): Observable<Category[]> {
      return this.httpService.get<Category[]>("categories");
  }

  createCategory(category: Category): Observable<Category> {
    return this.httpService.post<Category>("category", category);
  }

  updateCategory(category: Category): Observable<Category> {
    return this.httpService.put<Category>("categories/" + category.id, category);
  }
}
