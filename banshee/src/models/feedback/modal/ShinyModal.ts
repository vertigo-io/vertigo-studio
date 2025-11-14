import { ShinyBlock } from '../../ShinyBlock';

export interface ShinyModal extends ShinyBlock {
    title: string;
    content: ShinyBlock;
    persistent: boolean;
}
