import { ShinyModel } from '../../ShinyModel';
import { ShinyFormSection } from './ShinyFormSection';

export interface ShinyForm extends ShinyModel {
  title: string;
  sections: ShinyFormSection[];
}
