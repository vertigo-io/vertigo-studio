import { ShinyLayout } from '../../ShinyLayout';
import { ShinyBlock } from '../../ShinyBlock';
import { ShinyElement } from '../../ShinyElement';

export interface ShinyContainer extends ShinyLayout {
  content: (ShinyBlock | ShinyElement | ShinyLayout)[];
}
