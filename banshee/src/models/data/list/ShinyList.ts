import { ShinyComponent } from '../../ShinyComponent';
import { ShinyListType } from './ShinyListType';

export interface ShinyList extends ShinyComponent {
  title: string;
  items: (string | ShinyList)[];
  listType: ShinyListType;
  type: string;
}
