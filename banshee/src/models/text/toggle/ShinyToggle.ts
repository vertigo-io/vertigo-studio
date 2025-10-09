import { ShinyComponent } from '../../../ShinyComponent';
import { ShinyToggleType } from './ShinyToggleType';

export class ShinyToggle implements ShinyComponent {
  label: string;
  value: boolean;
  toggleType: ShinyToggleType;
  onText?: string;
  offText?: string;
  showText: boolean;
  type: string = 'toggle';

  constructor(
    label: string,
    value: boolean,
    toggleType: ShinyToggleType,
    onText: string,
    offText: string,
    showText: boolean
  ) {
    this.label = label;
    this.value = value;
    this.toggleType = toggleType;
    this.onText = onText;
    this.offText = offText;
    this.showText = showText;
  }
}
