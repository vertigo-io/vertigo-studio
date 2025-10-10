import { ShinyComponent } from '../../ShinyComponent';
import type { ShinyChipVariant } from "./ShinyChipVariant";

export interface ShinyChip extends ShinyComponent {
    text: string;
    color?: string;
    variant?: ShinyChipVariant;
    closable: boolean;
    icon?: string;
}
