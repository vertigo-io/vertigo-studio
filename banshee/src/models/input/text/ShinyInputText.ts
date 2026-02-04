// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyBlock } from '../../ShinyBlock';

export interface ShinyInputText extends ShinyBlock {
    label: string;
    required: boolean;
    validationPattern?: string;
    suggestions?: string[];
    defaultValue?: string;
    value?: string;
}
