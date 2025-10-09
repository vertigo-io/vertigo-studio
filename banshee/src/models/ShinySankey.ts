import { ShinyComponent } from '../ShinyComponent';
import { ShinySankeyLink } from './ShinySankeyLink';

export class ShinySankey implements ShinyComponent {
  title: string;
  data: ShinySankeyLink[];
  type: string = 'sankey';

  constructor(title: string, data: ShinySankeyLink[]) {
    this.title = title;
    this.data = data;
  }
}
