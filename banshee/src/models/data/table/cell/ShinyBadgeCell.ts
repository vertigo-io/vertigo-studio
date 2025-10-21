import { ShinyTableCell } from "./ShinyTableCell";

export interface ShinyBadgeCell extends ShinyTableCell {
  id: string;
  content: string;
  color: string;
}
