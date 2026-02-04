// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyBlock } from '../../ShinyBlock';
import { ShinyDataGridColumn } from './ShinyDataGridColumn';

export interface ShinyDataGrid extends ShinyBlock {
    title?: string;
    columns: ShinyDataGridColumn[];
    data: Record<string, any>[];
}
