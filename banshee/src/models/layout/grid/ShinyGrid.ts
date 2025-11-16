import { ShinyLayout } from "../../ShinyLayout";
import { ShinyModel } from "../../ShinyModel";

export interface ShinyGrid extends ShinyLayout {
  columns: number;
  content: ShinyModel[];
}
