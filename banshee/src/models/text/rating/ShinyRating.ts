// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyModel } from '../../ShinyModel';
import { ShinyRatingScale, getShinyRatingMaxValue } from './ShinyRatingScale';
import { ShinyState } from '../../ShinyState';

export class ShinyRating implements ShinyModel {
  id?: string;
  shinyType: string = 'rating';
  state?: ShinyState;

  label: string;
  value: number;
  scale: ShinyRatingScale;
  customMaxValue: number;
  showValue: boolean;
  showPercentage: boolean;
  showBox: boolean;
  separator: string;
  allowHalfRating: boolean;

  constructor(
    label: string,
    value: number,
    scale: ShinyRatingScale,
    customMaxValue: number,
    showValue: boolean,
    showPercentage: boolean,
    showBox: boolean,
    separator: string,
    allowHalfRating: boolean,
    id?: string,
    state?: ShinyState
  ) {
    this.label = label;
    this.value = value;
    this.scale = scale;
    this.customMaxValue = customMaxValue;
    this.showValue = showValue;
    this.showPercentage = showPercentage;
    this.showBox = showBox;
    this.separator = separator;
    this.allowHalfRating = allowHalfRating;
    this.id = id;
    this.state = state;
  }

  getMaxValue(): number {
    return this.customMaxValue !== -1 ? this.customMaxValue : getShinyRatingMaxValue(this.scale);
  }
}
