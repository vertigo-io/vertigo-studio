import { ShinyModel } from '../../../ShinyModel';
import { ShinyToggleType } from './ShinyToggleType';

export interface ShinyToggle extends ShinyModel {
  label: string;
  value: boolean;
  toggleType: ShinyToggleType;
  onText?: string;
  offText?: string;
  showText: boolean;
}
