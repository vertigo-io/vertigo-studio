// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyTableCell } from "./ShinyTableCell";
import { ShinyChipVariant } from "../../../text/chip/ShinyChipVariant";

export interface ShinyChipCell extends ShinyTableCell {
  id: string;
  text: string;
  color?: string;
  variant?: ShinyChipVariant;
  closable?: boolean;
  icon?: string;
}
