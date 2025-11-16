import { ShinyBlock } from '../../ShinyBlock';
import { ShinyModel } from '../../ShinyModel';

export interface ShinyContainer extends ShinyBlock {
  id?: string;
  content: ShinyModel[];
}
