import { ShinyBlock } from '../../ShinyBlock';

export interface ShinyPage extends ShinyBlock {
    title: string;
    layout: ShinyBlock;
}
