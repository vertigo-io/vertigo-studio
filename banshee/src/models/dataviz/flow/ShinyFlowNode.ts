import { NodeType } from './NodeType'; // Import NodeType

export interface ShinyFlowNode {
  id: string;
  label: string;
  position: { x: number; y: number };
  nodeType: NodeType; // Changed 'type' to 'nodeType' and its type to NodeType
}
