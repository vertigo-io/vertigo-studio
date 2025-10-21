import { ShinyTableCell } from "./ShinyTableCell";

export interface ShinyIconCell extends ShinyTableCell {
  id: string;
  icon: string;
  color: string;
  size: string;
}
