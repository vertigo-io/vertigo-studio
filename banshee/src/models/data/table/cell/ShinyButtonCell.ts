import { ShinyTableCell } from "./ShinyTableCell";

export interface ShinyButtonCell extends ShinyTableCell {
  id: string;
  text: string;
  color: string;
  action: string;
}
