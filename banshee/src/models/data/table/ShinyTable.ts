import { ShinyComponent } from '../../../ShinyComponent';

export interface ShinyTable extends ShinyComponent {
  id: string;
  title: string;
  noDataFound: string;
  header: string[];
  rows: string[][];
  sortable: boolean;
  sortColumn: number;
  sortDirection: string;
  type: string;
}
