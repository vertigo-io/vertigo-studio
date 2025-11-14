import { ShinyBlock } from '../../ShinyBlock';

export interface ShinyStep {
    title: string;
    subtitle?: string;
    content: ShinyBlock;
}
