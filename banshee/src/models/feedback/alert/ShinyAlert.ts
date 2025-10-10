import type { ShinyAlertType } from "./ShinyAlertType";

export interface ShinyAlert {
    alertType: ShinyAlertType;
    title?: string;
    content: string;
    closable: boolean;
    icon?: string;
    prominent: boolean;
}
