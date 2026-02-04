// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyLayout } from "../../ShinyLayout";
import { ShinyModel } from "../../ShinyModel";

export interface ShinyGrid extends ShinyLayout {
  columns: number;
  content: ShinyModel[];
}
