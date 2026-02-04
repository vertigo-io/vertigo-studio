// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyBlock } from '../../ShinyBlock';
import { ShinyTableCell } from './cell/ShinyTableCell';

export interface ShinyTable extends ShinyBlock {
  title: string;
  noDataFound: string;
  header: string[];
  rows: ShinyTableCell[][];
}
