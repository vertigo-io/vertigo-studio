import { ShinyComponent } from '../../../ShinyComponent';

export interface ShinyGauge extends ShinyComponent {
  title: string;
  value: number;
  maxValue: number;
}
