import { ShinyComponent } from '../../ShinyComponent';
import type { ShinyOrganizationNode } from "./ShinyOrganizationNode";

export interface ShinyOrganization extends ShinyComponent{
    nodes: ShinyOrganizationNode[];
}
