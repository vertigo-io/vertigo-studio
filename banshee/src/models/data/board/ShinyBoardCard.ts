import { ShinyModel } from "../../ShinyModel";

export interface ShinyBoardCard extends ShinyModel {
  id: string;
  title: string;
  description: string;
}
