import { ShinyElement } from '../../ShinyElement';
import { ShinyStatusType } from './ShinyStatusType';

export interface ShinyStatus extends ShinyElement {
  title: string;
  types: ShinyStatusType[];
}
