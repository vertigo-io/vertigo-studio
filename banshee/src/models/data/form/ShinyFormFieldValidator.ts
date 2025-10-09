export class ShinyFormFieldValidator {
  pattern?: string;
  minLength?: number;
  maxLength?: number;
  minValue?: any;
  maxValue?: any;

  constructor(
    pattern?: string,
    minLength?: number,
    maxLength?: number,
    minValue?: any,
    maxValue?: any
  ) {
    this.pattern = pattern;
    this.minLength = minLength;
    this.maxLength = maxLength;
    this.minValue = minValue;
    this.maxValue = maxValue;
  }
}
