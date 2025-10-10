import { ShinyComponent } from '../../../ShinyComponent';
import { ShinySankeyLink } from './ShinySankeyLink';

export interface ShinySankey extends ShinyComponent {
  title: string;
  data: ShinySankeyLink[];
}
