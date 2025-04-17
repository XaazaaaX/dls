import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'booleanToJaNein',
  standalone: true
})
export class BooleanToJaNeinPipe implements PipeTransform {
  transform(value: string | boolean): string {
    return value === 'true' || value === true ? 'Ja' : 'Nein';
  }
}