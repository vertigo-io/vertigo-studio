import { ShinyElement } from '../../ShinyElement';

export interface ShinySlider extends ShinyElement {
    label: string;
    min: number;
    max: number;
    step: number;
    value?: number;
    color?: string;
    thumbLabel: boolean;
}
