import { ShinyModel } from '../../ShinyModel';

export interface ShinyInputText extends ShinyModel {
  label: string;
  required: boolean;
  validationPattern?: string;
  suggestions?: string[];
  defaultValue?: string;
  value?: string;
}
