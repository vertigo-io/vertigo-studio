import { ShinyState } from '../../ShinyState';

export interface ShinyTableState extends ShinyState {
  sortColumn: number;
  sortDirection: string;
  page: number;
  pageCount: number;
}
