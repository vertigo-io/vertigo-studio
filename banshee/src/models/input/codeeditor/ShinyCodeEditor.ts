import { ShinyBlock } from '../../ShinyBlock';

export interface ShinyCodeEditor extends ShinyBlock {
    language: string;
    content: string;
}
