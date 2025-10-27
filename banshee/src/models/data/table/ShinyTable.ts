import { ShinyModel } from '../../ShinyModel';
import { ShinyTableCell } from './cell/ShinyTableCell';
import { ShinyTableState } from './ShinyTableState';

export interface ShinyTable extends ShinyModel {
  title: string;
  noDataFound: string;
  header: string[];
  rows: ShinyTableCell[][];
  state?: ShinyTableState;
}
