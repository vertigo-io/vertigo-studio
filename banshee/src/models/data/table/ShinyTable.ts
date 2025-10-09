import { ShinyComponent } from '../../../ShinyComponent';

export class ShinyTable implements ShinyComponent {
  id: string;
  title: string;
  noDataFound: string;
  header: string[];
  rows: string[][];
  sortable: boolean;
  sortColumn: number;
  sortDirection: string;
  type: string = 'table';

  constructor(
    id: string,
    title: string,
    noDataFound: string,
    header: string[],
    rows: string[][],
    sortable: boolean,
    sortColumn: number,
    sortDirection: string
  ) {
    this.id = id;
    this.title = title;
    this.noDataFound = noDataFound;
    this.header = header;
    this.rows = rows;
    this.sortable = sortable;
    this.sortColumn = sortColumn;
    this.sortDirection = sortDirection;
  }
}
