// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyBlock } from '../../ShinyBlock';

export interface ShinyRangeSlider extends ShinyBlock {
    label: string;
    min: number;
    max: number;
    step: number;
    value: number[];
    color?: string;
    thumbLabel: boolean;
}
