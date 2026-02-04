// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

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
