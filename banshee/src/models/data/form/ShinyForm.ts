import { ShinyComponent } from '../../ShinyComponent';
import { ShinyFormSection } from './ShinyFormSection';

export interface ShinyForm extends ShinyComponent {
  title: string;
  sections: ShinyFormSection[];
}
