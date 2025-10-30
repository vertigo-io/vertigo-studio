import { ShinyModel } from '../../ShinyModel';
import { ShinyTableCell } from './cell/ShinyTableCell';

export interface ShinyTable extends ShinyModel {
  title: string;
  noDataFound: string;
  header: string[];
  rows: ShinyTableCell[][];
}
