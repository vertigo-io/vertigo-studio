import { ShinyBlock } from '../../ShinyBlock';

export interface ShinyDatePicker extends ShinyBlock {
    label: string;
    value?: string;
    required: boolean;
}
