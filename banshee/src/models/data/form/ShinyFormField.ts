import { ShinyFormFieldType } from './ShinyFormFieldType';
import { ShinyFormOption } from './ShinyFormOption';
import { ShinyFormFieldValidator } from './ShinyFormFieldValidator';

export interface ShinyFormField {
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
}
