// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

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
