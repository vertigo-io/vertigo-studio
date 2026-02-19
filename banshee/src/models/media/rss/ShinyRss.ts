// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyBlock } from '../../ShinyBlock';
import { ShinyRssItem } from './ShinyRssItem';

export interface ShinyRss extends ShinyBlock {
  title?: string;
  items: ShinyRssItem[];
}
