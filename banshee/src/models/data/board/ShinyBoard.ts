import { ShinyBlock } from "../../ShinyBlock";
import { ShinyBoardList } from "./ShinyBoardList";

export interface ShinyBoard extends ShinyBlock {
  id: string;
  name: string;
  description: string;
  backgroundColor: string;
  lists: ShinyBoardList[];
}
