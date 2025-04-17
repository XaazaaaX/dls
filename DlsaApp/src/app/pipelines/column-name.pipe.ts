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

    return map[value] || value;
  }
}
