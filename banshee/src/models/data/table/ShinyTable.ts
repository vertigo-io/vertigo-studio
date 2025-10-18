import { ShinyModel } from '../../ShinyModel';

export interface ShinyTable extends ShinyModel {
  title: string;
  noDataFound: string;
  header: string[];
  rows: string[][];
  sortable: boolean;
  sortColumn: number;
  sortDirection: string;
}
