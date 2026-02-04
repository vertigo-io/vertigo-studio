// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyTableCell } from "./ShinyTableCell";
import { ShinyRatingScale } from "../../../text/rating/ShinyRatingScale";

export interface ShinyRatingCell extends ShinyTableCell {
  id: string;
  value: number;
  scale: ShinyRatingScale;
  allowHalfRating: boolean;
}
