import { ShinyComponent } from '../../../ShinyComponent';

export interface ShinySparkline extends ShinyComponent {
  title: string;
  values: number[];
}
