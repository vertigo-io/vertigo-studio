import { ShinyComponent } from '../../../ShinyComponent';
import { ShinyCardFormat } from './ShinyCardFormat';

export interface ShinyCardComponent extends ShinyComponent {
  title: string;
  subtitle?: string;
  description?: string;
  imageUrl?: string;
  imageAlt?: string;
  link?: string;
  icon?: string;
  badgeLabel?: string;
  badgeColor?: string;
  format: ShinyCardFormat;
  type: string;
}
