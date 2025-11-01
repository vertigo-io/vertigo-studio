import { ShinyBlock } from '../../ShinyBlock';
import { ShinySankeyLink } from './ShinySankeyLink';

export interface ShinySankey extends ShinyBlock {
  title: string;
  data: ShinySankeyLink[];
}
