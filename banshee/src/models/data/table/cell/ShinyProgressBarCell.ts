import { ShinyTableCell } from "./ShinyTableCell";

export interface ShinyProgressBarCell extends ShinyTableCell {
  id: string;
  value: number;
  maxValue: number;
  color: string;
}
