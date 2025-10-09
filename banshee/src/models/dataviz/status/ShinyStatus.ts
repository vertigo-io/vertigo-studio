import { ShinyComponent } from '../../../ShinyComponent';
import { ShinyStatusType } from './ShinyStatusType';

export class ShinyStatus implements ShinyComponent {
  title: string;
  types: ShinyStatusType[];
  type: string = 'status';

  constructor(title: string, types: ShinyStatusType[]) {
    this.title = title;
    this.types = types;
  }
}
