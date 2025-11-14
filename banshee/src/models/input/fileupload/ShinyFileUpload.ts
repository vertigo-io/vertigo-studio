import { ShinyBlock } from '../../ShinyBlock';

export interface ShinyFileUpload extends ShinyBlock {
    label: string;
    multiple: boolean;
    accept?: string;
}
