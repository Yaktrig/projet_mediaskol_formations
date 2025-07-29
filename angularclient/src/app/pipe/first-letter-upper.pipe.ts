import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'firstLetterUpper',
  standalone: true,
})
export class FirstLetterUpperPipe implements PipeTransform {

  transform(value: string | null | undefined): string {
    if (!value) {
      return '';
    }
    // récupère la première lettre en majuscule
    return value.charAt(0).toUpperCase();
  }
}
