import { ShinyModel } from '../../ShinyModel';

export interface ShinyGauge extends ShinyModel {
  title: string;
  value: number;
  maxValue: number;
}
