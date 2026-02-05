// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyBlock } from '../../models/ShinyBlock';
import { ShinyModel } from '../../models/ShinyModel';

export interface ShinyContainer extends ShinyBlock {
  id: string;
  content: ShinyModel[];
}
