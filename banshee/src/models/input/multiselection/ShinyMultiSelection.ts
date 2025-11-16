import { ShinyBlock } from '../../ShinyBlock';

export interface ShinyMultiSelection extends ShinyBlock {
    title: string;
    options: string[];
    selectedIndices: number[];
}
