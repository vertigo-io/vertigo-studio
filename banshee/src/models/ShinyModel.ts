// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

/**
 * ShinyModel is the base interface for all Shiny components in the Banshee frontend.
 * It defines common properties that all Shiny models should have.
 */
import { ShinyState } from "./ShinyState";

export interface ShinyModel {
  /**
   * The unique identifier for the Shiny component. Optional unless the component is stateful.
   */
  id?: string;
  /**
   * The type of the Shiny component, typically corresponding to its Java backend class name.
   */
  shinyType: string;
  /**
   * The state of the Shiny component, if it is stateful.
   */
  state?: ShinyState;
}
