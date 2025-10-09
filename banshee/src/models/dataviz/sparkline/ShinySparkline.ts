import { ShinyComponent } from '../../../ShinyComponent';

export class ShinySparkline implements ShinyComponent {
  title: string;
  values: number[];
  type: string = 'sparkLine';

  constructor(title: string, values: number[]) {
    this.title = title;
    this.values = values;
  }
}
