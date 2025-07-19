/**
 * CategoryService – Verwaltung von Kategorien (z. B. DLS-Klassifizierungen)
 *
 * Funktionen:
 * - Abrufen aller Kategorien
 * - Erstellen und Aktualisieren einzelner Kategorien
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from './http.service';

// Modell einer Kategorie (z. B. „Jugend“, „Verwaltung“, etc.)
export interface Category {
  id?: number;
  categoryName?: string;
}

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private httpService: HttpService) {}

  /**
   * Ruft alle verfügbaren Sparten vom Server ab
   */
  getAllCategories(): Observable<Category[]> {
    return this.httpService.get<Category[]>("categories");
  }

  /**
   * Erstellt eine neue Sparte
   * @param category - Kategorieobjekt mit Name
   */
  createCategory(category: Category): Observable<Category> {
    return this.httpService.post<Category>("category", category);
  }

  /**
   * Aktualisiert eine bestehende Sparte
   * @param category - Kategorieobjekt mit ID und neuem Namen
   */
  updateCategory(category: Category): Observable<Category> {
    return this.httpService.put<Category>("categories/" + category.id, category);
  }

  /**
   * Löscht einen Bereich anhand seiner ID
   * @param sectorId ID der Sparte
   */
  deleteCategory(sectorId?: number): Observable<any> {
    return this.httpService.delete<any>("category/" + sectorId);
  }
}
