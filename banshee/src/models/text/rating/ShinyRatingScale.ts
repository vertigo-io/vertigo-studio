// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

export enum ShinyRatingScale {
  SCALE_5 = 'SCALE_5',
  SCALE_10 = 'SCALE_10',
  SCALE_100 = 'SCALE_100',
}

export function getShinyRatingMaxValue(scale: ShinyRatingScale): number {
  switch (scale) {
    case ShinyRatingScale.SCALE_5:
      return 5;
    case ShinyRatingScale.SCALE_10:
      return 10;
    case ShinyRatingScale.SCALE_100:
      return 100;
    default:
      return 5;
  }
}
