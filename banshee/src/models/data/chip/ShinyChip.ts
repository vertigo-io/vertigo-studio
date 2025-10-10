import type { ShinyChipVariant } from "./ShinyChipVariant";

export interface ShinyChip {
    text: string;
    color?: string;
    variant?: ShinyChipVariant;
    closable: boolean;
    icon?: string;
}
