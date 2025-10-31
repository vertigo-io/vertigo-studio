import { ShinyElement } from '../../ShinyElement';
import type { ShinyChipVariant } from "./ShinyChipVariant";

export interface ShinyChip extends ShinyElement {
    text: string;
    color?: string;
    variant?: ShinyChipVariant;
    closable: boolean;
    icon?: string;
}
