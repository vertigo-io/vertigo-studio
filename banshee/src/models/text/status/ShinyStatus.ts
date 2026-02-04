// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyModel } from '../../ShinyModel';
import { ShinyStatusType } from './ShinyStatusType';

export interface ShinyStatus extends ShinyModel {
  title: string;
  types: ShinyStatusType[];
}
