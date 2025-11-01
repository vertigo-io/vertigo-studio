import { ShinyBlock } from '../../ShinyBlock';
import { ShinyFlowEdge } from './ShinyFlowEdge';
import { ShinyFlowNode } from './ShinyFlowNode';

export interface ShinyFlow extends ShinyBlock {
  nodes: ShinyFlowNode[];
  edges: ShinyFlowEdge[];
}