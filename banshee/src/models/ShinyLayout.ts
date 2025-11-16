import { ShinyModel } from "./ShinyModel";
import { ShinyBlock } from "./ShinyBlock";

export interface ShinyLayout extends ShinyModel {
  content: (ShinyBlock )[];
}
