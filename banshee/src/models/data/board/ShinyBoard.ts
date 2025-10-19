import { ShinyModel } from "../../ShinyModel";
import { ShinyBoardList } from "./ShinyBoardList";

export interface ShinyBoard extends ShinyModel {
  id: string;
  name: string;
  description: string;
  backgroundColor: string;
  lists: ShinyBoardList[];
}
