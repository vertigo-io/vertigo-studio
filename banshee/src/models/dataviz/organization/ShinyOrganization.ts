// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyBlock } from '../../ShinyBlock';
import type { ShinyOrganizationNode } from "./ShinyOrganizationNode";

export interface ShinyOrganization extends ShinyBlock{
    nodes: ShinyOrganizationNode[];
}
