import { ShinyComponent } from '../../../ShinyComponent';

export class ShinyInputText implements ShinyComponent {
  label: string;
  required: boolean;
  validationPattern?: string;
  suggestions?: string[];
  defaultValue?: string;
  value?: string;
  type: string = 'inputText';

  constructor(
    label: string,
    required: boolean,
    validationPattern?: string,
    suggestions?: string[],
    defaultValue?: string,
    value?: string
  ) {
    this.label = label;
    this.required = required;
    this.validationPattern = validationPattern;
    this.suggestions = suggestions;
    this.defaultValue = defaultValue;
    this.value = value;
  }
}
