import { ShinyComponent } from '../../../ShinyComponent';
import { ShinyChartType } from './ShinyChartType';
import { ShinyChartSerie } from './ShinyChartSerie';

export interface ShinyChart extends ShinyComponent {
  title: string;
  chartType: ShinyChartType;
  labels: string[];
  series: ShinyChartSerie[];
}
