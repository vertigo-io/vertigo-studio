import { ShinyModel } from '../../ShinyModel';

export interface ShinySparkline extends ShinyModel {
  title: string;
  values: number[];
}
