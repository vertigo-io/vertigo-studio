import { ShinyBlock } from '../../ShinyBlock';
import { ShinyChartType } from './ShinyChartType';
import { ShinyChartSerie } from './ShinyChartSerie';

export interface ShinyChart extends ShinyBlock {
  title: string;
  chartType: ShinyChartType;
  labels: string[];
  series: ShinyChartSerie[];
}
