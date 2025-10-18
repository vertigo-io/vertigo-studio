import { ShinyModel } from '../../ShinyModel';
import type { ShinyChipVariant } from "./ShinyChipVariant";

export interface ShinyChip extends ShinyModel {
    text: string;
    color?: string;
    variant?: ShinyChipVariant;
    closable: boolean;
    icon?: string;
}
