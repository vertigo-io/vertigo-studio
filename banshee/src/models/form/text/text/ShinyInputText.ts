import { ShinyElement } from '../../../ShinyElement';

export interface ShinyInputText extends ShinyElement {
  label: string;
  required: boolean;
  validationPattern?: string;
  suggestions?: string[];
  defaultValue?: string;
  value?: string;
}
