import { ShinyBlock } from '../../ShinyBlock';

export interface ShinyInputText extends ShinyBlock {
    label: string;
    required: boolean;
    validationPattern?: string;
    suggestions?: string[];
    defaultValue?: string;
    value?: string;
}
