import { ShinyProp } from "./ShinyProp";

export interface ShinyModel {
  id : string ;
  shinyType: string;
  props?: ShinyProp[];
}
