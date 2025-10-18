import { ShinyProp } from "./ShinyProp";

export interface ShinyModel {
  id : string|undefined ;
  shinyType: string;
  props?: ShinyProp[];
}
