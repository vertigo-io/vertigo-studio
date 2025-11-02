import { ShinyModel } from "../../ShinyModel";
import { ShinyBoardCard } from "./ShinyBoardCard";

export interface ShinyBoardList extends ShinyModel {
  id: string;
  name: string;
  position: number;
  color: string;
  cards: ShinyBoardCard[];
}
