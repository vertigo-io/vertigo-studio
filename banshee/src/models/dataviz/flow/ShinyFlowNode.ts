// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

import { NodeType } from './NodeType';

export interface ShinyFlowNode {
  id: string;
  label: string;
  position: { x: number; y: number };
  nodeType: NodeType; 
}
