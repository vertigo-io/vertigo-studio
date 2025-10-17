import { NodeType } from './NodeType';

export interface ShinyFlowNode {
  id: string;
  label: string;
  position: { x: number; y: number };
  nodeType: NodeType; 
}
