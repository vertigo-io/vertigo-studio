// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyBlock } from '../../ShinyBlock';
import { ShinyListType } from './ShinyListType';

export interface ShinyList extends ShinyBlock {
  title: string;
  items: (string | ShinyList)[];
  listType: ShinyListType;
  shinyType: string;
}
