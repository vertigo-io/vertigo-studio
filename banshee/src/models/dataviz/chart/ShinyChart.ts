import { ShinyModel } from '../../ShinyModel';
import { ShinyChartType } from './ShinyChartType';
import { ShinyChartSerie } from './ShinyChartSerie';

export interface ShinyChart extends ShinyModel {
  title: string;
  chartType: ShinyChartType;
  labels: string[];
  series: ShinyChartSerie[];
}
