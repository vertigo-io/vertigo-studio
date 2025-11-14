import { ShinyBlock } from '../../ShinyBlock';
import { ShinyTab } from './ShinyTab';

export interface ShinyTabs extends ShinyBlock {
    tabs: ShinyTab[];
}
