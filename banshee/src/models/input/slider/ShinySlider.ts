import { ShinyModel } from '../../ShinyModel';

export interface ShinySlider extends ShinyModel {
    label: string;
    min: number;
    max: number;
    step: number;
    value?: number;
    color?: string;
    thumbLabel: boolean;
}
