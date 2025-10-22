import { ShinyModel } from '../../ShinyModel';
import { ShinyFlowEdge } from './ShinyFlowEdge';
import { ShinyFlowNode } from './ShinyFlowNode';

export interface ShinyFlow extends ShinyModel {
  nodes: ShinyFlowNode[];
  edges: ShinyFlowEdge[];
}