import { ShinyModel } from '../../ShinyModel';
import type { ShinyAlertType } from "./ShinyAlertType";

export interface ShinyAlert extends ShinyModel {
    alertType: ShinyAlertType;
    title?: string;
    content: string;
    closable: boolean;
    icon?: string;
    prominent: boolean;
}
