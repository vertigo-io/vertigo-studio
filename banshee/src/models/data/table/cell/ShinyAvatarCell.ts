import { ShinyTableCell } from "./ShinyTableCell";

export interface ShinyAvatarCell extends ShinyTableCell {
  id: string;
  src: string;
  alt: string;
  size: string;
}
