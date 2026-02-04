// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyBlock } from '../../ShinyBlock';

export type ShinyToggleType = 'TOGGLE' | 'CHECK' | 'SWITCH' | 'LIGHT' | 'BATTERY' | 'STATUS' | 'THUMBS' | 'ARROW' | 'STAR' | 'CLASSIC';

export interface ShinyToggle extends ShinyBlock {
    label: string;
    value: boolean;
    toggleType: ShinyToggleType;
    onText?: string;
    offText?: string;
    showText: boolean;
}
