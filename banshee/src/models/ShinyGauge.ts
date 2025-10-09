import { ShinyComponent } from '../ShinyComponent';

export class ShinyGauge implements ShinyComponent {
  title: string;
  value: number;
  maxValue: number;
  type: string = 'gauge';

  constructor(title: string, value: number, maxValue: number) {
    this.title = title;
    this.value = value;
    this.maxValue = maxValue;
  }
}
