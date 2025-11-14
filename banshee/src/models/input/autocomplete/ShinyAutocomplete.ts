import { ShinyBlock } from '../../ShinyBlock';

export interface ShinyAutocomplete extends ShinyBlock {
    label: string;
    options: string[];
    value?: string;
    placeholder?: string;
}
