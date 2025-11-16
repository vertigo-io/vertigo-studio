import { ShinyBlock } from '../../ShinyBlock';
import type { ShinyAlertType } from "./ShinyAlertType";

export interface ShinyAlert extends ShinyBlock {
    alertType: ShinyAlertType;
    title?: string;
    content: string;
    closable: boolean;
    icon?: string;
    prominent: boolean;
}
