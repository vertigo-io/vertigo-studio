import { ShinyModel } from '../../ShinyModel';
import type { ShinyOrganizationNode } from "./ShinyOrganizationNode";

export interface ShinyOrganization extends ShinyModel{
    nodes: ShinyOrganizationNode[];
}
