import { ShinyComponent } from '../ShinyComponent';
import { ShinyChartType } from './ShinyChartType';
import { ShinyChartSerie } from './ShinyChartSerie';

export class ShinyChart implements ShinyComponent {
  title: string;
  chartType: ShinyChartType;
  labels: string[];
  series: ShinyChartSerie[];
  type: string;

  constructor(
    title: string,
    chartType: ShinyChartType,
    labels: string[],
    series: ShinyChartSerie[]
  ) {
    this.title = title;
    this.chartType = chartType;
    this.labels = labels;
    this.series = series;
    switch (chartType) {
      case ShinyChartType.BAR:
        this.type = 'barChart';
        break;
      case ShinyChartType.AREA:
        this.type = 'areaChart';
        break;
      case ShinyChartType.LINE:
        this.type = 'lineChart';
        break;
      case ShinyChartType.RADAR:
        this.type = 'radarChart';
        break;
      case ShinyChartType.DONUT:
        this.type = 'donutChart';
        break;
      case ShinyChartType.PIE:
        this.type = 'pieChart';
        break;
      default:
        this.type = 'chart';
    }
  }
}
