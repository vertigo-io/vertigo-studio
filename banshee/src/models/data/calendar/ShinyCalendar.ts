import { ShinyComponent } from '../../../ShinyComponent';

export class ShinyCalendar implements ShinyComponent {
  year: number;
  month: number;
  type: string = 'calendar';

  constructor(year: number, month: number) {
    this.year = year;
    this.month = month;
  }
}
