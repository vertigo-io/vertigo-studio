import { ShinyBlock } from '../../ShinyBlock';
import { ShinyFormSection } from './ShinyFormSection';

export interface ShinyForm extends ShinyBlock {
  title: string;
  sections: ShinyFormSection[];
}
