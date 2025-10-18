import { ShinyModel } from '../../ShinyModel';
import { ShinyRssItem } from './ShinyRssItem';

export interface ShinyRss extends ShinyModel {
  title: string;
  items: ShinyRssItem[];
}
