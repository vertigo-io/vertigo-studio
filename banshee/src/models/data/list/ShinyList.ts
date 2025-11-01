import { ShinyBlock } from '../../ShinyBlock';
import { ShinyListType } from './ShinyListType';

export interface ShinyList extends ShinyBlock {
  title: string;
  items: (string | ShinyList)[];
  listType: ShinyListType;
  shinyType: string;
}
