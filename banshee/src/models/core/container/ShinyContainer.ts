import { ShinyLayout } from '../../ShinyLayout';
import { ShinyModel } from '../../ShinyModel';

export interface ShinyContainer extends ShinyLayout {
  content: ShinyModel[];
}
