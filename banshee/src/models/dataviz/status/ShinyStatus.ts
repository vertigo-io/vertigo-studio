import { ShinyComponent } from '../../../ShinyComponent';
import { ShinyStatusType } from './ShinyStatusType';

export interface ShinyStatus extends ShinyComponent {
  title: string;
  types: ShinyStatusType[];
  type: string;
}
