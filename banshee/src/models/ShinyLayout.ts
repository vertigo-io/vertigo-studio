/**
 * ShinyLayout is an interface representing a component that defines the structure and arrangement
 * of ShinyBlocks within the Banshee frontend. It extends ShinyModel and typically contains a list
 * of ShinyBlock objects that form the layout's content.
 */
import { ShinyModel } from "./ShinyModel";
import { ShinyBlock } from "./ShinyBlock";

export interface ShinyLayout extends ShinyModel {
  /**
   * An array of ShinyBlock objects that constitute the content of this layout.
   */
  content: (ShinyBlock )[];
}
