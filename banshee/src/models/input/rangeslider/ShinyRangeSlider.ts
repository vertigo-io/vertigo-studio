import { ShinyBlock } from '../../ShinyBlock';

export interface ShinyRangeSlider extends ShinyBlock {
    label: string;
    min: number;
    max: number;
    step: number;
    value: number[];
    color?: string;
    thumbLabel?: boolean;
}
