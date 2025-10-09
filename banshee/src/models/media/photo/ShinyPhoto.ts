import { ShinyComponent } from '../../../ShinyComponent';

export interface ShinyPhoto extends ShinyComponent {
  title?: string;
  url: string;
  alt?: string;
  type: string;
}
