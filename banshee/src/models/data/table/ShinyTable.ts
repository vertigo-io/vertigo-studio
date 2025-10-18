import { ShinyModel } from '../../ShinyModel';

export interface ShinyTable extends ShinyModel {
  id: string;
  title: string;
  noDataFound: string;
  header: string[];
  rows: string[][];
  sortable: boolean;
  sortColumn: number;
  sortDirection: string;
}
