// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

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
