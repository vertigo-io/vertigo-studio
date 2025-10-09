import { ShinyComponent } from '../../../ShinyComponent';
import { ShinyRssItem } from './ShinyRssItem';

export class ShinyRss implements ShinyComponent {
  title: string;
  items: ShinyRssItem[];
  type: string = 'rss';

  constructor(title: string, items: ShinyRssItem[]) {
    this.title = title;
    this.items = items;
  }
}
