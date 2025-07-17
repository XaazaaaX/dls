/**
 * BooleanToJaNeinPipe – Konvertiert boolesche Werte oder Strings in „Ja“ bzw. „Nein“
 *
 * Einsatz:
 * - Wird in Templates verwendet, um z. B. `true` als „Ja“ und `false` als „Nein“ anzuzeigen
 * - Unterstützt auch String-Eingaben wie "true"/"false"
 *
 * Beispiel:
 *   {{ true | booleanToJaNein }}     → Ja
 *   {{ "false" | booleanToJaNein }}  → Nein
 *
 * Autor: Benito Ernst
 * Datum: 05/2025
 */

import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'booleanToJaNein',
  standalone: true
})
export class BooleanToJaNeinPipe implements PipeTransform {
  transform(value: string | boolean): string {
    // Wandelt true / 'true' in "Ja", alles andere in "Nein" um
    return value === 'true' || value === true ? 'Ja' : 'Nein';
  }
}