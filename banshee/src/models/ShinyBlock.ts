/**
 * ShinyBlock is an interface representing a block of content within the Banshee frontend.
 * It extends ShinyModel, indicating that a block is also a type of Shiny component.
 * Blocks are typically used for structuring layout and grouping other ShinyModels.
 */
import { ShinyModel } from "./ShinyModel";

export interface ShinyBlock extends ShinyModel {
  // Specific properties for blocks if any
}
