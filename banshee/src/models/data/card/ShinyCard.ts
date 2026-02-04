// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyBlock } from '../../ShinyBlock';
import { ShinyCardFormat } from './ShinyCardFormat';

export interface ShinyCard extends ShinyBlock {
  id: string;
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
