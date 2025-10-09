import { ShinyFormField } from './ShinyFormField';

export class ShinyFormSection {
  title: string;
  fields: ShinyFormField[];
  collapsible: boolean;
  initiallyCollapsed: boolean;

  constructor(
    title: string,
    fields: ShinyFormField[],
    collapsible: boolean,
    initiallyCollapsed: boolean
  ) {
    this.title = title;
    this.fields = fields;
    this.collapsible = collapsible;
    this.initiallyCollapsed = initiallyCollapsed;
  }
}
