import { ShinyBlock } from '../../ShinyBlock';
import { ShinyRssItem } from './ShinyRssItem';

export interface ShinyRss extends ShinyBlock {
  title: string;
  items: ShinyRssItem[];
}
