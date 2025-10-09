import { ShinyComponent } from '../../../ShinyComponent';
import { ShinyFormSection } from './ShinyFormSection';

export class ShinyForm implements ShinyComponent {
  title: string;
  sections: ShinyFormSection[];
  type: string = 'form';

  constructor(title: string, sections: ShinyFormSection[]) {
    this.title = title;
    this.sections = sections;
  }
}
