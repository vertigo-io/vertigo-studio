import { ShinyElement } from '../../../ShinyElement';
import { ShinyToggleType } from './ShinyToggleType';

export interface ShinyToggle extends ShinyElement {
  label: string;
  value: boolean;
  toggleType: ShinyToggleType;
  onText?: string;
  offText?: string;
  showText: boolean;
}
