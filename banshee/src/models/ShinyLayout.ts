import { ShinyModel } from "./ShinyModel";
import { ShinyBlock } from "./ShinyBlock";

export interface ShinyLayout extends ShinyModel {
  // A layout contains other blocks or layouts
  content: (ShinyBlock | ShinyLayout)[];
}
