import { ShinyFormField } from './ShinyFormField';

export interface ShinyFormSection {
  title: string;
  fields: ShinyFormField[];
  collapsible: boolean;
  initiallyCollapsed: boolean;
}
