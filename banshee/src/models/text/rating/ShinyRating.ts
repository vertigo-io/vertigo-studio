import { ShinyElement } from '../../ShinyElement';
import { ShinyRatingScale, getShinyRatingMaxValue } from './ShinyRatingScale';
import { ShinyState } from '../../ShinyState';

export class ShinyRating implements ShinyElement {
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
