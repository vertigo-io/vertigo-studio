// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyBlock } from '../../ShinyBlock';
import { ShinyChartType } from './ShinyChartType';
import { ShinyChartSerie } from './ShinyChartSerie';

export interface ShinyChart extends ShinyBlock {
  title: string;
  chartType: ShinyChartType;
  labels: string[];
  series: ShinyChartSerie[];
}
