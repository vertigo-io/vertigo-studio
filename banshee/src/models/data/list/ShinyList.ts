import { ShinyModel } from '../../ShinyModel';
import { ShinyListType } from './ShinyListType';

export interface ShinyList extends ShinyModel {
  title: string;
  items: (string | ShinyList)[];
  listType: ShinyListType;
  type: string;
}
