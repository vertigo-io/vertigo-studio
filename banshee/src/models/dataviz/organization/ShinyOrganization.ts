import { ShinyBlock } from '../../ShinyBlock';
import type { ShinyOrganizationNode } from "./ShinyOrganizationNode";

export interface ShinyOrganization extends ShinyBlock{
    nodes: ShinyOrganizationNode[];
}
