import { ShinyModel } from '../../ShinyModel';
import { ShinyStatusType } from './ShinyStatusType';

export interface ShinyStatus extends ShinyModel {
  title: string;
  types: ShinyStatusType[];
}
