import { ShinyComponent } from '../../../ShinyComponent';
import { ShinyToggleType } from './ShinyToggleType';

export interface ShinyToggle extends ShinyComponent {
  label: string;
  value: boolean;
  toggleType: ShinyToggleType;
  onText?: string;
  offText?: string;
  showText: boolean;
  type: string;
}
