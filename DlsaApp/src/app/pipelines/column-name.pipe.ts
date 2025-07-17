/**
 * ColumnNamePipe – Übersetzt interne Spaltennamen in benutzerfreundliche Labels
 *
 * Funktionen:
 * - Wandelt z. B. Datenbank- oder API-Feldnamen in lesbare deutsche Begriffe um
 * - Wird typischerweise in Tabellenköpfen oder Änderungsverläufen verwendet
 *
 * Beispiel:
 *   {{ 'GROUP' | columnName }} → Funktionsgruppe
 *   {{ 'ENTRYDATE' | columnName }} → Eintrittsdatum
 *   {{ 'UNKNOWN_COLUMN' | columnName }} → UNKNOWN_COLUMN (Fällt zurück auf Original)
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  standalone: true,
  name: 'columnName'
})
export class ColumnNamePipe implements PipeTransform {
  transform(value: string): string {
    const map: { [key: string]: string } = {
      GROUP: 'Funktionsgruppe',
      ACTIVE: 'Aktiv',
      ENTRYDATE: 'Eintrittsdatum',
      LEAVINGDATE: 'Austrittsdatum'
    };

    // Gibt den gemappten Namen zurück oder – falls nicht vorhanden – den Originalwert
    return map[value] || value;
  }
}