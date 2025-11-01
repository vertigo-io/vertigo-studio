import { ShinyBlock } from '../../ShinyBlock';
import { ShinyCardFormat } from './ShinyCardFormat';

export interface ShinyCardComponent extends ShinyBlock {
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
}
