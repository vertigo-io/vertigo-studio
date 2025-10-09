import { ShinyFormFieldType } from './ShinyFormFieldType';
import { ShinyFormOption } from './ShinyFormOption';
import { ShinyFormFieldValidator } from './ShinyFormFieldValidator';

export class ShinyFormField {
  name: string;
  label: string;
  type: ShinyFormFieldType;
  value?: any;
  required: boolean;
  placeholder?: string;
  helpText?: string;
  defaultValue?: any;
  options?: ShinyFormOption[];
  validator?: ShinyFormFieldValidator;
  readOnly: boolean;

  constructor(
    name: string,
    label: string,
    type: ShinyFormFieldType,
    value: any,
    required: boolean,
    placeholder: string,
    helpText: string,
    defaultValue: any,
    options: ShinyFormOption[],
    validator: ShinyFormFieldValidator,
    readOnly: boolean
  ) {
    this.name = name;
    this.label = label;
    this.type = type;
    this.value = value;
    this.required = required;
    this.placeholder = placeholder;
    this.helpText = helpText;
    this.defaultValue = defaultValue;
    this.options = options;
    this.validator = validator;
    this.readOnly = readOnly;
  }
}
