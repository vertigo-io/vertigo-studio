import { ShinyBlock } from '../../ShinyBlock';
import { ShinyDataGridColumn } from './ShinyDataGridColumn';

export interface ShinyDataGrid extends ShinyBlock {
    title?: string;
    columns: ShinyDataGridColumn[];
    data: Record<string, any>[];
}
