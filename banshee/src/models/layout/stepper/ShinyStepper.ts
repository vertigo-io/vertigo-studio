import { ShinyBlock } from '../../ShinyBlock';
import { ShinyStep } from './ShinyStep';

export interface ShinyStepper extends ShinyBlock {
    steps: ShinyStep[];
}
