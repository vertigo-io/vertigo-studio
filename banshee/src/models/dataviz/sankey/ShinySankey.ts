import { ShinyModel } from '../../ShinyModel';
import { ShinySankeyLink } from './ShinySankeyLink';

export interface ShinySankey extends ShinyModel {
  title: string;
  data: ShinySankeyLink[];
}
