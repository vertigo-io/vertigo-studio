import { ShinyElement } from "../../ShinyElement";
import { ShinyBoardCard } from "./ShinyBoardCard";

export interface ShinyBoardList extends ShinyElement {
  id: string;
  name: string;
  position: number;
  color: string;
  cards: ShinyBoardCard[];
}
