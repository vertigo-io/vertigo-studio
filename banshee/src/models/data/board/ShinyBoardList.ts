// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyModel } from "../../ShinyModel";
import { ShinyBoardCard } from "./ShinyBoardCard";

export interface ShinyBoardList extends ShinyModel {
  id: string;
  name: string;
  position: number;
  color: string;
  cards: ShinyBoardCard[];
}
