import { ShinyModel } from '../../ShinyModel';
import { ShinyCardFormat } from './ShinyCardFormat';

export interface ShinyCardComponent extends ShinyModel {
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
