import { ShinyBlock } from '../../ShinyBlock';
import { ShinyTableCell } from './cell/ShinyTableCell';

export interface ShinyTable extends ShinyBlock {
  title: string;
  noDataFound: string;
  header: string[];
  rows: ShinyTableCell[][];
}
