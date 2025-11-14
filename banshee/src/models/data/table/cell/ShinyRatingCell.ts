import { ShinyTableCell } from "./ShinyTableCell";
import { ShinyRatingScale } from "../../../text/rating/ShinyRatingScale";

export interface ShinyRatingCell extends ShinyTableCell {
  id: string;
  value: number;
  scale: ShinyRatingScale;
  allowHalfRating: boolean;
}
