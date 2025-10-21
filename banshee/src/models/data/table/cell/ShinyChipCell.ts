import { ShinyTableCell } from "./ShinyTableCell";
import { ShinyChipVariant } from "../../chip/ShinyChipVariant";

export interface ShinyChipCell extends ShinyTableCell {
  id: string;
  text: string;
  color: string;
  variant: ShinyChipVariant;
  closable: boolean;
  icon: string;
}
