import { ShinyComponent } from '../../../ShinyComponent';
import { ShinyRssItem } from './ShinyRssItem';

export interface ShinyRss extends ShinyComponent {
  title: string;
  items: ShinyRssItem[];
}
