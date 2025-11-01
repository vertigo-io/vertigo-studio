import { ShinyBlock } from '../../ShinyBlock';

export interface ShinyGauge extends ShinyBlock {
  title: string;
  value: number;
  maxValue: number;
}
