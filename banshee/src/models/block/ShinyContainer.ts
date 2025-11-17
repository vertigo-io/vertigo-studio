import { ShinyBlock } from '../../models/ShinyBlock';
import { ShinyModel } from '../../models/ShinyModel';

export interface ShinyContainer extends ShinyBlock {
  id?: string;
  content: ShinyModel[];
}
