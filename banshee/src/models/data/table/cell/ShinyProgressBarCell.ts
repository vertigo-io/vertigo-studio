// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyTableCell } from "./ShinyTableCell";

export interface ShinyProgressBarCell extends ShinyTableCell {
  id: string;
  value: number;
  maxValue: number;
  color: string;
}
