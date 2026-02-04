// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyModel } from '../../ShinyModel';
import type { ShinyChipVariant } from "./ShinyChipVariant";

export interface ShinyChip extends ShinyModel {
    text: string;
    color?: string;
    variant?: ShinyChipVariant;
    closable: boolean;
    icon?: string;
}
