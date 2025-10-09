import { ShinyComponent } from '../ShinyComponent';
import { ShinyListType } from './ShinyListType';

export class ShinyList implements ShinyComponent {
  title: string;
  items: (string | ShinyList)[];
  listType: ShinyListType;
  type: string = 'list';

  constructor(title: string, items: (string | ShinyList)[], listType: ShinyListType) {
    this.title = title;
    this.items = items;
    this.listType = listType;
  }
}
