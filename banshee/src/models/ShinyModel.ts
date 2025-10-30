import { ShinyState } from "./ShinyState";

export interface ShinyModel {
  id?: string;
  shinyType: string;
  state?: ShinyState;
}
