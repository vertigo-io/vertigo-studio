// WARNING: DO NOT EDIT MANUALLY
// This file is the TypeScript representation of a Java model.
// Any changes must be made in the corresponding Java file first.

export interface ShinyMindMapNode {
  id: string;
  topic: string;
  background?: string;
  foreground?: string;
  direction: 'left' | 'right';
  expanded: boolean; 
  children: ShinyMindMapNode[];
}
