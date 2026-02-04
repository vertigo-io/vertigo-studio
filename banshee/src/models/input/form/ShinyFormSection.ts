// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyFormField } from './ShinyFormField';

export interface ShinyFormSection {
  title: string;
  fields: ShinyFormField[];
  collapsible: boolean;
  initiallyCollapsed: boolean;
}
