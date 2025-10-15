import { ShinyComponent } from '../../ShinyComponent';
import { ShinyRatingScale, getShinyRatingMaxValue } from './ShinyRatingScale';

export class ShinyRating implements ShinyComponent {
  label: string;
  value: number;
  scale: ShinyRatingScale;
  customMaxValue: number;
  showValue: boolean;
  showPercentage: boolean;
  showBox: boolean;
  separator: string;
  allowHalfRating: boolean;
  type: string = 'rating';

  constructor(
    label: string,
    value: number,
    scale: ShinyRatingScale,
    customMaxValue: number,
    showValue: boolean,
    showPercentage: boolean,
    showBox: boolean,
    separator: string,
    allowHalfRating: boolean
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
  }

  getMaxValue(): number {
    return this.customMaxValue !== -1 ? this.customMaxValue : getShinyRatingMaxValue(this.scale);
  }
}
