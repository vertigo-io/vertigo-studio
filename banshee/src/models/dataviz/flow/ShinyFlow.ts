import { ShinyFlowEdge } from './ShinyFlowEdge';
import { ShinyFlowNode } from './ShinyFlowNode';

export interface ShinyFlow {
  nodes: ShinyFlowNode[];
  edges: ShinyFlowEdge[];
}