import { ShinyComponent } from '../../../ShinyComponent';

export interface ShinyInputText extends ShinyComponent {
  label: string;
  required: boolean;
  validationPattern?: string;
  suggestions?: string[];
  defaultValue?: string;
  value?: string;
  type: string;
}
