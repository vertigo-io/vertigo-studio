// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { ShinyBlock } from '../../ShinyBlock';
import { ShinyFlowEdge } from './ShinyFlowEdge';
import { ShinyFlowNode } from './ShinyFlowNode';

export interface ShinyFlow extends ShinyBlock {
  nodes: ShinyFlowNode[];
  edges: ShinyFlowEdge[];
}