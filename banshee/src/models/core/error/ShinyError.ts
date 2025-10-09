import { ShinyComponent } from '../../ShinyComponent';

export interface ShinyError extends ShinyComponent {
  text: string;
  type: string;
}
